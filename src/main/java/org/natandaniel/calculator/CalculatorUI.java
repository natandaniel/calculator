package org.natandaniel.calculator;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;

public class CalculatorUI extends Application {
  private final Calculator calculator = new Calculator();

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setScene(createCalculatorScene());
    primaryStage.setResizable(false);
    primaryStage.setTitle("Calculator");
    primaryStage.show();
  }

  private Scene createCalculatorScene() {
    Scene scene = new Scene(createCalculatorUI());
    scene.getStylesheets()
         .add(Objects.requireNonNull(CalculatorUI.class.getResource("/calculator.css"))
                     .toExternalForm());
    return scene;
  }

  private VBox createCalculatorUI() {
    HBox display = createCalculatorDisplay();
    GridPane controls = createCalculatorControls(display);

    VBox userInterface = new VBox(display, controls);
    userInterface.setId("user-interface");
    VBox.setVgrow(controls, Priority.ALWAYS);

    return userInterface;
  }

  private HBox createCalculatorDisplay() {
    Label currentValue = new Label("0");
    currentValue.setId("current-value");

    HBox currentValueBox = new HBox(currentValue);
    currentValueBox.setId("current-value-box");

    Label currentExpression = new Label("");
    currentExpression.setId("current-expression");

    HBox currentExpressionBox = new HBox(currentExpression);
    currentExpressionBox.setId("current-expression-box");

    VBox displayStack = new VBox(currentValueBox, currentExpressionBox);
    HBox displayBox = new HBox(displayStack);
    HBox.setHgrow(displayStack, Priority.ALWAYS);
    displayBox.setId("display-box");

    return displayBox;
  }

  private GridPane createCalculatorControls(HBox displayBox) {
    VBox displayStack = getDisplayStack(displayBox);
    Label value = getValueLabel(displayStack);
    Label expression = getExpressionLabel(displayStack);

    GridPane controlsGrid = new GridPane();
    controlsGrid.setId("controls-grid");

    String[][] controlLabels =
        new String[][] {
            { "C", "DEL", "MC", "M+", "M-", "MR" },
            { "rad", "cos", "0", "1", "2", "+" },
            { "sin", "tan", "3", "4", "5", "-" },
            { "exp", "log", "6", "7", "8", "x" },
            { "^", "^2", "9", "∏", ".", "/" },
            { "√", "1/x", "(", ")", "%", "=" } };

    for (int row = 0; row < controlLabels.length; row++)
      for (int col = 0; col < controlLabels[0].length; col++) {
        String label = controlLabels[row][col];

        Button button = new Button(label);
        button.setId(getLabelId(label));
        button.setOnMouseClicked(e -> applyButtonEffect(button, value, expression));
        if (label.equals("rad")) button.getStyleClass().add("radians");

        controlsGrid.add(button, col, row);
      }

    return controlsGrid;
  }

  private void applyButtonEffect(Button clickedButton, Label value, Label expression) {
    String buttonText = clickedButton.getText();

    switch (buttonText) {
      case "C" -> {
        value.setText("0");
        expression.setText("");
      }

      case "DEL" -> {
        if (expression.getText().length() > 0)
          expression.setText(expression.getText().substring(0, expression.getText().length() - 1));
      }

      case "rad" -> {
        calculator.setRadians(!calculator.isRadians());
        clickedButton.getStyleClass().removeAll("degrees", "radians");
        clickedButton.getStyleClass().add(calculator.isRadians() ? "radians" : "degrees");
      }

      case "MC" -> calculator.clearMemory();
      case "M+" -> calculator.addToMemory(Double.parseDouble(value.getText()));
      case "M-" -> calculator.subtractFromMemory(Double.parseDouble(value.getText()));
      case "MR" -> expression.setText(expression.getText() + formatValue(calculator.getMemory()));

      case "exp", "log", "√", "cos", "sin", "tan" -> {
        String text = buttonText + "(";
        expression.setText(expression.getText() + text);
      }

      case "1/x" -> expression.setText("1/(" + expression.getText() + ")");

      case "=" -> {
        try {
          value.setText(formatValue(calculator.compute(expression.getText())));
        }
        catch (Exception e) {
          if (e instanceof ArithmeticException | e instanceof IllegalArgumentException)
            value.setText(e.getMessage());
          else value.setText("ERROR");
        }
      }

      default -> expression.setText(expression.getText() + buttonText);
    }
  }

  private static String formatValue(double value) {
    NumberFormat numFormat = new DecimalFormat("0.########E0");

    if (!String.valueOf(value).contains("E") && value % 1 == 0) {
      long r = Math.round(value);
      return String.valueOf(r).length() > 12 ? numFormat.format(r) : "" + r;
    }
    else {
      String val = String.valueOf(value).length() > 12 ? numFormat.format(value) : "" + value;
      return val.contains("E0") ? val.replaceFirst("E0", "") : val;
    }
  }

  private static VBox getDisplayStack(HBox displayBox) {
    ObservableList<Node> children =
        Objects.requireNonNull(displayBox, "the display box cannot be null").getChildren();

    if (children.isEmpty() || !(children.get(0) instanceof VBox))
      throw new IllegalArgumentException("expected a VBox child node in the display box");

    return (VBox) children.get(0);
  }

  private static Label getValueLabel(VBox displayStack) {
    HBox valueBox = getValueBox(displayStack);

    ObservableList<Node> children =
        Objects.requireNonNull(valueBox, "the value box cannot be null").getChildren();

    if (children.isEmpty() || !(children.get(0) instanceof Label))
      throw new IllegalArgumentException("expected a Label child node in the value box");

    return (Label) children.get(0);
  }

  private static HBox getValueBox(VBox displayStack) {
    return getChildHBox(displayStack, 0);
  }

  private static HBox getChildHBox(VBox displayStack, int index) {
    ObservableList<Node> children =
        Objects.requireNonNull(displayStack, "the display stack cannot be null").getChildren();

    if (index != 0 && index != 1)
      throw new IllegalArgumentException("expected index values are 0 or 1 but got " + index);

    if (children.size() != 2 || !(children.get(index) instanceof HBox))
      throw new IllegalArgumentException("expected an HBox child node in the display stack");

    return (HBox) children.get(index);
  }

  private static Label getExpressionLabel(VBox displayStack) {
    HBox expressionBox = getExpressionBox(displayStack);

    ObservableList<Node> children =
        Objects.requireNonNull(expressionBox, "the expression box cannot be null")
               .getChildren();

    if (children.isEmpty() || !(children.get(0) instanceof Label))
      throw new IllegalArgumentException("expected a Label child node in the expression box");

    return (Label) children.get(0);
  }

  private static HBox getExpressionBox(VBox displayStack) {
    return getChildHBox(displayStack, 1);
  }

  private static String getLabelId(String label) {
    return switch (label) {
      case "MC", "M+", "M-", "MR" -> "memory";
      case "C", "DEL" -> "clear";
      case "exp", "log", "√", "cos", "sin", "tan", "1/x", "^", "^2" -> "function";
      case "+", "-", "x", "/", "%" -> "operator";
      case "(", ")", "." -> "separator";
      case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "∏" -> "number";
      case "=" -> "equals";
      default -> label;
    };
  }

}
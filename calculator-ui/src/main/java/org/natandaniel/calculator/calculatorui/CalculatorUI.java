package org.natandaniel.calculator.calculatorui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class CalculatorUI extends Application {

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

  private static Scene createCalculatorScene() {
    Scene scene = new Scene(createCalculatorUI());
    scene.getStylesheets().add(
        Objects.requireNonNull(CalculatorUI.class.getResource("/calculator.css")).toExternalForm());
    return scene;
  }

  private static VBox createCalculatorUI() {
    HBox display = createCalculatorDisplay();
    GridPane controls = createCalculatorControls();

    VBox userInterface = new VBox(display, controls);
    userInterface.setId("user-interface");
    VBox.setVgrow(controls, Priority.ALWAYS);

    return userInterface;
  }

  private static HBox createCalculatorDisplay() {
    Label currentValue = new Label("0");
    currentValue.setId("current-value");

    HBox currentValueBox = new HBox(currentValue);
    currentValueBox.setId("current-value-box");

    TextField currentExpression = new TextField();
    currentExpression.setPromptText("");
    currentExpression.setId("current-expression");

    HBox currentExpressionBox = new HBox(currentExpression);
    currentExpressionBox.setId("current-expression-box");

    VBox displayStack = new VBox(currentValueBox, currentExpressionBox);
    HBox displayBox = new HBox(displayStack);
    HBox.setHgrow(displayStack, Priority.ALWAYS);
    displayBox.setId("display-box");

    return displayBox;
  }

  private static GridPane createCalculatorControls() {
    GridPane controlsGrid = new GridPane();
    controlsGrid.setId("controls-grid");

    String[][] controlLabels =
        new String[][] {
            { "MC", "M+", "M-", "MR", "C", "<" },
            { "(", ")", "0", "1", "2", "+" },
            { "exp", "log", "3", "4", "5", "-" },
            { "^2", "√", "6", "7", "8", "x" },
            { "^", "1/x", "9", ".", "%", "/" },
            { "Rad", "cos", "sin", "tan", "∏", "=" } };

    for (int row = 0; row < controlLabels.length; row++)
      for (int col = 0; col < controlLabels[0].length; col++) {
        String label = controlLabels[row][col];
        Button button = new Button(label);
        button.setId(getLabelId(label));
        controlsGrid.add(button, col, row);
      }

    return controlsGrid;
  }

  private static String getLabelId(String label) {
    return switch (label) {
      case "+" -> "add";
      case "-" -> "subtract";
      case "x" -> "multiply";
      case "/" -> "divide";
      case "=" -> "equals";
      default -> label;
    };
  }

}
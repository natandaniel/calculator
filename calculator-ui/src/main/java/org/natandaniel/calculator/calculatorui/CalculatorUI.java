package org.natandaniel.calculator.calculatorui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CalculatorUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox mainBox = new VBox();
        Scene scene = new Scene(mainBox, 500, 600);

        HBox displayBox = new HBox();
        Label display = new Label();
        displayBox.getChildren().add(display);

        mainBox.getChildren().add(displayBox);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Calculator");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
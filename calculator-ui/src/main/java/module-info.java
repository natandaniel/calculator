module org.natandaniel.calculator.calculatorui {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.natandaniel.calculator.calculatorui to javafx.fxml;
    exports org.natandaniel.calculator.calculatorui;
}
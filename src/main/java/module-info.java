module org.natandaniel.calculator.calculatorui {
  requires javafx.controls;
  requires javafx.fxml;

  opens org.natandaniel.calculator to javafx.fxml;
  exports org.natandaniel.calculator;
}
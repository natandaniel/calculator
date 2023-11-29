package org.natandaniel.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculatorTest {

  public static final double DELTA = 1e-15;

  @Test
  public void testAddition() {
    Assertions.assertEquals(2,
        new Calculator().compute("1+1"));
  }

  @Test
  public void testSubtraction() {
    Assertions.assertEquals(0, new Calculator().compute("1-1"));
  }

  @Test
  public void testMultiplication() {
    Assertions.assertEquals(4, new Calculator().compute("2*2"));
  }

  @Test
  public void testDivision() {
    Assertions.assertEquals(4 / 3d, new Calculator().compute("4/3"), DELTA);
  }

  @Test
  public void testCosinusInRadians() {
    Assertions.assertEquals(Math.cos(Math.PI / 6),
        new Calculator().compute("cos(∏/6)"), DELTA);
  }

  @Test
  public void testSinusInRadians() {
    Assertions.assertEquals(Math.sin(Math.PI / 3),
        new Calculator().compute("sin(∏/3)"), DELTA);
  }

  @Test
  public void testTangentInRadians() {
    Assertions.assertEquals(Math.tan(Math.PI / 4),
        new Calculator().compute("tan(∏/4)"), DELTA);
  }

  @Test
  public void testExponential() {
    Assertions.assertEquals(Math.exp(3),
        new Calculator().compute("exp(3)"), DELTA);
  }

  @Test
  public void testLogarithm() {
    Assertions.assertEquals(Math.log(5),
        new Calculator().compute("log(5)"), DELTA);
  }

  @Test
  public void testSquareRoot() {
    Assertions.assertEquals(Math.sqrt(6),
        new Calculator().compute("√(6)"), DELTA);
  }

  @Test
  public void testExponent() {
    Assertions.assertEquals(Math.pow(2, 3),
        new Calculator().compute("2^3"), DELTA);
  }

}

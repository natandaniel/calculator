package org.natandaniel.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculatorTest {

  @Test
  public void testAddition() {
    Assertions.assertEquals(2, new Calculator().compute("1+1"));
  }

  @Test
  public void testSubtraction() {
    Assertions.assertEquals(0, new Calculator().compute("1-1"));
  }

  @Test
  public void testMultiplication() {
    Assertions.assertEquals(4, new Calculator().compute("2x2"));
  }

  @Test
  public void testDivision() {
    Assertions.assertEquals(4 / 3d, new Calculator().compute("4/3"));
  }

  @Test
  public void testCosinusWithRadians() {
    Assertions.assertEquals(Math.cos(Math.PI / 6),
        new Calculator().compute("cos(∏/6)"));
  }

  @Test
  public void testSinusWithRadians() {
    Assertions.assertEquals(Math.sin(Math.PI / 3),
        new Calculator().compute("sin(∏/3)"));
  }
  @Test
  public void testTangentWithRadians() {
    Assertions.assertEquals(Math.tan(Math.PI / 4), new Calculator().compute("tan(∏/4)"));
  }

  @Test
  public void testCosinusWithDegrees() {
    Assertions.assertEquals(Math.cos(Math.toRadians(45)), new Calculator(false).compute("cos(45)"));
  }

  @Test
  public void testSinusWithDegrees() {
    Assertions.assertEquals(Math.sin(Math.toRadians(60)), new Calculator(false).compute("sin(60)"));
  }

  @Test
  public void testTangentWithDegrees() {
    Assertions.assertEquals(Math.tan(Math.toRadians(30)), new Calculator(false).compute("tan(30)"));
  }

  @Test
  public void testExponential() {
    Assertions.assertEquals(Math.exp(3), new Calculator().compute("exp(3)"));
  }

  @Test
  public void testLogarithm() {
    Assertions.assertEquals(Math.log(5), new Calculator().compute("log(5)"));
  }

  @Test
  public void testSquareRoot() {
    Assertions.assertEquals(Math.sqrt(6), new Calculator().compute("√(6)"));
  }

  @Test
  public void testExponent() {
    Assertions.assertEquals(Math.pow(2, 3), new Calculator().compute("2^3"));
  }

  @Test
  public void testCombination1() {
    Assertions.assertEquals(
        Math.sqrt(16) + 1 / (3 * Math.exp(2)),
        new Calculator().compute("√(16) + 1 / (3 x exp(2))")
    );
  }

  @Test
  public void testCombination2() {
    Assertions.assertEquals(Math.cos(Math.PI / 4) * (1 + 1 / Math.sqrt(2)),
        new Calculator().compute("cos(∏/4) x (1 + 1 / √(2))")
    );
  }

  @Test
  public void testCombination3() {
    Assertions.assertEquals(Math.pow(2, 3) / (1 + Math.sqrt(9)),
        new Calculator().compute("2^3 / (1 + √(9))")
    );
  }

  @Test
  public void testCombination4() {
    Assertions.assertEquals(Math.exp(1) * Math.sin(Math.PI / 6) / (2 - 1 / Math.sqrt(4)),
        new Calculator().compute("exp(1) x sin(∏/6) / (2 - 1 / √(4))")
    );
  }

  @Test
  public void testCombination5() {
    Assertions.assertEquals(Math.sqrt(25) * 2 / (3 - 1) + 1 / Math.cos(Math.PI / 3),
        new Calculator().compute("√(25) x 2 / (3 - 1) + 1 / cos(∏/3)")
    );
  }

  @Test
  public void testCombination6() {
    Assertions.assertEquals(Math.pow(2, 4) + 1 / (5 * Math.sqrt(25)),
        new Calculator().compute("2^4 + 1 / (5 x √(25))")
    );
  }

  @Test
  public void testCombination7() {
    Assertions.assertEquals(Math.sin(Math.PI / 3) * (1 + 1 / Math.pow(2, 3)),
        new Calculator().compute("sin(∏/3) x (1 + 1 / 2^3)")
    );
  }

  @Test
  public void testCombination8() {
    Assertions.assertEquals(Math.log(100) / (2 + Math.sqrt(16)) + 1 / Math.exp(2),
        new Calculator().compute("log(100) / (2 + √(16)) + 1 / exp(2)")
    );
  }

  @Test
  public void testCombination9() {
    Assertions.assertEquals(Math.pow(2, 3) / (1 + Math.sqrt(9)) - 1 / Math.exp(2),
        new Calculator().compute("2^3 / (1 + √(9)) - 1 / exp(2)")
    );
  }

  @Test
  public void testCombination10() {
    Assertions.assertEquals(Math.cos(Math.PI / 6) + 1 / Math.sqrt(25) * 2 / (3 - 1),
        new Calculator().compute("cos(∏/6) + 1 / √(25) x 2 / (3 - 1)")
    );
  }

}

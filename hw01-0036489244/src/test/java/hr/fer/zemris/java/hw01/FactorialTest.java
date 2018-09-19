package hr.fer.zemris.java.hw01;

import static hr.fer.zemris.java.hw01.Factorial.calculateFactorial;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * JUnit tests for the calculateFactorial method.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class FactorialTest {
  @Test
  public void calculateFactorialTestMinimalValue() {
    assertEquals(1, calculateFactorial(1));
  }

  @Test
  public void calculateFactorialTestMaximalValue() {
    assertEquals(2432902008176640000L, calculateFactorial(20));
  }

  @Test(expected = IllegalArgumentException.class)
  public void calculateFactorialOutOfRange() {
    calculateFactorial(21);
  }

  @Test
  public void calculateFactorialTestValueInRange() {
    assertEquals(120, calculateFactorial(5));
  }

  @Test(expected = IllegalArgumentException.class)
  public void calculateFactorialTestNegativeValue() {
    calculateFactorial(-13);
  }

  @Test
  public void calculateFactorialTestWithZeroValue() {
    assertEquals(1, calculateFactorial(0));
  }
}

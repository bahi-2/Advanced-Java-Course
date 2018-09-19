package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.assertEquals;

import hr.fer.zemris.java.hw02.ComplexNumber;

import org.junit.Test;

/**
 * Junit tests for factory methods.
 * @author Blaz Bagic
 * @version 1.0
 */
public class ComplexNumberTest {
  
  @Test
  public void fromRealTest() {
    assertEquals("3.5+0.0i", ComplexNumber.fromReal(3.5).toString());
  }
  
  @Test
  public void fromImaginaryTest() {
    assertEquals("0.0+2.7i", ComplexNumber.fromImaginary(2.7).toString());
  }
  
  @Test
  public void fromMagnitudeAndAngleTest() {
    assertEquals("0.0+2.0i", ComplexNumber.fromMagnitudeAndAngle(2, Math.PI/2).toString());
  }
}

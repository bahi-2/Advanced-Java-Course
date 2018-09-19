package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Junit tests for {@link Complex}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ComplexTest {

  /** Allowed deviation for double comparison. */
  final double DELTA = 10E-9;

  @Test
  public void multiplyTest() {
    assertEquals(new Complex(-5, 14), new Complex(3, 2).multiply(new Complex(1, 4)));
  }

  @Test
  public void addTest() {
    assertEquals(new Complex(4, 12.1), new Complex(3, 2).add(new Complex(1, 10.1)));
  }

  @Test
  public void subTest() {
    assertEquals(new Complex(11.5, 11.9), new Complex(13, 22).sub(new Complex(1.5, 10.1)));
  }

  @Test
  public void moduleTest() {
    assertEquals(Math.sqrt(4 * 4 + 5 * 5), new Complex(4, 5).module(), DELTA);
  }

  @Test
  public void divideTest() {
    assertEquals(new Complex(6. / 25, 17. / 25), new Complex(3, 2).divide(new Complex(4, -3)));
  }

  @Test
  public void negateTest() {
    assertEquals(new Complex(11, -1), new Complex(-11, 1).negate());
  }

  @Test
  public void powTest() {
    assertEquals(new Complex(-972, -972), new Complex(3, 3).power(5));
    assertEquals(new Complex(1, 5), new Complex(1, 5).power(1));
    assertEquals(new Complex(1, 0), new Complex(1, 0).power(0));
  }

  @Test
  public void rootTest() {
    List<Complex> expected = new ArrayList<Complex>();
    double r = Math.pow(2, 1. / 3);
    
    expected.add(new Complex(r * Math.cos(-Math.PI / 18), r * Math.sin(-Math.PI / 18)));
    expected.add(new Complex(r * Math.cos(11 * Math.PI / 18), r * Math.sin(11 * Math.PI / 18)));
    expected.add(new Complex(r * Math.cos(23 * Math.PI / 18), r * Math.sin(23 * Math.PI / 18)));
    
    assertEquals(expected, new Complex(Math.sqrt(3), -1).root(3));
  }
}

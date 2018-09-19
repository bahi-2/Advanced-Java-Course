package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Junit tests for {@link ComplexPolynomial}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ComplexPolynomialTest {

  @Test
  public void orderTest() {
    assertEquals(0, new ComplexPolynomial(new Complex()).order());
    assertEquals(1, new ComplexPolynomial(new Complex(), new Complex()).order());
  }

  @Test
  public void multiplyTest1() {
    ComplexPolynomial poly1 = new ComplexPolynomial(new Complex(-5, 0), new Complex(4, 0));
    ComplexPolynomial poly2 = new ComplexPolynomial(new Complex(-6, 0), new Complex(3, 0),
        new Complex(2, 0));
    ComplexPolynomial expected = new ComplexPolynomial(new Complex(30, 0), new Complex(-39, 0),
        new Complex(2, 0), new Complex(8, 0));
    ComplexPolynomial actual = poly1.multiply(poly2);
    assertEquals(expected, actual);
  }

  @Test
  public void multiplyTest2() {
    ComplexPolynomial poly1 = new ComplexPolynomial(new Complex(5.2, 1), new Complex(1, 1));
    ComplexPolynomial poly2 = new ComplexPolynomial(new Complex(-1.3, 25), new Complex(2, 3));
    ComplexPolynomial expected = new ComplexPolynomial(new Complex(-31.76, 128.7),
        new Complex(-18.9, +41.3), new Complex(-1, 5));
    ComplexPolynomial actual = poly1.multiply(poly2);
    assertEquals(expected, actual);
  }

  @Test
  public void applyTest() {
    ComplexPolynomial polynomial = new ComplexPolynomial(new Complex(2, 3), new Complex(-1, 4));
    assertEquals(new Complex(-19, 2), polynomial.apply(new Complex(1, 5)));
  }

  @Test
  public void applyTest2() {
    ComplexPolynomial polynomial = new ComplexPolynomial(new Complex(12, 3), new Complex(-11, 23),
        new Complex(-2.3, 4.1));
    assertEquals(new Complex(13, 60), polynomial.apply(new Complex(-7, 1)));
  }

  @Test
  public void deriveTest() {
    Complex z1 = new Complex(1, 0);
    Complex z2 = new Complex(5, 0);
    Complex z3 = new Complex(2, 0);
    Complex z4 = new Complex(7, 2);

    Complex z5 = new Complex(5, 0);
    Complex z6 = new Complex(4, 0);
    Complex z7 = new Complex(21, 6);

    ComplexPolynomial actual = new ComplexPolynomial(z1, z2, z3, z4).derive();
    ComplexPolynomial expected = new ComplexPolynomial(z5, z6, z7);
    assertEquals(expected, actual);
  }

  @Test
  public void deriveTest2() {
    Complex z1 = new Complex(2.3, 4.5);
    Complex z2 = new Complex(-5, 23);
    Complex z3 = new Complex(6, -12);
    Complex z4 = new Complex(2, 4);

    Complex z5 = new Complex(-5, 23);
    Complex z6 = new Complex(12, -24);
    Complex z7 = new Complex(6, 12);

    ComplexPolynomial actual = new ComplexPolynomial(z1, z2, z3, z4).derive();
    ComplexPolynomial expected = new ComplexPolynomial(z5, z6, z7);
    assertEquals(expected, actual);
  }

}

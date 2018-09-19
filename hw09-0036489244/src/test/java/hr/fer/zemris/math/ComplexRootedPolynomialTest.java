package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Junit tests for {@link ComplexRootedPolynomial}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ComplexRootedPolynomialTest {

  @Test
  public void applyTest() {
    ComplexRootedPolynomial polynomial = new ComplexRootedPolynomial(new Complex(2, 3),
        new Complex(-1, 4));
    assertEquals(new Complex(-4, 3), polynomial.apply(new Complex(1, 5)));
  }

  @Test
  public void toStringTest() {
    ComplexRootedPolynomial polynomial = new ComplexRootedPolynomial(new Complex(2, 3),
        new Complex(-1, 4));
    assertEquals("(z-(2.000000 + 3.000000i))*(z-(-1.000000 + 4.000000i))", polynomial.toString());
  }

  @Test
  public void toComplexPolynomTest1() {
    Complex z1 = new Complex(1, 3);
    ComplexPolynomial expected = new ComplexPolynomial(z1.negate(), Complex.ONE);
    ComplexPolynomial actual = new ComplexRootedPolynomial(new Complex[] { z1 }).toComplexPolynom();
    assertEquals(expected, actual);

  }

  @Test
  public void toComplexPolynomTest2() {
    Complex z1 = new Complex(1, 3);
    Complex z2 = new Complex(2, 4);
    ComplexPolynomial expected = new ComplexPolynomial(z1.multiply(z2), z1.add(z2).negate(),
        Complex.ONE);
    ComplexPolynomial actual = new ComplexRootedPolynomial(z1, z2).toComplexPolynom();
    assertEquals(expected, actual);
  }

  @Test
  public void closestRootTest() {
    Complex z = new Complex(1, 1);

    Complex z1 = new Complex(5, 2);
    Complex z2 = new Complex(2, -1);
    Complex z3 = new Complex(-1, 3);
    ComplexRootedPolynomial polynomial = new ComplexRootedPolynomial(z1, z2, z3);
    
    assertEquals(-1, polynomial.indexOfClosestRootFor(z, 1));
    assertEquals(1, polynomial.indexOfClosestRootFor(z, 3.3));
  }

}

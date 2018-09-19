package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.List;

/**
 * Model of a complex number polynomial equation in this form f(z) =
 * (z-z1)*(z-z2)*...*(z-zn), where z1 to zn are it's roots.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ComplexRootedPolynomial {

  /**
   * List of complex numbers which are roots to this polynomial equation.
   */
  private List<Complex> roots;

  /**
   * Constructor which creates a {@link ComplexRootedPolynomial} object out of
   * given roots.
   * 
   * @param roots
   *          roots of the new polynomial equation
   * @throws IllegalArgumentException
   *           in case no roots are given
   */
  public ComplexRootedPolynomial(Complex... roots) {
    if (roots == null || roots.length == 0) {
      throw new IllegalArgumentException("You must specify at least one root.");
    }
    this.roots = Arrays.asList(roots);
  }

  /**
   * Computes the polynomial value at the point z.
   * 
   * @param z
   *          complex point
   * @return the polynomial value at the point z
   */
  public Complex apply(Complex z) {
    Complex result = z.sub(roots.get(0));

    int order = roots.size();
    for (int i = 1; i < order; i++) {
      Complex point = roots.get(i);
      result = result.multiply(z.sub(point));
    }
    return result;
  }

  /**
   * Method returns the index of the closest root for the given complex number
   * that is within the threshold.
   * 
   * @param z
   *          complex number
   * @param threshold
   *          allowed distance between roots and complex number z
   * @return index of the closest root, or -1 if there is no such root
   */
  public int indexOfClosestRootFor(Complex z, double threshold) {
    int length = roots.size();
    double minR = z.sub(roots.get(0)).module();
    int resultingIndex = -1;

    for (int i = 0; i < length; i++) {
      double r = z.sub(roots.get(i)).module();
      if (r <= threshold && r <= minR) {
        minR = r;
        resultingIndex = i;
      }
    }
    return resultingIndex;
  }

  /**
   * Returns a {@link ComplexPolynomial} representation of this polynomial
   * equation.
   * 
   * @return {@link ComplexPolynomial} representation of this polynomial equation
   */
  public ComplexPolynomial toComplexPolynom() {
    ComplexPolynomial result = new ComplexPolynomial(Complex.ONE);
    for (Complex root : roots) {
      result = result.multiply(new ComplexPolynomial(root.negate(), Complex.ONE));
    }

    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int length = roots.size();
    for (int i = 0; i < length; i++) {
      sb.append("(z-(" + roots.get(i) + "))");
      if (i < length - 1) {
        sb.append("*");
      }
    }
    return sb.toString();
  }
}

package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.List;

/**
 * Model of a complex number polynomial in it's general form. (i.e.
 * zn*zn+zn-1*zn-1+...+z2*z2+z1*z+z0)
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ComplexPolynomial {

  /** Internal list of polynomial factors. */
  private List<Complex> factors;

  /**
   * Constructor which creates a {@link ComplexPolynomial} object out of given
   * factors, first factor given being the lowest order factor (e.g. z0 of
   * zn*zn+zn-1*zn-1+...+z2*z2+z1*z+z0) and the last factor being the highest
   * order factor.
   * 
   * @param factors
   *          factors for a polynomial
   * @throws IllegalArgumentException
   *           in case no factors are given
   */
  public ComplexPolynomial(Complex... factors) {
    if (factors == null || factors.length == 0) {
      throw new IllegalArgumentException("You must specify at least one root.");
    }
    this.factors = Arrays.asList(factors);
  }

  /**
   * Returns the order of this polynomial equation.
   * 
   * @return the order of this polynomial equation
   */
  public short order() {
    return (short) (factors.size() - 1);
  }

  /**
   * Returns a new polynomial equation computed by multiplying this one to the one
   * given in the argument.
   * 
   * @param p
   *          other polynomial equation
   * @return new polynomial equation computed by multiplying this one to the one
   *         given in the argument
   */
  public ComplexPolynomial multiply(ComplexPolynomial p) {

    int resultingLength = p.order() * this.order() + 2;

    Complex[] resultingFactors = new Complex[resultingLength];

    for (int i = 0; i < this.order() + 1; i++) {
      for (int j = 0; j < p.order() + 1; j++) {
        if (resultingFactors[i + j] == null) {
          resultingFactors[i + j] = Complex.ZERO;
        }
        resultingFactors[i + j] = resultingFactors[i + j]
            .add(factors.get(i).multiply(p.factors.get(j)));
      }
    }
    return new ComplexPolynomial(resultingFactors);
  }

  /**
   * Returns the first derivative of this polynomial equation.
   * 
   * @return the first derivative of this polynomial equation
   */
  public ComplexPolynomial derive() {
    int length = factors.size();
    Complex[] result = new Complex[length - 1];
    for (int i = 1; i < length; i++) {
      result[i - 1] = factors.get(i).multiply(new Complex(i, 0));
    }
    return new ComplexPolynomial(result);
  }

  /**
   * Computes the polynomial value at the point z.
   * 
   * @param z
   *          complex point
   * @return the polynomial value at the point z
   */
  public Complex apply(Complex z) {
    int length = factors.size();
    Complex result = Complex.ZERO;
    for (int i = 0; i < length; i++) {
      result = result.add(factors.get(i).multiply(z.power(i)));
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int length = factors.size();
    for (int i = length - 1; i >= 0; i--) {
      if (factors.get(i).equals(Complex.ZERO)) {
        continue;
      }
      if (i != length - 1) {
        sb.append(" + ");
      }
      String z;
      if (i == 0) {
        z = "";
      } else if (i == 1) {
        z = "z*";
      } else {
        z = "z^" + i + "*";
      }
      sb.append(z + "(" + factors.get(i) + ")");
    }
    return sb.toString();
  }

  @Override
  public boolean equals(Object other) {

    if (!(other instanceof ComplexPolynomial) || other == null) {
      return false;
    }
    ComplexPolynomial otherPolynomial = (ComplexPolynomial) other;
    return this.factors.equals(otherPolynomial.factors);
  }

}

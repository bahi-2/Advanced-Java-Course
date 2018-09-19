package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a model of a complex number and supports basic arithmetic
 * operations on that number.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class Complex {
  /** Zero valued complex number. */
  public static final Complex ZERO = new Complex(0, 0);
  /** Complex number without the imaginary part. */
  public static final Complex ONE = new Complex(1, 0);
  /** Complex number without the imaginary part, has negative real part. */
  public static final Complex ONE_NEG = new Complex(-1, 0);
  /** Complex number without the real part. */
  public static final Complex IM = new Complex(0, 1);
  /** Complex number without the real part has negative imaginary part. */
  public static final Complex IM_NEG = new Complex(0, -1);
  /** Real part of this complex number. */
  private double real;
  /** Imaginary part of this complex number. */
  private double imaginary;
  /** Angle of this complex number. */
  private double angle;

  /**
   * Default constructor which instantiates a zero valued complex number. (0 + 0i)
   */
  public Complex() {
    this(ZERO.real, ZERO.imaginary);
  }

  /**
   * Constructor which instantiates a complex numbers with given parts.
   * 
   * @param re
   *          real part of this complex number
   * @param im
   *          imaginary part of this complex number
   */
  public Complex(double re, double im) {
    this.real = re;
    this.imaginary = im;
    this.angle = Math.atan2(im, re);
  }

  /**
   * Returns the module of this complex number.
   * 
   * @return the module of this complex number
   */
  public double module() {
    return Math.sqrt(real * real + imaginary * imaginary);
  }

  /**
   * Multiplies this complex number with the one given through the argument.
   * 
   * @param c
   *          other complex to multiply with
   * @return resulting complex number
   */
  public Complex multiply(Complex c) {
    return new Complex(this.real * c.real - this.imaginary * c.imaginary,
        this.imaginary * c.real + this.real * c.imaginary);
  }

  /**
   * Divides this complex number by the one given through the argument.
   * 
   * @param c
   *          other complex to divide by
   * @return resulting complex number
   */
  public Complex divide(Complex c) {
    double denominator = c.real * c.real + c.imaginary * c.imaginary;

    double newReal = (this.real * c.real + this.imaginary * c.imaginary) / denominator;
    double newImaginary = (this.imaginary * c.real - this.real * c.imaginary) / denominator;

    return new Complex(newReal, newImaginary);
  }

  /**
   * Adds this complex number to the one given through the argument.
   * 
   * @param c
   *          other complex to add to this one
   * @return resulting complex number
   */
  public Complex add(Complex c) {
    return new Complex(this.real + c.real, this.imaginary + c.imaginary);
  }

  /**
   * Subtracts the complex number given through the argument from this one.
   * 
   * @param c
   *          other complex with which to subtract
   * @return resulting complex number
   */
  public Complex sub(Complex c) {
    return new Complex(this.real - c.real, this.imaginary - c.imaginary);
  }

  /**
   * Negates this complex number.
   * 
   * @return negated complex number
   */
  public Complex negate() {
    return new Complex(this.real * -1, this.imaginary * -1);
  }

  /**
   * Calculates the n-th power of this complex number.
   * 
   * @param n
   *          power factor
   * @return this complex number powered by n
   * @throws IllegalArgumentException
   *           in case n is negative
   */
  public Complex power(int n) {
    if (n < 0) {
      throw new IllegalArgumentException("N must be a positive number, was: " + n);
    }
    double numerator = Math.pow(module(), n);
    return new Complex(numerator * Math.cos(n * angle), numerator * Math.sin(n * angle));
  }

  /**
   * Calculations based on De Moivre's formula for the roots of a complex number.
   * 
   * @param n
   *          power factor
   * @return list of solutions
   */
  public List<Complex> root(int n) {
    if (n < 0) {
      throw new IllegalArgumentException("N must be a positive number, was: " + n);
    }

    List<Complex> result = new ArrayList<Complex>();
    double r = Math.pow(module(), 1. / n);

    for (int i = 0; i < n; i++) {
      double newReal = r * Math.cos((angle + 2 * Math.PI * i) / n);
      double newImaginary = r * Math.sin((angle + 2 * Math.PI * i) / n);
      result.add(new Complex(newReal, newImaginary));
    }
    return result;
  }

  @Override
  public String toString() {
    if (real == 0 && imaginary == 0) {
      return "0";
    } else if (real == 0) {
      return String.format("%.6fi", imaginary);
    } else if (imaginary == 0) {
      return String.format("%.6f", real);
    }
    
    if (imaginary >= 0) {
      return String.format("%.6f + %.6fi", real, imaginary);
    } else {
      return String.format("%.6f - %.6fi", real, -imaginary);
    }
  }

  @Override
  public boolean equals(Object other) {
    /** Allowed deviation for double comparison. */
    final double DELTA = 10E-9;

    if (!(other instanceof Complex) || other == null) {
      return false;
    }
    Complex otherComplex = (Complex) other;
    if (Math.abs(this.real - otherComplex.real) > DELTA) {
      return false;
    }
    if (Math.abs(this.imaginary - otherComplex.imaginary) > DELTA) {
      return false;
    }
    return true;
  }

}
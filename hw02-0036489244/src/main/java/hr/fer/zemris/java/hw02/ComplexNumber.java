package hr.fer.zemris.java.hw02;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class represents an unmodifiable complex number.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ComplexNumber {
  double real;
  double imaginary;
  double magnitude;
  double angle;

  /**
   * .
   * 
   * @param real
   *          real part
   * @param imaginary
   *          imaginary part
   */
  public ComplexNumber(double real, double imaginary) {
    super();
    this.real = real;
    this.imaginary = imaginary;
  }

  public double getReal() {
    return real;
  }

  public double getImaginary() {
    return imaginary;
  }

  public double getMagnitude() {
    return magnitude;
  }

  public double getAngle() {
    return angle;
  }

  /**
   * Factory method for creating a complex number out of only a real part.
   * @param real
   * @return
   */
  public static ComplexNumber fromReal(double real) {
    return new ComplexNumber(real, 0);
  }
  
  /**
   * Factory method for creating a complex number out of only an imaginary part.
   * @param real
   * @return
   */
  public static ComplexNumber fromImaginary(double imaginary) {
    return new ComplexNumber(0, imaginary);
  }
  
  /**
   * Factory method for creating a complex number out of polar coordinates.
   * @param real
   * @return
   */
  public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
    return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
  }

  /**
   * Method which accepts inputs such as "-3.2", "1-3j", "-2j" and converts them
   * to ComplexNumber objects.
   * 
   * @param s string to parse
   * @return ComplexNumber object
   */
  public static ComplexNumber parse(String s) {
    Matcher matcher = Pattern.compile("[\\-,\\+]?\\d+\\.\\d+").matcher(s);
    String real = "0";
    String imaginary = "0";
    if (matcher.find()) {
      real = matcher.group(0);
      if (matcher.find()) {
        imaginary = matcher.group(0);
      }
    }
    double realNumber = Double.parseDouble(real);
    double imaginaryNumber = Double.parseDouble(imaginary);
    return new ComplexNumber(realNumber, imaginaryNumber);

  }

  public ComplexNumber add(ComplexNumber c) {
    return new ComplexNumber(real + c.real, imaginary + c.imaginary);
  }

  public ComplexNumber sub(ComplexNumber c) {
    return new ComplexNumber(real - c.real, imaginary - c.imaginary);
  }

  public ComplexNumber mul(ComplexNumber c) {
    return new ComplexNumber(real * c.real - imaginary * c.imaginary,
        real * c.imaginary + imaginary * c.real);
  }

  /**
   * Dividing two complex numbers.
   * @param c other complex number
   * @return
   */
  public ComplexNumber div(ComplexNumber c) {
    double divideBy = c.real * c.real + c.imaginary * c.imaginary;
    double newReal = (real * c.real + imaginary * c.imaginary) / divideBy;
    double newImaginary = (imaginary * c.real - real * c.imaginary) / divideBy;
    return new ComplexNumber(newReal, newImaginary);
  }

  /**
   * Calculating the n-th power of the complex number.
   * @param n power factor
   * @return
   */
  public ComplexNumber power(int n) {
    double newReal = Math.pow(real, n) * Math.cos(n * angle);
    double newImaginary = Math.pow(real, n) * Math.sin(n * angle);
    return new ComplexNumber(newReal, newImaginary);
  }

  /**
   * Calculations based on De Moivre's formula for the roots of
   * a complex number.
   * 
   * @param n power factor
   * @return fixed size array
   */
  public ComplexNumber[] root(int n) {
    final int NUMBER_OF_SOLUTINS = 10;
    ComplexNumber[] result = new ComplexNumber[NUMBER_OF_SOLUTINS];
    for (int i = 0; i < NUMBER_OF_SOLUTINS; i++) {
      double newReal = Math.pow(real, 1 / n) * Math.cos(angle + 2 * Math.PI * i / n);
      double newImaginary = Math.pow(real, 1 / n) * Math.sin(angle + 2 * Math.PI * i / n);
      result[i] = new ComplexNumber(newReal, newImaginary);
    }
    return result;
  }

  @Override
  public String toString() {
    if (imaginary >= 0) {
      return String.format("%.1f+%.1fi", real, imaginary);
    } else {
      return String.format("%.1f-%.1fi", real, imaginary);
    }
  }
}

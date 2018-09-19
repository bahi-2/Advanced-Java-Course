package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * Demonstration program for the class ComplexNumber.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ComplexDemo {
  /**
   * Method that tests the output of various methods in the ComplexNumber class.
   * 
   * @param args
   */
  public static void main(String[] args) {
    ComplexNumber c1 = new ComplexNumber(2, 3);
    ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
    ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3)
        .root(2)[1];
    System.out.println(c3);
  }

}

package hr.fer.zemris.java.hw01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Program reads width and height from the user's input and calculates the area
 * and the perimeter of a Rectangle with those parameters.
 * 
 * @author Blaz Bagic
 * @version 2.0
 */
public class Rectangle {

  /**
   * The method invoked when running the program. It reads the width and height of
   * a rectangle from the command line arguments or if they are not present from
   * the standard input. If the input is valid the area and perimeter of the
   * rectangle are printed to the standard output.
   * 
   * @param args
   *          Command line arguments, which can be used to pass the width and
   *          height of the rectangle
   * @throws IOException
   *           Can occur while reading from the standard input.
   */
  public static void main(String[] args) throws IOException {
    double width;
    double height;

    if (args.length != 0) {
      if (args.length == 2) { // assign values from the command line arguments, if they are present
        width = convertToDouble(args[0]);
        height = convertToDouble(args[1]);
      } else {
        throw new IllegalArgumentException(
            "If the command line arguments are present there should be exactly 2, but there were: "
                + args.length + " arguments!");
      }
    } else { // reading from standard input
      width = readFromConsole("Unesite širinu > ");
      height = readFromConsole("Unesite visinu > ");
    }
    System.out.printf("Pravokutnik širine %.1f i visine %.1f ima površinu %.1f te opseg %.1f.",
        width, height, width * height, 2 * (width + height));
  }

  /**
   * Auxiliary method that reads from the standard input until the input is a
   * valid {@code double} value and writes the appropriate message if it isn't.
   * 
   * @param message
   *          The prompt message being printed until the input is correct
   * @return {@code double} value
   * @throws IOException
   *           Can occur while reading from the standard input
   */
  public static double readFromConsole(String message) throws IOException {
    double number;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    do {
      System.out.print(message);
      String line = reader.readLine();
      number = convertToDouble(line);
    } while (number == -1);

    return number;
  }

  /**
   * Auxiliary method that checks if the given string is a real number and parses
   * it to the {@code double} type. It also writes an appropriate message if the
   * input is invalid.
   * 
   * @param string
   *          Text to parse to {@code double}
   * @return Returns the {@code double} value of this text or if it isn't a number
   *         it returns -1
   */
  public static double convertToDouble(String string) {
    Scanner sc = new Scanner(string);
    if (sc.hasNextDouble()) {
      double number = sc.nextDouble();
      sc.close();

      if (number < 0) {
        System.out.println("Rectangle's side can't have a negative value.");
        return -1;
      }
      return number;
      
    } else {
      System.out.printf("'%s' can not be interpreted as a number.\n", string);
      sc.close();
      return -1;
    }

  }
}
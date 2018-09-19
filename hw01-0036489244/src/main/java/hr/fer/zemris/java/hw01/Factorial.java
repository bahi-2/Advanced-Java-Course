package hr.fer.zemris.java.hw01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Program supports reading numbers from the standard input and writing the
 * factorial of the given input to the standard output.
 * 
 * @author Blaz Bagic
 * @version 2.0
 */
public class Factorial {

  /** Valid factorial range constants. **/
  private static final int MAX_NUMBER = 20;
  private static final int MIN_NUMBER = 0;

  /** String constant that matches the end of the program's runtime. */
  private static final String INPUT_END = "end";

  /**
   * Invoked when running the program. Reads from standard input expecting
   * numbers. If the input is a decimal number ranging from 1 to 20 method prints
   * the factorial to the standard output.
   * 
   * @param args
   *          Unused command line arguments
   * @throws IOException
   *           While reading from the standard input.
   */
  public static void main(String[] args) throws IOException {

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
      System.out.print("Enter a number > ");
      String line = reader.readLine().trim();

      if (line.toLowerCase().equals(INPUT_END)) {
        System.out.println("Goodby.");
        break;
      }

      Scanner sc = new Scanner(line);
      if (sc.hasNextInt()) {
        int number = sc.nextInt();
        if (number < 1 || number > 20) {
          System.out.printf("'%d' is not in a valid range [%d, %d].\n", number, MIN_NUMBER,
              MAX_NUMBER);
        } else {
          System.out.printf("%d! = %d\n", number, calculateFactorial(number));
        }
      } else {
        System.out.printf("'%s' is not a decimal number.\n", line);
      }
      
      sc.close();
    }
  }

  /**
   * Recursive method with a complexity of O(n).
   * 
   * @param number
   *          Number on which to perform the factorial operation.
   * @return Factorial of the give number.
   * @throws IllegalArgumentException
   *           in case the number is not in range of [{@value #MIN_NUMBER},
   *           {@value #MAX_NUMBER}]
   */
  public static long calculateFactorial(int number) {
    if (number < MIN_NUMBER || number > MAX_NUMBER) {
      throw new IllegalArgumentException(String.format(
          "Legal arguments are from %d to %d, but was: %d", MIN_NUMBER, MAX_NUMBER, number));
    }
    if (number == 1 || number == 0) {
      return 1;
    }

    return calculateFactorial(number - 1) * number;
  }
}

package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This program evaluates an expression using the ObjectStack.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class StackDemo {
  /**
   * Method that prints to the standard output the evaluation of an expression
   * given through the first argument in the command line.
   * 
   * @param args
   *          Expecting one argument, which represents an expression
   * @throws IllegalArgumentException
   *           if the user doesn't provide exactly 1 argument
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      throw new IllegalArgumentException(
          "Make sure to run this with exactly 1 command line argument");
    }
    String[] elements = args[0].split("\\s+");
    ObjectStack stack = new ObjectStack();
    for (String element : elements) {
      if (element.matches("[\\+,\\-]?[0-9]+")) {
        stack.push(element);
      } else {
        int secondOperand = Integer.parseInt(stack.pop().toString());
        int firstOperand = Integer.parseInt(stack.pop().toString());
        switch (element) {
          case "%":
            stack.push(firstOperand % secondOperand);
            break;
          case "+":
            stack.push(firstOperand + secondOperand);
            break;
          case "-":
            stack.push(firstOperand - secondOperand);
            break;
          case "*":
            stack.push(firstOperand * secondOperand);
            break;
          case "/":
            stack.push((int) firstOperand / secondOperand);
            break;
          default:
            break;
        }
      }
    }
    if (stack.size() != 1) {
      System.err.println("Stack size different than 1");
    } else {
      System.out.println("Expression evaluates to " + stack.pop());
    }
  }
}

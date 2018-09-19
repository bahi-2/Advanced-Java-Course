package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * This is a model of a calculator providing basic methods needed for creating a
 * calculator.
 * 
 * @author Blaz Bagic
 */
public interface CalcModel {

  /**
   * Adds an observer to the current value of this calculator object. Whenever
   * this value changes the observer will be informed by invoking the
   * {@link CalcValueListener#valueChanged(CalcModel)} method.
   * 
   * @param l
   *          observer to add
   */
  void addCalcValueListener(CalcValueListener l);

  /**
   * Removes an observer from this model and stops informing it about value
   * changes.
   * 
   * @param l
   *          observer to remove
   */
  void removeCalcValueListener(CalcValueListener l);

  /**
   * Returns a {@link String} representation of the current value of the
   * calculator.
   * 
   * @return a {@link String} representation of the current value of the
   *         calculator.
   */
  String toString();

  /**
   * Returns a decimal current value in the calculator.
   * 
   * @return a decimal current value in the calculator
   */
  double getValue();

  /**
   * Sets the value of the calculator to the new value.
   * 
   * @param value
   *          new value
   */
  void setValue(double value);

  /**
   * Clears the current calculator value.
   */
  void clear();

  /**
   * Resets the calculator to it's initial state.
   */
  void clearAll();

  /**
   * Inverts the sign of the calculator's current value.
   */
  void swapSign();

  /**
   * Inserts a decimal point to the value of the calculator if it's not already
   * present.
   */
  void insertDecimalPoint();

  /**
   * Appends a digit to the end of the calculator's value.
   * 
   * @param digit
   */
  void insertDigit(int digit);

  /**
   * Returns true if there is an active operand set for a binary operation, false
   * otherwise.
   * 
   * @return true if there is an active operand set for a binary operation, false
   *         otherwise
   */
  boolean isActiveOperandSet();

  /**
   * Returns the active operand for a binary operation.
   * 
   * @return the active operand for a binary operation
   */
  double getActiveOperand();

  /**
   * Sets the active operand to the one given through the argument.
   * 
   * @param activeOperand
   *          new active operand for a binary operation
   */
  void setActiveOperand(double activeOperand);

  /**
   * Unsets the active operand.
   */
  void clearActiveOperand();

  /**
   * Returns the pending binary operation.
   * 
   * @return the pending binary operation
   */
  DoubleBinaryOperator getPendingBinaryOperation();

  /**
   * Sets a new pending binary operation.
   * 
   * @param op
   *          new pending binary operation
   */
  void setPendingBinaryOperation(DoubleBinaryOperator op);
}
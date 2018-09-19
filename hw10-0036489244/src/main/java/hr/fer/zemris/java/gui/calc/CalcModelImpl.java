package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

/**
 * An implementation of the {@link CalcModel}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class CalcModelImpl implements CalcModel {

  /**
   * Current value in the calculator.
   */
  private String currentValue = null;
  /**
   * Stored operand to be used in the next binary operation.
   */
  private double activeOperand;
  /**
   * Indicates if the operand for a binary operation is set.
   */
  private boolean isOperandSet = false;
  /**
   * Pending binary operation to be performed.
   */
  private DoubleBinaryOperator pendingOperation;
  /**
   * Internal list of all observers listening for changes to the current value in
   * the calculator.
   */
  private List<CalcValueListener> observers = new ArrayList<>();

  @Override
  public void addCalcValueListener(CalcValueListener l) {
    observers.add(l);
  }

  @Override
  public void removeCalcValueListener(CalcValueListener l) {
    observers.remove(l);
  }

  @Override
  public double getValue() {
    return currentValue == null ? 0 : Double.parseDouble(currentValue);
  }

  @Override
  public void setValue(double value) {
    if (Double.isNaN(value) || Double.isInfinite(value)) {
    } else {
      currentValue = Double.toString(value);
      informObservers();
    }
  }

  @Override
  public void clear() {
    currentValue = null;
    informObservers();
  }

  @Override
  public void clearAll() {
    currentValue = null;
    informObservers();
    pendingOperation = null;
    activeOperand = 0;
    isOperandSet = false;
  }

  @Override
  public void swapSign() {
    if (currentValue != null) {
      if (currentValue.startsWith("-")) {
        currentValue = currentValue.substring(1);
      } else {
        currentValue = "-" + currentValue;
      }
      informObservers();
    }
  }

  @Override
  public void insertDecimalPoint() {
    if (currentValue != null && !currentValue.contains(".")) {
      currentValue = currentValue.concat(".");
      informObservers();
    }
    if (currentValue == null) {
      currentValue = "0.";
      informObservers();
    }
  }

  @Override
  public void insertDigit(int digit) {
    final int maxDigitCount = 308;
    if (currentValue != null && currentValue.length() >= maxDigitCount) {
      return;
    }

    currentValue = (currentValue == null) ? String.valueOf(digit)
        : currentValue.concat(String.valueOf(digit));
    informObservers();
  }

  @Override
  public boolean isActiveOperandSet() {
    return isOperandSet;
  }

  @Override
  public double getActiveOperand() {
    if (!isActiveOperandSet())
      throw new IllegalStateException("Operand not set.");
    return activeOperand;
  }

  @Override
  public void setActiveOperand(double activeOperand) {
    this.activeOperand = activeOperand;
    isOperandSet = true;
  }

  @Override
  public void clearActiveOperand() {
    activeOperand = 0;
    isOperandSet = false;
  }

  @Override
  public DoubleBinaryOperator getPendingBinaryOperation() {
    if (pendingOperation == null) {
      throw new IllegalStateException("Binary operation not set.");
    }
    return pendingOperation;
  }

  @Override
  public void setPendingBinaryOperation(DoubleBinaryOperator op) {
    pendingOperation = Objects.requireNonNull(op);
  }

  @Override
  public String toString() {
    if (currentValue == null)
      return "0";
    else {
      currentValue = currentValue.replaceFirst("^0+", "0");
      currentValue = currentValue.replaceFirst("^0([1-9])", "$1");
      return currentValue;
    }
  }

  /**
   * Auxiliary method which informs all the observers of a change to the current
   * value of this calculator model.
   */
  private void informObservers() {
    for (CalcValueListener observer : observers) {
      observer.valueChanged(this);
    }
  }

}

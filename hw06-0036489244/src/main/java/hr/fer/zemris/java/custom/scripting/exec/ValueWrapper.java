package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class represents numeric values, and can supports arithmetic operations on
 * those values. Supported object types that can be wrapped into this class are
 * {@link Integer}, {@link Double}, {@link String}, <code>null</code>. Take in
 * consideration that a <code>null</code> reference will be converted to the
 * {@link Integer} value 0, and String will be converted either to Integer, or
 * Double format.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */

public class ValueWrapper {

  /** Wrapped numeric value. */
  private Object value;

  /**
   * Constructor which accepts any objects to wrap.
   * 
   * @param value
   *          value
   */
  public ValueWrapper(Object value) {
    this.value = value;
  }

  /**
   * Adds the argument to the wrapped value.
   * 
   * @param incValue
   *          value to add
   */
  public void add(Object incValue) {
    throwDisallowedValue(incValue);
    performArithmetic(incValue, (a, b) -> a.doubleValue() + b.doubleValue());
  }

  /**
   * Subtracts the wrapped value by the argument.
   * 
   * @param decValue
   *          value by which to subtract
   */
  public void subtract(Object decValue) {
    throwDisallowedValue(decValue);
    performArithmetic(decValue, (a, b) -> a.doubleValue() - b.doubleValue());
  }

  /**
   * Multiplies the wrapped value by the argument.
   * 
   * @param mulValue
   *          value to multiply by
   */
  public void multiply(Object mulValue) {
    throwDisallowedValue(mulValue);
    performArithmetic(mulValue, (a, b) -> a.doubleValue() * b.doubleValue());
  }

  /**
   * Divides the wrapped value by the argument.
   * 
   * @param divValue
   *          value to divide by
   */
  public void divide(Object divValue) {
    throwDisallowedValue(divValue);
    performArithmetic(divValue, (a, b) -> a.doubleValue() / b.doubleValue());
  }

  /**
   * Compares the wrapped value with the argument value and returns a number
   * larger than 0 if the wrapped value is bigger than the argument value, a
   * number smaller than 0 if it's smaller, or 0 if the values are the same.
   * 
   * @param withValue
   *          value to compare with
   * @return a number larger than 0 if the wrapped value is bigger than the
   *         argument value, a number smaller than 0 if it's smaller, or 0 if the
   *         values are the same
   */
  public int numCompare(Object withValue) {
    throwDisallowedValue(withValue);
    if (value == withValue) { // True for null reference or comparing to itself
      return 0;
    }
    Number a = formatToNumber(value);
    Number b = formatToNumber(withValue);
    Double result = a.doubleValue() - b.doubleValue();
    return result.intValue();
  }

  /**
   * Functional interface which should be used for performing an arithmetic
   * operation.
   * 
   * @author Blaz Bagic
   *
   */
  @FunctionalInterface
  private interface Operation {
    /**
     * Performs the arithmetic operation on the parameters and returns the result.
     * 
     * @param a
     *          first parameter
     * @param b
     *          second parameter
     * @return result of the operation
     */
    public Number doOperation(Number a, Number b);
  }

  /**
   * Auxiliary method which performs an arithmetic operation on the wrapped
   * object, and the object from the method parameter.
   * 
   * @param argValue
   *          value for the arithmetic operation
   * @param operation
   *          specific operation to perform
   * 
   */
  private void performArithmetic(Object argValue, Operation operation) {
    Number a = formatToNumber(value);
    Number b = formatToNumber(argValue);
    Number result = operation.doOperation(a, b);
    if (a.getClass() == Integer.class && b.getClass() == Integer.class) {
      value = result.intValue();
    } else {
      value = result.doubleValue();
    }
  }

  /**
   * Parses an {@link Object} to a {@link Double} or {@link Integer} value which
   * both extend {@link Number} that is the type returned here.
   * 
   * @param value
   *          {@link Object} to parse to a numerical type
   * @return a {@link Double} or {@link Integer} type object
   */
  private Number formatToNumber(Object value) {
    if (value == null) {
      return Integer.valueOf(0);
    }
    if (value.getClass() == String.class) {
      try {
        return Integer.parseInt((String) value);
      } catch (NumberFormatException ex) {
        return Double.parseDouble((String) value);
      }
    }
    if (value.getClass() == Integer.class)
      return (Integer) value;
    else
      return (Double) value;
  }

  /**
   * Returns the wrapped value.
   * 
   * @return the wrapped value
   */
  public Object getValue() {
    return value;
  }

  /**
   * Sets the wrapped value to a new value.
   * 
   * @param value
   *          new value to set
   */
  public void setValue(Object value) {
    this.value = value;
  }

  /**
   * Auxiliary method which throws an {@link RuntimeException} in case both given
   * argument and the wrapped value aren't instances of {@link Integer},
   * {@link Double}, {@link String} or a null reference.
   * 
   * @param value
   *          value to check
   * @throws RuntimeException
   *           in case both given argument and the wrapped value aren't instances
   *           of {@link Integer}, {@link Double}, {@link String} or a null
   *           reference.
   */
  private void throwDisallowedValue(Object value) throws RuntimeException {
    //@formatter:off
    if (!( value == null
        || value.getClass() == Integer.class
        || value.getClass() == Double.class
        || value.getClass() == String.class)) {
      throw new RuntimeException(
          "Value must be instance of Integer, Double or String classes or a null reference, was: "
              + value.getClass());
    }
    if (this.value != value) { // Also check the stored value
      throwDisallowedValue(this.value);
    }
  }

}
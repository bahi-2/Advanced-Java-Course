package hr.fer.zemris.java.hw06.observer2;

/**
 * Instances of this class encapsulate information about one change in the
 * {@link IntegerStorage} subject.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class IntegerStorageChange {

  /** The reference to the subject. */
  private IntegerStorage integerStorage;
  /** Value before the change. */
  private int oldValue;
  /** Value after the change. */
  private int newValue;

  /**
   * Constructor which initializes the read-only fields for this objects.
   * 
   * @param integerStorage
   *          reference to the subject
   * @param oldValue
   *          value before the change
   * @param newValue
   *          value after the change
   */
  public IntegerStorageChange(IntegerStorage integerStorage, int oldValue, int newValue) {
    this.integerStorage = integerStorage;
    this.oldValue = oldValue;
    this.newValue = newValue;
  }

  /**
   * Returns the reference to the subject.
   * @return the reference to the subject
   */
  public IntegerStorage getIntegerStorage() {
    return integerStorage;
  }

  /**
   * Returns the value after the change.
   * @return the value after the change
   */
  public int getNewValue() {
    return newValue;
  }

  /**
   * Returns the value before the change.
   * @return the value before the change
   */
  public int getOldValue() {
    return oldValue;
  }
}

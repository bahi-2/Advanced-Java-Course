package hr.fer.zemris.java.custom.collections;

/**
 * Class represents a general collection of objects. None of it's methods are
 * functional.
 *
 * @author Blaz Bagic
 * @version 1.0
 */
public class Collection {

  protected Collection() {
  }

  /**
   * Method determines the size of a collection by looking at it's size.
   * 
   * @return Returns true if collection contains no objects and false otherwise.
   */
  public boolean isEmpty() {
    if (this.size() == 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method that always returns zero. Must be overridden for functionality.
   * 
   * @return Always returns {@code 0}.
   * @Deprecated
   */
  public int size() {
    return 0;
  }

  /**
   * Method that does nothing, must be overridden for functionality.
   * 
   * @param value
   *          Value to add to the collection.
   * @Deprecated
   */
  public void add(Object value) {
  }

  /**
   * Method that always returns {@code false}. Must be overridden for
   * functionality.
   * 
   * @param value
   *          Element value
   * @return Always returns {@code false}.
   * @Deprecated
   */
  public boolean contains(Object value) {
    return false;
  }

  /**
   * Method that always returns {@code false}. Must be overridden for
   * functionality.
   * 
   * @param value
   *          Element value
   * @return Always returns {@code false}.
   * @Deprecated
   */
  public boolean remove(Object value) {
    return false;
  }

  /**
   * Guaranteed to throw an exception.
   * 
   * @throws UnsupportedOperationException
   *           always
   * @CanIgnoreReturnValue
   * @Deprecated
   */
  public Object[] toArray() {
    throw new UnsupportedOperationException();
  }

  /**
   * Does nothing. Must be overridden for functionality.
   * 
   * @param processor
   *          Processor object which provides the process method for each
   *          iteration.
   */
  public void forEach(Processor processor) {
  }

  /**
   * Method adds into the current collection all elements from the given
   * collection.
   * 
   * @param other
   *          Collection from which the elements should be added.
   */
  public void addAll(Collection other) {
    Processor processor = new Processor();
    processor.process(other);
  }

  /**
   * Does nothing. Must be overridden for functionality.
   */
  public void clear() {
  }
}

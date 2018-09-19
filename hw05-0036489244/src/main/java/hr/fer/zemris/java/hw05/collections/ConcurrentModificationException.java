package hr.fer.zemris.java.hw05.collections;

/**
 * This exception occurs when the structure of the {@code Hashtable} has been
 * modified outside of the active iterator.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ConcurrentModificationException extends RuntimeException {

  public ConcurrentModificationException() {
  }

  ConcurrentModificationException(String message) {
    super(message);
  }

}

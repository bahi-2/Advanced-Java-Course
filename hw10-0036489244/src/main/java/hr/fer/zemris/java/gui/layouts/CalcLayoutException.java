package hr.fer.zemris.java.gui.layouts;

/**
 * This exception is thrown when the user provides invalid or duplicate position
 * parameters to the {@link CalcLayout}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CalcLayoutException extends RuntimeException {

  /**
   * Default constructor for throwing the exception.
   */
  public CalcLayoutException() {
    super();
  }

  /**
   * Throw the exception along with the message to the user.
   * 
   * @param message
   *          message to the user explaining why the exception is thrown
   */
  public CalcLayoutException(String message) {
    super(message);
  }
}

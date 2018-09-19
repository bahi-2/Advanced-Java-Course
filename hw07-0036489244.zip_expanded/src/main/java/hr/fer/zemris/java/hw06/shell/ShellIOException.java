package hr.fer.zemris.java.hw06.shell;

/**
 * Exception which should be thrown in case reading or writing to the console
 * fails.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ShellIOException extends RuntimeException {

  /**
   * Default constructor for this exception.
   */
  public ShellIOException() {
  }

  /**
   * Constructor which takes a message and prints it out to the console along with
   * the stack trace.
   * 
   * @param message
   *          message to be printed out to the console
   */
  public ShellIOException(String message) {
    super(message);
  }

}

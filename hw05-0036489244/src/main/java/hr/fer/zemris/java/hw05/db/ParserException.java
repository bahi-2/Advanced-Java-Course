package hr.fer.zemris.java.hw05.db;

/**
 * {@link Exception} class which should be thrown in case of the parser being in
 * an invalid state, caused by the invalid query.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ParserException extends IllegalStateException {

  /**
   * Defualt constructor.
   */
  public ParserException() {
  }

  /**
   * Constructor which prints out an error message, along with the stack trace.
   * 
   * @param message
   */
  public ParserException(String message) {
    super(message);
  }

}

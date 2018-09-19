package hr.fer.zemris.java.custom.scripting.parser;
/**
 * @author Blaz Bagic
 * @version 1.0
 */
public class SmartScriptParserException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public SmartScriptParserException() {
  }

  public SmartScriptParserException(String message) {
    System.err.println(message);
  }
  
}

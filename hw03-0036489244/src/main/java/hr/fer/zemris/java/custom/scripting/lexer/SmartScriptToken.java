package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * A class representing a lexical token.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class SmartScriptToken {

  /**
   * type of the token
   */
  private SmartScriptTokenType type;
  /**
   * value of the token
   */
  private Object value;
  
  public SmartScriptToken(SmartScriptTokenType type, Object value) {
    super();
    this.type = type;
    this.value = value;
  }
  
  public SmartScriptTokenType getType() {
    return type;
  }
  
  public Object getValue() {
    return value;
  }
  
}

package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumerations that define the tokens type.
 * @author Blaz Bagic
 * @version 1.0
 */
public enum SmartScriptTokenType {
  
  EOF,

  VARIABLE,
  
  STRING, 

  FUNCTION, 

  OPERATOR, 

  OPEN_TAG,

  CLOSED_TAG,

  NUMBER,

  SYMBOL,

  TAG_NAME,
  
  TEXT;
  
}
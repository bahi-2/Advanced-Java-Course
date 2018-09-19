package hr.fer.zemris.java.hw05.db;

/**
 * Enumerations for the type of the {@link Token}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public enum TokenType {

  /** Attribute token type. */
  ATTRIBUTE,

  /** Operator token type. */
  OPERATOR,

  /** Token containing the and logical operator. */
  AND,

  /** String literal token type. */
  STRING_LITERAL;

}

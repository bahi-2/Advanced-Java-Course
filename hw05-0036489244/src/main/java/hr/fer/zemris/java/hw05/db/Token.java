package hr.fer.zemris.java.hw05.db;


/**
 * Class represents one token extracted by lexical grouping.
 * @author Blaz Bagic
 * @version 1.0
 */
public class Token {
  /** The type of the token. */
  private TokenType type;
  /** The value of the token. */
  private String value;

  /**
   * Default constructor for the token.
   * @param type null is not allowed
   * @param value some value
   */
  public Token(TokenType type, String value) {
    if (type == null)
      throw new IllegalArgumentException("Token type can not be null.");
    this.type = type;
    this.value = value;
  }

  /**
   * Gets the value of the token.
   * @return value of the token
   */
  public String getValue() {
    return value;
  }
  
  /**
   * Gets the type of the token.
   * @return type of the token
   */
  public TokenType getType() {
    return type;
  }
}
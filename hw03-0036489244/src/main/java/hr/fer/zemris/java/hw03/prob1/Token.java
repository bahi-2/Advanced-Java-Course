package hr.fer.zemris.java.hw03.prob1;


/**
 * @author Blaz Bagic
 * @version 1.0
 */
public class Token {
  private TokenType type;
  private Object value;

  public Token(TokenType type, Object value) {
    if (type == null)
      throw new IllegalArgumentException("Token type can not be null.");
    this.type = type;
    this.value = value;
  }

  public Object getValue() {
    return value;
  }

  public TokenType getType() {
    return type;
  }
}
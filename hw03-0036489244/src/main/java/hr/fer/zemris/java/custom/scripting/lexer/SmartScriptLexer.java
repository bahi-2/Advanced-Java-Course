package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

import hr.fer.zemris.java.hw03.prob1.LexerException;

/**
 * Class used for production of tokens based on lexical analysis.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class SmartScriptLexer {
  private SmartScriptToken token;
  private char[] data; // ulazni tekst
  private int currentIndex; // indeks prvog neobrađenog znaka
  private SmartScriptLexerState state;

  /**
   * Constructor method which sets the document to tokenize.
   * 
   * @param document
   */
  public SmartScriptLexer(String document) {
    if (document == null)
      throw new NullPointerException("Document can't be a null reference.");
    data = document.toCharArray();
    state = SmartScriptLexerState.TEXT;
    currentIndex = 0;
  }

  /**
   * Method that creates a new lexical token from the document that was set in the
   * constructor.
   * 
   * @return a new token
   * @throws LexerException
   */
  public SmartScriptToken nextToken() {
    if (token != null && token.getType() == SmartScriptTokenType.EOF) {
      throw new LexerException("Document already parsed.");
    }

    if (currentIndex >= data.length) {
      token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
      return token;
    }

    if (state == SmartScriptLexerState.TEXT) {
      if (isOpenTagChar()) {
        token = new SmartScriptToken(SmartScriptTokenType.OPEN_TAG, "{$");
        currentIndex += 2;
        return token;
      } else {
        token = extractText();
        return token;
      }
    } else if (state == SmartScriptLexerState.TAG) {
      skipBlanks();

      if (Character.isDigit(data[currentIndex])) {
        token = extractNumber();
        return token;
      } else if (data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1])) {
        token = extractNumber();
        return token;
      } else if (isClosedTagChar()) {
        token = new SmartScriptToken(SmartScriptTokenType.CLOSED_TAG, "$}");
        currentIndex += 2;
        return token;
      } else if (Character.isLetter(data[currentIndex])) {
        token = extractVariable();
        return token;
      } else if (data[currentIndex] == '@') {
        token = extractFunction();
        return token;
      } else if (isCharOperator()) {
        token = new SmartScriptToken(SmartScriptTokenType.OPERATOR, data[currentIndex]);
        currentIndex++;
        return token;
      } else {
        // ovo se poziva kada je znak simbol
        token = new SmartScriptToken(SmartScriptTokenType.SYMBOL, data[currentIndex]);
        currentIndex++;
        return token;
      }
    } else if (state == SmartScriptLexerState.TAG_NAME) {
      skipBlanks();
      token = extractTagName();
      return token;
    } else {
      // ovo se poziva kada je state STRING
      if (data[currentIndex] == '"') {
        token = new SmartScriptToken(SmartScriptTokenType.SYMBOL, data[currentIndex]);
        currentIndex++;
        return token;
      } else {
        token = extractString();
        return token;
      }
    }
  }

  /**
   * Helper method that tests if a character is an arithmetic operator.
   * 
   * @return true if it is, otherwise false.
   */
  private boolean isCharOperator() {
    char c = data[currentIndex];
    if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^') {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Helper method which extracts a decimal number out of the document.
   * 
   * @return a new token with the value equal to the extracted number.
   */
  private SmartScriptToken extractNumber() {
    int firstPosition = currentIndex;
    if (data[firstPosition] == '-' && Character.isDigit(data[firstPosition + 1])) {
      currentIndex++;
    }
    while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
      currentIndex++;
    }
    int lastPosition = currentIndex;
    String result = new String(data, firstPosition, lastPosition - firstPosition);
    int number = Integer.parseInt(result);
    return new SmartScriptToken(SmartScriptTokenType.NUMBER, number);
  }

  /**
   * 
   * Helper method which extracts text out of the document.
   * 
   * @return a new token with the value equal to the extracted text.
   */
  private SmartScriptToken extractText() {
    int firstPosition = currentIndex;
    while (currentIndex < data.length) {
      if (isOpenTagChar() && data[currentIndex - 1] != '\\') {
        break;
      }
      currentIndex++;
    }
    int lastPosition = currentIndex;
    String text = new String(data, firstPosition, lastPosition - firstPosition);
    text = text.replaceAll("\\\\(?!\\\\)", "");
    return new SmartScriptToken(SmartScriptTokenType.TEXT, text);
  }

  /**
   * 
   * Helper method which extracts a variable out of the document.
   * 
   * @return a new token with the value equal to the extracted variable.
   */
  private SmartScriptToken extractVariable() {
    int firstPosition = currentIndex;

    if (!Character.isLetter(data[firstPosition])) { // this shouldn't happen since this method is
                                                    // only called when the current char is a letter
      throw new LexerException("Variables must start with a letter.");
    }

    while (currentIndex < data.length) {
      if (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
          || data[currentIndex] == '_') {
        currentIndex++;
      } else if (isCurrentBlank() || isClosedTagChar()) {
        break;
      } else {
        throw new LexerException("Variables can only contain letters, digits and underscores.");
      }
    }
    int lastPosition = currentIndex;
    String result = new String(data, firstPosition, lastPosition - firstPosition);
    token = new SmartScriptToken(SmartScriptTokenType.VARIABLE, result);
    return token;
  }

  /**
   * Helper method which is called after the occurence of a tag symbol.
   * 
   * @return a new token with the value equal to the name of the tag.
   */
  private SmartScriptToken extractTagName() {
    int firstPosition = currentIndex;

    if (data[firstPosition] == '=') {
      token = new SmartScriptToken(SmartScriptTokenType.TAG_NAME, '=');
      currentIndex++;
      return token;
    }

    if (!Character.isLetter(data[firstPosition])) { // this shouldn't happen since this method is
                                                    // only called when the current char is a letter
      throw new LexerException("Tag name must start with a letter ore be equal to = sign.");
    }

    while (currentIndex < data.length) {
      if (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
          || data[currentIndex] == '_') {
        currentIndex++;
      } else if (isCurrentBlank() || isClosedTagChar()) {
        break;
      } else {
        throw new LexerException(
            "Tag names can only contain letters, digits and underscores or be equal to = sign.");
      }
    }
    int lastPosition = currentIndex;
    String result = new String(data, firstPosition, lastPosition - firstPosition);
    token = new SmartScriptToken(SmartScriptTokenType.TAG_NAME, result);
    return token;
  }

  /**
   * Helper method which extracts a string out of the document. A string is always
   * enclosed in apostrophes.
   * 
   * @return a new token with the value equal to the extracted string.
   */
  private SmartScriptToken extractString() {
    int firstPosition = currentIndex;

    while (currentIndex < data.length) {
      if (data[currentIndex] == '"' && data[currentIndex - 1] != '\\') {
        break;
      }
      currentIndex++;
    }
    int lastPosition = currentIndex;
    if (data[lastPosition] != '"') { // this should only happen if currentIndex == data.length
      throw new LexerException("String not properly closed");
    }
    String result = new String(data, firstPosition, lastPosition - firstPosition);
    result = result.replaceAll("\\\\(?!\\\\)", "");
    token = new SmartScriptToken(SmartScriptTokenType.STRING, result);
    return token;
  }

  /**
   * Helper method which extracts a function name out of the document. A function
   * always starts with @.
   * 
   * @return a new token with the value equal to the extracted function name.
   */
  private SmartScriptToken extractFunction() {
    if (data[currentIndex] != '@') { // this shouldn't happen since this method is
                                     // only called when the current char is @
      throw new LexerException("Functions have to start with @.");
    }
    currentIndex++;
    int firstPosition = currentIndex;

    while (currentIndex < data.length) {
      if (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
          || data[currentIndex] == '_') {
        currentIndex++;
      } else if (isCurrentBlank() || isClosedTagChar()) {
        break;
      } else {
        throw new LexerException("Functions can only contain letters, digits and underscores.");
      }
    }

    int lastPosition = currentIndex;
    if (lastPosition == firstPosition) {
      throw new LexerException("Empty function name");
    }

    String result = new String(data, firstPosition, lastPosition - firstPosition);
    token = new SmartScriptToken(SmartScriptTokenType.FUNCTION, result);
    return token;
  }

  /**
   * Helper method to skip the whitespaces in the document.
   */
  private void skipBlanks() {
    while (currentIndex < data.length) {
      if (isCurrentBlank()) {
        currentIndex++;
      } else {
        break;
      }
    }
  }

  /**
   * Helper test method. Self-explanatory.
   * 
   * @return
   */
  private boolean isOpenTagChar() {
    return (data[currentIndex] == '{' && data[currentIndex + 1] == '$');
  }

  /**
   * Helper test method. Self-explanatory.
   * 
   * @return
   */
  private boolean isClosedTagChar() {
    return (data[currentIndex] == '$' && data[currentIndex + 1] == '}');
  }

  /**
   * Helper test method. Self-explanatory.
   * 
   * @return
   */
  private boolean isCurrentBlank() {
    char c = data[currentIndex];
    if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
      return true;
    } else {
      return false;
    }
  }

  public SmartScriptToken getToken() {
    return token;
  }

  public void setState(SmartScriptLexerState state) {
    this.state = Objects.requireNonNull(state);
  }
}
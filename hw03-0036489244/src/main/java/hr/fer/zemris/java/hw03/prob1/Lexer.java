package hr.fer.zemris.java.hw03.prob1;

/**
 * Class used for production of tokens based on lexical analysis.
 * @author Blaz Bagic
 * @version 1.0
 */
public class Lexer {
  private char[] data; // ulazni tekst
  private Token token; // trenutni token
  private int currentIndex; // indeks prvog neobrađenog znaka
  private LexerState state; // trenutno stanje lexera

  /**
   * Constructor method which sets the document to tokenize.
   * 
   * @param document
   */
  public Lexer(String text) {
    if (text == null)
      throw new NullPointerException("Input must not be a null value");
    data = text.toCharArray();
    state = LexerState.BASIC;
  }

  /**
   * Method that creates a new lexical token from the document that was set in the
   * constructor.
   * 
   * @return a new token
   * @throws LexerException
   */
  public Token nextToken() throws LexerException {

    if (token != null && token.getType() == TokenType.EOF)
      throw new LexerException("Data has already been completely parsed.");

    skipBlanks();

    if (currentIndex >= data.length) {
      token = new Token(TokenType.EOF, null);
      return token;
    }

    if (data[currentIndex] == '#') {
      return createSymbol();
    }

    if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
      return extractWord();
    }

    if (Character.isDigit(data[currentIndex])) {
      if (state == LexerState.EXTENDED) {
        return extractWord();
      }
      return extractNumber();
    }

    return createSymbol();
  }

  private Token createSymbol() {
    token = new Token(TokenType.SYMBOL, data[currentIndex]);
    currentIndex++;
    return token;
  }

  private void skipBlanks() {
    while (currentIndex < data.length) {
      if (isCurrentBlank()) {
        currentIndex++;
      } else {
        break;
      }
    }
  }

  private boolean isCurrentBlank() {
    char c = data[currentIndex];
    if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
      return true;
    } else {
      return false;
    }
  }

  private Token extractWord() {
    int firstPosition = currentIndex;
    while (currentIndex < data.length) {
      if (Character.isLetter(data[currentIndex])) {
        currentIndex++;
      } else if (state == LexerState.EXTENDED) {
        if (isCurrentBlank() || data[currentIndex] == '#') {
          break;
        } else {
          currentIndex++;
        }
      } else if (data[currentIndex] == '\\') {
        if (currentIndex + 1 >= data.length) {
          throw new LexerException("Escape character can't be the last character in your string.");
        }

        if (Character.isDigit(data[currentIndex + 1]) || data[currentIndex + 1] == '\\') {
          currentIndex += 2;
        } else {
          throw new LexerException("Invalid escape character in your string.");
        }
      } else {
        break;
      }
    }
    int lastPosition = currentIndex;
    String result = new String(data, firstPosition, lastPosition - firstPosition);

    if (state == LexerState.BASIC) {
      // the line below removes all occurances of the backslash character while
      // converting the double backslash to slash. It uses a regular expression with
      // the negative lookahead method
      result = result.replaceAll("\\\\(?!\\\\)", "");
    }
    token = new Token(TokenType.WORD, result);
    return token;
  }

  private Token extractNumber() {
    int firstPosition = currentIndex;

    while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
      currentIndex++;
    }
    int lastPosition = currentIndex;
    String value = new String(data, firstPosition, lastPosition - firstPosition);
    long number;
    try {
      number = Long.parseLong(value);
    } catch (NumberFormatException ex) {
      throw new LexerException("Input is invalid.");
    }
    token = new Token(TokenType.NUMBER, number);
    return token;
  }

  // vraća zadnji generirani token; može se pozivati
  // više puta; ne pokreće generiranje sljedećeg tokena
  public Token getToken() {
    return token;
  }

  public void setState(LexerState state) {
    if (state == null)
      throw new NullPointerException();
    this.state = state;
  }

}
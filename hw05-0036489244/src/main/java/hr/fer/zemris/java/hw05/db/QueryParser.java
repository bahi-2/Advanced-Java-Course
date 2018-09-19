package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a parser of a query statement.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class QueryParser {

  /** Used for token grouping. */
  private QueryLexer lexer;
  /** List of expression extracted from the query. */
  private List<ConditionalExpression> query;

  /**
   * Constructor method which initiates the extraction of the query.
   * 
   * @param query
   *          Query to extract
   */
  public QueryParser(String query) {
    lexer = new QueryLexer(query);
    this.query = parseQuery();
  }

  /**
   * Returns true if the query has only one conditional expression, false
   * otherwise.
   * 
   * @return true if the query has only one conditional expression, false
   *         otherwise
   */
  public boolean isDirectQuery() {
    return query.size() == 1
        && query.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS)
        && query.get(0).getFieldGetter().isJMBAG();
  }

  /**
   * Returns the JMBAG of a direct query.
   * 
   * @return the JMBAG of a direct query
   * @throws ParserException
   *           in case the query isn't direct.
   */
  public String getQueriedJMBAG() {
    if (!isDirectQuery()) {
      throw new ParserException("Not a direct query.");
    }
    return query.get(0).getStringLiteral();
  }

  /**
   * Returns the list of conditional expressions extracted from the query given in
   * the constructor.
   * 
   * @return the list of conditional expressions
   */
  public List<ConditionalExpression> getQuery() {
    return query;
  }

  /**
   * Auxiliary method which creates an {@code IFieldValueGetter} based on the
   * attribute extracted from the query given in the constructor.
   * 
   * @return an {@code IFieldValueGetter} based on the attribute extracted from
   *         the query
   * @throws ParserException
   *           see {@link #parseQuery()}
   */
  private IFieldValueGetter extractValueGetter() {
    Token token = lexer.nextToken();
    if (token.getType() == TokenType.ATTRIBUTE) {
      switch (token.getValue()) {
        case "firstName":
          return FieldValueGetters.FIRST_NAME;
        case "lastName":
          return FieldValueGetters.LAST_NAME;
        case "jmbag":
          return FieldValueGetters.JMBAG;
        default:
          throw new ParserException();
      }
    } else {
      throw new ParserException();
    }
  }

  /**
   * Auxiliary method which extracts an operator from the query.
   * 
   * @return query operator
   * @throws ParserException
   *           see {@link #parseQuery()}
   */
  private IComparisonOperator extractOperator() {
    Token token = lexer.nextToken();
    if (token.getType() == TokenType.OPERATOR) {
      switch (token.getValue().toUpperCase()) {
        case ">":
          return ComparisonOperators.GREATER;

        case "=":
          return ComparisonOperators.EQUALS;

        case "<":
          return ComparisonOperators.LESS;

        case ">=":
          return ComparisonOperators.GREATER_OR_EQUALS;

        case "<=":
          return ComparisonOperators.LESS_OR_EQUALS;

        case "!=":
          return ComparisonOperators.NOT_EQUALS;

        case "LIKE":
          return ComparisonOperators.LIKE;

        default:
          throw new ParserException();
      }
    } else {
      throw new ParserException();
    }
  }

  /**
   * Auxiliary method which extracts the {@code String} literal from the query.
   * 
   * @return {@code String} literal
   * @throws ParserException
   *           see {@link #parseQuery()}
   */
  private String extractStringLiteral() {
    Token token = lexer.nextToken();
    if (token.getType() == TokenType.STRING_LITERAL) {
      return token.getValue();
    } else {
      throw new ParserException();
    }
  }

  /**
   * Auxiliary method which parses the query. Called from the constructor at
   * initialization.
   * 
   * @return the list of conditional expressions
   * @throws ParserException
   *           in case the query given in the constructor is not properly
   *           structured
   */
  private List<ConditionalExpression> parseQuery() {
    List<ConditionalExpression> result = new ArrayList<>();
    while (lexer.hasNext()) {
      IFieldValueGetter getter = extractValueGetter();
      IComparisonOperator operator = extractOperator();
      String literal = extractStringLiteral();

      result.add(new ConditionalExpression(getter, literal, operator));

      if (lexer.hasNext() && lexer.nextToken().getType() == TokenType.AND) {
        continue;
      } else {
        break;
      }
    }
    return result;
  }

  /**
   * Nested class that serves the purpose of lexical analysis for the
   * {@link QueryParser}.
   * 
   * @author Blaz Bagic
   */
  private class QueryLexer {
    /** Input query in {@code Character} format. */
    private char[] data;
    /** Index to the first not extracted character */
    private int currentIndex;

    /**
     * Constructor method which prepares the data for lexical analysis.
     * 
     * @param text
     *          data to be analyzed
     */
    public QueryLexer(String text) {
      if (text == null)
        throw new NullPointerException("Input must not be a null value");
      data = text.toCharArray();
    }

    /**
     * This method groups the next sequence of characters to a token from the given
     * text.
     * 
     * @return token
     */
    public Token nextToken() {
      skipBlanks();
      char c = data[currentIndex];
      if (c == '>' || c == '<' || c == '=' || c == '!') {
        return extractOperator();
      } else if (c == '"') {
        currentIndex++;
        return extractString();
      } else if (Character.isLetter(c)) {
        return extractLetters();
      } else {
        throw new ParserException();
      }
    }

    /**
     * Returns {@code true} if there are more tokens to group, {@code false}
     * otherwise.
     * 
     * @return {@code true} if there are more tokens to group, {@code false}
     *         otherwise
     */
    public boolean hasNext() {
      skipBlanks();
      return (currentIndex < data.length);
    }

    /**
     * Auxiliary method for extracting tokens which are grouped from letters.
     * 
     * @return token with type from the set [AND, OPERATOR, ATTRIBUTE]
     */
    private Token extractLetters() {
      int firstPosition = currentIndex;
      while (currentIndex < data.length) {
        if (!Character.isLetter(data[currentIndex])) {
          break;
        }
        currentIndex++;
      }
      int lastPosition = currentIndex;
      String text = new String(data, firstPosition, lastPosition - firstPosition);
      if (text.toUpperCase().equals("AND")) {
        return new Token(TokenType.AND, text);
      } else if (text.toUpperCase().equals("LIKE")) {
        return new Token(TokenType.OPERATOR, text);
      } else {
        return new Token(TokenType.ATTRIBUTE, text);
      }
    }

    /**
     * Helper method which extracts string literal out of the text.
     * 
     * @return a new token with the value equal to the extracted string literal.
     */
    private Token extractString() {
      int firstPosition = currentIndex;
      while (currentIndex < data.length) {
        if (data[currentIndex] == '"') {
          break;
        }
        currentIndex++;
      }
      int lastPosition = currentIndex;
      String text = new String(data, firstPosition, lastPosition - firstPosition);
      currentIndex++;
      return new Token(TokenType.STRING_LITERAL, text);
    }

    private Token extractOperator() {
      if (currentIndex + 1 < data.length) {
        // @formatter:off
        String op = new StringBuilder()
            .append(data[currentIndex])
            .append(data[currentIndex + 1])
            .toString();
        // @formatter:on

        if (op.equals(">=") || op.equals("<=") || op.equals("!=")) {
          currentIndex += 2;
          return new Token(TokenType.OPERATOR, op);
        }
      }

      char c = data[currentIndex];
      if (c == '>' || c == '<' || c == '=') {
        currentIndex++;
        return new Token(TokenType.OPERATOR, String.valueOf(c));
      } else {
        throw new ParserException();
      }
    }

    /**
     * Auxiliary method for skipping blank characters.
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
     * Auxiliary method for checking if the current character is a blank character.
     * 
     * @return true if the current character is a whitespace, false otherwise
     */
    private boolean isCurrentBlank() {
      char c = data[currentIndex];
      if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
        return true;
      } else {
        return false;
      }
    }
  }

  public static void main(String[] args) {
    QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
    System.out.println("isDirectQuery(): " + qp1.isDirectQuery()); // true
    System.out.println("jmbag was: " + qp1.getQueriedJMBAG()); // 0123456789
    System.out.println("size: " + qp1.getQuery().size()); // 1

    QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
    System.out.println("isDirectQuery(): " + qp2.isDirectQuery()); // false
    // System.out.println(qp2.getQueriedJMBAG()); // would throw!
    System.out.println("size: " + qp2.getQuery().size()); // 2
  }
}

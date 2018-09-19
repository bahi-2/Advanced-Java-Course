package hr.fer.zemris.java.custom.scripting.lexer;
/**
 * @author Blaz Bagic
 * @version 1.0
 */

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class SmartScriptLexerTest {

  SmartScriptLexer lexer;

  @Test
  public void fla() {
    lexer = new SmartScriptLexer("This is sample text.\n" + "{$ FOR i 1 10 1 $}\n"
        + " This is {$= i $}-th time this message is generated.\n" + "{$END$}\n"
        + "{$FOR i 0 10 2 $}\n" + " sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n"
        + "{$END$}");
    do {
      System.out.println(lexer.nextToken().getValue());
      if (lexer.getToken().getValue().equals("{$")) {
        lexer.setState(SmartScriptLexerState.TAG);
      }
      if (lexer.getToken().getValue().equals("$}")) {
        lexer.setState(SmartScriptLexerState.TEXT);
      }
    } while (lexer.getToken().getType() != SmartScriptTokenType.EOF);
  }

  @Test
  public void emptyDocument() {
    lexer = new SmartScriptLexer("");
    checkToken(new SmartScriptToken(SmartScriptTokenType.EOF, null), lexer.nextToken());
  }

  @Test
  public void testTextInput() {
    lexer = new SmartScriptLexer(" Pricam ti pricu$} o ivici.");
    checkToken(new SmartScriptToken(SmartScriptTokenType.TEXT, " Pricam ti pricu$} o ivici."),
        lexer.nextToken());
    checkToken(new SmartScriptToken(SmartScriptTokenType.EOF, null), lexer.nextToken());

  }

  @Test
  public void testTextAndTag() {
    lexer = new SmartScriptLexer("Pricam ti pricu {$ o ivici.");
    checkToken(new SmartScriptToken(SmartScriptTokenType.TEXT, "Pricam ti pricu "),
        lexer.nextToken());
  }

  @Test
  public void testTextEscaping() {
    lexer = new SmartScriptLexer("Example \\\\ \\\" \\{$=1$}.");
    checkToken(new SmartScriptToken(SmartScriptTokenType.TEXT, "Example \\ \" {$=1$}."),
        lexer.nextToken());
    checkToken(new SmartScriptToken(SmartScriptTokenType.EOF, null), lexer.nextToken());

  }

  @Test
  public void testOpenTag() {
    lexer = new SmartScriptLexer("{$= i i * @sin \"0.000\" @decfmt $}");
    checkToken(new SmartScriptToken(SmartScriptTokenType.OPEN_TAG, "{$"), lexer.nextToken());
  }

  @Test
  public void testEndTag() {
    lexer = new SmartScriptLexer("{$END$}");
    checkToken(new SmartScriptToken(SmartScriptTokenType.OPEN_TAG, "{$"), lexer.nextToken());
    
    lexer.setState(SmartScriptLexerState.TAG_NAME);
    
    checkToken(new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "END"), lexer.nextToken());
    
    lexer.setState(SmartScriptLexerState.TAG);
    
    checkToken(new SmartScriptToken(SmartScriptTokenType.CLOSED_TAG, "$}"), lexer.nextToken());
  }

  @Test
  public void testTagState() {
    lexer = new SmartScriptLexer("{$ = i i * @sin \"0.000\" -5 @dec_fmt $}");

    checkToken(new SmartScriptToken(SmartScriptTokenType.OPEN_TAG, "{$"), lexer.nextToken());

    lexer.setState(SmartScriptLexerState.TAG_NAME);

    checkToken(new SmartScriptToken(SmartScriptTokenType.TAG_NAME, '='), lexer.nextToken());

    lexer.setState(SmartScriptLexerState.TAG);

    checkToken(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"), lexer.nextToken());
    checkToken(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"), lexer.nextToken());
    checkToken(new SmartScriptToken(SmartScriptTokenType.OPERATOR, '*'), lexer.nextToken());
    checkToken(new SmartScriptToken(SmartScriptTokenType.FUNCTION, "sin"), lexer.nextToken());
    checkToken(new SmartScriptToken(SmartScriptTokenType.SYMBOL, '"'), lexer.nextToken());

    lexer.setState(SmartScriptLexerState.STRING);

    checkToken(new SmartScriptToken(SmartScriptTokenType.STRING, "0.000"), lexer.nextToken());
    checkToken(new SmartScriptToken(SmartScriptTokenType.SYMBOL, '"'), lexer.nextToken());

    lexer.setState(SmartScriptLexerState.TAG);

    checkToken(new SmartScriptToken(SmartScriptTokenType.NUMBER, -5), lexer.nextToken());
    checkToken(new SmartScriptToken(SmartScriptTokenType.FUNCTION, "dec_fmt"), lexer.nextToken());
    checkToken(new SmartScriptToken(SmartScriptTokenType.CLOSED_TAG, "$}"), lexer.nextToken());
    checkToken(new SmartScriptToken(SmartScriptTokenType.EOF, null), lexer.nextToken());
  }

  private void checkToken(SmartScriptToken expected, SmartScriptToken actual) {
    String msg = "Tokens are not equal.";
    assertEquals(msg, expected.getType(), actual.getType());
    assertEquals(msg, expected.getValue(), actual.getValue());
  }
}

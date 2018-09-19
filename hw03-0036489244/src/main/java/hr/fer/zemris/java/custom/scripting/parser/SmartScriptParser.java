package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Parser class, not fully implemented, because a lack of time.
 * @author Blaz Bagic
 * @version 1.0
 */
public class SmartScriptParser {
  // Parser parsira zadanu gramatiku (u ovom slučaju pravila pisanja dokumenta),
  // te na temelju toga govori da li je svaki tag ispravno otvoren/zatvoren, da li
  // je svaki tag/text ispravno i točnim redosljedom napisan i sl. (ono što se na
  // prevođenju programskih jezika zove sintaksna analiza).
  DocumentNode document = new DocumentNode();

  public SmartScriptParser(String document) {
    SmartScriptLexer lexer = new SmartScriptLexer(document);
    try {
      parse(lexer);
    } catch (Exception ex) {
      throw new SmartScriptParserException("An error occured during parsing");
    }
  }

  public void parse(SmartScriptLexer lexer) {
    ObjectStack stack = new ObjectStack();
    stack.push(document);
    Node parent;
    SmartScriptToken token = lexer.nextToken();

    while (token.getType() != SmartScriptTokenType.EOF) {
      parent = (Node) stack.peek();

      switch (token.getType()) {
      case TEXT:
        TextNode textNode = new TextNode((String) token.getValue());
        parent.addChildNode(textNode);
        break;
      case OPEN_TAG:
        lexer.setState(SmartScriptLexerState.TAG_NAME);
        break;
      case CLOSED_TAG:
        lexer.setState(SmartScriptLexerState.TEXT);
        break;
      case EOF: // unreachable
        break;
      case FUNCTION:
        break;
      case NUMBER:
        break;
      case OPERATOR:
        break;
      case STRING:
        break;
      case SYMBOL:
        break;
      case TAG_NAME:
        lexer.setState(SmartScriptLexerState.TAG);
        if (token.getValue().equals("FOR")) {
          token = lexer.nextToken();
          if (token.getType() != SmartScriptTokenType.VARIABLE) {
            throw new SmartScriptParserException();
          } else {
            ForLoopNode forLoopNode = new ForLoopNode(new ElementVariable((String) token.getValue()),
                new ElementConstantInteger((int) lexer.nextToken().getValue()),
                new ElementConstantInteger((int) lexer.nextToken().getValue()),
                new ElementConstantInteger((int) lexer.nextToken().getValue()));
            stack.push(forLoopNode);
          }
        }
        break;
      case VARIABLE:
        break;
      default:
        break;
      }
      token = lexer.nextToken();
    }
  }

  public DocumentNode getDocumentNode() {
    return document;
  }

}

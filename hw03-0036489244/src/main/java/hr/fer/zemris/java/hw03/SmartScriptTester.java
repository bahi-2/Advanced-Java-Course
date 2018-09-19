package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * @author Blaz Bagic
 * @version 1.0
 */
public class SmartScriptTester {

  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      throw new IllegalArgumentException(
          "Please include the path to the document in a command line argument.");
    }

    String filepath = args[0];
    String docBody = new String(
        Files.readAllBytes(Paths.get(filepath)),
        StandardCharsets.UTF_8
       );
    
    SmartScriptParser parser = null;
    try {
      parser = new SmartScriptParser(docBody);
    } catch (SmartScriptParserException e) {
      System.out.println("Unable to parse document!");
      System.exit(-1);
    } catch (Exception e) {
      System.out.println("If this line ever executes, you have failed this class!");
      System.exit(-1);
    }
    DocumentNode document = parser.getDocumentNode();
    printOriginalDocumentBody(document);
  }

  public static void printOriginalDocumentBody(Node document) {
    if (document.numberOfChildren() == 0 ) {
      System.out.println(document.toString());
    }
    for (int index = 0; index<document.numberOfChildren(); index++) {
      printOriginalDocumentBody(document.getChild(index));
    }
  }

}

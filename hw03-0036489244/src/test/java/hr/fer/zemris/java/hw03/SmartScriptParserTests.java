package hr.fer.zemris.java.hw03;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * @author Blaz Bagic
 * @version 1.0
 */
public class SmartScriptParserTests {

  @Test
  public void testParserOnDocument1() {
    String document = loader("document1.txt");
    SmartScriptParser parser = new SmartScriptParser(document);
    System.out.println(parser.getDocumentNode().toString());
  }

  private String loader(String filename) {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
      byte[] buffer = new byte[1024];
      while (true) {
        int read = is.read(buffer);
        if (read < 1)
          break;
        bos.write(buffer, 0, read);
      }
      return new String(bos.toByteArray(), StandardCharsets.UTF_8);
    } catch (IOException ex) {
      return null;
    }
  }

}

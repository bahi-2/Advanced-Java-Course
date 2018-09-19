package hr.fer.zemris.java.hw05.db;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import hr.fer.zemris.java.hw05.db.QueryFilter;
import hr.fer.zemris.java.hw05.db.QueryParser;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Demonstration class for the whole homework.
 * @author Blaz Bagic
 * @version 1.0
 */
public class Demonstration {
  @Test
  public void main() {
    StudentDatabase db = new StudentDatabase(loader("database.txt").split("\\r?\\n"));
    QueryParser parser = new QueryParser("lastName LIKE   \"B*\""); // Here you can test multiple options.
    if (parser.isDirectQuery()) {
      StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
      System.out.println(r);
    } else {
      for (StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
        System.out.println(r);
      }
    }
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
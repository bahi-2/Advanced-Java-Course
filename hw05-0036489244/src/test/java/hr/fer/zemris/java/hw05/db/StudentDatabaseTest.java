package hr.fer.zemris.java.hw05.db;
/**
 * @author Blaz Bagic
 * @version 1.0
 */

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.hw05.db.StudentDatabase;

public class StudentDatabaseTest {

  StudentDatabase database;

  @Before
  public void initializeDatabase() throws IOException {
    String document = loader("database.txt");
    String[] lines = document.split("\\r?\\n");
    database = new StudentDatabase(lines);
  }

  @Test
  public void forJMBAGTest() {
    assertEquals("0000000006 Cvrlje Ivan 3", database.forJMBAG("0000000006").toString());
  }

  @Test
  public void testFilterAcceptAll() {
    assertEquals(63, database.filter((s) -> true).size());
  }

  @Test
  public void testFilterAcceptNone() {
    assertEquals(0, database.filter((s) -> false).size());
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

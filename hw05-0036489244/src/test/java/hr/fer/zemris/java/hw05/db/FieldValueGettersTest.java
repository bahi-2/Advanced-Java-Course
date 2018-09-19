package hr.fer.zemris.java.hw05.db;
/**
 * @author Blaz Bagic
 * @version 1.0
 */

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FieldValueGettersTest {
  
  StudentRecord record;
  
  @Test
  public void getTest() {
    record = new StudentRecord("0036489244", "Bagić", "Blaž", "5");
    
    assertEquals("Blaž", FieldValueGetters.FIRST_NAME.get(record));
    assertEquals("Bagić", FieldValueGetters.LAST_NAME.get(record));
    assertEquals("0036489244", FieldValueGetters.JMBAG.get(record));
  }
  
}

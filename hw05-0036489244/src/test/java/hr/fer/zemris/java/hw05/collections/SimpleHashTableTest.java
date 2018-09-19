package hr.fer.zemris.java.hw05.collections;
/**
 * @author Blaz Bagic
 * @version 1.0
 */

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class SimpleHashTableTest {

  SimpleHashtable<String, Integer> table;

  /**
   * Auxiliary method for instancing a hash table with three elements.
   */
  @Before
  public void fillTable() {
    table = new SimpleHashtable<>();
    table.put("First", 12);
    table.put("SeCond", 1);
    table.put("last", 0);
  }

  @Test
  public void sizeTest() {
    table = new SimpleHashtable<>();
    assertEquals(0, table.size());
    table.put("a", 2);
    assertEquals(1, table.size());
  }

  @Test
  public void getTest() {
    fillTable();
    table.put("null_ref", null);
    assertEquals(Integer.valueOf(12), table.get("First"));
    assertEquals(Integer.valueOf(1), table.get("SeCond"));
    assertEquals(Integer.valueOf(0), table.get("last"));
    assertEquals(null, table.get("Unexistant"));
    assertEquals(null, table.get(null));
    assertEquals(null, table.get("null_ref"));
  }

  @Test(expected = NullPointerException.class)
  public void putNullKeyTest() {
    table.put(null, 1);
  }

  @Test
  public void putExistingKey() {
    fillTable();
    table.put("SeCond", 13);
    assertEquals(3, table.size()); // assert that the size hasn't changed
    assertEquals(Integer.valueOf(13), table.get("SeCond")); // assert that the value has changed
  }

  @Test
  public void containsKeyTest() {
    fillTable();
    assertEquals(true, table.containsKey("last"));
    assertEquals(false, table.containsKey("fog"));
    assertEquals(false, table.containsKey(null));
  }

  @Test
  public void containsValueTest() {
    fillTable();
    assertEquals(true, table.containsValue(12));
    assertEquals(false, table.containsValue(255));
  }

  @Test
  public void containsNullValueTest() {
    assertEquals(false, table.containsValue(null));
    table.put("key", null);
    assertEquals(true, table.containsValue(null));
  }

  @Test
  public void clearTest() {
    fillTable();
    table.clear();
    assertEquals(0, table.size());
    assertEquals(false, table.containsKey("First"));
    assertEquals(false, table.containsKey("last"));
    assertEquals(false, table.containsKey("SeCond"));
  }

  @Test
  public void containsNullKeyTest() {
    assertEquals(false, table.containsKey(null));
  }

  @Test
  public void removeTest1() {
    fillTable();
    table.remove("First");
    assertEquals(2, table.size());

    fillTable();
    table.remove("SeCond");
    assertEquals(2, table.size());

    fillTable();
    table.remove("last");
    assertEquals(2, table.size());
  }

  @Test
  public void removeTest2() {
    fillTable();
    assertEquals(3, table.size());

    table.remove("not existant key");
    assertEquals(3, table.size());

    table.remove(null); // does nothing
  }
}

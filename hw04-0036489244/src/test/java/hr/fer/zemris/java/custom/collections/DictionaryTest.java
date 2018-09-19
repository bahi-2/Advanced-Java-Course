package hr.fer.zemris.java.custom.collections;
/**
 * @author Blaz Bagic
 * @version 1.0
 */

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DictionaryTest {

  Dictionary dictionary;

  @Before
  public void initializeDictionary() {
    dictionary = new Dictionary();
  }

  @Test
  public void isEmptyTest() {
    dictionary = new Dictionary();
    assertEquals(true, dictionary.isEmpty());
    dictionary.put("first", "121");
    assertEquals(false, dictionary.isEmpty());
  }

  @Test
  public void sizeTest() {
    dictionary = new Dictionary();
    dictionary.put("first", "121");
    dictionary.put("second", "333");
    assertEquals(2, dictionary.size());
  }

  @Test
  public void clearTest() {
    dictionary.put("not", "empty");
    dictionary.clear();
    assertEquals(0, dictionary.size());
  }

  @Test
  public void getTest() {
    dictionary.put(123, '$');
    assertEquals('$', dictionary.get(123));
  }

  @Test
  public void getTestNull() {
    dictionary.clear();
    dictionary.put("1", '5');
    dictionary.put(2, "han");
    dictionary.put(3.0, "solo");
    assertEquals("han", dictionary.get(2));
    assertEquals(null, dictionary.get(2.2));
  }

  @Test
  public void putOverrideTest() {
    dictionary.put(12, 'a');
    dictionary.put(12, "new_value");
    assertEquals("new_value", dictionary.get(12));
  }

  @Test(expected = NullPointerException.class)
  public void getNullKey() {
    dictionary.get(null);
  }

  @Test(expected = NullPointerException.class)
  public void putNullKey() {
    dictionary.put(null, "");
  }

}

package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Junit tests.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class LinkedListIndexedCollectionTest {

  @Test(expected = NullPointerException.class)
  public void addNull() {
    LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
    collection.add(null);
  }

  @Test
  public void emptySize() {
    LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
    assertEquals(0, collection.size());
  }

  @Test
  public void sizeTest() {
    LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
    collection.add("Bok");
    collection.add(12);
    assertEquals(2, collection.size());
  }

  @Test
  public void containsTest() {
    LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
    collection.add("Zdravo");
    collection.add(123);
    assertTrue(collection.contains(123));
  }

  @Test
  public void doesNotContainTest() {
    LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
    collection.add("Zdravo");
    collection.add(123);
    assertFalse(collection.contains("12"));
  }

  @Test
  public void removeTest() {
    LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
    collection.add("Zdravo");
    collection.add(123);
    collection.add("Franjo");
    collection.remove((Object) 123);
    assertFalse(collection.contains(123));
  }

  @Test
  public void toArrayTest() {
    LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
    collection.add("Zdravo");
    collection.add(123);
    collection.add("Franjo");
    assertEquals(123, (int) collection.toArray()[1]);
  }

  @Test
  public void addAllTest() {
    ArrayIndexedCollection otherCollection = new ArrayIndexedCollection();
    otherCollection.add("A");
    otherCollection.add("B");
    otherCollection.add(122.1);

    LinkedListIndexedCollection collection = new LinkedListIndexedCollection(otherCollection);
    assertArrayEquals(otherCollection.toArray(), collection.toArray());
  }

  @Test
  public void clearTest() {
    LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
    collection.add("Zdravo");
    collection.add(123);
    collection.add("Franjo");

    collection.clear();
    collection.add("value");

    assertEquals(1, collection.size());
  }

  @Test
  public void getTest() {
    LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
    collection.add("Zdravo");
    collection.add(123);
    collection.add("Franjo");
    collection.add("Nikola");
    collection.add(13);

    assertEquals("Nikola", collection.get(3));
  }

  @Test
  public void insertTest() {
    LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
    collection.add("Zdravo");
    collection.add(123);
    collection.add("Franjo");
    collection.add("Nikola");
    collection.add(13);

    collection.insert("insertTest", 2);
    assertEquals(2, collection.indexOf("insertTest"));
  }

}

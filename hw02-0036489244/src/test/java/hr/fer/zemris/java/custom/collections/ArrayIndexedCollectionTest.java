package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * JUnit tests.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ArrayIndexedCollectionTest {
  
  ArrayIndexedCollection filledCollection;
  
  {
    filledCollection = new ArrayIndexedCollection();
    filledCollection.add(27);
    filledCollection.add("SampleString");
    filledCollection.add(3.212);
  }

  @Test(expected = NullPointerException.class)
  public void addNull() {
    filledCollection.add(null);
  }

  @Test
  public void addNumber() {
    filledCollection.add(12);
    assertTrue(filledCollection.contains(12));
  }

  @Test
  public void containsString() {
    filledCollection.add("Hello");
    assertTrue(filledCollection.contains("Hello"));
  }

  @Test
  public void doesNotContainObject() {
    filledCollection.add(3.14);
    assertFalse(filledCollection.contains("3.14"));
  }

  @Test
  public void removeObjectTest() {
    ArrayIndexedCollection collection = new ArrayIndexedCollection();
    collection.add(8);
    collection.remove((Object) 8);
    assertFalse(collection.contains(8));
  }

  @Test
  public void removeObjectAtIndexTest() {
    ArrayIndexedCollection collection = new ArrayIndexedCollection();
    collection.add(8);
    collection.remove(0);
    assertFalse(collection.contains(8));
  }

  @Test
  public void sizeTest() {
    ArrayIndexedCollection collection = new ArrayIndexedCollection();
    collection.add(1);
    collection.add(2);
    collection.add(3);
    collection.remove((Object) 3);
    assertEquals(2, collection.size());
  }

  @Test
  public void addAllTest() {
    ArrayIndexedCollection collection = new ArrayIndexedCollection();
    ArrayIndexedCollection otherCollection = new ArrayIndexedCollection();
    otherCollection.add(5);
    otherCollection.add("Bok!");
    collection.addAll(otherCollection);
    assertArrayEquals(otherCollection.toArray(), collection.toArray());
  }

  @Test
  public void clearTest() {
    ArrayIndexedCollection collection = new ArrayIndexedCollection();
    collection.add(1);
    collection.add(2);
    collection.add(3);
    collection.clear();
    assertEquals(0, collection.size());
  } 
  
  @Test
  public void getTest() {
    ArrayIndexedCollection collection = new ArrayIndexedCollection();
    collection.add(1);
    collection.add(2);
    collection.add(3);
    collection.clear();
    assertEquals(-1, collection.get(1));
  }
  
  @Test
  public void indexOfTest() {
    ArrayIndexedCollection collection = new ArrayIndexedCollection();
    collection.add("Da");
    collection.add("Ne");
    collection.add("Mozda");
    assertEquals(2, collection.indexOf("Mozda"));
  }
  
  @Test
  public void insertTest() {
    filledCollection.insert("insertTest", 2);
    assertEquals(2, filledCollection.indexOf("insertTest"));
  }
}

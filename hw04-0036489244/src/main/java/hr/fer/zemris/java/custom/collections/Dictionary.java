package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Implementation of a dictionary that stores <code>Object</code> type values
 * under <code>Object</code> type keys.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class Dictionary {

  /**
   * This private collection is used for storing the actual key-value pairs.
   */
  private ArrayIndexedCollection dictionary;

  /**
   * Class represents a key-value pair of a dictionary.
   * 
   * @author pago
   *
   */
  private class Entry {
    /** The key to a value in a dictionary. */
    private Object key;
    /** The value to be stored in a dictionary. */
    private Object value;

    /**
     * Constructor creating a dictionary entry.
     * 
     * @param key
     *          must not be null
     * @param value
     *          associated with the key
     */
    private Entry(Object key, Object value) {
      this.key = Objects.requireNonNull(key);
      this.value = value;
    }
  }

  public Dictionary() {
    dictionary = new ArrayIndexedCollection();
  }

  /**
   * Checks if the dictionary is empty.
   * 
   * @return <code>true</code> if the dictionary is empty, or <code>false</code>
   *         otherwise.
   */
  public boolean isEmpty() {
    return dictionary.isEmpty();
  }

  /**
   * Returns the length of this dictionary.
   * 
   * @return size
   */
  public int size() {
    return dictionary.size();
  }

  /**
   * Removes all entries from the dictionary.
   */
  public void clear() {
    dictionary.clear();
  }

  /**
   * Method used for inserting a <code>value</code> in the dictionary, under the
   * <code>key</code>. If the <code>key</code> already has an assigned
   * <code>value</code>, that <code>value</code> is overwritten.
   * 
   * @param key
   *          key at which to insert a new value
   * @param value
   *          value to insert
   */
  public void put(Object key, Object value) {
    if (key == null) {
      throw new NullPointerException("The key to the object can not be a null reference!");
    }

    Entry entry = new Entry(key, value);
    int index = indexOfKey(key);
    if (index != -1) { // then overwrite an entry
      dictionary.insert(entry, index);
    } else {
      dictionary.add(entry);
    }
  }

  /**
   * Auxiliary method for finding the index of a key in our private collection.
   * 
   * @param key
   *          key of a dictionary entry
   * @return index or -1 if there is no value associated with the key
   */
  private int indexOfKey(Object key) {
    if (key == null) {
      throw new NullPointerException("The key to the object can not be a null reference!");
    }
    for (int index = 0; index < dictionary.size(); index++) {
      Entry entry = (Entry) dictionary.get(index);
      if (entry.key.equals(key)) {
        return index;
      }
    }
    return -1;
  }

  /**
   * Method returns a value associated with the <code>key</code> unless the
   * <code>key</code> doesn't have a value, then it returns null.
   * 
   * @param key
   *          the key under which the value is stored
   * @return value or null
   */
  public Object get(Object key) {
    if (key == null) {
      throw new NullPointerException("The key to the object can not be a null reference!");
    }
    int index = indexOfKey(key);
    return (index == -1) ? null : ((Entry) dictionary.get(index)).value;
  }
}

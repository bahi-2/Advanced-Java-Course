package hr.fer.zemris.java.hw05.collections;

import static java.lang.Math.abs;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of a hash table that stores key-value pairs. These entries are
 * stored in appropriate slots based on their hash code. One slot can contain
 * multiple entries. The key of an entry can't be null, but the value can.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

  /**
   * When the number of entries exceeds this ratio times the number of slots, the
   * number of slots will be doubled.
   */
  private static final double MAXIMAL_SIZE_TO_SLOTS_RATIO = 0.75;
  /** Default value of maximum entries in the table. */
  private static final int DEFAULT_CAPACITY = 16;
  /** Maximum entries in the table. */
  private int capacity;

  /** Array of entry slots. One slot can contain multiple entries. */
  private TableEntry<K, V>[] table;
  /** Current number of entries in the hash table. */
  private int size;
  /**
   * The number of times this {@code Hashtable} has been structurally modified by
   * changing the number of entries in the {@code Hashtable} or rehashing it's
   * entries. Iterators use this value to check that the {@code Hashtable} hasn't
   * been modified externally. (See {@link ConcurrentModificationException}).
   */
  private int modificationCount;

  /**
   * Class represents one {@code Hashtable} entry. Each entry has a key, a value
   * and a reference to the next entry in the slot if it exists.
   * 
   * @author Blaz Bagic
   * @param <K>
   *          The type of the key of an entry
   * @param <V>
   *          The type of the value of an entry
   */
  public static class TableEntry<K, V> {
    /** Key of the entry. */
    private K key;
    /** Value of the entry. */
    private V value;
    /**
     * Reference to the next entry in the slot or {@code null} if such doesn't
     * exists.
     */
    private TableEntry<K, V> next;

    /**
     * Constructor of an entry.
     * 
     * @param key
     *          Key of the entry
     * @param value
     *          Value of the entry
     */
    public TableEntry(K key, V value) {
      super();
      this.key = key;
      this.value = value;
    }

    /**
     * Returns the value of this entry.
     * 
     * @return value of this entry
     */
    public V getValue() {
      return value;
    }

    /**
     * Sets the value of an entry.
     * 
     * @param value
     *          New value
     */
    public void setValue(V value) {
      this.value = value;
    }

    /**
     * Returns the key of this entry.
     * 
     * @return key of this entry
     */
    public K getKey() {
      return key;
    }
  }

  private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
    /** Current slot of the hash table. */
    private int index;
    /**
     * This field is used to check if any structural modifications on the
     * {@code Hashtable} occurred outside of this {@code Iterator}.
     */
    private int expectedModificationCount;
    /** Current table entry. */
    private TableEntry<K, V> current;
    /** Next table entry. */
    private TableEntry<K, V> next;

    public IteratorImpl() {
      index = 0;
      expectedModificationCount = modificationCount;
      if (!isEmpty()) { // find the first table entry
        nextNonEmptySlot();
      }
    }

    private void nextNonEmptySlot() {
      while (index < table.length && next == null) {
        next = table[index];
        index++;
      }
    }

    public boolean hasNext() {
      if (modificationCount != expectedModificationCount) {
        throw new ConcurrentModificationException();
      }
      return next != null;
    }

    public TableEntry<K, V> next() {
      if (modificationCount != expectedModificationCount) {
        throw new ConcurrentModificationException();
      }

      current = next;
      if (current == null) {
        throw new NoSuchElementException();
      }

      next = current.next;
      if (next == null) {
        nextNonEmptySlot();
      }
      return current;
    }

    /**
     * Removes the element returned by the last call to {@link #next()} from the
     * {@code Hashtable}.
     * 
     * @throws ConcurrentModificationException
     *           if the {@code Hashtable} has been modified outside of this iterator
     *           object
     * @throws IllegalStateException
     *           if the next method has not yet been called, or the remove method
     *           has already been called after the last call to the next method
     */
    public void remove() {
      if (current == null) {
        throw new IllegalStateException();
      }
      if (modificationCount != expectedModificationCount) {
        throw new ConcurrentModificationException();
      }

      SimpleHashtable.this.remove(current.getKey());
      current = null;
      expectedModificationCount = modificationCount;
    }
  }

  /**
   * Constructor for the {@code SimpleHashtable} which creates a table with the
   * first capacity that is a power of two and equal or bigger to the given
   * capacity.
   * 
   * @param capacity
   *          Capacity for the new table. If this value is not a power of two,
   *          then the first bigger number which is the power of two is chosen for
   *          the capacity
   */
  @SuppressWarnings("unchecked")
  public SimpleHashtable(int capacity) {
    if (capacity < 1) {
      throw new IllegalArgumentException(
          "Capacity must be a number greater then 1, but was: " + capacity);
    }
    while (!isPowerOfTwo(capacity)) {
      capacity++;
    }
    this.capacity = capacity;
    table = (TableEntry<K, V>[]) new TableEntry[capacity];
  }

  /**
   * The default constructor for the {@code SimpleHashtable} which creates a table
   * with a capacity of {@value #DEFAULT_CAPACITY}.
   */
  public SimpleHashtable() {
    this(DEFAULT_CAPACITY);
  }

  /** Auxiliary method that checks if the number is a power of two. */
  private boolean isPowerOfTwo(int number) {
    return (number & (number - 1)) == 0;
  }

  /**
   * Returns the number of entries in the table.
   * 
   * @return the number of entries in the table
   */
  public int size() {
    return size;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append('[');

    //@formatter:off
    for (int i = 0; i < capacity; i++) {
      for (TableEntry<K, V> entry = table[i]; entry != null; entry = entry.next) {
        result.append(entry.getKey())
          .append('=')
          .append(entry.getValue())
          .append(", ");
      }
    }
    //@formatter:on
    return result.substring(0, result.length() - 2) + ']';
  }

  /**
   * Method adds a new entry to the end of the {@code SimpleHashtable}. If an
   * entry with the same key already exists, it's value is overwritten with the
   * new value.
   * 
   * @param key
   *          Key of an entry to put
   * @param value
   *          Value of an entry to put
   * @throws NullPointerException
   *           if the key is a {@code null} reference
   */
  public void put(K key, V value) {
    if (key == null) {
      throw new NullPointerException("Key can not be a null reference.");
    }

    if (containsKey(key)) {
      getEntryByKey(key).setValue(value);
      return;
    }

    TableEntry<K, V> entry = new TableEntry<>(key, value);
    int slot = calculateSlot(key);
    if (table[slot] == null) {
      table[slot] = entry;
      size++;
      modificationCount++;
    } else {
      TableEntry<K, V> entryIterator = table[slot];
      while (true) {
        if (entryIterator.next == null) {
          entryIterator.next = entry;
          size++;
          modificationCount++;
          break;
        }

        entryIterator = entryIterator.next;
      }
    }

    if (size >= capacity * MAXIMAL_SIZE_TO_SLOTS_RATIO) {
      increaseCapacity();
    }
  }

  /**
   * Method doubles the capacity of the {@code hashTable} and shifts all elements
   * to their new slots.
   */
  @SuppressWarnings("unchecked")
  private void increaseCapacity() {
    TableEntry<K, V>[] oldTable = table;
    int oldCapacity = capacity;
    do {
      capacity = capacity * 2;
    } while (size >= capacity * MAXIMAL_SIZE_TO_SLOTS_RATIO);
    size = 0;
    table = (TableEntry<K, V>[]) new TableEntry[capacity];

    for (int i = 0; i < oldCapacity; i++) {
      for (TableEntry<K, V> entry = oldTable[i]; entry != null; entry = entry.next) {
        put(entry.getKey(), entry.getValue());
      }
    }
  }

  /**
   * Method gets the value associated with the given key or returns null if the
   * table doesn't contain such a value.
   * 
   * @param key
   *          The key of the value to get
   * @return the value associated with the given key
   */
  public V get(Object key) {
    TableEntry<K, V> entry = getEntryByKey(key);
    return entry == null ? null : entry.getValue();
  }

  /**
   * Auxiliary method that calculates the slot at which an entry with the given
   * key should be stored.
   * 
   * @param key
   *          key of an entry
   * @return the slot at which an entry with the given key should be stored.
   */
  private int calculateSlot(Object key) {
    return abs(key.hashCode()) % capacity;
  }

  /**
   * Returns true if the {@code Hashtable} contains an entry with the given key or
   * false otherwise. The complexity of this method is
   * O(numberOfEntries/capacity).
   * 
   * @param key
   *          key of an entry
   * @return true if the table contains an entry with the given key or false
   *         otherwise
   */
  public boolean containsKey(Object key) {
    if (key == null) {
      return false;
    }
    return getEntryByKey(key) == null ? false : true;
  }

  /**
   * Returns true if the {@code Hashtable} contains an entry with the given value
   * or false otherwise. The complexity of this method is
   * O(numberOfEntries/capacity).
   * 
   * @param value
   *          value of an entry
   * @return true if the table contains an entry with the given value or false
   *         otherwise
   */
  public boolean containsValue(Object value) {
    for (int i = 0; i < table.length; i++) {
      for (TableEntry<K, V> entry = table[i]; entry != null; entry = entry.next) {
        // the first condition is necessary for null reference values
        if (entry.getValue() == value || entry.getValue().equals(value)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Removes the entry with the given key.
   * 
   * @param key
   *          key of an entry
   */
  public void remove(Object key) {
    if (key == null) {
      return;
    }

    int slot = calculateSlot(key);
    TableEntry<K, V> entry = table[slot];

    // Do nothing if the slot is empty
    if (entry == null) {
      return;
    }

    // If the first entry in the slot has the given key replace it
    // with the following one
    if (entry.getKey().equals(key)) {
      table[slot] = entry.next;
      size--;
      modificationCount++;
      return;
    }

    // If any entry in the middle of the slot has the given
    // key replace the reference to that entry with the reference to
    // the following one, garbage collector does the rest
    while (entry.next != null) {
      if (entry.next.getKey().equals(key)) {
        table[slot] = entry.next.next;
        size--;
        modificationCount++;
        return;
      }

      entry = entry.next;
    }
  }

  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Removes all elements from the table with the complexity of O(capacity).
   */
  public void clear() {
    for (int i = 0; i < table.length; i++) {
      table[i] = null;
    }
    size = 0;
    modificationCount++;
  }

  /**
   * Auxiliary method for accessing an entry with the given key.
   * 
   * @param key
   *          key of an entry
   * @return the entry with the given key
   */
  private TableEntry<K, V> getEntryByKey(Object key) {
    if (key == null) {
      return null;
    }
    int slot = calculateSlot(key);
    for (TableEntry<K, V> entry = table[slot]; entry != null; entry = entry.next) {
      if (entry.getKey().equals(key)) {
        return entry;
      }
    }
    return null;
  }

  /**
   * Demo method for this class. Copied from the homework assignment.
   * 
   * @param args
   *          unused command line arguments
   */
  public static void main(String[] args) {
    // create collection:
    SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
    // fill data:
    examMarks.put("Ivana", 2);
    examMarks.put("Ante", 2);
    examMarks.put("Jasna", 2);
    examMarks.put("Kristina", 5);
    examMarks.put("Ivana", 5); // overwrites old grade for Ivana
    // query collection:
    Integer kristinaGrade = examMarks.get("Kristina");
    System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5
    // What is collection's size? Must be four!
    System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4
    System.out.println(examMarks.toString());

    printSeparator();

    // iterator demonstration 1
    for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
      System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
    }

    printSeparator();

    // iterator demonstration 2 (cartesian product)
    for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
      for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
        System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(), pair1.getValue(),
            pair2.getKey(), pair2.getValue());
      }
    }
    
    printSeparator();

    Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
    while (iter.hasNext()) {
      SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
      if (pair.getKey().equals("Ivana")) {
        iter.remove(); // the iterator itself removes the element (no exception expected)
      }
    }
    System.out.println(examMarks); // should write without Ivana
  }

  private static void printSeparator() {
    int delimiterLength = 40;
    for (int i = 0; i < delimiterLength; i++) {
      System.out.print("_");
    }
    System.out.println("\n");
  }

  @Override
  public Iterator<TableEntry<K, V>> iterator() {
    return new IteratorImpl();
  }
}

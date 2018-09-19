package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * Implementation of linked list-backed collection of objects. Duplicate objects
 * are allowed and storage of {@code null} references is not allowed.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class LinkedListIndexedCollection extends Collection {

  private static class ListNode {
    ListNode previous;
    ListNode next;
    Object value;
  }

  int size;
  ListNode first;
  ListNode last;

  public LinkedListIndexedCollection() {
    size = 0;
  }

  public LinkedListIndexedCollection(Collection other) {
    this();
    this.addAll(other);
  }

  /**
   * Returns the current size of this collection.
   * 
   * @return size
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Appends an object to the end of the list.
   * 
   * @throws NullPointerException
   *           if the given {@code Object} is null
   */
  @Override
  public void add(Object value) {
    if (value == null) {
      throw new NullPointerException("Value cannot be null!");
    }

    ListNode newNode = new ListNode();
    newNode.value = value;
    newNode.previous = last;

    if (this.isEmpty()) {
      first = last = newNode;
    } else {
      last.next = newNode;
      last = newNode;
    }
    size++;
  }

  /**
   * Searches for a {@code value} in the list with the complexity O(n).
   */
  @Override
  public boolean contains(Object value) {
    if (value == null || this.isEmpty()) {
      return false;
    }

    for (ListNode node = first; node != null; node = node.next) {
      if (node.value.equals(value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Removes an object from the linked list with the complexity of O(n).
   */
  @Override
  public boolean remove(Object value) {
    if (value == null || this.isEmpty()) {
      return false;
    }

    for (ListNode node = first; node != null; node = node.next) {
      if (node.value.equals(value)) {
        node.previous.next = node.next;
        node.next.previous = node.previous;
        return true;
      }
    }
    return false;
  }

  /**
   * Removes an object at the specified {@code index}.
   * 
   * @param index
   *          position of the object to be removed.
   */
  public void remove(int index) {
    if (index < 0 || index > size - 1) {
      throw new IndexOutOfBoundsException(
          "Index must be between 0 and " + (size - 1) + ", but was " + index);
    }

    remove(get(index));
  }

  /**
   * Constructs an {@code Object} array out of this linked list.
   */
  @Override
  public Object[] toArray() {
    ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
    arrayIndexedCollection.addAll(this);
    return arrayIndexedCollection.toArray();
  }

  /**
   * Calls the processor's {@code process} method on each element in this
   * collection.
   */
  @Override
  public void forEach(Processor processor) {
    for (ListNode node = first; node != null; node = node.next) {
      processor.process(node.value);
    }
  }

  /**
   * Adds all the objects from another collection to this one.
   */
  @Override
  public void addAll(Collection other) {
    class LocalProcessor extends Processor {
      public void process(Object value) {
        add(value);
      }
    }

    Processor processor = (Processor) new LocalProcessor();
    other.forEach(processor);
  }

  /**
   * Removes all references to objects and empties the list.
   */
  @Override
  public void clear() {
    first = null;
    last = null;
    size = 0;
  }

  /**
   * Returns the object that is stored in linked list at position index with a
   * complexity of O(n/2).
   * 
   * @param index
   *          position of the object
   * @return {@code Object}
   */
  public Object get(int index) {
    if (index < 0 || index > size - 1) {
      throw new IndexOutOfBoundsException(
          "Index must be between 0 and " + (size - 1) + ", but was " + index);
    }

    // choosing the optimal iteration side
    if (index < size / 2) {

      ListNode node = first;
      while (index > 0) {
        node = node.next;
        index--;
      }

      return node.value;
    } else {

      ListNode node = last;
      while (index < size - 1) {
        node = node.previous;
        index++;
      }

      return node.value;
    }
  }

  /**
   * Inserts an object in between two other list elements.
   * 
   * @param value
   *          the {@code Object}
   * @param position
   *          index for insertion
   */
  public void insert(Object value, int position) {
    if (position < 0 || position > size) {
      throw new IndexOutOfBoundsException(
          "Index must be between 0 and " + (size - 1) + ", but was " + position);
    }
    ListNode node = first;
    while (position > 0) {
      node = node.next;
      position--;
    }
    ListNode newNode = new ListNode();
    newNode.value = value;
    newNode.previous = node.previous;
    newNode.next = node;
    node.previous.next = newNode;
    node.previous = newNode;
  }

  /**
   * Finds the index of the first occurrence of {@code value} with a complexity
   * O(n).
   * 
   * @param value
   *          {@code Object}
   * @return index of {@code value}
   */
  public int indexOf(Object value) {
    if (value == null || this.isEmpty()) {
      return -1;
    }
    int index = 0;
    for (ListNode node = first; node != null; node = node.next) {
      if (node.value.equals(value)) {
        return index;
      }
      index++;
    }
    return -1;
  }

  /**
   * Demonstration method for this class.
   * 
   * @param args
   *          not used
   */
  @SuppressWarnings("deprecation")
  public static void main(String[] args) {
    ArrayIndexedCollection col = new ArrayIndexedCollection(2);
    col.add(new Integer(20));
    col.add("New York");
    col.add("San Francisco"); // here the internal array is reallocated to 4
    System.out.println(col.contains("New York")); // writes: true
    col.remove(1); // removes "New York"; shifts "San Francisco" to position 1
    System.out.println(col.get(1)); // writes: "San Francisco"
    System.out.println(col.size()); // writes: 2
    col.add("Los Angeles");
    // This is local class representing a Processor which writes objects to
    // System.out
    class P extends Processor {
      public void process(Object o) {
        System.out.println(o);
      }
    }

    System.out.println("col elements:");
    col.forEach(new P());
    System.out.println("col elements again:");
    System.out.println(Arrays.toString(col.toArray()));
    System.out.println("col2 elements:");
    LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col);
    col2.forEach(new P());
    System.out.println("col2 elements again:");
    System.out.println(Arrays.toString(col2.toArray()));
    System.out.println(col.contains(col2.get(1))); // true
    System.out.println(col2.contains(col.get(1))); // true
    col.remove(new Integer(20)); // removes 20 from collection (at position 0).
  }
}

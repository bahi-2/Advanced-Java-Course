package hr.fer.zemris.java.custom.collections;

/**
 * Implementation of resizable array-backed collection of objects. Doesn't
 * permit a {@code null} element. Each instance has a capacity. The capacity is
 * the size of the array used to store the elements in the list. It is always at
 * least as large as the list size.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ArrayIndexedCollection extends Collection {
  private int size;
  private int capacity;
  private Object[] elements;

  /**
   * Construct an array with the capacity equal to 16.
   */
  public ArrayIndexedCollection() {
    capacity = 16;
    size = 0;
    elements = new Object[capacity];
  }

  /**
   * Constructor method that allocates an array with a capacity of
   * initialCapacity.
   * 
   * @param initialCapacity
   *          Maximum size of the array.
   * @throws IllegalArgumentException
   *           If given capacity is less than 1
   */
  public ArrayIndexedCollection(int initialCapacity) {
    this();
    if (initialCapacity < 1) {
      throw new IllegalArgumentException(
          "Capacity must be larger than 1, but was:" + initialCapacity);
    }
    capacity = initialCapacity;
    elements = new Object[capacity];
  }

  /**
   * Constructor method that allocates an array of size equal to initialCapacity
   * and adds elements from the {@code other} collection. Note that if the
   * {@code other} collection has more elements than the value of initialCapacity
   * then the array is allocated with the size of that collection.
   * 
   * @param other
   *          Collection from which elements will be copied into a newly created
   *          array.
   * @param initialCapacity
   *          Maximum size of the array.
   */
  public ArrayIndexedCollection(Collection other, int initialCapacity) {
    this(initialCapacity);
    if (other == null) {
      throw new NullPointerException("The other collection must not be null");
    }
    size = other.size();
    if (initialCapacity < size) {
      elements = new Object[size];
    }
    this.addAll(other);
  }

  /**
   * Gets the current value of the array's size.
   * 
   * @return The number of elements in the array.
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Adds the given object into the first empty place in the elements array. If
   * the array is full it's size is doubled. The average complexity to add an
   * object is O(1), but the average complexity to resize the array is O(n), n
   * being the number of elements in the array.
   * 
   * @param value
   *          Object to add to the array, can't be null.
   * @throws NullPointerException
   *           if the argument {@code value} is null.
   */
  @Override
  public void add(Object value) {
    if (value == null) {
      throw new NullPointerException("The object value must not be null");
    }
    if (capacity < size + 1) {
      capacity = capacity * 2;
      Object[] oldArray = elements;
      elements = new Object[capacity];
      for (int i = 0; i < size; i++) {
        elements[i] = oldArray[i];
      }
    }
    elements[size++] = value;
  }

  /**
   * Returns true if this list contains the specified element. Complexity of this
   * method is O(n).
   * 
   * @param value
   *          The element to search for.
   * @return {@code true} only if the collection contains given value, as
   *         determined by equals method.
   */
  @Override
  public boolean contains(Object value) {
    if (value == null || size == 0) {
      return false;
    }

    for (int i = 0; i < size; i++) {
      if (elements[i].equals(value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Removes the first occurrence of the given element if it exists. Moves all
   * subsequent elements to the left.
   * 
   * @param value
   *          The element to be removed.
   * @return {@code true} if the first occurrence of the given element is removed
   *         from the array or {@code false} if there is no such element in the
   *         array.
   */
  @Override
  public boolean remove(Object value) {
    if (value == null) {
      return false;
    }

    for (int i = 0; i < size; i++) {
      if (elements[i].equals(value)) {
        for (int j = i; j < size - 1; j++) {
          elements[j] = elements[j + 1];
        }
        size--;
        return true;
      }
    }
    return false;
  }

  /**
   * Removes element at specified {@code index} from the array and shifts all
   * subsequent objects one {@code index} forward.
   * 
   * @param index
   *          position at which the object that will be deleted is stored in the
   *          array
   * @return
   */
  public void remove(int index) {
    if (index < 0 || index > size - 1) {
      throw new IndexOutOfBoundsException(
          "Index needed to be between 0 and" + (size - 1) + ", but was " + index);
    }
    for (int i = index; i > 0; i--) {
      elements[i] = elements[i + 1];
    }
    elements[--size] = null;
  }

  /**
   * Allocates a new array with the content from this array and the
   * capacity equal to this array's current size.
   * 
   * @return array of objects
   */
  @Override
  public Object[] toArray() {
    ArrayIndexedCollection result = new ArrayIndexedCollection(size);
    result.addAll(this);
    return result.elements;
  }

  /**
   * Performs an action specified in the given {@code processor} argument on every
   * element in the array.
   * 
   * @param processor
   *          Processor object which provides the process method for each
   *          iteration.
   */
  @Override
  public void forEach(Processor processor) {
    for (int i = 0; i < size; i++) {
      processor.process(elements[i]);
    }
  }

  /**
   * Method adds into the current collection all elements from the given
   * collection.
   * 
   * @param other
   *          Collection from which the elements should be added.
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
   * Removes all elements from this array, by replacing {@code Object}s with null
   * references. The complexity of this method is O(n), n being the size of the
   * array.
   */
  @Override
  public void clear() {
    for (int i = 0; i < size; i++) {
      elements[i] = null;
    }
    size = 0;
  }

  /**
   * Returns the object at position index from this array with the complexity of
   * O(1). Valid indexes are 0 to {@code size}-1.
   * 
   * @param index
   *          Position at which the object is stored in the array.
   * @return object at position index from this array, or -1 if the array is
   *         empty.
   */
  public Object get(int index) {
    if (this.isEmpty()) {
      return -1;
    }
    if (index < 0 || index > size - 1) {
      throw new IndexOutOfBoundsException(
          "Index needed to be between 0 and " + (size - 1) + ", but was " + index);
    }
    return elements[index];
  }

  /**
   * Inserts {@code value} to a given index in the array and shifts all subsequent
   * objects one index forward. Index must be between 0 and {@code size}.
   * 
   * @param value
   *          object to insert
   * @param position
   *          index at which the object will be inserted
   */
  public void insert(Object value, int position) {
    if (position < 0 || position > size) {
      throw new IndexOutOfBoundsException(
          "Index needed to be between 0 and" + size + ", but was " + position);
    }
    for (int i = size; i > position; i--) {
      elements[i] = elements[i - 1];
    }
    elements[position] = value;
  }

  /**
   * Searches the collection and returns the index of the first occurrence of the
   * given value or -1 if the value is not found. The complexity of this method is
   * O(n), n being the size of the array.
   * 
   * @param value
   *          {@code Object} to search for
   * @return index of the first occurrence of the given {@code value}
   */
  public int indexOf(Object value) {
    if (value == null) {
      return -1;
    }

    for (int i = 0; i < size; i++) {
      if (elements[i].equals(value)) {
        return i;
      }
    }
    return -1;
  }

}

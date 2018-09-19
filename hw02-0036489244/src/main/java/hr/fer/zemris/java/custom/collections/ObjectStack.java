package hr.fer.zemris.java.custom.collections;

/**
 * This is the adaptor class for the ArrayIndexedCollection, it "masks" method
 * names to look like stack methods to improve user experience. Please see:
 * https://en.wikipedia.org/wiki/Adapter_pattern
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ObjectStack {
  private ArrayIndexedCollection stack;

  /**
   * Default constructor that initializes the array serving as the stack.
   */
  public ObjectStack() {
    stack = new ArrayIndexedCollection();
  }

  /**
   * Checks if the stack is empty.
   * @return true if the stack is empty, otherwise false.
   */
  public boolean isEmpty() {
    return stack.isEmpty();
  }

  /**
   * Gets the number of elements on the stack.
   * @return size
   */
  public int size() {
    return stack.size();
  }

  /**
   * Push an object on the stack. Complexity O(n).
   * 
   * @param value
   *          value to be pushed on the stack
   * @throws NullPointerException
   *           if the {@code value} is null
   */
  public void push(Object value) {
    stack.add(value);
  }

  /**
   * Pop an object from the stack. Complexity O(n). 
   * 
   * @throws EmptyStackException
   *           if the stack is empty
   */
  public Object pop() {
    Object topOfStack = peek();
    stack.remove(topOfStack);
    return topOfStack;
  }

  /**
   * Peek at an object on the stack. Complexity O(n).
   * 
   * @throws EmptyStackException
   *           if the stack is empty
   */
  public Object peek() {
    Object topOfStack = stack.get(stack.size() - 1);
    if (topOfStack.equals(-1)) {
      throw new EmptyStackException();
    }
    return topOfStack;
  }

  /**
   * Removes all elements from the stack.
   */
  public void clear() {
    stack.clear();
  }
}

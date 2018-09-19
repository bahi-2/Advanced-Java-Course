package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

/**
 * Storage structure that provides a stack for each unique {@link String} key.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ObjectMultistack {

  /** Inner map of stacks. */
  private Map<String, MultistackEntry> stackMap = new HashMap<>();

  /**
   * Pushes a value on the stack at the specified key with the complexity of O(1).
   * 
   * @param name
   *          name of the stack on which to push the value
   * @param valueWrapper
   *          value to push on the stack
   */
  public void push(String name, ValueWrapper valueWrapper) {
    MultistackEntry firstEntry = stackMap.get(name);
    stackMap.put(name, new MultistackEntry(valueWrapper, firstEntry));
  }

  /**
   * Removes the value from top of the stack at the specified key with the
   * complexity of O(1) and returns that value.
   * 
   * @param name
   *          name of the stack from which to take the value
   * @return the value from the top of the stack
   * @throws EmptyStackException
   *           if you try to pop an empty stack
   */
  public ValueWrapper pop(String name) {
    if (isEmpty(name)) {
      throw new EmptyStackException();
    }
    MultistackEntry firstEntry = stackMap.get(name);
    stackMap.put(name, firstEntry.next);
    return firstEntry.value;
  }

  /**
   * Returns the value from top of the stack at the specified key with the
   * complexity of O(1).
   * 
   * @param name
   *          name of the stack from which to take the value
   * @return the value from the top of the stack
   * @throws EmptyStackException
   *           if you try to peek from an empty stack
   */
  public ValueWrapper peek(String name) {
    if (isEmpty(name)) {
      throw new EmptyStackException();
    }
    return stackMap.get(name).value;
  }

  /**
   * Checks if the stack is empty.
   * 
   * @param name
   *          name of the stack
   * @return true if the stack is empty, false otherwise
   */
  public boolean isEmpty(String name) {
    return stackMap.get(name) == null;
  }

  /**
   * Nested class representing one stack entry. It contains a value and a
   * reference to the next entry in the same stack.
   * 
   * @author Blaz Bagic
   *
   */
  private static class MultistackEntry {
    /** Value of the entry. */
    private ValueWrapper value;
    /** Reference to the next entry on the stack. */
    private MultistackEntry next;

    /**
     * Constructor for one entry.
     * 
     * @param value
     *          of the new entry
     * @param next
     *          reference to the next entry on the stack
     */
    private MultistackEntry(ValueWrapper value, MultistackEntry next) {
      this.value = value;
      this.next = next;
    }
  }

}

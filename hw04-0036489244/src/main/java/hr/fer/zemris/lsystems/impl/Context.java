package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class stores fractal states. It provides options of adding, removing and
 * viewing past and current states using a stack structure.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class Context {

  /** Adapter object on which fractal states are stored. */
  private ObjectStack stack;

  /** Constructor initializing an empty context stack. */
  public Context() {
    stack = new ObjectStack();
  }

  /**
   * Method returns the state at the top of the stack.
   * 
   * @return current fractal state
   */
  public TurtleState getCurrentState() {
    return (TurtleState) stack.peek();
  }

  /**
   * Method adds a new state to the top of the stack, making it the current state.
   * 
   * @param state
   * @throws NullPointerException
   *           if the argument <code>state</code> is a null reference
   */
  public void pushState(TurtleState state) {
    if (state == null) {
      throw new NullPointerException("State can not be null!");
    }
    stack.push(state);
  }

  /**
   * Deletes the current fractal state from the top of the stack, thereby making
   * the previous state the current one if it exists.
   */
  public void popState() {
    stack.pop();
  }
}

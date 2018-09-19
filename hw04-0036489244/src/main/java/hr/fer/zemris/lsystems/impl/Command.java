package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * A functional interface representing a command that configures the turtle state.
 * See {@link TurtleState}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public interface Command {
  /**
   * Method performing a state change.
   * 
   * @param ctx
   *          provides a reference to all previously stored fractal states
   * @param painter
   *          provides a method for drawing lines
   */
  void execute(Context ctx, Painter painter);
}

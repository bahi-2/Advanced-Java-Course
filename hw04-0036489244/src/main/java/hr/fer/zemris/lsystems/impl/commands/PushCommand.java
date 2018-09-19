package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Pushes a copy of a turtles current state on the context stack.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class PushCommand implements Command {

  /**
   * Pushes a copy of a turtles current state on the context stack.
   */
  @Override
  public void execute(Context ctx, Painter painter) {
    ctx.pushState(ctx.getCurrentState().getCopy());
  }

}

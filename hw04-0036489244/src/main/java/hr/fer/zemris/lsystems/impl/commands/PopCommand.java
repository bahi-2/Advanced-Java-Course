package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Removes the last turtle state from the context.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class PopCommand implements Command {
  
  /**
   * Removes the last turtle state from the context.
   */
  @Override
  public void execute(Context ctx, Painter painter) {
    ctx.popState();
  }

}

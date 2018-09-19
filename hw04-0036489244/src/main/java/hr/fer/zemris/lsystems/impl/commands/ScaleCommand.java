package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Scales the turtle by a factor.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ScaleCommand implements Command {
  
  /** Scaling factor */
  private double factor;
  
  /** Asigning factor */
  public ScaleCommand(double factor) {
    this.factor = factor;
  }

  /** Scaling the turtle by the factor. */
  @Override
  public void execute(Context ctx, Painter painter) {
    ctx.getCurrentState().getPosition().scale(factor);
  }
  
}

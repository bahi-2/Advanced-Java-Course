package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Moves the turtle in the direction it is facing by step units.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class SkipCommand implements Command {
  
  /** Step by which to move the turtle. */
  private double step;

  /** Assigning the step. */
  public SkipCommand(double step) {
    this.step = step;
  }

  /** Moving the turtle in the direction it is facing by step units. */
  @Override
  public void execute(Context ctx, Painter painter) {
    TurtleState state = ctx.getCurrentState();
    Vector2D positionOld = state.getPosition();
    Vector2D offset = state.getDirection().scaled(step);
    Vector2D positionNew = positionOld.translated(offset);

    ctx.popState();
    ctx.pushState(new TurtleState(positionNew, state.getDirection(), state.getColor(),
        state.getEffectiveUnitLength()));
  }
}

package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Draws a line on the screen from the current position of the turtle in the
 * direction it is facing with the size of <code>step</code>.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class DrawCommand implements Command {

  /** Value by which to move the turtle. */
  private double step;

  /** Constructor assigning the given step value. */
  public DrawCommand(double step) {
    this.step = step;
  }

  /**
   * Changes the turtle's current position and draws a line on the screen from
   * last position to the new position.
   */
  @Override
  public void execute(Context ctx, Painter painter) {
    TurtleState state = ctx.getCurrentState();
    Vector2D positionOld = state.getPosition();
    Vector2D offset = state.getDirection().scaled(step);
    Vector2D positionNew = positionOld.translated(offset);
    float lineSize = 1;

    painter.drawLine(positionOld.getX(), positionOld.getY(), positionNew.getX(), positionNew.getY(),
        state.getColor(), lineSize);
    ctx.popState();
    ctx.pushState(new TurtleState(positionNew, state.getDirection(), state.getColor(),
        state.getEffectiveUnitLength()));
  }
}

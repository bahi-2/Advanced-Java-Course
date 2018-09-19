package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Rotates the turtle.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class RotateCommand implements Command {

  /** Angle by which to rotate the turtle. */
  double angle;

  /** Assign the rotation angle. */
  public RotateCommand(double angle) {
    this.angle = angle;
  }

  /** Rotate the turtle by the angle. */
  @Override
  public void execute(Context ctx, Painter painter) {
    ctx.getCurrentState().getDirection().rotate(toRadians(angle));
  }

  /** Auxiliary method that converts the angle in degrees to radians. */
  private double toRadians(double degrees) {
    return degrees * Math.PI / 180;
  }
}

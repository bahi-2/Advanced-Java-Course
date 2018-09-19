package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Configures the color of the turtle.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ColorCommand implements Command {

  /** Color to set to the turtle. */
  private Color color;
  
  /** Constructor that takes a color which will be set to the turtle */
  public ColorCommand(Color color) {
    this.color = color;
  }
  
  /** Changes the turtles current color */
  @Override
  public void execute(Context ctx, Painter painter) {
    ctx.getCurrentState().setColor(color);
  }

}

package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Contains parameters of the current point of our polygon referred to as the
 * turtle.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class TurtleState {

  /** Current position of our turtle in vector format. */
  private Vector2D position;
  /** Direction at which the turtle is pointed at. */
  private Vector2D direction;
  /** Color of the lines drawn by the turtle. */
  private Color color;
  /** Unidirectional length of turtle movement. */
  private double effectiveUnitLength;

  /**
   * Constructor assigning the following fields.
   * 
   * @param position
   *          Current position of our turtle in vector format
   * @param direction
   *          Direction at which the turtle is pointed at
   * @param color
   *          Color of the lines drawn by the turtle
   * @param effectiveUnitLength
   *          Unidirectional length of turtle movement
   */
  public TurtleState(Vector2D position, Vector2D direction, Color color, double effectiveUnitLength) {
    super();
    this.position = position;
    this.direction = direction;
    this.color = color;
    this.effectiveUnitLength = effectiveUnitLength;
  }

  /**
   * Creates a copy of this object.
   * @return a new object equal to this one
   */
  public TurtleState getCopy() {
    return new TurtleState(position, direction, color, effectiveUnitLength);
  }

  /**
   * Change the color property of the turtle.
   * @param color the new color
   */
  public void setColor(Color color) {
    this.color = color;
  }

  /**
   * Get the current position in vector format.
   * 
   * @return position
   */
  public Vector2D getPosition() {
    return position;
  }

  /**
   * Change the current position of the turtle.
   * @param position the new position
   */
  public void setPosition(Vector2D position) {
    this.position = position;
  }

  /**
   * Get the current direction in vector format.
   * 
   * @return direction
   */
  public Vector2D getDirection() {
    return direction;
  }

  /**
   * Change the direction of the turtle.
   * @param direction The new direction.
   */
  public void setDirection(Vector2D direction) {
    this.direction = direction;
  }

  /**
   * Get the effective unit length of this turtle state.
   * @return effective unit length
   */
  public double getEffectiveUnitLength() {
    return effectiveUnitLength;
  }

  /**
   * Set the effective unit length of this turtle state.
   * @param effectiveUnitLength new effective unit length
   */
  public void setEffectiveUnitLength(double effectiveUnitLength) {
    this.effectiveUnitLength = effectiveUnitLength;
  }

  /**
   * Get the current line color of the turtle.
   * @return color
   */
  public Color getColor() {
    return color;
  }

}

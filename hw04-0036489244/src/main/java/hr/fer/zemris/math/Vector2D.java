package hr.fer.zemris.math;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.abs;

/**
 * Class represents a two dimensional vector and implements basic vector
 * arithmetic.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class Vector2D {

  /**
   * Vector coordinates.
   */
  private double x;
  private double y;

  /**
   * Constructs a vector out of given two dimensional coordinates.
   * 
   * @param x
   *          horizontal coordinate
   * @param y
   *          vertical coordinate
   */
  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  /**
   * Translates a vector by an <code>offset</code>.
   * 
   * @param offset
   *          offset
   */
  public void translate(Vector2D offset) {
    x += offset.getX();
    y += offset.getY();
  }

  /**
   * Allocates a new vector equal to this one translated by an
   * <code>offset</code>.
   * 
   * @param offset
   *          vector offset
   * @return translated vector
   */
  public Vector2D translated(Vector2D offset) {
    Vector2D result = new Vector2D(x, y);
    result.translate(offset);
    return result;
  }

  /**
   * Rotates the vector by <code>angle</code> parameter.
   * 
   * @param angle
   *          Expected to be in radians.
   */
  public void rotate(double angle) {
    double newX = cos(angle) * x - sin(angle) * y;
    y = sin(angle) * x + cos(angle) * y;
    
    x = newX;
  }

  /**
   * Allocates a new vector by rotating this one by the <code>angle</code>
   * parameter.
   * 
   * @param angle
   *          Expected to be in radians.
   * @return rotated vector
   */
  public Vector2D rotated(double angle) {
    Vector2D result = new Vector2D(x, y);
    result.rotate(angle);
    return result;
  }

  /**
   * Rotates the vector by the <code>scaler</code> parameter.
   * 
   * @param scaler
   *          the desired scale
   */
  public void scale(double scaler) {
    x *= scaler;
    y *= scaler;
  }

  /**
   * Allocates a new vector equal to this one scaled by the <code>scaler</code>
   * parameter.
   * 
   * @param scaler
   *          the desired scale
   */
  public Vector2D scaled(double scaler) {
    Vector2D result = new Vector2D(x, y);
    result.scale(scaler);
    return result;
  }

  /**
   * Returns a copy of this <code>Vector2D</code> object.
   * 
   * @return copy of the object
   */
  public Vector2D copy() {
    return new Vector2D(x, y);
  }

  @Override
  public boolean equals(Object obj) {
    final double delta = 1E-10;
    if (obj == null)
      return false;
    if (!(obj instanceof Vector2D))
      return false;
    final Vector2D other = (Vector2D) obj;
    return (abs(other.getX() - x) < delta && abs(other.getY() - y) < delta);
  }

  @Override
  public String toString() {
    char sign = (y > 0) ? '+' : '-';
    return String.format("%.2f %c %.2f", x, sign, abs(y));
  }
}

package hr.fer.zemris.math;

import static java.lang.Math.sqrt;

/**
 * Class represents a three dimensional vector and implements basic vector
 * arithmetic.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class Vector3 {

  /** Vector's x coordinate */
  private double x;
  /** Vector's y coordinate */
  private double y;
  /** Vector's z coordinate */
  private double z;

  /**
   * Constructor which instantiates a vector with given coordinates.
   * 
   * @param x
   *          x coordinate
   * @param y
   *          y coordinate
   * @param z
   *          z coordinate
   */
  public Vector3(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Returns the vector's length.
   * 
   * @return length of this vector
   */
  public double norm() {
    return sqrt(x * x + y * y + z * z);
  }

  /**
   * Returns a normalized vector.
   * 
   * @return normalized vector
   */
  public Vector3 normalized() {
    double length = norm();
    return new Vector3(x / length, y / length, z / length);
  }

  /**
   * Constructs a new vector by adding this one to the other one.
   * 
   * @param other
   *          other vector to add
   * @return a new vector constructed by addition operation
   */
  public Vector3 add(Vector3 other) {
    return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
  }

  /**
   * Constructs a new vector by subtracting this one with the other one.
   * 
   * @param other
   *          other vector to subtract with
   * @return a new vector constructed by the addition operation
   */
  public Vector3 sub(Vector3 other) {
    return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
  }

  /**
   * Returns the scalar product of two vectors.
   * 
   * @param other
   *          vector
   * @return the scalar product of two vectors
   */
  public double dot(Vector3 other) {
    return this.x * other.x + this.y * other.y + this.z * other.z;
  }

  /**
   * Returns the vector product of two vectors.
   * 
   * @param other
   *          vector
   * @return the vector product of two vectors
   */
  public Vector3 cross(Vector3 other) {
    return new Vector3(this.y * other.z - this.z * other.y, this.x * other.z - this.z * other.x,
        this.x * other.y - this.y * other.x);
  }

  /**
   * Constructs a new vector equal to this one scaled by the <code>scaler</code>
   * parameter.
   * 
   * @param scaler
   *          the desired scale
   */
  public Vector3 scale(double s) {
    return new Vector3(x * s, y * s, z * s);
  } // skaliranje zadanim faktorom

  /**
   * Returns the cosine of the angle between this vector and the
   * <code>other<code>.
   * 
   * @param other
   *          vector
   * @return the cosine of the angle between this vector and the <code>other<code>
   */
  public double cosAngle(Vector3 other) {
    return this.dot(other) / (this.norm() * (other.norm()));
  }

  /**
   * Returns the value of coordinate x.
   * 
   * @return the value of coordinate x
   */
  public double getX() {
    return x;
  }

  /**
   * Returns the value of coordinate y.
   * 
   * @return the value of coordinate y
   */
  public double getY() {
    return y;
  }

  /**
   * Returns the value of coordinate z.
   * 
   * @return the value of coordinate z
   */
  public double getZ() {
    return z;
  }

  /**
   * Returns an array out of vectors coordinates.
   * 
   * @return an array out of vectors coordinates
   */
  public double[] toArray() {
    return new double[] { x, y, z };
  }

  @Override
  public String toString() {
    return String.format("(%.6f, %.6f, %.6f)", x, y, z);
  }

  @Override
  public boolean equals(Object other) {

    /** Allowed deviation for double comparison. */
    final double DELTA = 10E-9;
    if (other == null || !(other instanceof Vector3)) {
      return false;
    }
    Vector3 otherVector = (Vector3) other;
    if (Math.abs(this.x - otherVector.x) > DELTA) {
      return false;
    }

    if (Math.abs(this.y - otherVector.y) > DELTA) {
      return false;
    }

    if (Math.abs(this.z - otherVector.z) > DELTA) {
      return false;
    }
    return true;
  }
}

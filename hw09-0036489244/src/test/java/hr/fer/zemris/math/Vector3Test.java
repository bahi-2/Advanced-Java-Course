package hr.fer.zemris.math;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Junit tests for {@link Vector3}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class Vector3Test {

  /** Allowed deviation for double comparison. */
  private static final double DELTA = 10E-9;

  @Test
  public void normTest() {
    assertEquals(Math.sqrt(29), new Vector3(2, 3, 4).norm(), DELTA);
  }

  @Test
  public void normalizeTest() {
    double length = new Vector3(2, 3, 4).norm();
    assertEquals(new Vector3(2 / length, 3 / length, 4 / length),
        new Vector3(2, 3, 4).normalized());
  }

  @Test
  public void cosAngleTest() {
    assertEquals(-1, new Vector3(1, 1, 1).cosAngle(new Vector3(-1, -1, -1)), DELTA);
  }

  @Test
  public void dotTest() {
    assertEquals(12, new Vector3(1, 2, 3).dot(new Vector3(4, -5, 6)), DELTA);
  }

  @Test
  public void crossTest() {
    assertEquals(new Vector3(-15, 2, 39), new Vector3(3, -3, 1).cross(new Vector3(4, 9, 2)));
  }

  @Test
  public void scaleTest() {
    assertEquals(new Vector3(4, -6, 3), new Vector3(-2, 3, -1.5).scale(-2));
  }

  @Test
  public void addTest() {
    assertEquals(new Vector3(-15, 2, 31), new Vector3(0, 1, 15).add(new Vector3(-15, 1, 16)));
  }

  @Test
  public void subTest() {
    assertEquals(new Vector3(40, 2, 1), new Vector3(10, 3, 17).sub(new Vector3(-30, 1, 16)));
  }

  @Test
  public void toArrayTest() {
    assertArrayEquals(new double[] { 1.2, 2.234, -9.12 }, new Vector3(1.2, 2.234, -9.12).toArray(),
        DELTA);
  }

}

package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Blaz Bagic
 * @version 1.0
 */
public class Vector2DTest {

  Vector2D vector;

  @Test
  public void copyTest() {
    vector = new Vector2D(-1, 4);
    assertEquals(vector, vector.copy());
  }

  @Test
  public void translateTest() {
    vector = new Vector2D(1, 1);
    vector.translate(new Vector2D(-2, 1));
    assertEquals(new Vector2D(-1, 2), vector);
  }

  @Test
  public void translatedTest() {
    vector = new Vector2D(12.1, -3);
    Vector2D vector2 = vector.translated(vector);
    assertEquals(new Vector2D(24.2, -6), vector2);
  }

  @Test
  public void rotateTest() {
    vector = new Vector2D(1, 1);
    vector.rotate(Math.PI);
    assertEquals(new Vector2D(-1, -1), vector);
  }

  @Test
  public void rotatedTest() {
    vector = new Vector2D(2, 3.1);
    Vector2D vector2 = vector.rotated(Math.PI / 2);
    assertEquals(new Vector2D(-3.1, 2), vector2);
  }

  @Test
  public void scaleTest() {
    vector = new Vector2D(2, 5.1);
    vector.scale(1.2);
    assertEquals(new Vector2D(2.4, 6.12), vector);
  }

  @Test
  public void scaledTest() {
    vector = new Vector2D(3, 4);
    assertEquals(new Vector2D(9, 12), vector.scaled(3));
  }

}

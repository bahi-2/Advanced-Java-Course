package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Junit tests for {@link ValueWrapper}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ValueWrapperTest {

  public static final double DELTA = 1E-15;

  @Test
  public void testNullWrapper() {
    ValueWrapper v1 = new ValueWrapper(null);
    ValueWrapper v2 = new ValueWrapper(null);
    v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.
    assertEquals(Integer.valueOf(0), v1.getValue());
    assertEquals(null, v2.getValue());
  }

  @Test
  public void addDoubleIntegerTest() {
    ValueWrapper v1 = new ValueWrapper("1.2E1");
    ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
    v1.add(v2.getValue()); // v1 now stores Double(13); v2 still stores Integer(1).
    assertEquals(13.0, (double) v1.getValue(), DELTA);
    assertEquals(Integer.valueOf(1), v2.getValue());
  }

  @Test
  public void addIntegersTest() {
    ValueWrapper v1 = new ValueWrapper("12");
    ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
    v1.add(v2.getValue()); // v1 now stores Integer(13); v2 still stores Integer(1).
    assertEquals(Integer.valueOf(13), v1.getValue());
    assertEquals(Integer.valueOf(1), v2.getValue());
  }

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Test
  public void addInvalidValue() {
    ValueWrapper v1 = new ValueWrapper("Ankica");
    ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
    exception.expect(RuntimeException.class);
    v1.add(v2.getValue()); // throws RuntimeException
  }

  @Test
  public void divideDoubleIntegerTest() {
    ValueWrapper v1 = new ValueWrapper("2.32");
    ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
    v1.divide(v2.getValue());
    assertEquals(2.32, (double) v1.getValue(), DELTA);
  }

  @Test
  public void divideIntegerTest() {
    ValueWrapper v1 = new ValueWrapper("17");
    ValueWrapper v2 = new ValueWrapper(Integer.valueOf(2));
    v1.divide(v2.getValue());
    assertEquals(8, v1.getValue());
  }

  @Test
  public void multiplyTest() {
    ValueWrapper v1 = new ValueWrapper(3.2);
    ValueWrapper v2 = new ValueWrapper(Integer.valueOf(2));
    v1.multiply(v2.getValue());
    assertEquals(6.4, (double) v1.getValue(), DELTA);
  }

  @Test
  public void multiplyWithNullTest() {
    ValueWrapper v1 = new ValueWrapper(3.2);
    ValueWrapper v2 = new ValueWrapper(null);
    v1.multiply(v2.getValue());
    assertEquals(0.0, (double) v1.getValue(), DELTA);
  }

  @Test
  public void subtractTest() {
    ValueWrapper v1 = new ValueWrapper(Double.valueOf(5.2));
    ValueWrapper v2 = new ValueWrapper(Integer.valueOf(2));
    v1.subtract(v2.getValue());
    assertEquals(3.2, (double) v1.getValue(), DELTA);
  }

  @Test
  public void subtractByNullTest() {
    ValueWrapper v1 = new ValueWrapper(Integer.valueOf(7));
    ValueWrapper v2 = new ValueWrapper(null);
    v1.subtract(v2.getValue());
    assertEquals(7, v1.getValue());
  }

  @Test
  public void compareNullObjects() {
    ValueWrapper v1 = new ValueWrapper(null);
    ValueWrapper v2 = new ValueWrapper(null);
    assertTrue(v1.numCompare(v2.getValue()) == 0);
  }

  @Test
  public void compareToItself() {
    ValueWrapper v1 = new ValueWrapper("3.6");
    assertTrue(v1.numCompare(v1.getValue()) == 0);
  }

  @Test
  public void compareNumbers() {
    ValueWrapper v1 = new ValueWrapper(0);
    ValueWrapper v2 = new ValueWrapper(null);
    assertTrue(v1.numCompare(v2.getValue()) == 0);
    v1 = new ValueWrapper("-8");
    v2 = new ValueWrapper(3.2);
    assertTrue(v1.numCompare(v2.getValue()) < 0);
    v1 = new ValueWrapper(Integer.valueOf(17));
    v2 = new ValueWrapper(Integer.valueOf(5));
    assertTrue(v1.numCompare(v2.getValue()) > 0);
  }

}
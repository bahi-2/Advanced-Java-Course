package hr.fer.zemris.java.hw01;

import static org.junit.Assert.assertEquals;
import static hr.fer.zemris.java.hw01.Rectangle.convertToDouble;

import org.junit.Test;

/**
 * @author Blaz Bagic
 * @version 1.0
 */
public class RectangleTest {
	/**
	 * Metoda je JUnit test koji provjerava 4 slucaja za metodu convertToDouble.
	 */
	@Test
	public void convertToDoubleTests() {
		double delta = 1E-6;
		assertEquals(5.2d, convertToDouble("5.2"), delta);
		assertEquals(2345.2342124d, convertToDouble("2345.2342124"), delta);
		assertEquals(2, convertToDouble("2"), delta);
		assertEquals(-1, convertToDouble("nisam_broj"), delta);
	}
}

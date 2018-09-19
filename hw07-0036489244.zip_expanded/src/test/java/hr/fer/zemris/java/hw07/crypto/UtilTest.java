package hr.fer.zemris.java.hw07.crypto;

import static hr.fer.zemris.java.hw07.crypto.Util.byteToHex;
import static hr.fer.zemris.java.hw07.crypto.Util.hexToByte;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.JUnit4;

/**
 * {@link JUnit4} tests for {@link Util} class.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class UtilTest {

  @Test
  public void hexToByteTest() {
    assertArrayEquals(new byte[] { 1, -82, 34 }, hexToByte("01aE22"));
    assertArrayEquals(new byte[] {}, hexToByte(""));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHexToByteTest() {
    hexToByte("a*bc");
  }

  @Test
  public void zeroHexToByteTest() {
    assertArrayEquals(new byte[] { 0, 0 }, hexToByte("0000"));
  }

  @Test
  public void byteToHexTest() {
    assertEquals("01ae22", byteToHex(new byte[] { 1, -82, 34 }));
  }

  @Test
  public void byteToHexTestEmpty() {
    assertEquals("", byteToHex(new byte[] {}));
  }
}

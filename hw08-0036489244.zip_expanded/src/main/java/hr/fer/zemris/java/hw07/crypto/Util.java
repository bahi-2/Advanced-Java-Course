package hr.fer.zemris.java.hw07.crypto;

/**
 * Utility class which provides functionalities of converting a
 * {@code byte array} into a hexadecimal {@link String} representation and
 * reverse.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class Util {

  /** Radix used for hex conversions. */
  public static final int HEX_RADIX = 16;

  /**
   * Converts the given hexadecimal {@link String} representation into an array of
   * bytes using the big endian notation.
   * 
   * @param keyText
   *          hexadecimal {@link String}
   * @return an array of bytes
   */
  public static byte[] hexToByte(String keyText) {
    if (keyText.length() == 0) {
      return new byte[] {};
    }
    if (keyText.length() % 2 != 0) {
      throw new IllegalArgumentException("Invalid hex, odd number of characters.");
    }
    if (!keyText.matches("[0-9a-fA-F]+")) {
      throw new IllegalArgumentException("Invalid characters in the input hex.");
    }

    byte[] result = new byte[keyText.length() / 2];
    for (int i = 0; i < keyText.length(); i += 2) {
      // @formatter:off
      // using singed shift operator for the higher position hex value 
      result[i / 2] = (byte) ((Character.digit(keyText.charAt(i), HEX_RADIX) << 4)
                             + Character.digit(keyText.charAt(i + 1), HEX_RADIX));
    } // @formatter:on
    return result;
  }

  /**
   * Converts a given array of bytes into a hexadecimal {@link String}
   * representation using the big endian notation.
   * 
   * @param keyBytes
   *          an array of bytes
   * @return a hexadecimal {@link String} representation of an array of bytes
   */
  public static String byteToHex(byte[] keyBytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : keyBytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }
}

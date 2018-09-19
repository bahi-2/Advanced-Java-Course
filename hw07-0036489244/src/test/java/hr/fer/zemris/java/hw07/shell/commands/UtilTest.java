package hr.fer.zemris.java.hw07.shell.commands;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for the {@link ShellCommand}'s utility class.
 * 
 * @author Blaz Bagic
 *
 */
public class UtilTest {
  @Test
  public void parseArgumentTest() {
    assertEquals("C:\\\\Documents and Settings\\\\Users\\\\jav\"ko",
        Util.parseArgument("\"C:\\Documents and Settings\\\\Users\\jav\\\"ko\""));
  }

  @Test
  public void splitArgumentsTest() {
    assertArrayEquals(
        new String[] { "C:/Program Files/Program1/info.txt", "C:/tmp/informacije.txt" },
        Util.splitArguments("\"C:/Program Files/Program1/info.txt\" C:/tmp/informacije.txt"));
  }
}

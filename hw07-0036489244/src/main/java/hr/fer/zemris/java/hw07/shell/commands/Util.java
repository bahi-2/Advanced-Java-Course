package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class used by some {@link ShellCommand}s. It supports extracting
 * multiple parameters from a line of text and parsing string literals, this is
 * useful for file-paths.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class Util {

  /**
   * If the given string is enclosed in quotes, then it is considered a string
   * literal, if it is not, no changes are made and the original string is
   * returned. If it is a literal all backslashes are converted to double
   * backslashes, except if they are escaping a quote character.
   * 
   * @param argument
   *          argument to parse
   * @return the parsed argument
   */
  public static String parseArgument(String argument) {
    if (!(argument.startsWith("\"") && argument.endsWith("\""))) {
      return argument;
    }
    argument = argument.substring(1, argument.length() - 1);

    argument = argument.replaceAll("\\\\\"", "\"").replaceAll("\\\\(?=\\\\)", "").replaceAll("\\\\",
        "\\\\\\\\");
    return argument;
  }

  /**
   * Method takes a line of text and extracts arguments into a string array.
   * String literals enclosed in quotes are supported.
   * 
   * @param arguments
   *          line from which to extract shell arguments
   * @return a string array of shell arguments
   */
  public static String[] splitArguments(String arguments) {
    String rx = "[^\"\\s]+|\"(\\\\.|[^\\\\\"])*\"";
    Scanner sc = new Scanner(arguments);
    List<String> result = new ArrayList<>();
    while (sc.hasNext()) {
      result.add(parseArgument(sc.findInLine(rx)));
    }

    sc.close();
    return result.toArray(new String[0]);
  }

}

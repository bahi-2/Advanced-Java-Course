package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser which creates a name based on the expression and an original name.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class NameBuilderParser {

  /**
   * Value represents a start of a group.
   */
  private static final String GROUP_START = "${";
  /**
   * Value represents an end of a group.
   */
  private static final String GROUP_END = "}";
  /**
   * Internal list of {@link NameBuilder} objects.
   */
  private List<NameBuilder> builders = new ArrayList<>();
  /**
   * Internal copy of the regular expression being parsed.
   */
  private String expression;

  /**
   * Constructor which starts the parsing process.
   * 
   * @param expression
   *          regular expression to parse
   */
  public NameBuilderParser(String expression) {
    this.expression = expression;
    while (true) {
      if (!extractString()) {
        break;
      }
      if (!extractGroup()) {
        break;
      }
    }
  }

  /**
   * Returns the {@link NameBuilder} which can be used for getting the parsing
   * result.
   * 
   * @return the {@link NameBuilder} which can be used for getting the parsing
   *         result
   */
  public NameBuilder getNameBuilder() {
    return new NameBuilderFinal(builders);
  }

  /**
   * Extracts a group from an expression.
   * 
   * @return true if the end of expression isn't reached, false otherwise
   */
  private boolean extractGroup() {
    int endIndex = expression.indexOf(GROUP_END);
    if (endIndex == -1) {
      throw new IllegalArgumentException("Group not closed.");
    }
    String[] parts = expression.substring(0, endIndex).split(",");
    this.expression = expression.substring(endIndex + GROUP_END.length());
    int group;
    try {
      group = Integer.parseInt(parts[0].trim());
    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException("Input group is invalid.");
    }
    if (parts.length < 2) {
      builders.add(new NameBuilderGroup(group));
    } else {
      String paddingOptions = parts[1].trim();
      boolean zerosPadding = paddingOptions.startsWith("0");
      int length;
      try {
        length = Integer.parseInt(paddingOptions.trim());
      } catch (NumberFormatException ex) {
        throw new IllegalArgumentException("Input group is invalid.");
      }
      builders.add(new NameBuilderGroup(group, zerosPadding, length));
    }
    return true;
  }

  /**
   * Extracts a string from the expression.
   * 
   * @return true if the end of expression isn't reached, false otherwise
   */
  private boolean extractString() {
    int endIndex = expression.indexOf(GROUP_START);
    if (endIndex == -1) {
      builders.add(new NameBuilderString(expression));
      return false;
    } else {
      builders.add(new NameBuilderString(expression.substring(0, endIndex)));
      this.expression = expression.substring(endIndex + GROUP_START.length());
      return true;
    }
  }
}
package hr.fer.zemris.java.hw07.shell.commands;

import java.util.regex.Matcher;

/**
 * See {@link NameBuilderInfo}
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class NameBuilderInfoImpl implements NameBuilderInfo {

  /**
   * Internal {@link Matcher} object.
   */
  Matcher matcher;
  /**
   * Internal {@link StringBuilder} object used for name construction.
   */
  StringBuilder builder;

  public NameBuilderInfoImpl(Matcher matcher) {
    this.matcher = matcher;
    this.builder = new StringBuilder();
  }

  @Override
  public StringBuilder getStringBuilder() {
    return builder;
  }

  @Override
  public String getGroup(int index) {
    if (index > matcher.groupCount() || index < 0) {
      throw new IllegalArgumentException(
          "Index is is not in range of [0-" + matcher.groupCount() + "], was: " + index);
    }
    return matcher.group(index);
  }

}

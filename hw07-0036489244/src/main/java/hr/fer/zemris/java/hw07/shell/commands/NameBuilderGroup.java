package hr.fer.zemris.java.hw07.shell.commands;

/**
 * {@link NameBuilder} implementation for regular expression capturing groups.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class NameBuilderGroup implements NameBuilder {

  /**
   * Group number in the matcher.
   */
  private int group;
  /**
   * Padding option, if true the padding is done with zeros, otherwise spaces.
   */
  private boolean zerosPadding;
  /**
   * Total length that should be used for formatting this name.
   */
  private int totalLength;

  /**
   * Constructs a group builder with the following parameters.
   * 
   * @param group
   *          group number
   * @param zerosPadding
   *          true for zeroes, false for spaces
   * @param totalLength
   *          total length that should be used for formatting this name.
   */
  public NameBuilderGroup(int group, boolean zerosPadding, int totalLength) {
    this.group = group;
    this.zerosPadding = zerosPadding;
    this.totalLength = totalLength;
  }

  /**
   * Constructor which sets the padding to spaces, and minimal length of the
   * resulting name to 0.
   * 
   * @param group
   *          group number
   */
  public NameBuilderGroup(int group) {
    this(group, false, 0);
  }

  @Override
  public void execute(NameBuilderInfo info) {
    char padding = zerosPadding ? '0' : ' ';
    String text = info.getGroup(group);
    int paddingLength = totalLength - text.length();
    for (int i = 0; i < paddingLength; i++) {
      info.getStringBuilder().append(padding);
    }
    info.getStringBuilder().append(text);
  }

}

package hr.fer.zemris.java.hw07.shell.commands;

/**
 * {@link NameBuilder} containing a constant string.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class NameBuilderString implements NameBuilder {

  /**
   * Internal string to append when executing.
   */
  private final String name;

  /**
   * Constructor which sets the value of name.
   * 
   * @param name
   *          string to append when executing
   */
  public NameBuilderString(String name) {
    this.name = name;
  }

  @Override
  public void execute(NameBuilderInfo info) {
    info.getStringBuilder().append(name);
  }

}

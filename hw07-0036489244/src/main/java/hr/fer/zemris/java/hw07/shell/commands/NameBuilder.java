package hr.fer.zemris.java.hw07.shell.commands;

/**
 * NameBuilder implementations append values to a builder from the
 * {@link NameBuilderInfo}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public interface NameBuilder {

  /**
   * Appends a value to the {@link NameBuilderInfo} internal string builder.
   * 
   * @param info
   */
  void execute(NameBuilderInfo info);

}

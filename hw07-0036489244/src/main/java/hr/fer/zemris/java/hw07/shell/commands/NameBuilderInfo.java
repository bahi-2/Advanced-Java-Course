package hr.fer.zemris.java.hw07.shell.commands;

/**
 * Interface which contains a method to get a reference to a
 * {@link StringBuilder} object used for creating the final name of files and a
 * group value from the index.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public interface NameBuilderInfo {

  /**
   * Returns a {@link StringBuilder} object used for creating the final name of
   * files.
   * 
   * @return a {@link StringBuilder} object used for creating the final name of
   *         files
   */
  StringBuilder getStringBuilder();

  /**
   * Returns a name associated with the index.
   * 
   * @param index
   *          index of the value
   * @return a name associated with the index
   */
  String getGroup(int index);

}

package hr.fer.zemris.java.hw05.db;
/**
 * Filter class for objects of type {@link StudentRecord}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public interface IFilter {
  /**
   * Method returns true if the filter is accepted or false otherwise.
   * @param record record to check
   * @return true if the filter is accepted or false otherwise
   */
  public boolean accepts(StudentRecord record);
}

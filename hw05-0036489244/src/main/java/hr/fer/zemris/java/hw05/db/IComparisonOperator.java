package hr.fer.zemris.java.hw05.db;

/**
 * Functional interface which compares two {@code String} objects.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public interface IComparisonOperator {
  /**
   * Method compares two {@code String} objects based on a criteria that needs to
   * be implemented.
   */
  public boolean satisfied(String value1, String value2);
}

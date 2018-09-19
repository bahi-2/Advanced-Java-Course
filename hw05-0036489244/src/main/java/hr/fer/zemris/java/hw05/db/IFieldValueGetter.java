package hr.fer.zemris.java.hw05.db;

/**
 * Functional interface which is used for getting a field attribute from the
 * conditional expression.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public interface IFieldValueGetter {
  /**
   * Method gets the field value from the {@link StudentRecord}.
   * @param record {@link StudentRecord}
   * @return the field attribute
   */
  default public boolean isJMBAG() {return false;}
  public String get(StudentRecord record);
}

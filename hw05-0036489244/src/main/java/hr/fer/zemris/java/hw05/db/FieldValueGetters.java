package hr.fer.zemris.java.hw05.db;

/**
 * A set of implementations for the {@link IFieldValueGetter}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class FieldValueGetters {
  /**
   * A set of implementations for the {@link IComparisonOperator}, implemented
   * using lambda expressions for brevity.
   */
  public static final IFieldValueGetter FIRST_NAME = (student) -> student.getFirstName();
  public static final IFieldValueGetter LAST_NAME = (student) -> student.getLastName();

  /**
   * Also an implementations for the {@link IComparisonOperator}, but also has a
   * method proving it's a jmbag field query type.
   */
  public static final IFieldValueGetter JMBAG = new IFieldValueGetter() {

    @Override
    public boolean isJMBAG() {
      return true;
    };

    @Override
    public String get(StudentRecord record) {
      return record.getJmbag();
    }
  };
}

package hr.fer.zemris.java.hw05.db;

/**
 * An instance of this class represents one conditional expression. Provides
 * functionalities of getting the field of the expression.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ConditionalExpression {

  /**
   * Functional interface which provides the method for getting the field from the
   * expression.
   */
  private IFieldValueGetter fieldGetter;

  /**
   * The string with which the field of the query is being compared to.
   */
  private String stringLiteral;

  /**
   * Functional interface which provides the method for testing the expression.
   */
  private IComparisonOperator comparisonOperator;

  /**
   * Constructor method initializing the expression through arguments.
   * @param fieldGetter See {@link #fieldGetter}
   * @param stringLiteral See {@link #stringLiteral}
   * @param comparisonOperator See {@link #comparisonOperator}
   */
  public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
      IComparisonOperator comparisonOperator) {
    super();
    this.fieldGetter = fieldGetter;
    this.stringLiteral = stringLiteral;
    this.comparisonOperator = comparisonOperator;
  }

  /**
   * Returns the fieldGetter object.
   * @return the fieldGetter object
   */
  public IFieldValueGetter getFieldGetter() {
    return fieldGetter;
  }

  /**
   * Returns the string literal.
   * @return the string literal
   */
  public String getStringLiteral() {
    return stringLiteral;
  }

  /**
   * Returns the comparison operator.
   * @return the comparison operator
   */
  public IComparisonOperator getComparisonOperator() {
    return comparisonOperator;
  }

  /**
   * Demonstration method for this class, copied from the homework assignment.
   * @param args unused
   */
  public static void main(String[] args) {
    ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*",
        ComparisonOperators.LIKE);
    StudentRecord record = new StudentRecord("0036489244", "Bosla", "Blaž", "5");
    boolean recordSatisfies = expr.getComparisonOperator().satisfied(
        expr.getFieldGetter().get(record), // returns lastName from given record
        expr.getStringLiteral() // returns "Bos*"
    );
    System.out.println(recordSatisfies);
  }

}

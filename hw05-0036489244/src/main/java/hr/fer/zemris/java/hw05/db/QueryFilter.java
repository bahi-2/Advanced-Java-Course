package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Class represents a filter for a query. It is constructed using a list of
 * conditional expressions.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class QueryFilter implements IFilter {

  /**
   * Internal list of expressions used in this filter.
   */
  private List<ConditionalExpression> expressions;

  /**
   * Constructor which sets the expressions list.
   * @param expressions list to use for filtering
   */
  public QueryFilter(List<ConditionalExpression> expressions) {
    this.expressions = expressions;
  }

  @Override
  public boolean accepts(StudentRecord record) {
    for (ConditionalExpression expression : expressions) {
      String field = expression.getFieldGetter().get(record);
      String literal = expression.getStringLiteral();
      if (!expression.getComparisonOperator().satisfied(field, literal)) {
        return false;
      }
    }
    return true;
  }

}

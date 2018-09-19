package hr.fer.zemris.java.hw05.db;

/**
 * A set of implementations for the {@link IComparisonOperator}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ComparisonOperators {

  /**
   * A set of implementations for the {@link IComparisonOperator}, implemented
   * using lambda expressions for brevity.
   */
  public static final IComparisonOperator LESS = (a, b) -> a.compareTo(b) < 0;
  public static final IComparisonOperator LESS_OR_EQUALS = (a, b) -> a.compareTo(b) <= 0;
  public static final IComparisonOperator GREATER = (a, b) -> a.compareTo(b) > 0;
  public static final IComparisonOperator GREATER_OR_EQUALS = (a, b) -> a.compareTo(b) >= 0;;
  public static final IComparisonOperator EQUALS = (a, b) -> a.compareTo(b) == 0;;
  public static final IComparisonOperator NOT_EQUALS = (a, b) -> a.compareTo(b) != 0;;

  /**
   * Implementation of the {@link IComparisonOperator} which compares a string to
   * a pattern that can contain exactly one wildcard symbol (*).
   */
  public static final IComparisonOperator LIKE;
  static {
    LIKE = new IComparisonOperator() {

      @Override
      public boolean satisfied(String string, String pattern) {
        String[] parts = pattern.split("\\*", -1);
        if (parts.length > 2) {
          throw new IllegalArgumentException(
              "The pattern can only contain 1 wildcard character, not " + (pattern.length() - 1));
        }
        if (string.startsWith(parts[0])) {
          String rest = string.substring(parts[0].length());
          if (rest.endsWith(parts[1])) {
            return true;
          }
        }
        return false;

      }
    };
  }

}

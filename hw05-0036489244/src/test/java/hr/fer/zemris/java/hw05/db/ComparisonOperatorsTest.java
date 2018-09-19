package hr.fer.zemris.java.hw05.db;
/**
 * @author Blaz Bagic
 * @version 1.0
 */

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ComparisonOperatorsTest {

  @Test
  public void testLess() {
    IComparisonOperator oper = ComparisonOperators.LESS;
    assertTrue(oper.satisfied("Ana", "Jasna"));
  }

  @Test
  public void testLessEquals() {
    IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
    assertTrue(oper.satisfied("Ana", "Ana"));
  }

  @Test
  public void testGreater() {
    IComparisonOperator oper = ComparisonOperators.GREATER;
    assertFalse(oper.satisfied("Ana", "Jasna"));
  }

  @Test
  public void testGreaterEquals() {
    IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
    assertTrue(oper.satisfied("Ana", "Ana"));
  }

  @Test
  public void testEquals() {
    IComparisonOperator oper = ComparisonOperators.EQUALS;
    assertTrue(oper.satisfied("Ana", "Ana"));
  }

  @Test
  public void testNotEquals() {
    IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
    assertFalse(oper.satisfied("Ana", "Ana"));
  }

  @Test
  public void likeTests() {
    IComparisonOperator oper = ComparisonOperators.LIKE;
    assertFalse(oper.satisfied("Zagreb", "Aba*"));
    assertFalse(oper.satisfied("AAA", "AA*AA"));
    assertTrue(oper.satisfied("AAAA", "AA*AA"));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void likeMultipleWildCardTest() {
    IComparisonOperator oper = ComparisonOperators.LIKE;
    oper.satisfied("some-string", "invalid*pattern*");
  }
}

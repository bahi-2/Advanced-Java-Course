package hr.fer.zemris.java.gui.layouts;

/**
 * {@link RCPosition} stands for row-column position which are used for adding
 * elements to the {@link CalcLayout}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class RCPosition {

  /** Row in the layout. */
  private final int row;
  /** Column in the layout. */
  private final int column;

  /**
   * Default constructor setting the value to the read-only position properties.
   * 
   * @param row
   *          row number
   * @param column
   *          column number
   */
  public RCPosition(int row, int column) {
    super();
    this.row = row;
    this.column = column;
  }

  /**
   * Returns the row number.
   * 
   * @return the row number
   */
  public int getRow() {
    return row;
  }

  /**
   * Returns the column number.
   * 
   * @return the column number
   */
  public int getColumn() {
    return column;
  }

  @Override
  public String toString() {
    return "[" + row + "," + column + "]";
  }
}

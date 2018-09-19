package hr.fer.zemris.java.gui.charts;

/**
 * @author Blaz Bagic
 * @version 1.0
 */
public class XYValue {
  private final int x;
  private final int y;

  public XYValue(int x, int y) {
    super();
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  @Override
  public String toString() {
    return "[" + x + " " + y + "]";
  }
}

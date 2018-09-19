package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * @author Blaz Bagic
 * @version 1.0
 */
public class BarChart {
  private List<XYValue> values;
  private String xInfo;
  private String yInfo;
  private int minY;
  private int maxY;
  private int spacing;

  public BarChart(List<XYValue> values, String xInfo, String yInfo, int minY, int maxY,
      int spacing) {
    if (spacing > (maxY - minY) || spacing == 0) {
      throw new IllegalArgumentException("Spacing is invalid, was: " + spacing);
    }
    while ((maxY - minY) % spacing != 0) {
      maxY++;
    }
    this.values = values;
    this.xInfo = xInfo;
    this.yInfo = yInfo;
    this.minY = minY;
    this.maxY = maxY;
    this.spacing = spacing;
  }

  public List<XYValue> getValues() {
    return values;
  }

  public String getxInfo() {
    return xInfo;
  }

  public String getyInfo() {
    return yInfo;
  }

  public int getMinY() {
    return minY;
  }

  public int getMaxY() {
    return maxY;
  }

  public int getSpacing() {
    return spacing;
  }
  
}

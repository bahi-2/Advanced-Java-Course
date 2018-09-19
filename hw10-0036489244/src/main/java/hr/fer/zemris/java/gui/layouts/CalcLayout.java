package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link CalcLayout} is a layout manager intended to be used for displaying a
 * calculator. The component at the first position in this layout is wider than
 * the other ones and is intended to contain the output of the calculator's
 * computations. All other components have the same dimensions, which are equal
 * to the preferred dimension of the biggest component in the layout.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class CalcLayout implements LayoutManager2 {

  /** Maximum number of rows in a layout. */
  private static final int NUMBER_OF_ROWS = 5;

  /** Maximum number of columns in a layout. */
  private static final int NUMBER_OF_COLUMNS = 7;

  /** Starting row and column position. */
  private static final int MIN_POSITION = 1;

  /** Spacing between subsequent components. */
  private int spacing;

  /**
   * Internal map of all components in this layout manager along with their
   * positions.
   */
  private Map<Component, RCPosition> components;

  /**
   * Width of every component except of the one at the first position. Calculated
   * as the biggest of all preferred component widths.
   */
  private int elementWidth;

  /**
   * Height of every component. Calculated as the biggest of all preferred
   * component heights.
   */
  private int elementHeight;

  /**
   * Minimal width to fill the contents of the fixed size frame.
   */
  private int minimalWidth;

  /**
   * Minimal width to fill the contents of the fixed size frame.
   */
  private int minimalHeight;

  /**
   * Empty layout manager constructor which sets the spacing between components to
   * zero.
   */
  public CalcLayout() {
    this(0);
  }

  /**
   * Constructor for the layout manager which sets the spacing between components
   * to the spacing given in the argument.
   * 
   * @param spacing
   *          spacing between components
   */
  public CalcLayout(int spacing) {
    this.components = new HashMap<>();
    this.spacing = spacing;
  }

  @Override
  public void addLayoutComponent(Component component, Object constraints) {
    if (constraints instanceof RCPosition) {
      components.put(component, validateConstraints(constraints));
    } else if (constraints instanceof String) {
      components.put(component, validateConstraints(parseConstraints((String) constraints)));
    } else {
      throw new CalcLayoutException();
    }
  }

  @Override
  public void addLayoutComponent(String str, Component component) {
  }

  @Override
  public float getLayoutAlignmentX(Container container) {
    return 0.5f;
  }

  @Override
  public float getLayoutAlignmentY(Container container) {
    return 0.5f;
  }

  @Override
  public void invalidateLayout(Container container) {
  }

  @Override
  public void layoutContainer(Container container) {
    if (components.size() == 0) {
      return;
    }
    Insets ins = container.getInsets();
    minimalHeight = (container.getHeight() / NUMBER_OF_ROWS) - (ins.left + ins.right) / 2;
    minimalWidth = (container.getWidth() / NUMBER_OF_COLUMNS) - (ins.top + ins.bottom) / 2;
    setSizes(container.getComponents());
    for (Component comp : components.keySet()) {
      RCPosition position = components.get(comp);
      Rectangle rect = new Rectangle();

      int width = Math.max(minimalWidth, elementWidth);
      int height = Math.max(minimalHeight, elementHeight);

      rect.x = (position.getColumn() - 1) * (width + spacing) + (ins.left + ins.right) / 2;
      rect.y = (position.getRow() - 1) * (height + spacing) + (ins.top + ins.bottom) / 2;
      rect.height = height;

      if (isFirstPosition(position)) {
        rect.width = width * 5 + spacing * 4;
      } else {
        rect.width = width;
      }
      comp.setBounds(rect);
    }
  }

  @Override
  public Dimension maximumLayoutSize(Container container) {
    return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE); // pitaj za ovo
  }

  @Override
  public Dimension minimumLayoutSize(Container container) {
    return preferredLayoutSize(container);
  }

  /**
   * Parses a {@link String} of a format [number,number] into a {@link RCPosition}
   * object.
   * 
   * @throws IllegalArgumentException
   *           in case the {@link String} is not in a valid format
   * @param position
   *          {@link String} to be parsed
   * @return an {@link RCPosition} constraint
   */
  private RCPosition parseConstraints(String position) {
    Pattern pattern = Pattern.compile("([0-9])\\,([0-9])");
    Matcher matcher = pattern.matcher(position);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Incorrect constraint format!");
    }
    return new RCPosition(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
  }

  @Override
  public Dimension preferredLayoutSize(Container container) {
    Dimension dim = new Dimension(0, 0);

    setSizes(container.getComponents());

    Insets insets = container.getInsets();
    dim.width = NUMBER_OF_COLUMNS * (elementWidth + spacing) - spacing + insets.left + insets.right;
    dim.height = NUMBER_OF_ROWS * (elementHeight + spacing) - spacing + insets.top + insets.bottom;

    return dim;
  }

  @Override
  public void removeLayoutComponent(Component component) {
    components.remove(component);
  }

  /**
   * Finds the highest width and height from an array of components and assigns
   * the appropriate member variables.
   * 
   * @param comp
   *          array of components
   */
  private void setSizes(Component[] comp) {
    for (int i = 0; i < comp.length; i++) {
      Component c = comp[i];

      if (c.isVisible()) {
        Dimension preferred = c.getPreferredSize();
        if (isFirstPosition(components.get(c))) {
          elementWidth = (int) Math.max((preferred.getWidth() - 4 * spacing) / 5, elementWidth);
        } else {
          elementWidth = (int) Math.max(preferred.getWidth(), elementWidth);
        }
        elementHeight = (int) Math.max(preferred.getHeight(), elementHeight);
      }
    }
  }

  /**
   * Method expects an object which can be cast to {@link RCPosition} and checks
   * if the position constraints are valid.
   * 
   * @throws CalcLayoutException
   *           in case the position constraints are invalid or duplicate
   * @param constraint
   *          an object which can be cast to {@link RCPosition}
   * @return a valid {@link RCPosition} object
   */
  private RCPosition validateConstraints(Object constraint) {
    RCPosition position = (RCPosition) constraint;

    //@formatter:off
    int c = position.getColumn();
    int r = position.getRow();
    if (c > NUMBER_OF_COLUMNS ||
        c < MIN_POSITION ||
        r > NUMBER_OF_ROWS ||
        r < MIN_POSITION ||
        r == MIN_POSITION && (c > MIN_POSITION && c < 5)) {
      throw new CalcLayoutException("Invalid position: " + position);
    } 
    //@formatter:on

    for (RCPosition pos : components.values()) {
      if (pos.getRow() == position.getRow() && pos.getColumn() == position.getColumn()) {
        throw new CalcLayoutException("Duplicate position: " + position);
      }
    }

    return position;
  }

  /**
   * Returns true if the position is the first one in the layout (i.e. [1,1]).
   * 
   * @param position
   *          position constraint
   * @return true if the position is the first one in the layout, false otherwise
   */
  private boolean isFirstPosition(RCPosition position) {
    return position.getColumn() == MIN_POSITION && position.getRow() == MIN_POSITION;
  }

}

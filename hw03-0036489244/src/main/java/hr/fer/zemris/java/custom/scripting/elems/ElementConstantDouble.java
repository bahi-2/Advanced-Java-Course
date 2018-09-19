package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class representing double value node element.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ElementConstantDouble extends Element {
  private final double value;

  public ElementConstantDouble(double value) {
    this.value = value;
  }

  @Override
  public String asText() {
    return String.valueOf(value);
  }
}

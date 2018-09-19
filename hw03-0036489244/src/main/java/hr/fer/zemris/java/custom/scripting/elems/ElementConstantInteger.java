package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class representing int value node element.
 * @author Blaz Bagic
 * @version 1.0
 */
public class ElementConstantInteger extends Element {
  private final int value;

  public ElementConstantInteger(int value) {
    this.value = value;
  }

  @Override
  public String asText() {
    return String.valueOf(value);
  }
}

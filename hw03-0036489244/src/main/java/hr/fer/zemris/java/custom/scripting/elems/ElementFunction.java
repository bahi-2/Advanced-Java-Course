package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class representing string value node element.
 * @author Blaz Bagic
 * @version 1.0
 */
public class ElementFunction extends Element {
  private final String name;

  public ElementFunction(String name) {
    this.name = name;
  }

  @Override
  public String asText() {
    return name;
  }
}

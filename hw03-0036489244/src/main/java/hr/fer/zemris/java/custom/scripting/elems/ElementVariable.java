package hr.fer.zemris.java.custom.scripting.elems;

/**
 * 
 * Class representing a String value node element variable.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ElementVariable extends Element {
  private final String name;

  public ElementVariable(String name) {
    this.name = name;
  }

  @Override
  public String asText() {
    return name;
  }
}

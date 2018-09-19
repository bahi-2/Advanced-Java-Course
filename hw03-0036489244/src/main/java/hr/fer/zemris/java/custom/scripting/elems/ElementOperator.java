package hr.fer.zemris.java.custom.scripting.elems;
/**
 * 
 * Class representing a String value node operator element.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ElementOperator extends Element {
  private final String name;
  
  public ElementOperator(String name) {
    this.name = name;
  }
  
  @Override
  public String asText() {
    return name;
  }
}

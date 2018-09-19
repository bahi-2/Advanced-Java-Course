package hr.fer.zemris.java.custom.scripting.elems;
/**
 * 
 * Class representing a String value node element.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ElementString extends Element {
  private final String symbol;
  
  public ElementString(String value) {
    this.symbol = value;
  }
  
  @Override
  public
  String asText() {
    return symbol;
  }
}

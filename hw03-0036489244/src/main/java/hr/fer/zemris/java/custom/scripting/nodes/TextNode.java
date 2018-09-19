package hr.fer.zemris.java.custom.scripting.nodes;
/**
 * @author Blaz Bagic
 * @version 1.0
 */
public class TextNode extends Node {
  private final String text;
  
  public TextNode(String text) {
    this.text = text;
  }
  
  public String getText() {
    return text;
  }
  
  @Override
  public String toString() {
    return text;
  }
}

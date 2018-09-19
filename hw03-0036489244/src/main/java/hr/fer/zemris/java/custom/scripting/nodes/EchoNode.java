package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * @author Blaz Bagic
 * @version 1.0
 */
public class EchoNode extends Node {
  private Element[] elements;
  
  public EchoNode(Element[] elements) {
    this.elements = elements;
  }
  public Element[] getElements() {
    return elements;
  }
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < elements.length; i++) {
      sb.append(elements[i].asText());
    }
    return sb.toString();
  }
}

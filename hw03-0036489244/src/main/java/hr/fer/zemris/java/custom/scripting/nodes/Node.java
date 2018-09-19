package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * @author Blaz Bagic
 * @version 1.0
 */
public class Node {
  private ArrayIndexedCollection children;
  private boolean firstCall = true;

  public void addChildNode(Node child) {
    if (firstCall) {
      children = new ArrayIndexedCollection();
      firstCall = false;
    }
    children.add(child);
  }

  public int numberOfChildren() {
    if (children == null) {
      return 0;
    }
    return children.size();
  }

  public Node getChild(int index) {
    return (Node) children.get(index);
  }
  
}

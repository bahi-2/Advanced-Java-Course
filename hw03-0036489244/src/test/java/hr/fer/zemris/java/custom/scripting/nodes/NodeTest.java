package hr.fer.zemris.java.custom.scripting.nodes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Blaz Bagic
 * @version 1.0
 */
public class NodeTest {

  @Test
  public void testSizeOnNullCollection() {
    Node node = new Node();
    assertEquals(0, node.numberOfChildren());
  }
  
  @Test
  public void testNumberOfChildren() {
    Node node = new Node();
    node.addChildNode(new Node());
    node.addChildNode(new Node());
    assertEquals(2, node.numberOfChildren());
  }
  
  @Test(expected=IndexOutOfBoundsException.class)
  public void testGetOutOfBounds() {
    Node node = new Node();
    node.addChildNode(new Node());
    node.getChild(5);
  }
  
}

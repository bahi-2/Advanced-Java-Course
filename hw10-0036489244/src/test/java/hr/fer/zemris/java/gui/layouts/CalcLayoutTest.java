package hr.fer.zemris.java.gui.layouts;

import static org.junit.Assert.assertEquals;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Test;

/**
 * Unit tests for the {@link CalcLayout} class.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class CalcLayoutTest {

  @Test(expected = CalcLayoutException.class)
  public void invalidConstraintTest() {
    JPanel p = new JPanel(new CalcLayout());
    p.add(new JLabel(), new RCPosition(0, 3));
  }

  @Test(expected = CalcLayoutException.class)
  public void invalidConstraintTest2() {
    JPanel p = new JPanel(new CalcLayout());
    p.add(new JLabel(), new RCPosition(1, 9));
  }

  @Test(expected = CalcLayoutException.class)
  public void invalidConstraintTest3() {
    JPanel p = new JPanel(new CalcLayout());
    p.add(new JLabel(), new RCPosition(1, 3));
  }

  @Test(expected = CalcLayoutException.class)
  public void duplicateConstraint() {
    JPanel p = new JPanel(new CalcLayout());
    p.add(new JLabel(), new RCPosition(1, 5));
    p.add(new JLabel(), new RCPosition(1, 5));
  }

  @Test
  public void preferredDimensionTest() {
    JPanel p = new JPanel(new CalcLayout(2));
    JLabel l1 = new JLabel("");
    l1.setPreferredSize(new Dimension(10, 30));
    JLabel l2 = new JLabel("");
    l2.setPreferredSize(new Dimension(20, 15));
    p.add(l1, new RCPosition(2, 2));
    p.add(l2, new RCPosition(3, 3));
    Dimension dim = p.getPreferredSize();

    assertEquals(new Dimension(152, 158), dim);
  }

   @Test
   public void preferredDimensionTest2() {
   JPanel p = new JPanel(new CalcLayout(2));
   JLabel l1 = new JLabel("");
   l1.setPreferredSize(new Dimension(108, 15));
   JLabel l2 = new JLabel("");
   l2.setPreferredSize(new Dimension(16, 30));
   p.add(l1, new RCPosition(1, 1));
   p.add(l2, new RCPosition(3, 3));
   Dimension dim = p.getPreferredSize();
  
   assertEquals(new Dimension(152, 158), dim);
   }

}

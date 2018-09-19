package hr.fer.zemris.java.gui.calc;

/**
 * Following the observer pattern this functional interface is used for
 * observing the current value of the calculator. When said value is changed
 * this interface's method is invoked.
 * 
 * @author Blaz Bagic
 */
@FunctionalInterface
public interface CalcValueListener {
  /**
   * When the value of the calculator is changed this method is called.
   * 
   * @param model
   */
  void valueChanged(CalcModel model);
}
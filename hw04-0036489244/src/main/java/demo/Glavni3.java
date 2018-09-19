package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Demonstration of the Lindenmayer system program. Browse for a configuration
 * file. You can find samples in the example folder from the root directory.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class Glavni3 {
  /**
   * Main program starting the graphic unit interface.
   * @param args Unused here
   */
  public static void main(String[] args) {
    LSystemViewer.showLSystem(LSystemBuilderImpl::new);
  }
}

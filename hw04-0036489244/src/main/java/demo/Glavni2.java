package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Demonstration of the Lindenmayer system program by displaying Koch Curve.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class Glavni2 {
  
  /**
   * Main program starting the graphic unit interface.
   * @param args Unused here
   */
  public static void main(String[] args) {
    LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
  }
  
  /**
   * Configures a Lindermayer system to a Koch curve.
   * 
   * @param provider
   *          models an object that can create and configure a Lindermayer system
   * @return Lindermayer system presented in a graphic unit interface
   */
  private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
    String[] data = new String[] {
      "origin 0.05 0.4",
      "angle 0",
      "unitLength 0.9",
      "unitLengthDegreeScaler 1.0 / 3.0",
      "",
      "command F draw 1",
      "command + rotate 60",
      "command - rotate -60",
      "",
      "axiom F",
      "",
      "production F F+F--F+F"
    };
    return provider.createLSystemBuilder().configureFromText(data).build();
  }
}

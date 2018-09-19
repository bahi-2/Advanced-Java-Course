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
public class Glavni1 {
  /**
   * Main program starting the graphic unit interface.
   * 
   * @param args
   *          Unused here
   */
  public static void main(String[] args) {
    LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));
  }

  /**
   * Configures a Lindermayer system to a Koch curve.
   * 
   * @param provider
   *          models an object that can create and configure a Lindermayer system
   * @return Lindermayer system presented in a graphic unit interface
   */
  private static LSystem createKochCurve(LSystemBuilderProvider provider) {
    return provider.createLSystemBuilder()
        .registerCommand('F', "draw 1")
        .registerCommand('+', "rotate 60")
        .registerCommand('-', "rotate -60")
        .setOrigin(0.05, 0.4)
        .setAngle(0).setUnitLength(0.9)
        .setUnitLengthDegreeScaler(1.0 / 3.0)
        .registerProduction('F', "F+F--F+F")
        .setAxiom("F")
        .build();
  }
}

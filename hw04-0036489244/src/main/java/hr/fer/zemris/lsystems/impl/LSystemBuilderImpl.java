package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Scanner;
import static java.lang.Math.pow;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Implementation of a builder class that models configurable Lindenmayer system
 * objects. After the configuration is complete calling the build method creates
 * a <code>LSystem</code> object. See {@link LSystemImpl}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class LSystemBuilderImpl implements LSystemBuilder {

  /** Map instance for storing a collection of production rules. */
  private Dictionary productions;

  /** Map instance for storing a collection of commands tied to a symbol. */
  private Dictionary commands;

  /** Unit for the smallest possible move the turtle can make. */
  double unitLength = 0.1;

  /**
   * This value is used for calculating the effective movement unit of the turtle.
   */
  double unitLengthDegreeScaler = 1;

  /**
   * Origin of the coordinate system. The initial value is set to the bottom-left
   * side of the screen.
   */
  Vector2D origin = new Vector2D(0, 0);

  /** Angle at which the turtle is pointed to. */
  double angle = 0;

  /** A string of characters to be expanded by the fractal generation process. */
  String axiom = "";

  /**
   * Constructor that initializes the productions and commands dictionaries which
   * are used in the configuration process.
   */
  public LSystemBuilderImpl() {
    productions = new Dictionary();
    commands = new Dictionary();
  }

  /**
   * This class represents an L-system or Lindenmayer system which is a parallel
   * rewriting system and a type of formal grammar and uses it to build
   * self-simmilar fractals.
   * 
   * @see <a href="https://en.wikipedia.org/wiki/L-system">
   *      wikipedia.org/wiki/L-system
   * @author Blaz Bagic
   *
   */
  private class LSystemImpl implements LSystem {

    /**
     * Creates a new context and pushes an initial state of the turtle on it. Then
     * it starts the fractal generation process, based on the depth chosen in the
     * GUI and draws the correct shapes on that GUI.
     * 
     * @param depth
     *          the number of repetitions of applying productions to the axiom.
     * @param painter
     *          provides a method for drawing lines
     */
    @Override
    public void draw(int depth, Painter painter) {
      Context context = new Context();
      Vector2D unitVector = new Vector2D(1, 0);
      double effectiveUnitLength = unitLength * pow(unitLengthDegreeScaler, depth);
      TurtleState state = new TurtleState(origin, unitVector, Color.black, effectiveUnitLength);

      context.pushState(state);

      String oldAxiom = axiom;
      String generatedAxiom = generate(depth); // the generate method changes the axiom
      axiom = oldAxiom; // Reverting to the configured axiom

      for (char c : generatedAxiom.toCharArray()) {
        String command = (String) commands.get(c);
        if (command != null) {
          performAction(command, depth, painter, context);
        }
      }
    }

    /**
     * Generation of a new axiom based on the current axiom and the level of
     * generation. The new axiom is created by applying the preconfigured
     * productions the current one.
     * 
     * @param level
     *          the number of times the productions are applied to the axiom
     * @return an expended axiom
     */
    @Override
    public String generate(int level) {
      if (level == 0) {
        return axiom;
      }

      StringBuilder sb = new StringBuilder();
      for (char c : axiom.toCharArray()) {
        String production = (String) productions.get(c);
        if (production == null) {
          sb.append(c);
        } else {
          sb.append(production);
        }
      }
      axiom = sb.toString();
      return generate(level - 1);
    }
  }

  /**
   * Returns the Lindenmayer system instance with the current configuration.
   * 
   * @return <code>LSystem</code> object
   */
  @Override
  public LSystem build() {
    return new LSystemImpl();
  }

  /**
   * Parses an array of strings into configuration for this class.
   * 
   * @param text
   *          Configuration array
   * @return reference to the current instance of this class
   */
  @Override
  public LSystemBuilder configureFromText(String[] text) {
    for (String line : text) {
      if (!line.trim().isEmpty()) {
        addDirective(line);
      }
    }

    return this;
  }

  /**
   * Auxiliary method for configuring the appropriate class variables.
   * 
   * @param line
   *          One line of configuration text
   */
  private void addDirective(String line) {
    Scanner scanner = new Scanner(line);
    String directive = scanner.next();

    switch (directive) {
      case "origin":
        setOrigin(scanner.nextDouble(), scanner.nextDouble());
        break;
      case "angle":
        setAngle(scanner.nextDouble());
        break;
      case "unitLength":
        setUnitLength(scanner.nextDouble());
        break;
      case "axiom":
        setAxiom(scanner.next());
        break;
      case "production":
        registerProduction(scanner.next(".").charAt(0), scanner.nextLine());
        break;
      case "command":
        registerCommand(scanner.next(".").charAt(0), scanner.nextLine());
        break;
      case "unitLengthDegreeScaler":
        double scaler = scanner.nextDouble();
        if (scanner.hasNext()) {
          scanner.skip(".*/");
          scaler = scaler / scanner.nextDouble();
        }
        setUnitLengthDegreeScaler(scaler);
        break;

      default:
        scanner.close();
        throw new IllegalArgumentException("Wrong directive. Was: " + directive);
    }
    scanner.close();
  }

  /**
   * Auxiliary method for performing the appropriate action based on the following
   * parameters.
   * 
   * @param line
   *          A line of text containing the name of the action to be taken.
   * @param depth
   *          generation level
   * @param painter
   *          provides a method for drawing lines
   * @param context
   *          provides a reference to all previously stored fractal states
   */
  private void performAction(String line, double depth, Painter painter, Context context) {
    Scanner scanner = new Scanner(line);
    String action = scanner.next();

    double effectiveUnitLength = context.getCurrentState().getEffectiveUnitLength();
    double unitsNumber;
    double step;

    switch (action) {
      case "draw":
        unitsNumber = scanner.nextDouble();
        step = unitsNumber * effectiveUnitLength;
        new DrawCommand(step).execute(context, painter);
        break;
      case "skip":
        unitsNumber = scanner.nextDouble();
        step = unitsNumber * effectiveUnitLength;
        new SkipCommand(step).execute(context, painter);
        break;
      case "scale":
        double factor = scanner.nextDouble();
        new ScaleCommand(factor).execute(context, painter);
        break;
      case "rotate":
        double angle = scanner.nextDouble();
        new RotateCommand(angle).execute(context, painter);
        break;
      case "push":
        new PushCommand().execute(context, painter);
        break;
      case "pop":
        new PopCommand().execute(context, painter);
        break;
      case "color":
        Color color = new Color(scanner.nextInt(16));
        new ColorCommand(color).execute(context, painter);
        break;

      default:
        scanner.close();
        throw new IllegalArgumentException("Wrong command. Was: " + action);
    }
    scanner.close();
  }

  /**
   * Link a command to a given symbol.
   * 
   * @param c
   *          symbol
   * @param command
   *          command to be linked
   * @return reference to the current instance of this class
   */
  @Override
  public LSystemBuilder registerCommand(char c, String command) {
    commands.put(c, command);
    return this;
  }

  /**
   * Link a production to a given symbol.
   * 
   * @param c
   *          symbol
   * @param production
   *          production to be linked
   * @return reference to the current instance of this class
   */
  @Override
  public LSystemBuilder registerProduction(char c, String production) {
    productions.put(c, production);
    return this;
  }

  /**
   * Change the angle of the turtle.
   * 
   * @param angle
   *          angle in degrees
   * @return reference to the current instance of this class
   */
  @Override
  public LSystemBuilder setAngle(double angle) {
    this.angle = angle;
    return this;
  }

  /**
   * Set the axiom for this L system.
   * 
   * @param axiom
   *          the new value
   * @return reference to the current instance of this class
   */
  @Override
  public LSystemBuilder setAxiom(String axiom) {
    this.axiom = axiom;
    return this;
  }

  /**
   * Set the origin of the coordinate system to (x,y).
   * 
   * @param x
   *          new horizontal coordinate
   * @param y
   *          new vertical coordinate
   * @return reference to the current instance of this class
   */
  @Override
  public LSystemBuilder setOrigin(double x, double y) {
    this.origin = new Vector2D(x, y);
    return this;
  }

  /**
   * Sent the length of a turtle's unit of movement.
   * 
   * @return reference to the current instance of this class
   */
  @Override
  public LSystemBuilder setUnitLength(double unitLength) {
    this.unitLength = unitLength;
    return this;
  }

  /**
   * Sent the length of a turtle's unit of movement degree scaler.
   * 
   * @return reference to the current instance of this class
   */
  @Override
  public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
    this.unitLengthDegreeScaler = unitLengthDegreeScaler;
    return this;
  }

}

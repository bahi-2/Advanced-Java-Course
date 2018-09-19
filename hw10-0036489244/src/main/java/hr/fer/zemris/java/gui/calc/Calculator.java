package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * This class contains a main method which invokes a graphical interface. This
 * graphical interface supports basic calculator operations.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class Calculator {

  /**
   * Extension of a {@link JButton} performing binary operations which are given
   * through it's constructor.
   * 
   * @author Blaz Bagic
   *
   */
  private static class BinaryOperatorButton extends JButton {
    /**
     * Default serial version UID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Initial button label.
     */
    private final String label;
    /**
     * Label of the button in it's inverse state.
     */
    private final String invLabel;
    /**
     * Operation to be performed on button press.
     */
    private final DoubleBinaryOperator operator;
    /**
     * Operation to be performed on button press when the button is in the inverse
     * state.
     */
    private final DoubleBinaryOperator inverseOperator;
    /**
     * The state of the button, initial or inverse.
     */
    private boolean isInverse = false;

    /**
     * Constructor for buttons without the inverse state.
     * 
     * @param label
     *          label of the button
     * @param op
     *          operation to be performed on button click
     */
    public BinaryOperatorButton(String label, DoubleBinaryOperator op) {
      this(label, op, null, null);
    }

    /**
     * Constructor for creating a button which supports binary operations and has an
     * inverse state.
     * 
     * @param label
     *          initial label
     * @param op
     *          operation to perform on button click
     * @param invLabel
     *          inverse label
     * @param invOp
     *          operation to perform on button click when the button is in it's
     *          inverse state
     */
    public BinaryOperatorButton(String label, DoubleBinaryOperator op, String invLabel,
        DoubleBinaryOperator invOp) {
      super(label);
      this.label = label;
      this.invLabel = invLabel;
      this.operator = op;
      this.inverseOperator = invOp;
      this.addActionListener(this.getListener());
    }

    /**
     * Auxiliary method which returns the button's {@link ActionListener} of the
     * current state.
     * 
     * @return
     */
    private ActionListener getListener() {
      return a -> {
        double current = calc.getValue();
        calc.clear();
        if (calc.isActiveOperandSet()) {
          DoubleBinaryOperator pending = calc.getPendingBinaryOperation();
          double operand = calc.getActiveOperand();
          calc.clearActiveOperand();
          current = pending.applyAsDouble(operand, current);
        }
        calc.setPendingBinaryOperation(isInverse ? inverseOperator : operator);
        calc.setActiveOperand(current);
        calc.setValue(current);
        clearNeeded = true;
      };
    }

    /**
     * Inverses the operating state of this button.
     */
    public void inverse() {
      if (invLabel == null || inverseOperator == null) {
        return;
      }
      isInverse = !isInverse;
      this.setText(isInverse ? invLabel : label);
      this.removeActionListener(this.getActionListeners()[0]);
      this.addActionListener(getListener());
    }

  }

  /**
   * Extension to {@link JButton} which provides an action to be performed on the
   * click of that button.
   * 
   * @author Blaz Bagic
   *
   */
  private static class ControlButton extends JButton {
    /**
     * Default serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor for this button which starts an {@link ActionListener}.
     * 
     * @param label
     *          name of the button
     * @param consumer
     *          action to perform when the button is pressed
     */
    public ControlButton(String label, Consumer<Void> consumer) {
      super(label);
      this.addActionListener(a -> consumer.accept(null));
    }
  }

  /**
   * Calculator button containing one digit, upon pressing this digit it is
   * appended to the current value of the calculator.
   * 
   * @author Blaz Bagic
   *
   */
  private static class DigitButton extends JButton {
    /**
     * Default serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor for this button which creates an {@link ActionListener}
     * that appends the digit to the value of the calculator on button press.
     * 
     * @param digit
     *          button's digit
     */
    public DigitButton(int digit) {
      super(String.valueOf(digit));
      this.addActionListener(a -> {
        if (calc.isActiveOperandSet() && clearNeeded) {
          calc.clear();
          clearNeeded = false;
        }
        calc.insertDigit(digit);
      });
    }
  }

  /**
   * Extension of a {@link JButton} performing unary operations which are given
   * through it's constructor.
   * 
   * @author Blaz Bagic
   *
   */
  private static class UnaryOperatorButton extends JButton {
    /**
     * Default serial version UID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The initial label of the button.
     */
    private final String label;
    /**
     * The label of the inverse operation.
     */
    private final String invLabel;
    /**
     * Operation performed after the press of this button.
     */
    private final ActionListener operator;
    /**
     * Operation performed after the press of this button when this button is in
     * it's inverse state.
     */
    private final ActionListener inverseOperator;
    /**
     * State of the button, initial or inverse.
     */
    private boolean isInverse = false;

    /**
     * Constructor for buttons without the inverse state.
     * 
     * @param label
     *          label of the button
     * @param op
     *          operation to be performed on button click
     */
    public UnaryOperatorButton(String label, DoubleUnaryOperator op) {
      this(label, op, null, null);
    }

    /**
     * Constructor for creating a button which supports unary operations and has an
     * inverse state.
     * 
     * @param label
     *          initial label
     * @param op
     *          operation to perform on button click
     * @param invLabel
     *          inverse label
     * @param invOp
     *          operation to perform on button click when the button is in it's
     *          inverse state
     */
    public UnaryOperatorButton(String label, DoubleUnaryOperator op, String invLabel,
        DoubleUnaryOperator invOp) {
      super(label);
      this.label = label;
      this.invLabel = invLabel;
      this.operator = a -> calc.setValue(op.applyAsDouble(calc.getValue()));
      this.inverseOperator = a -> calc.setValue(invOp.applyAsDouble(calc.getValue()));
      this.addActionListener(this.operator);
    }

    /**
     * Inverses the operating state of this button.
     */
    public void inverse() {
      if (invLabel == null || inverseOperator == null) {
        return;
      }
      isInverse = !isInverse;
      this.setText(isInverse ? invLabel : label);
      this.removeActionListener(isInverse ? operator : inverseOperator);
      this.addActionListener(isInverse ? inverseOperator : operator);
    }

  }

  /** Reference to a {@link CalcModel} providing methods for needed operations. */
  private static CalcModel calc;

  /**
   * Value indicating if the display needs to be cleared upon entering a new
   * digit.
   */
  private static boolean clearNeeded = false;

  /** Internal stack used for storing calculator values. */
  private static Stack<Double> stack = new Stack<>();

  /**
   * Method which adds all of the buttons which perform binary operations along
   * with appropriate operation implementations.
   * 
   * @param p
   *          {@link JPanel} on which to add the buttons
   */
  private static void addBinaryOperators(JPanel p) {
    p.add(new BinaryOperatorButton("/", (x, y) -> x / y), "2,6");
    p.add(new BinaryOperatorButton("*", (x, y) -> x * y), "3,6");
    p.add(new BinaryOperatorButton("-", (x, y) -> x - y), "4,6");
    p.add(new BinaryOperatorButton("+", (x, y) -> x + y), "5,6");
    p.add(new BinaryOperatorButton("x^n", (x, y) -> Math.pow(x, y), "n√x",
        (root, current) -> Math.pow(current, 1 / root)), "5,1");
    p.add(new BinaryOperatorButton("=", (x, y) -> y), "1,6");
  }

  /**
   * Auxiliary method which adds calculator's control buttons.
   * 
   * @param p
   *          {@link JPanel} on which to add the buttons
   */
  private static void addControlButtons(JPanel p) {
    p.add(new ControlButton("+-", a -> calc.swapSign()), "5,4");
    p.add(new ControlButton(".", a -> calc.insertDecimalPoint()), "5,5");
    p.add(new ControlButton("clr", a -> calc.clear()), "1,7");
    p.add(new ControlButton("res", a -> calc.clearAll()), "2,7");
    p.add(new ControlButton("push", a -> stack.push(calc.getValue())), "3,7");
    p.add(new ControlButton("pop", a -> {
      if (stack.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Stack is empty.");
      } else {
        calc.setValue(stack.pop());
      }
    }), "4,7");

    // inverse button
    JCheckBox invButton = new JCheckBox("inv");
    invButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (Component comp : p.getComponents()) {
          if (comp instanceof UnaryOperatorButton) {
            ((UnaryOperatorButton) comp).inverse();
          } else if (comp instanceof BinaryOperatorButton) {
            ((BinaryOperatorButton) comp).inverse();
          }
        }
      }
    });
    p.add(invButton, "5,7");
  }

  /**
   * Auxiliary method used for adding all of the {@link DigitButton} objects to
   * the calculator's layout.
   * 
   * @param p
   *          {@link JPanel} on which to display the buttons
   */
  private static void addDigits(JPanel p) {
    int digit = 0;
    p.add(new DigitButton(digit), "5,3");
    for (int i = 4; i >= 2; i--) {
      for (int j = 3; j <= 5; j++) {
        digit++;
        String lab = i + "," + j;
        p.add(new DigitButton(digit), lab);
      }
    }
  }

  /**
   * Method which adds all of the buttons which perform unary operations along
   * with appropriate operation implementations.
   * 
   * @param p
   *          {@link JPanel} on which to add the buttons
   */
  private static void addUnaryOperators(JPanel p) {
    p.add(new UnaryOperatorButton("1/x", x -> 1 / x), "2,1");

    p.add(new UnaryOperatorButton("log", x -> Math.log10(x), "10^", x -> Math.pow(10, x)), "3,1");
    p.add(new UnaryOperatorButton("ln", x -> Math.log(x), "e^", x -> Math.pow(Math.E, x)), "4,1");
    p.add(new UnaryOperatorButton("sin", x -> Math.sin(x), "asin", x -> Math.asin(x)), "2,2");
    p.add(new UnaryOperatorButton("cos", x -> Math.cos(x), "acos", x -> Math.acos(x)), "3,2");
    p.add(new UnaryOperatorButton("tan", x -> Math.tan(x), "atan", x -> Math.atan(x)), "4,2");
    p.add(new UnaryOperatorButton("ctg", x -> 1 / Math.tan(x), "actg", x -> 1 / Math.atan(x)),
        "5,2");

  }

  /**
   * Method which initializes a GUI and lays out it's components.
   */
  private static void displayCalculator() {
    calc = new CalcModelImpl();

    // initialize layout
    JFrame frame = new JFrame("Calculator");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel p = new JPanel(new CalcLayout(5));
    p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // add an output display
    JLabel display = new JLabel();
    display.setBackground(Color.lightGray);
    display.setOpaque(true);
    display.setBorder(BorderFactory.createEtchedBorder());
    display.setPreferredSize(new Dimension(200, 60));
    display.setHorizontalAlignment(JLabel.RIGHT);
    display.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 23));
    CalcValueListener valueListener = c -> display.setText(c.toString());
    calc.addCalcValueListener(valueListener);
    p.add(display, "1,1");

    // add the buttons
    addDigits(p);
    addBinaryOperators(p);
    addUnaryOperators(p);
    addControlButtons(p);

    // display the GUI
    frame.setContentPane(p);
    frame.pack();
    frame.setVisible(true);
  }

  /**
   * Main method which invokes the display of the calculator on the screen.
   * 
   * @param args
   *          command line arguments, unused here
   */
  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        displayCalculator();
      }
    });
  }
}

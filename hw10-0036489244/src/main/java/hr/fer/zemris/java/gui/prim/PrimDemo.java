package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * This is a program which displays two lists on a graphic unit interface which
 * share elements, those elements being prime numbers, which can be generated by
 * the next button.
 * 
 * @author Blaz Bagic
 *
 */
public class PrimDemo extends JFrame {

  /**
   * Default serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor initializing the title of the window, the close operation
   * and initializing the GUI.
   */
  public PrimDemo() {
    setTitle("Prim Demo");
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    initGUI();
  }

  /**
   * {@link ListModel} implementation which focuses on generating prime numbers.
   * 
   * @author Blaz Bagic
   */
  static class PrimListModel implements ListModel<Integer> {
    /**
     * The current prime number in the model.
     */
    private int currentPrime = 1;
    /**
     * List of prime numbers.
     */
    private List<Integer> primes = new ArrayList<>();
    /**
     * List of observers observing this model.
     */
    private List<ListDataListener> listeners = new ArrayList<>();

    @Override
    public void addListDataListener(ListDataListener l) {
      listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
      listeners.remove(l);
    }

    @Override
    public int getSize() {
      return primes.size();
    }

    @Override
    public Integer getElementAt(int index) {
      return (Integer) primes.get(index);
    }

    /**
     * Bread and butter of this class. Generates and appends a new prime number to
     * the list.
     */
    public void next() {
      int pos = primes.size();
      primes.add(calculateNextPrime());
      ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
      for (ListDataListener l : listeners) {
        l.intervalAdded(event);
      }
    }

    /**
     * Auxiliary method which calculates the next prime number.
     * 
     * @return the next prime number
     */
    private int calculateNextPrime() {
      int number = currentPrime;
      while (true) {
        number++;
        if (isPrime(number)) {
          currentPrime = number;
          return number;
        }
      }
    }

    /**
     * Auxiliary method which checks if a number is prime.
     * 
     * @param number
     *          number to check
     * @return <code>true</code> if the number is prime, <code>false</code>
     *         otherwise
     */
    private boolean isPrime(int number) {
      for (int i = 2; i < number; i++) {
        if (number % i == 0) {
          return false;
        }
      }
      return true;
    }
  }

  /**
   * Initializes the GUI with two lists and a button for filling them with prime
   * numbers using {@link PrimListModel}.
   */
  private void initGUI() {
    Container content = getContentPane();
    content.setLayout(new BorderLayout());

    PrimListModel model = new PrimListModel();

    JList<Integer> list1 = new JList<>(model);
    JList<Integer> list2 = new JList<>(model);

    JPanel topPanel = new JPanel(new GridLayout(1, 0));
    topPanel.add(new JScrollPane(list1));
    topPanel.add(new JScrollPane(list2));

    JPanel bottomPanel = new JPanel(new GridLayout(1, 0));

    JButton next = new JButton("Next");
    bottomPanel.add(next);

    next.addActionListener(e -> {
      model.next();
    });
    content.add(topPanel, BorderLayout.CENTER);
    content.add(bottomPanel, BorderLayout.PAGE_END);
  }

  /**
   * Main method of this demonstration class. Invokes the GUI display.
   * 
   * @param args
   *          unused here
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new PrimDemo();
      frame.pack();
      frame.setVisible(true);
    });
  }
}

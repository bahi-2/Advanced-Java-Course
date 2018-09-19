package hr.fer.zemris.java.hw06.observer1;

/**
 * Demonstration class for the {@link IntegerStorage} subject and
 * {@link IntegerStorageObserver} implementations.
 * 
 * @author Blaz Bagic
 *
 */
public class ObserverExample {
  /**
   * Main method starting the demonstration.
   * @param args unused here
   */
  public static void main(String[] args) {
    IntegerStorage istorage = new IntegerStorage(20);
    IntegerStorageObserver observer = new SquareValue();

    istorage.addObserver(observer);

    istorage.setValue(5);
    istorage.setValue(2);
    istorage.setValue(25);

    istorage.removeObserver(observer);

    istorage.addObserver(new ChangeCounter());
    istorage.addObserver(new DoubleValue(1));
    istorage.addObserver(new DoubleValue(2));
    istorage.addObserver(new DoubleValue(2));

    istorage.setValue(13);
    istorage.setValue(22);
    istorage.setValue(15);
  }
}
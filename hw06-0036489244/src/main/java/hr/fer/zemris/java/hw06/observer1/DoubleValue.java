package hr.fer.zemris.java.hw06.observer1;

/**
 * Implementation of the {@link IntegerStorageObserver} which writes the double
 * value of the subject after each modification, until the limit given through
 * the constructor is reached.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class DoubleValue implements IntegerStorageObserver {

  /**
   * Number of times the double value should be printed, after which this observer
   * unregisters itself from the subject.
   */
  int numberOfTimes;
  /**
   * Counts the number of times the double value has been printed to the screen.
   */
  int counter;

  /**
   * Constructor which sets the number of modifications this observer should
   * track.
   * 
   * @param numberOfTimes
   *          the number of modifications this observer should track
   */
  public DoubleValue(int numberOfTimes) {
    this.numberOfTimes = numberOfTimes;
  }

  @Override
  public void valueChanged(IntegerStorage istorage) {
    if (numberOfTimes > counter) {
      System.out.println("Double value: " + istorage.getValue() * 2);
      counter++;
    } else {
      istorage.removeObserver(this);
    }
  }

}

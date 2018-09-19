package hr.fer.zemris.java.hw06.observer2;

/**
 * Implementation of the {@link IntegerStorageObserver} which writes the number
 * of times the subjects state has changed since the registration of this
 * observer to the standard output.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ChangeCounter implements IntegerStorageObserver {
  /** Number of times the value of the subject has changed. */
  int counter = 0;

  @Override
  public void valueChanged(IntegerStorageChange istorageChange) {
    System.out.printf("Number of value changes since tracking: %d\n", ++counter);
  }

}

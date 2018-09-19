package hr.fer.zemris.java.hw06.observer2;

/**
 * See {@link hr.fer.zemris.java.hw06.observer1.IntegerStorageObserver}.
 * 
 * @author Blaz Bagic
 *
 */
public interface IntegerStorageObserver {

  /**
   * Method which is triggered after the Subjects value changes.
   * 
   * @param istorage
   *          reference to one Subject's change
   */
  public void valueChanged(IntegerStorageChange istorage);

}
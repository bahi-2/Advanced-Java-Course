package hr.fer.zemris.java.hw06.observer1;

/**
 * Observer class for the {@link IntegerStorage} subject. Learn more about the
 * Observer pattern at:
 * <a href="https://en.wikipedia.org/wiki/Observer_pattern"> wikipedia.org </a>
 * 
 * @author Blaz Bagic
 *
 */
public interface IntegerStorageObserver {

  /**
   * Method which is triggered after the Subjects value changes.
   * @param istorage reference to the Subject
   */
  public void valueChanged(IntegerStorage istorage);

}
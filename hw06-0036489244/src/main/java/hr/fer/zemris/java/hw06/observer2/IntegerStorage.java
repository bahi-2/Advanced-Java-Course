package hr.fer.zemris.java.hw06.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * Following the observer pattern this class represents the Subject. It supports
 * registering and removing observers (see {@link IntegerStorageObserver}),
 * which track the state of this class.
 * 
 * @author Blaz Bagic
 *
 */
public class IntegerStorage {

  /** This field represents a state of the storage. */
  private int value;
  /** Internal list of registered observers. */
  private List<IntegerStorageObserver> observers = new ArrayList<>();

  /**
   * Constructor which sets the initial value of the storage.
   * 
   * @param initialValue
   *          initial storage value
   */
  public IntegerStorage(int initialValue) {
    this.value = initialValue;
  }

  /**
   * Method which registers a new observer to track this subject.
   * 
   * @param observer
   *          reference to the observer which will be registered.
   */
  public void addObserver(IntegerStorageObserver observer) {
    if (!observers.contains(observer))
      observers.add(observer);
  }

  /**
   * Method unregisters an observer.
   * 
   * @param observer
   *          reference to the observer which will be unregistered.
   */
  public void removeObserver(IntegerStorageObserver observer) {
    observers.remove(observer);
  }

  /** Method unregisters all registered observers. */
  public void clearObservers() {
    observers.clear();
  }

  /**
   * Returns the value of the storage.
   * 
   * @return the value of the storage
   */
  public int getValue() {
    return value;
  }

  /**
   * Sets the value of the storage to this value, and notifies all observers about
   * the modification.
   * 
   * @param value
   *          value to set
   */
  public void setValue(int value) {
    // Only if new value is different than the current value:
    if (this.value != value) {
      // Create an object with the change information
      IntegerStorageChange change = new IntegerStorageChange(this, this.value, value);
      // Update current value
      this.value = value;
      // Notify all registered observers
      if (observers != null) {
        for (int i = 0; i < observers.size(); i++) {
          observers.get(i).valueChanged(change);
        }
      }
    }
  }
}
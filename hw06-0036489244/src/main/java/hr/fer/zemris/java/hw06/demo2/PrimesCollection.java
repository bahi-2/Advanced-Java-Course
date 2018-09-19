package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class which supports iterating over prime numbers.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class PrimesCollection implements Iterable<Integer> {

  /** The number of prime numbers to iterate over. */
  private int size;

  /**
   * Default constructor which sets the number of prime numbers to iterate over.
   * 
   * @param size
   *          number of prime numbers to iterate over
   */
  public PrimesCollection(int size) {
    this.size = size;
  }

  /**
   * Nested iterator class that provides the functionality of iterating over prime
   * numbers.
   * 
   * @author Blaz Bagic
   *
   */
  public class PrimesIterator implements Iterator<Integer> {

    /** Current count of iterated prime numbers. */
    private int count;
    /** Current prime number. */
    private int currentPrime = 1;

    @Override
    public boolean hasNext() {
      return count < size;
    }

    @Override
    public Integer next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      currentPrime = calculateNextPrime();
      count++;
      return currentPrime;
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
          return number;
        }
      }
    }

    /**
     * Auxiliary method which checks if a number is prime.
     * @param number number to check
     * @return <code>true</code> if the number is prime, <code>false</code> otherwise
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

  @Override
  public Iterator<Integer> iterator() {
    return new PrimesIterator();
  }
}

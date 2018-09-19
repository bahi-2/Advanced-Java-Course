package hr.fer.zemris.java.hw06.demo2;

/**
 * Demonstration program for {@link PrimesCollection}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class PrimesDemo2 {
  /**
   * Main program demonstrating iterating using a double for loop over the
   * {@link PrimesCollection}.
   * 
   * @param args
   *          unused here
   */
  public static void main(String[] args) {
    PrimesCollection primesCollection = new PrimesCollection(2);
    for (Integer prime : primesCollection) {
      for (Integer prime2 : primesCollection) {
        System.out.println("Got prime pair: " + prime + ", " + prime2);
      }
    }
  }
}

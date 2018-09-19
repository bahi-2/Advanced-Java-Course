package hr.fer.zemris.java.hw06.demo2;

/**
 * Demonstration program for the {@link PrimesCollection}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class PrimesDemo1 {
  /**
   * Main program which demonstrates an iteration over the
   * {@link PrimesCollection}.
   * 
   * @param args
   *          unused here
   */
  public static void main(String[] args) {
    PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
    for (Integer prime : primesCollection) {
      System.out.println("Got prime: " + prime);
    }
  }
}

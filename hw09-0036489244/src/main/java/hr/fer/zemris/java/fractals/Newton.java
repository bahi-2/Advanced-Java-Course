package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Main program which takes the user's input, calculates a fractal and displays
 * it in the graphic unit interface, called fractal viewer.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class Newton {

  /**
   * Class which implements {@link Callable} and serves the purpose of computing
   * the correct dots to render on the screen based on the Newton–Raphson method.
   * 
   * @author Blaz Bagic
   *
   */
  public static class ConcurrentRenderer implements Callable<Void> {

    /**
     * Minimal distance between two concurrent complex number, which stops the
     * iteration when crossed.
     */
    private static final double CONVERGENCE_THRESHOLD = 1E-3;
    /**
     * Maximum number of iterations.
     */
    private static final int ITERATION_THRESHOLD = 16;
    /**
     * Maximal radius in which the closest root of a polynomial can be found.
     */
    private static final double ROOT_THRESHOLD = 2E-3;
    /** Minimal real part of a complex number. */
    private double realMin;
    /** Maximal real part of a complex number. */
    private double realMax;
    /** Minimal imaginary part of a complex number. */
    private double imagMin;
    /** Maximal imaginary part of a complex number. */
    private double imagMax;
    /** Starting height for a thread's computations. */
    private int yMin;
    /** Starting height for a thread's computations. */
    private int yMax;
    /** Screen height. */
    private int height;
    /** Screen width. */
    private int width;
    /** Polynomial in it's general form */
    private ComplexPolynomial polynomial;
    /** Polynomial in it's linearly decomposed form */
    private ComplexRootedPolynomial polynomialRooted;
    /**
     * Holds the indexes of roots in which observed complex point c has converged to
     * 0.
     */
    private short[] data;
    /** Current offset in the data object. */
    private int offset;

    /**
     * Default constructor which instantiates all the fields necessary for this
     * computation
     */
    public ConcurrentRenderer(double realMin, double realMax, double imagMin, double imagMax,
        int yMin, int yMax, int height, int width, ComplexRootedPolynomial polynomialRooted,
        ComplexPolynomial polynomial, short[] data, int offset) {
      this.realMin = realMin;
      this.realMax = realMax;
      this.imagMin = imagMin;
      this.imagMax = imagMax;
      this.yMin = yMin;
      this.yMax = yMax;
      this.height = height;
      this.width = width;
      this.polynomialRooted = polynomialRooted;
      this.polynomial = polynomial;
      this.data = data;
      this.offset = offset;
    }

    @Override
    public Void call() {

      for (int y = yMin; y <= yMax; y++) {
        for (int x = 0; x < width; x++) {
          double cReal = x / (width - 1.0) * (realMax - realMin) + realMin;
          double cImag = (height - y - 1.0) / (height - 1) * (imagMax - imagMin) + imagMin;
          Complex zn = new Complex(cReal, cImag);
          Complex zn1;
          int iter = 0;
          double module;
          do {
            zn1 = zn.sub(polynomial.apply(zn).divide(polynomial.derive().apply(zn)));
            module = zn1.sub(zn).module();
            zn = zn1;
            iter++;
          } while (module > CONVERGENCE_THRESHOLD && iter < ITERATION_THRESHOLD);
          int index = polynomialRooted.indexOfClosestRootFor(zn1, ROOT_THRESHOLD);
          if (index == -1) {
            data[offset++] = 0;
          } else {
            data[offset++] = (short) index;
          }
        }
      }
      return null;
    }
  }

  /**
   * Implements {@link IFractalProducer}, uses a fixed number of daemon threads
   * each computing what to render on it's on part of the screen.
   * 
   * @author Blaz Bagic
   *
   */
  public static class FractalProducerImpl implements IFractalProducer {

    /**
     * Time to wait after threading pool shutdown has been called, but some workers
     * are still running.
     */
    private static final long TERMINATION_TIMEOUT = 100;
    /**
     * Polynomial equation in it's general form.
     */
    private ComplexPolynomial polynomial;
    /**
     * Polynomial equation in it's linearly decomposed form.
     */
    private ComplexRootedPolynomial polynomialRooted;
    /**
     * Threading pool used for concurrent tasks.
     */
    private ExecutorService pool;

    /**
     * Default constructor initializing the threading pool and the polynomial
     * equations.
     * 
     * @param polynomial
     *          polynomial equation
     */
    public FractalProducerImpl(ComplexRootedPolynomial polynomial) {
      this.polynomial = polynomial.toComplexPolynom();
      this.polynomialRooted = polynomial;
      this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
          (job) -> {
            Thread t = new Thread(job);
            t.setDaemon(true);
            return t;
          });
    }

    @Override
    public void produce(double realMin, double realMax, double imagMin, double imagMax, int width,
        int height, long requestNumber, IFractalResultObserver observer) {

      int numberOfThreads = 8 * Runtime.getRuntime().availableProcessors();
      List<Future<Void>> jobs = new ArrayList<>();
      double jobSlice = height * 1.0 / numberOfThreads;
      short[] data = new short[width * height];

      for (int i = 0; i < numberOfThreads; i++) {
        int yMax = (int) Math.round(jobSlice * (i + 1) - 1);
        int yMin = (int) Math.round(jobSlice * i);
        ConcurrentRenderer renderer = new ConcurrentRenderer(realMin, realMax, imagMin, imagMax,
            yMin, yMax, height, width, polynomialRooted, polynomial, data, yMin * width);
        jobs.add(pool.submit(renderer));
      }

      for (Future<Void> job : jobs) {
        try {
          job.get();
        } catch (InterruptedException e) {
          System.out.println("The thread has been interupted, before finishing it's computation.");
          e.printStackTrace();
        } catch (ExecutionException e) {
          System.out.println("The task was aborted before the get call.");
          e.printStackTrace();
        }
      }
      observer.acceptResult(data, (short) polynomial.order(), requestNumber);
      try {
        pool.awaitTermination(TERMINATION_TIMEOUT, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        System.out.println("The thread has been interupted, before finishing it's computation.");
        e.printStackTrace();
      }
      pool.shutdown();
    }
  }

  /** When a user enters this string, the input is closed. */
  private static final String END_OF_INPUT = "done";

  /**
   * Main method which asks the user to enter polynomial roots and then it
   * displays the calculated fractal in the fractal viewer.
   * 
   * @param args
   *          command line arguments, unused here
   */
  public static void main(String[] args) {
    System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
    System.out.println("Please enter at least two roots, one root per line. Enter '" + END_OF_INPUT
        + "' when done.");
    List<Complex> roots = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    int index = 1;
    do {
      System.out.print("Root " + index + "> ");
      String input = sc.nextLine();
      if (input.toLowerCase().equals(END_OF_INPUT)) {
        if (roots.size() < 2) {
          System.out
              .println("You must enter at least two roots for the computation to take effect.");
          continue;
        }
        break;
      }
      Complex root = null;
      try {
        root = parseInput(input);
      } catch (IllegalArgumentException ex) {
        System.out.println("Input line doesn't match the supported format of complex numbers."
            + " Please try again.");
        continue;
      }
      roots.add(root);
      index++;
    } while (true);

    sc.close();
    System.out.println("Image of fractal will appear shortly. Thank you.");

    ComplexRootedPolynomial polynomial = new ComplexRootedPolynomial(roots.toArray(new Complex[0]));
    FractalViewer.show(new FractalProducerImpl(polynomial));
  }

  /**
   * Auxiliary method which parses user's input for a complex number.
   * 
   * @param line
   *          one line of user input
   * @return a {@link Complex} number based on the line of input
   * @throws IllegalArgumentException
   *           in case of invalid input
   */
  private static Complex parseInput(String line) {
    String realRegex = "([-+]?[0-9]+(?:[,.][0-9]+)?).*";
    Pattern pattern = Pattern.compile(realRegex);
    Matcher matcher = pattern.matcher(line.trim());
    String real = matcher.matches() ? matcher.group(1).replace(',', '.') : null;
    String imagRegex = "[^+-]*([+-]?\\s*i(?:[0-9]+(?:[.,][0-9]*)?)?)";
    pattern = Pattern.compile(imagRegex);
    matcher = pattern.matcher(line.trim());
    String imag = matcher.matches() ? matcher.group(1).replace(',', '.').replace("i", "") : null;
    if (imag != null && imag.equals("")) {
      imag = "1";
    }
    if (imag != null && imag.equals("-")) {
      imag = "-1";
    }

    if (real != null && imag != null) {
      return new Complex(Double.parseDouble(real), Double.parseDouble(imag));
    } else if (real != null) {
      return new Complex(Double.parseDouble(real), 0);
    } else if (imag != null) {
      return new Complex(0, Double.parseDouble(imag));
    } else {
      throw new IllegalArgumentException("Error occured due to bad input.");
    }
  }

}

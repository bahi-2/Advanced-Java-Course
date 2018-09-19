package hr.fer.zemris.java.gui.charts;

import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * @author Blaz Bagic
 * @version 1.0
 */
public class BarChartDemo extends JFrame {

  /**
   * Default serial version UID.
   */
  private static final long serialVersionUID = 1L;

  public BarChartDemo(BarChartComponent chart) {
    super();
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.add(chart);
    this.setVisible(true);
//    this.pack();
    this.setSize(new Dimension(600, 400));
  }

  public static void main(String[] args) {
    if (args.length != 1) {
      throw new IllegalArgumentException(
          "Program takes exactly one argument! Path to the graph file.");
    }
    Path path = Paths.get(args[0]);
    if (!Files.isReadable(path)) {
      throw new IllegalArgumentException("The given path isn't a readable file on your system.");
    }

    try {
      List<String> lines = Files.readAllLines(path);

      String desX = lines.get(0);
      String desY = lines.get(1);

      List<XYValue> values = new ArrayList<>();
      try {
        for (String component : lines.get(2).split(" ")) {
          String[] parts = component.split(",");

          int x = Integer.parseInt(parts[0]);
          int y = Integer.parseInt(parts[1]);
          values.add(new XYValue(x, y));
        }

        int minY = Integer.parseInt(lines.get(3));
        int maxY = Integer.parseInt(lines.get(4));
        int spacing = Integer.parseInt(lines.get(5));

        BarChart chart = new BarChart(values, desX, desY, minY, maxY, spacing);
        BarChartComponent comp = new BarChartComponent(chart);

        SwingUtilities.invokeLater(() -> new BarChartDemo(comp));

      } catch (NumberFormatException ex) {
        throw new IllegalArgumentException("Invalid file format.");
      }

    } catch (IOException e) {
      System.out.println("Error ocurred while reading from the file.");
      e.printStackTrace();
      return;
    }

  }
}

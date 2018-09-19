package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class demonstrates usage of the {@link Stream} API on a Collection of
 * students.
 * 
 * Note that the method names are in Croatian because they were explicitly
 * prescribed in the homework assignment.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class StudentDemo {
  /**
   * Main method which prints to the standard output the results of each method
   * call on a {@link StudentRecord} {@link List} for all methods in this class.
   * 
   * @param args
   *          unused here
   * @throws IOException
   *           in case of a file read error
   */
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("./src/main/resources/studenti.txt"));
    List<StudentRecord> records = convert(lines);

    System.out.println(vratiBodovaViseOd25(records) + " students had more than 25 points.\n");
    System.out.println(vratiBrojOdlikasa(records) + " students had the final mark 5.\n");

    System.out.println("Follows an unsorted list of students with the final mark 5:");
    vratiListuOdlikasa(records).forEach(System.out::println);
    System.out.println(
        "\nFollows a list of students with the final mark 5 sorted by the total points achieved:");
    vratiSortiranuListuOdlikasa(records).forEach(System.out::println);

    System.out.println("\nFollows a list of students who failed this class sorted by their JMBAG:");
    vratiPopisNepolozenih(records).forEach(System.out::println);

    System.out.println("\nFollows a mapping of students based on their final mark:");
    razvrstajStudentePoOcjenama(records)
        .forEach((mark, student) -> System.out.println(mark + "->" + student));
    System.out.println("\nFollows a list of grades and a count of students with that grade:");
    vratiBrojStudenataPoOcjenama(records)
        .forEach((mark, count) -> System.out.println(mark + "->" + count));
    System.out.println(
        "\nFollows a map with keys true for students who passed this class, and false for those who failed:");
    razvrstajProlazPad(records).forEach((mark, count) -> System.out.println(mark + "->" + count));

  }

  //@formatter:off
  
  /**
   * Returns the number of students from the list of students with the total 
   * points achieved in the class greater than 25.
   * @param records original list of students
   * @return 
   *    the number of students from the list of students with the total 
   *    points achieved in the class greater than 25.
   */
  public static long vratiBodovaViseOd25(List<StudentRecord> records) {
    return records.stream()
                  .filter(s -> s.getTotalPoints() > 25)
                  .count();
  }

  /**
   * Returns the number of students from the list of students that scored
   * an A (5) in the class.
   * 
   * @param records original list of students
   * @return the number of students from the list of students that scored
   * an A (5) in the class.
   */
  public static long vratiBrojOdlikasa(List<StudentRecord> records) {
    return records.stream()
                  .filter(s -> s.getFinalGrade() == 5)
                  .count();
  }
  
  /**
   * Returns an unsorted list of students that scored an A (5) in the class.
   * 
   * @param records original list of students
   * @return a list of students that scored an A (5) in the class.
   */
  public static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
    return records.stream()
                  .filter(s -> s.getFinalGrade() == 5)
                  .collect(Collectors.toList());
  }

  /**
   * Returns a list of students that scored an A (5) in the class, 
   * sorted descending by the points achieved.
   * 
   * @param records original list of students
   * @return a list of students that scored an A (5) in the class, 
   *        sorted descending by the points achieved
   */
  public static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
    return records.stream()
                  .filter(s -> s.getFinalGrade() == 5)
                  .sorted((s1, s2) -> (s1.getTotalPoints() - s2.getTotalPoints()) < 0 ? 1 : -1)
                  .collect(Collectors.toList());
  }

  /**
   * Returns a list of students who failed this class sorted by their JMBAG.
   * 
   * @param records original list of students
   * @return a list of students who failed this class sorted by their JMBAG
   */
  public static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
    return records.stream()
                  .filter(s -> s.getFinalGrade() == 1)
                  .map(StudentRecord::getJmbag)
                  .sorted()
                  .collect(Collectors.toList());
  }


  /**
  * Returns a map of students mapped to their final mark.
  * 
  * @param records original list of students
  * @return a map of students mapped to their final mark
  */
  public static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(
      List<StudentRecord> records) {
    return records.stream()
                  .collect(Collectors.groupingBy(StudentRecord::getFinalGrade));
  }

  /**
  * Returns a map in which the keys are grades and values the
  *  count of students with that grade.
  * 
  * @param records original list of students
  * @return a map in which the keys are grades and values the
  *     count of students with that grade.
  */
  public static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
    return records.stream()
                  .collect(Collectors.toMap(StudentRecord::getFinalGrade, 
                                            s -> Integer.valueOf(1), 
                                            (student1, student2) -> student1 + student2));
  }
  
  /**
   * Returns a map  with keys true for students who passed this class
   * and false for those who failed.
   * 
   * @param records original list of students
   * @return a map  with keys true for students who passed this class
   * and false for those who failed
   */
  public static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
    return records.stream()
                  .collect(Collectors.partitioningBy(s -> s.getFinalGrade() > 1));
  }

  //@formatter:on

  /**
   * Auxiliary method which returns the list of {@link StudentRecord} objects from
   * a list of {@link String} values.
   * 
   * @param lines
   *          list of {@link String} values to be converted
   * @return the list of {@link StudentRecord} objects from a list of
   *         {@link String} values
   */
  private static List<StudentRecord> convert(List<String> lines) {
    List<StudentRecord> result = new ArrayList<>();
    for (String line : lines) {
      String[] parts = line.split("\t");
      if (parts.length != 7) {
        break;
      }

      String jmbag = parts[0];
      String lastName = parts[1];
      String firstName = parts[2];

      double midTermExamPoints = Double.parseDouble(parts[3]);
      double endTermExamPoints = Double.parseDouble(parts[4]);
      double laboratoryPoints = Double.parseDouble(parts[5]);
      int finalGrade = Integer.parseInt(parts[6]);

      result.add(new StudentRecord(jmbag, lastName, firstName, midTermExamPoints, endTermExamPoints,
          laboratoryPoints, finalGrade));
    }
    return result;
  }
}

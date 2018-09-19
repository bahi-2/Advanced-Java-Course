package hr.fer.zemris.java.hw06.demo4;

/**
 * Instances of this class represent records for one student.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class StudentRecord {
  /** Unique identifier for a student. */
  private String jmbag;
  /** Students last name. */
  private String lastName;
  /** Students first name. */
  private String firstName;
  /** Points achieved on the mid-term exam. */
  private double midTermExamPoints;
  /** Points achieved on the end of term exam. */
  private double endTermExamPoints;
  /** Points achieved on laboratory exercises. */
  private double laboratoryPoints;
  /** The final grade of the student represented by a number from [0, 5]. */
  private int finalGrade;

  /**
   * Constructor for a {@link StudentRecord}, sets the values of class variables.
   * 
   * @param jmbag
   *          initializes the student's jmbag
   * @param lastName
   *          initializes the student's last name
   * @param firstName
   *          initializes the student's first name
   * @param midTermExamPoints
   *          initializes the student's mid-term exam points
   * @param endTermExamPoints
   *          initializes the student's end of term exam points
   * @param laboratoryPoints
   *          initializes the student's laboratory points
   * @param finalGrade
   *          initializes the student's final grade
   */
  public StudentRecord(String jmbag, String lastName, String firstName, double midTermExamPoints,
      double endTermExamPoints, double laboratoryPoints, int finalGrade) {
    this.jmbag = jmbag;
    this.lastName = lastName;
    this.firstName = firstName;
    this.midTermExamPoints = midTermExamPoints;
    this.endTermExamPoints = endTermExamPoints;
    this.laboratoryPoints = laboratoryPoints;
    this.finalGrade = finalGrade;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    StudentRecord other = (StudentRecord) obj;
    if (jmbag == null) {
      if (other.jmbag != null)
        return false;
    } else if (!jmbag.equals(other.jmbag))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
    return result;
  }

  @Override
  public String toString() {
    return String.format("%s => %s %s %.2f %.2f %.2f %d", jmbag, lastName, firstName,
        midTermExamPoints, endTermExamPoints, laboratoryPoints, finalGrade);
  }

  /**
   * Returns student's jmbag.
   * 
   * @return student's jmbag
   */
  public String getJmbag() {
    return jmbag;
  }

  /**
   * Returns student's last name.
   * 
   * @return student's last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Returns student's firstName.
   * 
   * @return student's firstName.
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Returns student's mid-term exam points.
   * 
   * @return student's mid-term exam points
   */
  public double getMidTermExamPoints() {
    return midTermExamPoints;
  }

  /**
   * Returns student's end of term exam points.
   * 
   * @return student's end of term exam points
   */
  public double getEndTermExamPoints() {
    return endTermExamPoints;
  }

  /**
   * Returns student's end of term laboratory points.
   * 
   * @return student's end of term laboratory points
   */
  public double getLaboratoryPoints() {
    return laboratoryPoints;
  }

  /**
   * Returns student's end of term final grade.
   * 
   * @return student's end of term final grade
   */
  public int getFinalGrade() {
    return finalGrade;
  }

  /**
   * Returns a sum of points the student has achieved on the class.
   * 
   * @return sum of points the student has achieved on the class
   */
  public double getTotalPoints() {
    return midTermExamPoints + endTermExamPoints + laboratoryPoints;
  }

}

package hr.fer.zemris.java.hw05.db;

/**
 * Instances of this class represent records for each student. There can not
 * exist multiple records for the same student.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class StudentRecord {
  /**
   * Field variables of one student record.
   */
  private String jmbag;
  private String lastName;
  private String firstName;
  private String finalGrade;

  /**
   * Constructor that initializes the values of the records fields.
   */
  public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
    super();
    this.jmbag = jmbag;
    this.lastName = lastName;
    this.firstName = firstName;
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

  /**
   * Returns students final grade.
   * @return final grade
   */
  public String getFinalGrade() {
    return finalGrade;
  }

  /**
   * Returns students first name.
   * @return first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Returns students jmbag.
   * @return jmbag
   */
  public String getJmbag() {
    return jmbag;
  }

  /**
   * Returns students last name.
   * @return last name
   */
  public String getLastName() {
    return lastName;
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
    return String.format("%s %s %s %s", jmbag, lastName, firstName, finalGrade);
  }

}

package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * Class stores all the {@link StudentRecord}s and acts like a database.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class StudentDatabase {
  /** Number of records in the database. */
  private int size;
  /** Internal list of student records. */
  private List<StudentRecord> studentRecords;
  /** Indexed records for faster querying. */
  private SimpleHashtable<String, StudentRecord> indexedRecords;

  /**
   * Constructor which adds records from an array to the database.
   * 
   * @param records
   */
  public StudentDatabase(String[] records) {
    size = 0;
    studentRecords = new ArrayList<>();
    indexedRecords = new SimpleHashtable<>();
    for (String record : records) {
      addRecord(record);
    }
  }

  /**
   * Returns the number of records in the database.
   * 
   * @return the number of records in the database
   */
  public int getSize() {
    return size;
  }

  /**
   * Auxiliary method that adds one record to the database.
   * 
   * @param record
   */
  private void addRecord(String record) {
    String[] parts = record.split("\t");
    StudentRecord studentRecord = new StudentRecord(parts[0], parts[1], parts[2], parts[3]);
    studentRecords.add(studentRecord);
    indexedRecords.put(parts[0], studentRecord);
    size++;
  }

  /**
   * Method for getting the student record based on his JMBAG with the complexity
   * of O(1).
   * 
   * @param jmbag
   *          JMBAG
   * @return Student record
   */
  public StudentRecord forJMBAG(String jmbag) {
    return indexedRecords.get(jmbag);
  }

  /**
   * Method returns the students from the database that suit the filter.
   * 
   * @param filter filter by which to get records
   * @return filtered list of student records
   */
  public List<StudentRecord> filter(IFilter filter) {
    List<StudentRecord> result = new ArrayList<>();
    for (StudentRecord record : studentRecords) {
      if (filter.accepts(record)) {
        result.add(record);
      }
    }
    return result;
  }
}

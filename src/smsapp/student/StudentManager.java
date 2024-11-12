package smsapp.student;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface for managing students in a student management system.
 * Provides methods for adding, removing, updating, and displaying students,
 * as well as calculating the average grade of all students.
 */
public interface StudentManager {

    /**
     * Adds a new student to the system.
     * @param student The student to be added
     * @throws SQLException If a database error occurs
     */
    void addStudent(Student student) throws SQLException;

    /**
     * Removes a student from the system based on their student ID.
     * @param studentID The ID of the student to be removed
     * @throws SQLException If a database error occurs
     */
    void removeStudent(String studentID) throws SQLException;

    /**
     * Updates the information of an existing student.
     * @param student The student with updated information
     * @throws SQLException If a database error occurs
     */
    void updateStudent(Student student) throws SQLException;

    /**
     * Displays all students in the system.
     * @return A list of all students
     * @throws SQLException If a database error occurs
     */
    ArrayList<Student> displayAllStudents() throws SQLException;

    /**
     * Calculates the average grade of all students.
     * @return The average grade of all students
     * @throws SQLException If a database error occurs
     */
    double calculateAverageGrade() throws SQLException;
}
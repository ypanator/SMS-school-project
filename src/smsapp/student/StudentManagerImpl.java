package smsapp.student;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementation of the {@link StudentManager} interface that manages the operations
 * related to students, such as adding, removing, updating, displaying, and calculating the average grade.
 * It interacts with the {@link StudentRepository} to perform CRUD (Create, Read, Update, Delete) operations.
 */
public class StudentManagerImpl implements StudentManager {
    private final StudentRepository studentRepository;

    /**
     * Constructs a {@link StudentManagerImpl} with the provided {@link StudentRepository}.
     * 
     * @param studentRepository The repository to be used for storing and accessing student data
     */
    public StudentManagerImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Adds a new student to the system.
     * If a student with the same ID already exists, an {@link IllegalArgumentException} is thrown.
     * 
     * @param student The student to be added
     * @throws SQLException If a database error occurs while saving the student
     * @throws IllegalArgumentException If a student with the provided ID already exists
     */
    @Override
    public void addStudent(Student student) throws SQLException {
        if (studentRepository.isPresent(student.getStudentID())) {
            throw new IllegalArgumentException("Student with provided ID already exists");
        }
        
        studentRepository.save(student);
    }

    /**
     * Removes a student from the system based on their student ID.
     * If the student does not exist, an {@link IllegalArgumentException} is thrown.
     * 
     * @param studentID The ID of the student to be removed
     * @throws SQLException If a database error occurs while removing the student
     * @throws IllegalArgumentException If the student with the provided ID does not exist
     */
    @Override
    public void removeStudent(String studentID) throws SQLException {
        if (!studentRepository.isPresent(studentID)) {
            throw new IllegalArgumentException("Student with provided ID doesn't exist");
        }

        studentRepository.remove(studentID);
    }

    /**
     * Updates the information of an existing student.
     * If the student does not exist, an {@link IllegalArgumentException} is thrown.
     * 
     * @param student The student with updated information
     * @throws SQLException If a database error occurs while updating the student
     * @throws IllegalArgumentException If the student with the provided ID does not exist
     */
    @Override
    public void updateStudent(Student student) throws SQLException {
        if (!studentRepository.isPresent(student.getStudentID())) {
            throw new IllegalArgumentException("Student with provided ID doesn't exist");
        }

        studentRepository.update(student);
    }

    /**
     * Retrieves all students stored in the system.
     * 
     * @return A list of all students
     * @throws SQLException If a database error occurs while retrieving the students
     * @throws IllegalStateException If no students exist.
     */
    @Override
    public ArrayList<Student> displayAllStudents() throws SQLException {
        ArrayList<Student> students = studentRepository.getAllStudents();
        if (students.isEmpty()) {
            throw new IllegalStateException("No students saved");
        }
        return studentRepository.getAllStudents();
    }

    /**
     * Calculates the average grade of all students.
     * The average is computed from the grades of all students retrieved from the repository.
     * 
     * @return The average grade of all students
     * @throws SQLException If a database error occurs while retrieving the students
     * @throws IllegalStateException If no students exist.
     */
    @Override
    public double calculateAverageGrade() throws SQLException {
        ArrayList<Student> students = studentRepository.getAllStudents();
        if (students.isEmpty()) {
            throw new IllegalStateException("No students saved");
        }
        return students.stream().mapToDouble(Student::getGrade).average().getAsDouble();
    }
}
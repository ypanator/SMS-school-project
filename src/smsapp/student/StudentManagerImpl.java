package smsapp.student;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentManagerImpl implements StudentManager {
    private final StudentRepository studentRepository;

    public StudentManagerImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void addStudent(Student student) throws SQLException {
        if (studentRepository.isPresent(student.getStudentID())) {
            throw new IllegalArgumentException("Student with provided ID already exists");
        }
        
        studentRepository.save(student);
    }

    @Override
    public void removeStudent(String studentID) throws SQLException {
        if (!studentRepository.isPresent(studentID)) {
            throw new IllegalArgumentException("Student with provided ID doesn't exist");
        }

        studentRepository.remove(studentID);
    }

    @Override
    public void updateStudent(Student student) throws SQLException {
        if (!studentRepository.isPresent(student.getStudentID())) {
            throw new IllegalArgumentException("Student with provided ID doesn't exist");
        }

        studentRepository.update(student);
    }

    @Override
    public ArrayList<Student> displayAllStudents() throws SQLException {
        ArrayList<Student> students = studentRepository.getAllStudents();
        if (students.isEmpty()) {
            throw new IllegalStateException("No students are saved");
        }

        return studentRepository.getAllStudents();
    }

    @Override
    public double calculateAverageGrade() throws SQLException {
        ArrayList<Student> students = displayAllStudents();
        return students.stream().mapToDouble(Student::getGrade).average().getAsDouble();
    }

}

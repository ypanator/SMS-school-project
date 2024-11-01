package smsapp.student;

import java.util.ArrayList;

public class StudentManagerImpl implements StudentManager {
    private final StudentRepository studentRepository;

    public StudentManagerImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void addStudent(Student student) {
        if (studentRepository.findByStudentId(student.getStudentID()) != null) {
            throw new IllegalArgumentException("Student with provided ID already exists");
        }

        studentRepository.save(student);
    }

    @Override
    public void removeStudent(String studentID) {
        if (studentRepository.findByStudentId(studentID) == null) {
            throw new IllegalArgumentException("Student with provided ID doesn't exist");
        }

        studentRepository.remove(studentID);
    }

    @Override
    public ArrayList<Student> displayAllStudents() {
        ArrayList<Student> students = studentRepository.getAllStudents();
        if (students.isEmpty()) {
            throw new IllegalStateException("No students are saved");
        }

        return studentRepository.getAllStudents();
    }

    @Override
    public double calculateAverageGrade() {
        ArrayList<Student> students = displayAllStudents();
        return students.stream().mapToDouble(Student::getGrade).average().getAsDouble();
    }

}

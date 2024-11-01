package smsapp.student;

import java.util.ArrayList;

public interface StudentManager {
    void addStudent(Student student);
    void removeStudent(String studentID);
    ArrayList<Student> displayAllStudents();
    double calculateAverageGrade();
}

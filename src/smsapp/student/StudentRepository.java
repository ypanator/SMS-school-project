package smsapp.student;

import java.sql.*;
import java.util.ArrayList;

public class StudentRepository {
    private final String url = "jdbc:sqlite:sms_app.db";

    public StudentRepository() throws SQLException {
        try (
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(
                """
                CREATE TABLE IF NOT EXISTS students (
                    studentID TEXT PRIMARY KEY,
                    name TEXT NOT NULL,
                    age INTEGER NOT NULL,
                    grade REAL NOT NULL
                );
                """
            );

        }
    }
    
    public void save(Student student) throws SQLException {
        String sql = "INSERT INTO students (studentID, name, age, grade) VALUES (?, ?, ?, ?);";

        try (
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, student.getStudentID());
            statement.setString(2, student.getName());
            statement.setInt(3, student.getAge());
            statement.setDouble(4, student.getGrade());

            statement.executeUpdate();
        }
    }

    public void update(Student student) throws SQLException {
        String sql = """
                    UPDATE students
                    SET 
                        name = ?,
                        age = ?,
                        grade = ?
                    WHERE studentID = ?;
                    """;

        try (
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            Student storedStudent = getStudent(student.getStudentID());
            
            statement.setString(
                1, student.getName().equals("") ? storedStudent.getName() : student.getName());
            statement.setInt(
                2, student.getAge() == -1 ? storedStudent.getAge() : student.getAge());
            statement.setDouble(
                3, student.getGrade() == -1 ? storedStudent.getGrade() : student.getGrade());

            statement.setString(4, student.getStudentID());

            statement.executeUpdate();
        }
    }
    
    public ArrayList<Student> getAllStudents() throws SQLException {
        try (
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet data = statement.executeQuery("SELECT * FROM students;")
        ) {
            ArrayList<Student> students = new ArrayList<>();

            while (data.next()) {
                students.add(new Student(
                    data.getString("name"), 
                    data.getInt("age"), 
                    data.getDouble("grade"),
                    data.getString("studentID")
                ));
            }

            return students;
        }
    }
    
    public void remove(String studentID) throws SQLException {
        String sql = "DELETE FROM students WHERE studentID = ?;";
        try (
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, studentID);
            statement.executeUpdate();
        }
    }

    public boolean isPresent(String studentID) throws SQLException {
        String sql = "SELECT 1 FROM students WHERE studentID = ? LIMIT 1;";
        try (
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, studentID);
            try (ResultSet data = statement.executeQuery()) {
                return data.next();
            }
        }
    }

    public Student getStudent(String studentID) throws SQLException {
        String sql = "SELECT * FROM students WHERE studentID = ? LIMIT 1;";
        try (
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, studentID);
            try (ResultSet data = statement.executeQuery()) {
                data.next();
                return new Student(
                    data.getString("name"), 
                    data.getInt("age"), 
                    data.getDouble("grade"),
                    data.getString("studentID")
                );
            }
        }
    }

    public String getName(String studentID) throws SQLException {
        return getStudent(studentID).getName();
    }

    public int getAge(String studentID) throws SQLException {
        return getStudent(studentID).getAge();
    }

    public double getGrade(String studentID) throws SQLException {
        return getStudent(studentID).getGrade();
    }
}
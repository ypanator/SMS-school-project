package smsapp.student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentRepository {
    private final String DBurl = "jdbc:mysql://localhost:3306";
    private final String tableUrl = "jdbc:mysql://localhost:3306/sms_app";

    private final String username = "root";
    private final String password = "root";

    public StudentRepository() throws SQLException {
        try (
            Connection connection = DriverManager.getConnection(DBurl, username, password);
            Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS sms_app;");
        }

        try (
            Connection connection = DriverManager.getConnection(tableUrl, username, password);
            Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(
                """
                CREATE TABLE students (
                    studentID VARCHAR(255) PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    age INT NOT NULL,
                    grade DOUBLE NOT NULL
                );
                """
            );

        }
    }
    
    public void save(Student student) throws SQLException {
        String sql = "INSERT INTO students (studentID, name, age, grade) VALUES (?, ?, ?, ?);";

        try (
            Connection connection = DriverManager.getConnection(tableUrl, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, student.getStudentID());
            statement.setString(2, student.getName());
            statement.setInt(3, student.getAge());
            statement.setDouble(4, student.getGrade());

            statement.executeUpdate();
        }
    }
    
    public ArrayList<Student> getAllStudents() throws SQLException {
        try (
            Connection connection = DriverManager.getConnection(tableUrl, username, password);
            Statement statement = connection.createStatement()
        ) {
            ArrayList<Student> students = new ArrayList<>();
            ResultSet data = statement.executeQuery("SELECT * FROM students;");

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
            Connection connection = DriverManager.getConnection(tableUrl, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, studentID);
            statement.executeUpdate();
        }
    }

    public boolean isPresent(String studentID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM students WHERE studentID = ?;";
        try (
            Connection connection = DriverManager.getConnection(tableUrl, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, studentID);
            ResultSet data = statement.executeQuery();
            data.next();
            int count = data.getInt(1);
            return count > 0;
        }
    }

}
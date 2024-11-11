package smsapp.student;

import java.sql.*;
import java.util.ArrayList;

public class StudentRepository {
    private final String url = "jdbc:sqlite:sms_app.db";

    public StudentRepository() throws SQLException {
        runUpdateQuery(
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
    
    public void save(Student student) throws SQLException {
        String sql = "INSERT INTO students (studentID, name, age, grade) VALUES (?, ?, ?, ?);";
        runUpdateQuery(sql, 
            student.getStudentID(), student.getName(), student.getAge(), student.getGrade()
        );
    }

    public void update(Student student) throws SQLException {
        String sql = "UPDATE students SET name = ?, age = ?, grade = ? WHERE studentID = ?;";
        runUpdateQuery(sql, 
            student.getName(), student.getAge(), student.getGrade(), student.getStudentID()
        );
    }
    
    public ArrayList<Student> getAllStudents() throws SQLException {
        String sql = "SELECT * FROM students;";
        return runSelectQuery(sql, data -> {
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
        });
    }
    
    public void remove(String studentID) throws SQLException {
        String sql = "DELETE FROM students WHERE studentID = ?;";
        runUpdateQuery(sql, studentID);
    }

    public boolean isPresent(String studentID) throws SQLException {
        String sql = "SELECT 1 FROM students WHERE studentID = ?;";
        return runSelectQuery(sql, data -> data.next(), studentID);
    }

    public Student getStudent(String studentID) throws SQLException {
        String sql = "SELECT * FROM students WHERE studentID = ?;";
        return runSelectQuery(sql, data -> {
            data.next();
            return new Student(
                data.getString("name"), 
                data.getInt("age"), 
                data.getDouble("grade"),
                data.getString("studentID")
            );
        }, studentID);
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

    private void runUpdateQuery(String sql, Object... params) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url)) {

            if (params.length > 0) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    for (int i = 0; i < params.length; i++) {
                        statement.setObject(i + 1, params[i]);
                    }
                    statement.executeUpdate();
                }
            } else {
                try (Statement statement = connection.createStatement()) {
                    statement.execute(sql);
                }
            }
        }
    }

    private <R> R runSelectQuery(String sql, SQLFunction<R, ResultSet> function, Object... params) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url)) {

            if (params.length > 0) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    for (int i = 0; i < params.length; i++) {
                        statement.setObject(i + 1, params[i]);
                    }
                    try (ResultSet data = statement.executeQuery()) {
                        return function.apply(data);
                    }
                }
            } else {
                try (Statement statement = connection.createStatement()) {
                    try (ResultSet data = statement.executeQuery(sql)) {
                        return function.apply(data);
                    }
                }
            }
        }
    }
}

@FunctionalInterface
interface SQLFunction<R, T> {
    R apply(T t) throws SQLException;
}
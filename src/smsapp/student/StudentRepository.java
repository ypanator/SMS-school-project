package smsapp.student;

import java.sql.*;
import java.util.ArrayList;

/**
 * A repository class responsible for performing CRUD operations on the student data stored in a SQLite database.
 * It provides methods for saving, updating, removing, retrieving, and checking the presence of students.
 * This class interacts directly with the database and handles SQL queries for student data.
 */
public class StudentRepository {

    /** The URL of the SQLite database */
    private final String url = "jdbc:sqlite:db/sms_app.db";

    /**
     * Constructs a new {@link StudentRepository} and ensures that the "students" table exists in the database.
     * If the table doesn't exist, it will be created automatically.
     * 
     * @throws SQLException If a database error occurs while creating the table
     */
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
    
    /**
     * Saves a new student in the database.
     * 
     * @param student The student to be saved
     * @throws SQLException If a database error occurs while saving the student
     */
    public void save(Student student) throws SQLException {
        String sql = "INSERT INTO students (studentID, name, age, grade) VALUES (?, ?, ?, ?);";
        runUpdateQuery(sql, 
            student.getStudentID(), student.getName(), student.getAge(), student.getGrade()
        );
    }

    /**
     * Updates the details of an existing student in the database.
     * 
     * @param student The student with updated information
     * @throws SQLException If a database error occurs while updating the student
     */
    public void update(Student student) throws SQLException {
        String sql = "UPDATE students SET name = ?, age = ?, grade = ? WHERE studentID = ?;";
        runUpdateQuery(sql, 
            student.getName(), student.getAge(), student.getGrade(), student.getStudentID()
        );
    }
    
    /**
     * Retrieves all students stored in the database.
     * 
     * @return A list of all students in the database
     * @throws SQLException If a database error occurs while retrieving the students
     */
    public ArrayList<Student> getAllStudents() throws SQLException {
        String sql = "SELECT * FROM students;";
        return runSelectQuery(sql, data -> {
            ArrayList<Student> students = new ArrayList<>();

            // while there is more student information, extract it and create a new student and add it to the returned list
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
    
    /**
     * Removes a student from the database using their student ID.
     * 
     * @param studentID The ID of the student to be removed
     * @throws SQLException If a database error occurs while removing the student
     */
    public void remove(String studentID) throws SQLException {
        String sql = "DELETE FROM students WHERE studentID = ?;";
        runUpdateQuery(sql, studentID);
    }

    /**
     * Checks if a student exists in the database by their student ID.
     * 
     * @param studentID The ID of the student to check
     * @return {@code true} if the student exists, {@code false} otherwise
     * @throws SQLException If a database error occurs while checking for the student's existence
     */
    public boolean isPresent(String studentID) throws SQLException {
        String sql = "SELECT 1 FROM students WHERE studentID = ?;";

        // data.next() returns true if any information is retrieved from the query
        return runSelectQuery(sql, data -> data.next(), studentID);
    }

    /**
     * Retrieves a student from the database using their student ID.
     * 
     * @param studentID The ID of the student to retrieve
     * @return The {@link Student} object representing the student
     * @throws SQLException If a database error occurs while retrieving the student
     */
    public Student getStudent(String studentID) throws SQLException {
        String sql = "SELECT * FROM students WHERE studentID = ?;";
        return runSelectQuery(sql, data -> {
            // push data pointer to the first student
            data.next();

            return new Student(
                data.getString("name"), 
                data.getInt("age"), 
                data.getDouble("grade"),
                data.getString("studentID")
            );
        }, studentID);
    }

    /**
     * Retrieves the name of a student using their student ID.
     * 
     * @param studentID The ID of the student whose name is to be retrieved
     * @return The name of the student
     * @throws SQLException If a database error occurs while retrieving the student's name
     */
    public String getName(String studentID) throws SQLException {
        return getStudent(studentID).getName();
    }

    /**
     * Retrieves the age of a student using their student ID.
     * 
     * @param studentID The ID of the student whose age is to be retrieved
     * @return The age of the student
     * @throws SQLException If a database error occurs while retrieving the student's age
     */
    public int getAge(String studentID) throws SQLException {
        return getStudent(studentID).getAge();
    }

    /**
     * Retrieves the grade of a student using their student ID.
     * 
     * @param studentID The ID of the student whose grade is to be retrieved
     * @return The grade of the student
     * @throws SQLException If a database error occurs while retrieving the student's grade
     */
    public double getGrade(String studentID) throws SQLException {
        return getStudent(studentID).getGrade();
    }

    /**
     * Executes an SQL update query (INSERT, UPDATE, DELETE) in the database.
     * Function was created to align with the DRY principle.
     * 
     * @param sql The SQL query to be executed
     * @param params The parameters to be set in the prepared statement
     * @throws SQLException If a database error occurs while executing the query
     */
    private void runUpdateQuery(String sql, Object... params) throws SQLException {
        // all properties after the first one will be stored as an Array named params

        // establish a connection
        try (Connection connection = DriverManager.getConnection(url)) {

            // if params array is not empty, inject the parameters into the sql query provided
            if (params.length > 0) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    for (int i = 0; i < params.length; i++) {
                        // inject them in order, if too many or too few arguments are provided, the SQLException will be thrown!
                        statement.setObject(i + 1, params[i]);
                    }
                    statement.executeUpdate();
                }
            } else {
                // else create a normal statement and execute it
                try (Statement statement = connection.createStatement()) {
                    statement.execute(sql);
                }
            }
        }
    }

    /**
     * Executes an SQL select query and processes the result set using the provided function.
     * Function was created to align with the DRY principle.
     * 
     * @param sql The SQL query to be executed
     * @param function The function to process the result set
     * @param params The parameters to be set in the prepared statement
     * @param <R> The type of the result to be returned
     * @return The result of the query, as processed by the provided function
     * @throws SQLException If a database error occurs while executing the query or processing the result
     */
    private <R> R runSelectQuery(String sql, SQLFunction<R, ResultSet> function, Object... params) throws SQLException {
        // all properties after the first one will be stored as an Array named params

        // establish a connection
        try (Connection connection = DriverManager.getConnection(url)) {

            // if params array is not empty, inject the parameters into the sql query provided
            if (params.length > 0) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    // inject them in order, if too many or too few arguments are provided, the SQLException will be thrown!
                    for (int i = 0; i < params.length; i++) {
                        statement.setObject(i + 1, params[i]);
                    }
                    try (ResultSet data = statement.executeQuery()) {
                        // pass in the result of the query into the provided function and return whatever it returns
                        return function.apply(data);
                    }
                }
            } else {
                // else create a normal statement and execute it
                try (Statement statement = connection.createStatement()) {
                    try (ResultSet data = statement.executeQuery(sql)) {
                        // pass in the result of the query into the provided function and return whatever it returns
                        return function.apply(data);
                    }
                }
            }
        }
    }
}

/**
 * A functional interface for SQL operations that accept a {@link ResultSet} and return a result.
 * Used to process the result of an SQL select query.
 * Required to run methods that throw an Exception.
 * 
 * @param <R> The type of the return value
 * @param <T> The type of the passed parameter (typically {@link ResultSet})
 */
@FunctionalInterface
interface SQLFunction<R, T> {
    /**
     * Applies the function to the given parameter.
     * 
     * @param t The parameter of type T (typically {@link ResultSet})
     * @return The result of the function of type R
     * @throws SQLException If an error occurs while processing the SQL result
     */
    R apply(T t) throws SQLException;
}
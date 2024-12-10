package smsapp.student;

/**
 * A simple POJO (Plain Old Java Object) class that represents a student.
 * This class holds the student's personal information such as name, age, grade, and student ID.
 * It provides getters and setters for each field, along with validation for age and grade values.
 */
public class Student {
    
    /** The name of the student */
    private String name;

    /** The age of the student */
    private int age;

    /** The grade of the student */
    private double grade;

    /** The unique student ID */
    private String studentID;

    /**
     * Constructor to create a new Student object with the specified name, age, grade, and student ID.
     * 
     * @param name The name of the student
     * @param age The age of the student (must be greater than 0)
     * @param grade The grade of the student (must be between 0 and 100 inclusive)
     * @param studentID The unique student ID
     * @throws IllegalArgumentException if the age is less than 1 or the grade is out of the valid range
     */
    public Student(String name, int age, double grade, String studentID) {
        // uses setters for additional validation
        setName(name);
        setAge(age);
        setGrade(grade);
        setStudentID(studentID);
    }

    /**
     * Gets the name of the student.
     * 
     * @return The name of the student
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the student.
     * 
     * @param name The name of the student
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the age of the student.
     * 
     * @return The age of the student
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the student.
     * 
     * @param age The age of the student (must be greater than 0)
     * @throws IllegalArgumentException if the age is less than 1
     */
    public void setAge(int age) {
        if (age < 1) {
            throw new IllegalArgumentException("Age must be greater than zero");
        }
        this.age = age;
    }

    /**
     * Gets the grade of the student.
     * 
     * @return The grade of the student
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Sets the grade of the student.
     * The grade must be between 0 and 100 inclusive.
     * 
     * @param grade The grade of the student
     * @throws IllegalArgumentException if the grade is less than 0.0 or greater than 100.0
     */
    public void setGrade(double grade) {
        if (grade < 0.0) {
            throw new IllegalArgumentException("Grade must be greater than or equal to 0.0");
        }
        if (grade > 100.0) {
            throw new IllegalArgumentException("Grade must be less than or equal to 100.0");
        }
        this.grade = grade;
    }

    /**
     * Gets the student ID.
     * 
     * @return The student ID
     */
    public String getStudentID() {
        return studentID;
    }

    /**
     * Sets the student ID.
     * 
     * @param studentID The unique student ID
     */
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    /**
     * Displays a string representation of the student, including their name, age, grade, and student ID.
     * 
     * @return A string representation of the student
     */
    public String displayInfo() {
        return "Student [name=" + name + ", age=" + age + ", grade=" + grade + ", studentID=" + studentID + "]";
    }
}
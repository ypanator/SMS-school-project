package smsapp.student;

public class Student {
    private String name;
    private int age;
    private double grade;
    private String studentID;

    public Student(String name, int age, double grade, String studentID) {
        setName(name);
        setAge(age);
        setGrade(grade);
        setStudentID(studentID);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 1) {
            throw new IllegalArgumentException("Age must be greater than zero");
        }
        this.age = age;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        if (grade < 0.0) {
            throw new IllegalArgumentException("Grade must be greater than or equal to 0.0");
        }
        if (grade > 100.0) {
            throw new IllegalArgumentException("Grade must be less than or equal to 100.0");
        }
        this.grade = grade;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String displayInfo() {
        return "Student [name=" + name + ", age=" + age + ", grade=" + grade + ", studentID=" + studentID + "]";
    }
}
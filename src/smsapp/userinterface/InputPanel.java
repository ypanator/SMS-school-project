package smsapp.userinterface;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import smsapp.student.StudentManager;
import smsapp.student.Student;

/**
 * A JPanel class that provides a user interface for inputting and interacting with student data.
 * The panel contains text fields for student details, such as name, age, grade, and student ID,
 * as well as buttons to perform actions like adding, removing, updating, displaying all students,
 * and calculating the average grade.
 */
public class InputPanel extends JPanel {
    private final JTextField name = new JTextField();
    private final JTextField age = new JTextField();
    private final JTextField grade = new JTextField();
    private final JTextField studentID = new JTextField();

    private final StudentManager studentManager;
    private final OutputPanel outputPanel;

    /**
     * Constructs a new InputPanel for interacting with the student data.
     * 
     * @param studentManager The StudentManager instance to manage student data operations
     * @param outputPanel The OutputPanel instance to display messages and data
     */
    public InputPanel(StudentManager studentManager, OutputPanel outputPanel) {
        this.studentManager = studentManager;
        this.outputPanel = outputPanel;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(createButtonsPanel());
        add(createStudentDataPanel());
    }

    /**
     * Retrieves the name entered in the name text field and clears the field.
     * 
     * @return The name entered by the user
     */
    public String getName() {
        String output = name.getText().trim();
        verify(output, "name");
        return output;
    }

    /**
     * Retrieves the age entered in the age text field, parses it as an integer, and clears the field.
     * Throws an exception if the entered value is not a valid integer.
     * 
     * @return The age entered by the user
     * @throws NumberFormatException If the age is not a valid integer
     */
    public int getAge() {
        try {
            String text = age.getText().trim();
            verify(text, "age");
            int output = Integer.parseInt(text);
            return output;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please provide age as a whole number");
        }
    }

    /**
     * Retrieves the grade entered in the grade text field, parses it as a double, and clears the field.
     * Throws an exception if the entered value is not a valid number.
     * 
     * @return The grade entered by the user
     * @throws NumberFormatException If the grade is not a valid number
     */
    public Double getGrade() {
        try {
            String text = grade.getText().trim();
            verify(text, "grade");
            Double output = Double.parseDouble(text);
            return output;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please provide grade as a number with two decimal places, separated with a dot");
        }
    }

    /**
     * Retrieves the student ID entered in the student ID text field and clears the field.
     * 
     * @return The student ID entered by the user
     */
    public String getStudentId() {
        String output = studentID.getText().trim();
        verify(output, "studentID");
        return output;
    }
    
    /**
     * Creates a panel containing buttons for various student operations such as adding,
     * removing, updating, displaying all students, and calculating the average grade.
     * 
     * @return A JPanel containing buttons for student operations
     */
    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton add = new JButton("Add Student");
        JButton remove = new JButton("Remove Student");
        JButton update = new JButton("Update Student");
        JButton displayAll = new JButton("Display All Students");
        JButton calculateAvg = new JButton("Calculate Average");

        add.addActionListener(event -> {
            try {
                studentManager.addStudent(createStudentFromFields());
                outputPanel.displayMessage("Student has been added");
                clearAllFields();
            } catch (Exception e) {
                outputPanel.handleException(e);
            }
        });

        remove.addActionListener(event -> {
            try {
                studentManager.removeStudent(getStudentId());
                outputPanel.displayMessage("Student has been removed");
                clearStudentIDField();
            } catch (Exception e) {
                outputPanel.handleException(e);
            }
        });

        update.addActionListener(event -> {
            try {
                studentManager.updateStudent(createStudentFromFields());
                outputPanel.displayMessage("Student has been updated");
                clearAllFields();
            } catch (Exception e) {
                outputPanel.handleException(e);
            }
        });

        displayAll.addActionListener(event -> {
            try {
                outputPanel.displayStudents(studentManager.displayAllStudents());
                outputPanel.displayMessage("Students displayed");
            } catch (Exception e) {
                outputPanel.handleException(e);
            }
        });

        calculateAvg.addActionListener(event -> {
            try {
                outputPanel.displayAverageGrade(studentManager.calculateAverageGrade());
                outputPanel.displayMessage("Average grade calculated");
            } catch (Exception e) {
                outputPanel.handleException(e);
            }
        });

        panel.add(add);
        panel.add(remove);
        panel.add(update);
        panel.add(displayAll);
        panel.add(calculateAvg);

        return panel;
    }

    /**
     * Creates a panel containing input fields for student data such as name, age, grade, and student ID.
     * 
     * @return A JPanel containing the input fields for student data
     */
    private JPanel createStudentDataPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 4, 2, 2));

        // first row
        panel.add(new JLabel("name"));
        panel.add(new JLabel("age"));
        panel.add(new JLabel("grade"));
        panel.add(new JLabel("studentID"));

        // second row
        panel.add(name);
        panel.add(age);
        panel.add(grade);
        panel.add(studentID);

        return panel;
    }

    private Student createStudentFromFields() {
        Student student = new Student(getName(), getAge(), getGrade(), getStudentId());
        return student;
    }

    private void clearAllFields() {
        clearNameField();
        clearAgeField();
        clearGradeField();
        clearStudentIDField();
    }

    private void clearNameField() { name.setText(""); }
    private void clearAgeField() { age.setText(""); }
    private void clearGradeField() { grade.setText(""); }
    private void clearStudentIDField() { studentID.setText(""); }

    /**
     * Verifies that the String is not empty. Otherwise throws an IllegalArgumentException.
     * 
     * @param str string to verify
     * @param fieldName fieldName for Exception information
     */
    private void verify(String str, String fieldName) {
        if (str.isEmpty()) { throw new IllegalArgumentException(fieldName + " cannot be empty"); }
    }
}
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

// TODO:
// 2. Show info on screen after each button is invoked

public class InputPanel extends JPanel {
    private final JTextField name = new JTextField();
    private final JTextField age = new JTextField();
    private final JTextField grade = new JTextField();
    private final JTextField studentID = new JTextField();

    private final StudentManager studentManager;
    private final OutputPanel outputPanel;

    public InputPanel(StudentManager studentManager, OutputPanel outputPanel) {
        this.studentManager = studentManager;
        this.outputPanel = outputPanel;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(createButtonsPanel());
        add(createStudentDataPanel());
    }

    public String getName() {
        String output = name.getText();
        name.setText("");
        return output;
    }

    public int getAge() {
        try {
            int output = Integer.parseInt(age.getText());
            age.setText("");
            return output;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please provide age as a whole number");
        }
    }

    public Double getGrade() {
        try {
            Double output = Double.parseDouble(grade.getText());
            grade.setText("");
            return output;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please provide grade as a number with two decimal places, separated with a dot");
        }
    }

    public String getStudentId() {
        String output = studentID.getText();
        studentID.setText("");
        return output;
    }
    

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER)); // to test out TODO!

        JButton add = new JButton("Add Student");
        JButton remove = new JButton("Remove Student");
        JButton update = new JButton("Update Student");
        JButton displayAll = new JButton("Display All Students");
        JButton calculateAvg = new JButton("Calculate Average");

        add.addActionListener(event -> {
            try {
                studentManager.addStudent(createStudentFromFields());
                outputPanel.displayMessage("Student has been added");
            } catch (Exception e) {
                outputPanel.handleException(e);
            }
        });

        remove.addActionListener(event -> {
            try {
                studentManager.removeStudent(getStudentId());
                outputPanel.displayMessage("Student has been removed");
            } catch (Exception e) {
                outputPanel.handleException(e);
            }
        });

        update.addActionListener(event -> {
            try {
                studentManager.updateStudent(createStudentFromFields());
                outputPanel.displayMessage("Student has been updated");
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

    private JPanel createStudentDataPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 4, 2, 2)); // set padding, TODO!

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
        return new Student(getName(), getAge(), getGrade(), getStudentId());
    }
}
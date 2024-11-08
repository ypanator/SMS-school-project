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
// 1. Use EDT
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

        JPanel buttons = createButtonsPanel();
        JPanel studentData = createStudentDataPanel();

        add(buttons);
        add(studentData);
    }

    public String getName() {
        return name.getText();
    }

    public int getAge() {
        try {
            return Integer.parseInt(age.getText());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please provide age as a whole number");
        }
    }

    public Double getGrade() {
        try {
            return Double.parseDouble(grade.getText());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please provide grade as a number with two decimal places, separated with a dot");
        }
    }

    public String getStudentId() {
        return studentID.getText();
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
            } catch (Exception e) {
                outputPanel.handleException(e);
            }
        });

        remove.addActionListener(event -> {
            try {
                studentManager.removeStudent(getStudentId());
            } catch (Exception e) {
                outputPanel.handleException(e);
            }
        });

        update.addActionListener(event -> {
            try {
                studentManager.updateStudent(createStudentFromFields());
            } catch (Exception e) {
                outputPanel.handleException(e);
            }
        });

        displayAll.addActionListener(event -> {
            try {
                outputPanel.displayStudents(studentManager.displayAllStudents());
            } catch (Exception e) {
                outputPanel.handleException(e);
            }
        });

        calculateAvg.addActionListener(event -> {
            try {
                outputPanel.displayAverageGrade(studentManager.calculateAverageGrade());
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
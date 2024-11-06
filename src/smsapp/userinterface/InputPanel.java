package sms_app.userinterface;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class InputPanel extends JPanel {
    private final JTextField name = new JTextField();
    private final JTextField age = new JTextField();
    private final JTextField grade = new JTextField();
    private final JTextField studentID = new JTextField();

    public InputPanel() {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));

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

        JButton add = new JButon("Add Student");
        JButton remove = new JButton("Remove Student");
        JButton update = new JButton("Update Student");
        JButton displayAll = new JButton("Display All Students");
        JButton calculateAvg = new JButton("Calculate Average");

        // TODO!
        add.addActionListener(event -> {

        });

        remove.addActionListener(event -> {
            
        });

        update.addActionListener(event -> {
            
        });

        displayAll.addActionListener(event -> {
            
        });

        calculateAvg.addActionListener(event -> {
            
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
}
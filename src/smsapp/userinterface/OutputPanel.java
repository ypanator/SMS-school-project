package smsapp.userinterface;

import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import smsapp.student.Student;
import smsapp.student.StudentManager;

/* TODO:
 * 1. Implement methods
 * 2. add a pretty table
 * 3. add a label to show operations results
 */
public class OutputPanel extends JPanel {
    private final JLabel avgGrade = new JLabel();
    private final String[] columns = {"name", "age", "grade", "studentID"};
    private final JTable students = new JTable(new DefaultTableModel(null, columns));
    private final JLabel info = new JLabel();

    private final StudentManager studentManager;

    public OutputPanel(StudentManager studentManager) throws SQLException {
        this.studentManager = studentManager;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(createDataPanel());
        add(info);
    }

    private JPanel createDataPanel() throws SQLException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        panel.add(new JScrollPane(students));
        panel.add(createAvgGradeWithLabel());

        displayStudents(studentManager.displayAllStudents());

        return panel;
    }

    private JPanel createAvgGradeWithLabel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(avgGrade);
        panel.add(new JLabel("Average grade"));

        return panel;
    }

    public void displayStudents(ArrayList<Student> storedStudents) {
        DefaultTableModel model = (DefaultTableModel) students.getModel();
        model.setRowCount(0);

        for (Student student : storedStudents) {
            model.addRow(new Object[] {
                student.getName(),
                student.getAge(),
                student.getGrade(),
                student.getStudentID()
            });
        }
    }

    public void displayAverageGrade(double number) {
        avgGrade.setText(String.valueOf(number));
    }

    public void handleException(Exception e) {
        updateLabel(e.getMessage(), Color.RED);
    }

    public void displayMessage(String string) {
        updateLabel(string, Color.GREEN);
    }

    private void updateLabel(String string, Color color) {
        info.setText(string);
        info.setForeground(color);
    }
}
package smsapp.userinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import smsapp.student.Student;

/**
 * A JPanel that displays student data in a table and the average grade. 
 * It also provides a way to show messages, including error and success messages.
 * The panel contains:
 * - A table to display student information (name, age, grade, and studentID).
 * - A label to show the average grade of all students.
 * - A label to display general messages (success or error).
 */
public class OutputPanel extends JPanel {
    private final JLabel avgGrade = new JLabel();
    private final String[] columns = {"name", "age", "grade", "studentID"};
    private final JTable students = new JTable(new DefaultTableModel(null, columns));
    private final JLabel info = new JLabel();

    // TODO: to change!
    private final Color avgGradeBgColor = new Color(190, 191, 189);
    private final int avgGradeWidth = 100, avgGradeHeight = 30;

    /**
     * Constructs a new OutputPanel that displays student data and messages.
     * 
     * @throws SQLException If an error occurs while retrieving student data.
     */
    public OutputPanel() throws SQLException {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(createDataPanel());
        add(info);
    }

    /**
     * Creates a panel to display student data (in a table) and the average grade label.
     * 
     * @return A JPanel containing the JTable and the average grade label.
     * @throws SQLException If an error occurs while retrieving student data.
     */
    private JPanel createDataPanel() throws SQLException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        students.setDefaultEditor(Object.class, null); // make table non-editable

        panel.add(new JScrollPane(students));
        panel.add(createAvgGradeWithLabel());

        return panel;
    }

    /**
     * Creates a panel containing the label for the average grade.
     * 
     * @return A JPanel containing the label for displaying the average grade.
     */
    private JPanel createAvgGradeWithLabel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        avgGrade.setBorder(new LineBorder(Color.BLACK, 3));
        avgGrade.setBackground(avgGradeBgColor);
        avgGrade.setOpaque(true);
        avgGrade.setPreferredSize(new Dimension(avgGradeWidth, avgGradeHeight));

        panel.add(avgGrade);
        panel.add(new JLabel("Average\ngrade"));

        return panel;
    }

    /**
     * Displays the given list of students in the JTable.
     * 
     * @param storedStudents A list of students to be displayed in the table.
     */
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

    /**
     * Displays the average grade in the corresponding label.
     * 
     * @param number The average grade to be displayed.
     */
    public void displayAverageGrade(double number) {
        avgGrade.setText(String.format("%.2f", number));
    }

    /**
     * Handles exceptions by displaying the exception message in red text.
     * 
     * @param e The exception that was thrown.
     */
    public void handleException(Exception e) {
        updateLabel(e.getMessage(), Color.RED);
    }

    /**
     * Displays a success message in green text.
     * 
     * @param string The success message to display.
     */
    public void displayMessage(String string) {
        updateLabel(string, Color.GREEN);
    }

    /**
     * Updates the info label with a message and sets its text color.
     * 
     * @param string The message to display.
     * @param color The color to set the label's text to (e.g., red for errors, green for success).
     */
    private void updateLabel(String string, Color color) {
        info.setText(string);
        info.setForeground(color);
    }
}
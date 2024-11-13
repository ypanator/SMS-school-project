package smsapp.userinterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
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
    private final int avgGradeBorderThickness = 3;

    private final Color InfoBgColor = new Color(190, 191, 189);
    private final int infoBorderThickness = 2;

    private final int tableFontSize = 14;
    private final Color tableEvenRowColor = new Color(240, 240, 240);
    private final Color tableOddRowColor = Color.WHITE;

    /**
     * Constructs a new OutputPanel that displays student data and messages.
     * 
     * @throws SQLException If an error occurs while retrieving student data.
     */
    public OutputPanel() throws SQLException {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        customizeAvgGrade();
        customizeInfo();
        customizeTable();
        
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

        if (storedStudents.isEmpty()) {
            handleException(new IllegalStateException("No students are saved"));
        }

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

    /**
     * Customizes the appearance of the 'info' component (e.g., a label, text field, or panel).
     * Sets a border, background color, and ensures the component is opaque.
     */
    private void customizeInfo() {
        info.setBorder(new LineBorder(Color.BLACK, infoBorderThickness));
        info.setBackground(InfoBgColor);
        info.setOpaque(true);
    }

    /**
     * Customizes the appearance of the 'avgGrade' component (e.g., a label or text field).
     * Sets a border, background color, opacity, and preferred size for the component.
     */
    private void customizeAvgGrade() {
        avgGrade.setBorder(new LineBorder(Color.BLACK, avgGradeBorderThickness));
        avgGrade.setBackground(avgGradeBgColor);
        avgGrade.setOpaque(true);
        avgGrade.setPreferredSize(new Dimension(avgGradeWidth, avgGradeHeight));
    }

    /**
     * Customizes the appearance and behavior of the 'students' JTable.
     * - Sets the table to be non-editable by default.
     * - Sets the font of the table to Arial with a specific size.
     * - Defines a custom renderer to alternate row colors between light gray and white.
     * 
     * This method sets the default cell renderer for the table, ensuring that alternating row
     * colors are applied (light gray for even rows and white for odd rows).
     */
    private void customizeTable() {
        students.setDefaultEditor(Object.class, null); // make table non-editable
        students.setFont(new Font("Arial", Font.PLAIN, tableFontSize));
        students.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                // Call the parent class method to set default rendering
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                // Set alternating background colors for rows
                component.setBackground(row % 2 == 0 ? tableEvenRowColor : tableOddRowColor);
                
                // Return the customized component
                return component;
            }
        });
    }
}
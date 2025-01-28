package smsapp.userinterface;

import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import smsapp.student.StudentManager;

/**
 * The main window of the student management application. It is a JFrame that contains the 
 * user interface components for managing students, including input fields for entering 
 * student data and an output panel for displaying results and messages.
 * 
 * The window is composed of two main panels:
 * - An OutputPanel to display messages, student information, and other outputs.
 * - An InputPanel to collect user input for adding, removing, and updating student data.
 */
public class MainWindow extends JFrame {
    private final OutputPanel outputPanel;
    private final InputPanel inputPanel;

    /**
     * Constructs a new MainWindow for the student management application.
     * 
     * @param studentManager The StudentManager instance that handles student data operations.
     * @throws SQLException If a database error occurs.
     */
    public MainWindow(StudentManager studentManager) throws SQLException {
        final int width = 700, height = 400;
        final boolean isResizable = false;

        setSize(width, height); 
        setResizable(isResizable);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        setIconImage(new ImageIcon("resources/book_icon.jpg").getImage());
        setTitle("Student Management System");

        outputPanel = new OutputPanel();
        inputPanel = new InputPanel(studentManager, outputPanel);

        add(outputPanel);
        add(inputPanel);

        pack(); // smartly resizes components
        setVisible(true);
    }

    /**
     * Retrieves the OutputPanel associated with this MainWindow.
     * 
     * @return The OutputPanel instance.
     */
    public OutputPanel getOutputPanel() {
        return outputPanel;
    }

    
    /**
     * Retrieves the InputPanel associated with this MainWindow.
     * 
     * @return The InputPanel instance.
     */
    public InputPanel getInputPanel() {
        return inputPanel;
    }
}
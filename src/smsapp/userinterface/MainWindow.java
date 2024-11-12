package smsapp.userinterface;

import java.sql.SQLException;

import javax.swing.BoxLayout;
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
     * @throws SQLException If an error occurs when initializing the student manager or the database.
     */
    public MainWindow(StudentManager studentManager) throws SQLException {
        setSize(500, 400); 
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        outputPanel = new OutputPanel(studentManager);
        inputPanel = new InputPanel(studentManager, outputPanel);

        add(outputPanel);
        add(inputPanel);

        pack();
        setVisible(true);
    }

    /**
     * Retrieves the OutputPanel associated with this MainWindow.
     * 
     * @return The OutputPanel instance used for displaying student information and messages.
     */
    public OutputPanel getOutputPanel() {
        return outputPanel;
    }

    
    /** 
     * @return InputPanel
     */
    public InputPanel getInputPanel() {
        return inputPanel;
    }
}
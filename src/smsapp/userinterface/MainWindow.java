package smsapp.userinterface;

import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import smsapp.student.StudentManager;

public class MainWindow extends JFrame {
    private final OutputPanel outputPanel;
    private final InputPanel inputPanel;

    public MainWindow(StudentManager studentManager) throws SQLException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        outputPanel = new OutputPanel(studentManager);
        inputPanel = new InputPanel(studentManager, outputPanel);

        add(outputPanel);
        add(inputPanel);

        pack();
        setVisible(true);
    }

    public OutputPanel getOutputPanel() {
        return outputPanel;
    }

    public InputPanel getInputPanel() {
        return inputPanel;
    }
}
package smsapp.userinterface;

import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import smsapp.student.StudentManager;

public class MainWindow extends JPanel {
    // TODO:
    // use box layout for main window
    // combine both panels together

    public MainWindow(StudentManager studentManager) throws SQLException {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        OutputPanel outputPanel = new OutputPanel(studentManager);
        InputPanel inputPanel = new InputPanel(studentManager, outputPanel);

        add(outputPanel);
        add(inputPanel);
    }
}
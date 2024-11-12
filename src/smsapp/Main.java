package smsapp;

import javax.swing.JOptionPane;

import smsapp.student.StudentManager;
import smsapp.student.StudentManagerImpl;
import smsapp.student.StudentRepository;
import smsapp.userinterface.MainWindow;

public class Main {
    public static void main(String[] args) {
        // TODO: connect everything together!
        MainWindow mainWindow = null;
        try {
            StudentRepository studentRepository = new StudentRepository();
            StudentManager studentManager = new StudentManagerImpl(studentRepository);
            mainWindow = new MainWindow(studentManager);
        } catch (Exception e) {
            if (mainWindow != null) {
                mainWindow.getOutputPanel().handleException(e);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "An error occurred: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } 
    }
}
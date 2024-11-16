package smsapp;

import javax.swing.JOptionPane;

import smsapp.student.StudentManager;
import smsapp.student.StudentManagerImpl;
import smsapp.student.StudentRepository;
import smsapp.userinterface.MainWindow;

/**
 * The entry point of the student management system application. 
 * This class is responsible for initializing the application, setting up the 
 * necessary components, and handling any exceptions that occur during the setup.
 * 
 * The `Main` class creates the following components:
 * - A `StudentRepository` for managing student data storage.
 * - A `StudentManagerImpl` to handle business logic related to students.
 * - A `MainWindow` to create the main user interface for the application.
 */
public class Main {
    public static void main(String[] args) {
        MainWindow mainWindow = null;
        try {
            StudentRepository studentRepository = new StudentRepository();
            StudentManager studentManager = new StudentManagerImpl(studentRepository);
            mainWindow = new MainWindow(studentManager);

            mainWindow.getOutputPanel().displayMessage("Application correctly loaded");
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

/*TODO:
 * use pretty theme for the whole app and better font
 * add title, icon
 * empty fields?
 */
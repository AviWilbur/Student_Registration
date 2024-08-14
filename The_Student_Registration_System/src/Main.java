//This project implements a student registration system with a graphical user interface (GUI) for managing student records. The 
//StudentRegistration class provides functionality to add, edit, delete, and search for students, with validation checks for input fields. 
//Student data is stored in a MySQL database, and the DataBaseManager class handles the connection to the database, allowing for CRUD operations
//(Create, Read, Update, Delete) on student records. Additionally, users can generate and save reports of student information through the 
//ReportWindow class.

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
    	


        SwingUtilities.invokeLater(() -> {
            new StudentRegistration();
        });

    }

}

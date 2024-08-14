import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Connection;

public class StudentRegistration extends JFrame {
    private JTextField nameField;
    private JTextField birthDateField;
    private JTextField addressField;
    private JTextField contactDetailsField;
    private JTextField courseNameField;
    private JTextField academicIdField;
    private JTextField courseCodeField;
    
 
    private JList<Student> studentList;
    private DefaultListModel<Student> listModel,filteredListModel;
    
    private JComboBox<String> filterComboBox;
    private JTextField searchField;
  

    public StudentRegistration() {
    	
        setTitle("Student Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(11, 2));
                

        JLabel academicIdLabel = new JLabel("Academic ID:");
        academicIdField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel birthDateLabel = new JLabel("Birth Date:");
        birthDateField = new JTextField("Enter your birthdate (yyyy-MM-dd)");
        birthDateField.setForeground(Color.GRAY);
        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField();
        JLabel contactDetailsLabel = new JLabel("Contact Details:");
        contactDetailsField = new JTextField("Enter your email");
        contactDetailsField.setForeground(Color.GRAY);
        JLabel courseNameLabel = new JLabel("Course Name:");
        courseNameField = new JTextField();
        JLabel courseCodeLabel = new JLabel("Course Code:");
        courseCodeField = new JTextField("enter course ID number");
        courseCodeField.setForeground(Color.GRAY);
        

        birthDateField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (birthDateField.getText().equals("Enter your birthdate (yyyy-MM-dd)")) {
                    birthDateField.setText("");
                    birthDateField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (birthDateField.getText().isEmpty()) {
                    birthDateField.setText("Enter your birthdate (yyyy-MM-dd)");
                    birthDateField.setForeground(Color.GRAY);
                }
            }
        });

        contactDetailsField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (contactDetailsField.getText().equals("Enter your email")) {
                    contactDetailsField.setText("");
                    contactDetailsField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (contactDetailsField.getText().isEmpty()) {
                    contactDetailsField.setText("Enter your email");
                    contactDetailsField.setForeground(Color.GRAY);
                }
            }
        });

        courseCodeField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (courseCodeField.getText().equals("enter course ID number")) {
                    courseCodeField.setText("");
                    courseCodeField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String input = courseCodeField.getText();
                if (input.isEmpty() || !input.matches("\\d+")) {
                    courseCodeField.setText("enter course ID number");
                    courseCodeField.setForeground(Color.GRAY);
                }
            }
        });

        JPanel searchPanel = new JPanel(new FlowLayout());

        
        String[] filterOptions = {"Name", "Student ID", "Course Enrollment"};
        filterComboBox = new JComboBox<>(filterOptions);

        JButton searchButton = new JButton("Search");
        JLabel searchLabel = new JLabel("Search:");
        searchField = new JTextField(15); 
        
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        
        
        filteredListModel = new DefaultListModel<>();
        listModel = new DefaultListModel<>();
        

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int academicId;
                String name;
                String birthDate;
                String address;
                String contactDetails;
                String courseName;
                int courseCode;
                
                String academicIdText = academicIdField.getText();


             if (academicIdText == null || academicIdText.trim().isEmpty()) {
                 showError("Academic ID cannot be empty.");
                 return; 
             }
                
                
                String courseCodeText = courseCodeField.getText();
                if (courseCodeText.isEmpty() || courseCodeText.trim().isEmpty()) {
                    showError("Course Code cannot be empty.");
                    return; 
                }
                
                try {
                    courseCode = Integer.parseInt(courseCodeField.getText());
                } catch (NumberFormatException ex) {
                    showError("Invalid Course Code. Please enter a valid numeric course code.");
                    return; 
                }

                academicId = Integer.parseInt(academicIdField.getText());
                name = nameField.getText();
                birthDate = birthDateField.getText();
                address = addressField.getText();
                contactDetails = contactDetailsField.getText();
                courseName = courseNameField.getText();
                courseCode = Integer.parseInt(courseCodeField.getText());


                try {
                    academicId = Integer.parseInt(academicIdField.getText());
                } catch (NumberFormatException ex) {
                    showError("Invalid Academic ID. Please enter a valid ID.");
                    return; 
                }


                name = nameField.getText();
                if (name.isEmpty()) {
                    showError("Name cannot be empty.");
                    return; 
                }
                if (!name.matches("^[a-zA-Z]+$")) {
                    showError("Invalid Name. Please enter only letters.");
                    return;
                }

                birthDate = birthDateField.getText();
                if (!isValidDate(birthDate)) {
                    showError("Invalid Birth Date. Please enter a valid date (yyyy-MM-dd).");
                    return; 
                }


                address = addressField.getText();
                if (address.isEmpty()) {
                    showError("Address cannot be empty.");
                    return; 
                }


                contactDetails = contactDetailsField.getText();
                if (contactDetails.isEmpty()) {
                    showError("Contact Details cannot be empty.");
                    return; 
                }


                String email = "^[A-Za-z0-9+_.-]+@(.+)$";

                if (!contactDetails.matches(email)) {
                    showError("Invalid Email Address. Please enter a valid email.(jon123@gmail.com)");
                    return; 
                }


                courseName = courseNameField.getText();
                if (courseName.isEmpty()) {
                    showError("Course Name cannot be empty.");
                    return; 
                }

                insertStudentIntoDatabase(academicId, name, birthDate, address, contactDetails, courseName, courseCode);

            
                Student student = new Student(academicId, name, birthDate, address, contactDetails, courseName, courseCode);
                

                listModel.addElement(student);
                filteredListModel.addElement(student);
                

            }
            

            private void showError(String message) {
                JOptionPane.showMessageDialog(
                    StudentRegistration.this,
                    message,
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }

          
            private boolean isValidDate(String date) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    dateFormat.setLenient(false);
                    dateFormat.parse(date);
                    return true;
                } catch (ParseException e) {
                    return false;
                }
            }            
        });

        studentList = new JList<>(listModel);
        

        studentList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedIndex = studentList.getSelectedIndex();
                    if (selectedIndex >= 0) {
                        Student selectedName = filteredListModel.getElementAt(selectedIndex);
                        displayStudentDetails(selectedName);
                    }
                }
            }
        });
        
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText().toLowerCase();
                String selectedFilter = filterComboBox.getSelectedItem().toString();
                filteredListModel.clear();
                
                if (searchText.isEmpty()) {
                    for (int i = 0; i < listModel.size(); i++) {
                        Student student = listModel.getElementAt(i);
                        filteredListModel.addElement(student);
                    }
                } else {
                	
	                for (int i = 0; i < listModel.size(); i++) {
	                    Student student = listModel.getElementAt(i);
	                    switch (selectedFilter) {
	                        case "Name":
	                            if (student.getName().toLowerCase().contains(searchText)) {
	                                filteredListModel.addElement(student);
	                            }
	                            break;
	                        case "Student ID":
	                            if (String.valueOf(student.getAcademicId()).contains(searchText)) {
	                                filteredListModel.addElement(student);
	                            }
	                            break;
	                        case "Course Enrollment":
	                            if (String.valueOf(student.getCourseCode()).contains(searchText)
	                                    || student.getCourseName().toLowerCase().contains(searchText)) {
	                                filteredListModel.addElement(student);
	                            }
	                            break;
	                    }
	                }	
	
	                studentList.setModel(filteredListModel);
	               
                }
            }
        });       
        
        
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = studentList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    Student selectedStudent = filteredListModel.getElementAt(selectedIndex);


                    JFrame editFrame = new JFrame("Edit Student Details");
                    editFrame.setSize(400, 300);
                    editFrame.setLocationRelativeTo(null);

 
                    JTextField editedNameField = new JTextField(selectedStudent.getName());
                    JTextField editedBirthDateField = new JTextField(selectedStudent.getBirthDate());
                    JTextField editedAddressField = new JTextField(selectedStudent.getAddress());
                    JTextField editedContactDetailsField = new JTextField(selectedStudent.getContactDetails());
                    JTextField editedCourseNameField = new JTextField(selectedStudent.getCourseName());
                    JTextField editedCourseCodeField = new JTextField(Integer.toString(selectedStudent.getCourseCode()));


                    JButton applyChangesButton = new JButton("Apply Changes");
                    applyChangesButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String editedName = editedNameField.getText();
                            String editedBirthDate = editedBirthDateField.getText();
                            String editedAddress = editedAddressField.getText();
                            String editedContactDetails = editedContactDetailsField.getText();
                            String editedCourseName = editedCourseNameField.getText();
                            int editedCourseCode;

                            
                            if (editedName.isEmpty()) {
                                showError("Name cannot be empty.");
                                return; 
                            }
                            if (!editedName.matches("^[a-zA-Z]+$")) {
                                showError("Invalid Name. Please enter only letters.");
                                return; 
                            }

                            
                            if (!isValidDate(editedBirthDate)) {
                                showError("Invalid Birth Date. Please enter a valid date (yyyy-MM-dd).");
                                return; 
                            }

                           
                            if (editedAddress.isEmpty()) {
                                showError("Address cannot be empty.");
                                return; 
                            }

                          
                            if (editedContactDetails.isEmpty()) {
                                showError("Contact Details cannot be empty.");
                                return;
                            }

                           
                            String email = "^[A-Za-z0-9+_.-]+@(.+)$";

                            if (!editedContactDetails.matches(email)) {
                                showError("Invalid Email Address. Please enter a valid email (jon123@gmail.com).");
                                return; 
                            }

                           
                            if (editedCourseName.isEmpty()) {
                                showError("Course Name cannot be empty.");
                                return; 
                            }

                           
                            try {
                                editedCourseCode = Integer.parseInt(editedCourseCodeField.getText());
                            } catch (NumberFormatException ex) {
                                showError("Invalid Course Code. Please enter a valid numeric course code.");
                                return;
                            }


                            selectedStudent.setName(editedName);
                            selectedStudent.setBirthDate(editedBirthDate);
                            selectedStudent.setAddress(editedAddress);
                            selectedStudent.setContactDetails(editedContactDetails);
                            selectedStudent.setCourseName(editedCourseName);
                            selectedStudent.setCourseCode(editedCourseCode);

                           
                            displayStudentDetails(selectedStudent);

                            updateStudentInDatabase(selectedStudent);
                           
                            editFrame.dispose();
                                                                                    
                        }
                      
                        private void showError(String message) {
                            JOptionPane.showMessageDialog(
                                StudentRegistration.this,
                                message,
                                "Validation Error",
                                JOptionPane.ERROR_MESSAGE
                            );
                        }
                     
                        private boolean isValidDate(String date) {
                            try {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                dateFormat.setLenient(false);
                                dateFormat.parse(date);
                                return true;
                            } catch (ParseException e) {
                                return false;
                            }
                        }
                    });
                   
                    JPanel editPanel = new JPanel(new GridLayout(7, 2));
                    editPanel.add(new JLabel("Name:"));
                    editPanel.add(editedNameField);
                    editPanel.add(new JLabel("Birth Date:"));
                    editPanel.add(editedBirthDateField);
                    editPanel.add(new JLabel("Address:"));
                    editPanel.add(editedAddressField);
                    editPanel.add(new JLabel("Contact Details:"));
                    editPanel.add(editedContactDetailsField);
                    editPanel.add(new JLabel("Course Name:"));
                    editPanel.add(editedCourseNameField);
                    editPanel.add(new JLabel("Course Code:"));
                    editPanel.add(editedCourseCodeField);
                    editPanel.add(applyChangesButton);

                    editFrame.add(editPanel);
                    editFrame.setVisible(true);
                }
            }
        });              
        
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = studentList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    Student selectedStudent = filteredListModel.getElementAt(selectedIndex);
                    int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this student?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        listModel.removeElement(selectedStudent);
                        filteredListModel.removeElement(selectedStudent);                        
                        deleteStudentFromDatabase(selectedStudent);
                    }
                }
            }
        });       
        
        JButton generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ReportWindow reportWindow = new ReportWindow();
                reportWindow.setVisible(true);
            }
        });
        
        
        add(academicIdLabel);
        add(academicIdField);
        add(nameLabel);
        add(nameField);
        add(birthDateLabel);
        add(birthDateField);
        add(addressLabel);
        add(addressField);
        add(contactDetailsLabel);
        add(contactDetailsField);
        add(courseNameLabel);
        add(courseNameField);
        add(courseCodeLabel);
        add(courseCodeField);
        add(registerButton);
        

        add(searchPanel, BorderLayout.NORTH);
        
        add(new JLabel("Registered Students:"));
        add(new JScrollPane(studentList));
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(filterComboBox);
        searchPanel.add(searchButton);
        
    
        add(editButton);
        add(deleteButton);
        
        add(generateReportButton);
        
        add(new JLabel("<html>Welcome to the student registration form!<br>"
                + "To update student information, select a student, and then choose either the 'Edit' or 'Delete' option.</html>"));
                        
        setVisible(true);
    }

    private void displayStudentDetails(Student student) {    	
    	
        JFrame detailsFrame = new JFrame("Student Details");
        detailsFrame.setSize(300, 200);
        detailsFrame.setLocationRelativeTo(this);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(7, 2));
        detailsPanel.add(new JLabel("Academic ID:"));
        detailsPanel.add(new JLabel(Integer.toString(student.getAcademicId())));
        detailsPanel.add(new JLabel("Name:"));
        detailsPanel.add(new JLabel(student.getName()));
        detailsPanel.add(new JLabel("Birth Date:"));
        detailsPanel.add(new JLabel(student.getBirthDate()));
        detailsPanel.add(new JLabel("Address:"));
        detailsPanel.add(new JLabel(student.getAddress()));
        detailsPanel.add(new JLabel("Contact Details:"));
        detailsPanel.add(new JLabel(student.getContactDetails()));
        detailsPanel.add(new JLabel("Course Name:"));
        detailsPanel.add(new JLabel(student.getCourseName()));
        detailsPanel.add(new JLabel("Course Code:"));
        detailsPanel.add(new JLabel(Integer.toString(student.getCourseCode())));

        detailsFrame.add(detailsPanel);
        detailsFrame.setVisible(true);
    }
    
    // Database operations
    private void insertStudentIntoDatabase(int academicId, String name, String birthDate, String address, String contactDetails, String courseName, int courseCode) {
        try (Connection connection = DataBaseManager.getConnection()) {
            String insertSQL = "INSERT INTO students (academic_id, name, birth_date, address, contact_details, course_name, course_code) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setInt(1, academicId);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, birthDate);
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, contactDetails);
                preparedStatement.setString(6, courseName);
                preparedStatement.setInt(7, courseCode);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Student inserted successfully.");
                } else {
                    System.out.println("Failed to insert student.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateStudentInDatabase(Student student) {
        try (Connection connection = DataBaseManager.getConnection()) {
            String updateSQL = "UPDATE students SET name=?, birth_date=?, address=?, contact_details=?, course_name=?, course_code=? WHERE academic_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
                preparedStatement.setString(1, student.getName());
                preparedStatement.setString(2, student.getBirthDate()); 
                preparedStatement.setString(3, student.getAddress());
                preparedStatement.setString(4, student.getContactDetails());
                preparedStatement.setString(5, student.getCourseName());
                preparedStatement.setInt(6, student.getCourseCode());
                preparedStatement.setInt(7, student.getAcademicId());

                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Student updated successfully.");
                } else {
                    System.out.println("Failed to update student.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteStudentFromDatabase(Student student) {
        try (Connection connection = DataBaseManager.getConnection()) {
            String deleteSQL = "DELETE FROM students WHERE academic_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
                preparedStatement.setInt(1, student.getAcademicId());

                int rowsDeleted = preparedStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Student deleted successfully.");
                } else {
                    System.out.println("Failed to delete student.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }   

}
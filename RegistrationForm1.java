package project_demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.regex.Pattern;

import static project_demo.dbConnection.getConnection;

public class RegistrationForm1 extends JFrame implements ActionListener {
    // Define form components
    JLabel nameLabel, emailLabel, passwordLabel, confirmPasswordLabel;
    JTextField nameField, emailField;
    JPasswordField passwordField, confirmPasswordField;
    JButton submitButton, clearButton, loginButton;
    JPanel formPanel;

//    // Define database connection properties
//    private final String DB_URL = "jdbc:mysql://localhost:3306/quizapp";
//    private final String USER = "root";
//    private final String PASSWORD = "Bikash@2000";

    public RegistrationForm1() {
        // Add form components to panel
        nameLabel = new JLabel("Name:");
        emailLabel = new JLabel("Email:");
        passwordLabel = new JLabel("Password:");
        confirmPasswordLabel = new JLabel("Confirm Password:");

        nameField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);

        submitButton = new JButton("Submit");
        clearButton = new JButton("Clear");
        loginButton =new JButton("Already a user ? Log in");

        submitButton.addActionListener(this);
        clearButton.addActionListener(this);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hide the current window
                setVisible(false);

                // Show the login form
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            }
        });

        formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 5, 5);
        formPanel.add(nameLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        formPanel.add(nameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        formPanel.add(emailLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        formPanel.add(emailField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        formPanel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        formPanel.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        formPanel.add(confirmPasswordLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        formPanel.add(confirmPasswordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(loginButton);
        formPanel.add(buttonPanel, constraints);

        add(formPanel, BorderLayout.CENTER);

        // Set form properties
        setTitle("Registration Form");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            // Validate form data
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            if (!Pattern.matches("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", name)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid name.");
                return;
            }
            if (!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
                return;
            }

// Establish database connection
            Connection conn = null;
            conn = getConnection();

// Insert user data into database
            String sql = "INSERT INTO registration_form (name, email, password, confirm_password) VALUES (?, ?, ?, ?)";
            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, password);
                stmt.setString(4, confirmPassword);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Registration successful.");
                    nameField.setText("");
                    emailField.setText("");
                    passwordField.setText("");
                    confirmPasswordField.setText("");
                }
                // Redirect to login page
                LoginForm login = new LoginForm();
                login.setVisible(true);

                // Close the registration page
                this.dispose();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

        }
    }
    public static void main(String[] args) {
        new RegistrationForm1();
    }
}
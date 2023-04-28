package project_demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static project_demo.dbConnection.getConnection;

public class LoginForm extends JFrame implements ActionListener {
    JLabel emailLabel, passwordLabel;
    JTextField emailField;
    JPasswordField passwordField;
    JButton loginButton, clearButton, registerButton;
    JPanel formPanel;

    public LoginForm() {
        // Define form components
        emailLabel = new JLabel("Email:");
        passwordLabel = new JLabel("Password:");

        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);

        loginButton = new JButton("Login");
        clearButton = new JButton("Clear");
        registerButton = new JButton("Register");

        loginButton.addActionListener(this);
        clearButton.addActionListener(this);
        registerButton.addActionListener(this);

        // Add form components to panel
        formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 5, 5);
        formPanel.add(emailLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        formPanel.add(emailField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        formPanel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        formPanel.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(registerButton);
        formPanel.add(buttonPanel, constraints);

        // Add panel to frame
        add(formPanel, BorderLayout.CENTER);

        // Set form properties
        setTitle("Login Form");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Connection con = dbConnection.getConnection();

        if (e.getSource() == loginButton) {
            // Validate form data
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            try {
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                String query = "SELECT * FROM registration_form WHERE email='" + email + "' AND password='" + password + "'";
                ResultSet rs = stmt.executeQuery(query);

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login successful.");
                    dispose(); // Close the login form
                    new Dashboard().setVisible(true); // Open the Dashboard
                    }
                     else {
                    JOptionPane.showMessageDialog(this, "Invalid email or password.");
                }

                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        } else if (e.getSource() == clearButton) {
            // Clear form fields
            emailField.setText("");
            passwordField.setText("");
        } else if (e.getSource() == registerButton) {
            // Open registration form
            new RegistrationForm1();
            dispose();
        }

    }
}

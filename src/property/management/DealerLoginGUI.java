package property.management;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DealerLoginGUI extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public DealerLoginGUI() {

        setTitle("Dealer Login");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Dealer Login");
        title.setFont(new Font("Arial", Font.BOLD, 24));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        panel.add(title, gbc);

        // Email
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;

        panel.add(new JLabel("Email:"), gbc);

        emailField = new JTextField();
        gbc.gridx = 1;

        panel.add(emailField, gbc);

        // Password
        gbc.gridy = 2;
        gbc.gridx = 0;

        panel.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField();
        gbc.gridx = 1;

        panel.add(passwordField, gbc);

        // Login button
        JButton loginButton = new JButton("Login");

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        panel.add(loginButton, gbc);

        loginButton.addActionListener(e -> loginDealer());
        // New User button
        JButton registerButton = new JButton("New User? Register");

        gbc.gridy = 4;
        gbc.gridwidth = 10;

        panel.add(registerButton, gbc);

        registerButton.addActionListener(e -> showRegistrationForm());

        add(panel);
    }




    //registeration of new dealer
    private void showRegistrationForm() {

        JDialog dialog = new JDialog(
                this,
                "Create Dealer Account",
                true
        );

        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = new JTextField(30);
        JTextField phoneField = new JTextField(30);
        JTextField emailField = new JTextField(30);
        JPasswordField passwordField = new JPasswordField(30);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Phone:"), gbc);

        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JButton registerButton =
                new JButton("Register");

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;

        panel.add(registerButton, gbc);

        registerButton.addActionListener(e -> {

            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String password =
                    new String(passwordField.getPassword());

            if (name.isEmpty() ||
                    phone.isEmpty() ||
                    email.isEmpty() ||
                    password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Please fill all fields.",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            Dealer dealer = new Dealer(
                    0,
                    name,
                    phone,
                    email,
                    password
            );

            PropertyManagementSystem system =
                    new PropertyManagementSystem();

            system.addDealerToDB(dealer);

            JOptionPane.showMessageDialog(
                    dialog,
                    "Account created successfully!\nPlease login.",
                    "Registration Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dialog.dispose();
        });

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void loginDealer() {

        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter email and password.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String query =
                "SELECT dealer_id, name FROM Dealer " +
                        "WHERE email = ? AND password = ?";

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int dealerId = rs.getInt("dealer_id");
                String dealerName = rs.getString("name");

                JOptionPane.showMessageDialog(
                        this,
                        "Login successful! Welcome " + dealerName,
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                MainGUI mainGUI =
                        new MainGUI(dealerId, dealerName);

                mainGUI.setVisible(true);

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid email or password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            DealerLoginGUI loginGUI =
                    new DealerLoginGUI();

            loginGUI.setVisible(true);
        });
    }
}
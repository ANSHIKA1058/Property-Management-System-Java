package property.management;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DealerLoginGUI extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    private boolean isValidEmail(String email) {
        return email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        );
    }

    private boolean isStrongPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[^A-Za-z0-9].*");
    }

    public DealerLoginGUI() {

        setTitle("Dealer Login");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(225, 200, 170));
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

        emailField = new JTextField(40);
        gbc.gridx = 1;

        panel.add(emailField, gbc);

        // Password
        gbc.gridy = 2;
        gbc.gridx = 0;

        panel.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(40);
        gbc.gridx = 1;

        panel.add(passwordField, gbc);

        // Login button
        JButton loginButton = new JButton("Login");

        loginButton.setBackground(new Color(92, 64, 51));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        panel.add(loginButton, gbc);

        loginButton.addActionListener(e -> loginDealer());
        // New User button
        JButton registerButton = new JButton("New User? Register");

        registerButton.setBackground(new Color(92, 64, 51));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 15));
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setPreferredSize(new Dimension(200, 40));
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

        dialog.setSize(500, 430);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        // Main panel
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(225, 200, 170));
        panel.setBorder(
                BorderFactory.createEmptyBorder(25, 40, 25, 40)
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Colors
        Color darkBrown = new Color(70, 45, 30);
        Color buttonBrown = new Color(92, 64, 51);

        // ---------------- TITLE ----------------

        JLabel titleLabel = new JLabel("Create Dealer Account");
        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );
        titleLabel.setForeground(darkBrown);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 20, 10);

        panel.add(titleLabel, gbc);

        // ---------------- FIELDS ----------------

        JTextField nameField = new JTextField(25);
        JTextField phoneField = new JTextField(25);
        JTextField emailField = new JTextField(25);
        JPasswordField passwordField = new JPasswordField(25);

        // Make fields white
        nameField.setBackground(Color.WHITE);
        phoneField.setBackground(Color.WHITE);
        emailField.setBackground(Color.WHITE);
        passwordField.setBackground(Color.WHITE);

        // ---------------- NAME ----------------

        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 1;

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(darkBrown);
        nameLabel.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // ---------------- PHONE ----------------

        gbc.gridx = 0;
        gbc.gridy = 2;

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setForeground(darkBrown);
        phoneLabel.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        panel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        // ---------------- EMAIL ----------------

        gbc.gridx = 0;
        gbc.gridy = 3;

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(darkBrown);
        emailLabel.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        panel.add(emailField, gbc);

        // ---------------- PASSWORD ----------------

        gbc.gridx = 0;
        gbc.gridy = 4;

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(darkBrown);
        passwordLabel.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // ---------------- REGISTER BUTTON ----------------

        JButton registerButton =
                new JButton("Register");

        registerButton.setBackground(buttonBrown);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(
                new Font("Arial", Font.BOLD, 15)
        );
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setPreferredSize(
                new Dimension(200, 40)
        );

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 5, 10);

        panel.add(registerButton, gbc);

        // ---------------- REGISTER LOGIC ----------------

        registerButton.addActionListener(e -> {

            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();

            String password =
                    new String(passwordField.getPassword());

            // Empty fields
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

            // Phone validation
            if (!phone.matches("\\d{10}")) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Phone number must contain exactly 10 digits.",
                        "Invalid Phone Number",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // Email validation
            if (!isValidEmail(email)) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Please enter a valid email address.",
                        "Invalid Email",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // Password validation
            if (!isStrongPassword(password)) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Password must be at least 8 characters and contain:\n" +
                                "• One uppercase letter\n" +
                                "• One lowercase letter\n" +
                                "• One number\n" +
                                "• One special character",
                        "Weak Password",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // Create dealer
            Dealer dealer = new Dealer(
                    0,
                    name,
                    phone,
                    email,
                    password
            );

            // Save to database
            PropertyManagementSystem system =
                    new PropertyManagementSystem();

            system.addDealerToDB(dealer);

            // Success message
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
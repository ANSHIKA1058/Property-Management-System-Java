package property.management;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DealerLoginGUI extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    private final Color BACKGROUND_COLOR =
            new Color(225, 200, 170);

    private final Color TEXT_COLOR =
            new Color(70, 45, 30);

    private final Color BUTTON_COLOR =
            new Color(92, 64, 51);

    public DealerLoginGUI() {

        setTitle("Property Management");
        setSize(500, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel =
                new JPanel(new GridBagLayout());

        panel.setBackground(BACKGROUND_COLOR);

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 45, 30, 45
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(10, 10, 10, 10);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // TITLE

        JLabel title =
                new JLabel("Property Management");

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        25
                )
        );

        title.setForeground(TEXT_COLOR);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        panel.add(title, gbc);

        // EMAIL

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;

        JLabel emailLabel =
                new JLabel("Email:");

        emailLabel.setForeground(TEXT_COLOR);

        panel.add(emailLabel, gbc);

        emailField =
                new JTextField(25);

        gbc.gridx = 1;

        panel.add(emailField, gbc);

        // PASSWORD

        gbc.gridy = 2;
        gbc.gridx = 0;

        JLabel passwordLabel =
                new JLabel("Password:");

        passwordLabel.setForeground(TEXT_COLOR);

        panel.add(passwordLabel, gbc);

        passwordField =
                new JPasswordField(25);

        gbc.gridx = 1;

        panel.add(passwordField, gbc);

        // LOGIN BUTTON

        JButton loginButton =
                new JButton("Login");

        styleButton(loginButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        panel.add(loginButton, gbc);

        loginButton.addActionListener(
                e -> loginDealer()
        );

        // FORGOT PASSWORD

        JButton forgotButton =
                new JButton("Forgot Password?");

        forgotButton.setForeground(TEXT_COLOR);
        forgotButton.setBackground(BACKGROUND_COLOR);
        forgotButton.setBorderPainted(false);
        forgotButton.setFocusPainted(false);

        gbc.gridy = 4;

        panel.add(forgotButton, gbc);

        forgotButton.addActionListener(
                e -> showForgotPasswordDialog()
        );

        // REGISTER

        JButton registerButton =
                new JButton("New User? Register");

        styleButton(registerButton);

        gbc.gridy = 5;

        panel.add(registerButton, gbc);

        registerButton.addActionListener(
                e -> showRegistrationForm()
        );

        add(panel);
    }


    // =========================
    // BUTTON STYLE
    // =========================

    private void styleButton(JButton button) {

        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setPreferredSize(
                new Dimension(220, 40)
        );
    }


    // =========================
    // EMAIL VALIDATION
    // =========================

    private boolean isValidEmail(
            String email) {

        return email.matches(
                "^[A-Za-z0-9+_.-]+@" +
                        "[A-Za-z0-9.-]+\\." +
                        "[A-Za-z]{2,}$"
        );
    }


    // =========================
    // PASSWORD VALIDATION
    // =========================

    private boolean isStrongPassword(
            String password) {

        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*\\d.*")
                && password.matches(
                ".*[^A-Za-z0-9].*"
        );
    }


    // =========================
    // REGISTRATION
    // =========================

    private void showRegistrationForm() {

        JDialog dialog =
                new JDialog(
                        this,
                        "Create Dealer Account",
                        true
                );

        dialog.setSize(500, 470);
        dialog.setLocationRelativeTo(this);

        JPanel panel =
                new JPanel(
                        new GridBagLayout()
                );

        panel.setBackground(
                BACKGROUND_COLOR
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 35, 25, 35
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(10, 10, 10, 10);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        JTextField nameField =
                new JTextField(25);

        JTextField phoneField =
                new JTextField(25);

        JTextField emailField =
                new JTextField(25);

        JPasswordField passwordField =
                new JPasswordField(25);


        // NAME

        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel nameLabel =
                new JLabel("Name:");

        nameLabel.setForeground(TEXT_COLOR);

        panel.add(nameLabel, gbc);

        gbc.gridx = 1;

        panel.add(nameField, gbc);


        // PHONE

        gbc.gridx = 0;
        gbc.gridy = 1;

        JLabel phoneLabel =
                new JLabel("Phone:");

        phoneLabel.setForeground(TEXT_COLOR);

        panel.add(phoneLabel, gbc);

        gbc.gridx = 1;

        panel.add(phoneField, gbc);


        // EMAIL

        gbc.gridx = 0;
        gbc.gridy = 2;

        JLabel emailLabel =
                new JLabel("Email:");

        emailLabel.setForeground(TEXT_COLOR);

        panel.add(emailLabel, gbc);

        gbc.gridx = 1;

        panel.add(emailField, gbc);


        // PASSWORD

        gbc.gridx = 0;
        gbc.gridy = 3;

        JLabel passwordLabel =
                new JLabel("Password:");

        passwordLabel.setForeground(TEXT_COLOR);

        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;

        panel.add(passwordField, gbc);


        // REGISTER BUTTON

        JButton registerButton =
                new JButton("Register");

        styleButton(registerButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;

        panel.add(
                registerButton,
                gbc
        );


        registerButton.addActionListener(e -> {

            String name =
                    nameField.getText().trim();

            String phone =
                    phoneField.getText().trim();

            String email =
                    emailField.getText().trim();

            String password =
                    new String(
                            passwordField.getPassword()
                    );


            // EMPTY CHECK

            if (name.isEmpty()
                    || phone.isEmpty()
                    || email.isEmpty()
                    || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Please fill all fields.",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            // PHONE CHECK

            if (!phone.matches("\\d{10}")) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Phone number must contain exactly 10 digits.",
                        "Invalid Phone Number",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            // EMAIL CHECK

            if (!isValidEmail(email)) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Please enter a valid email address.",
                        "Invalid Email",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            // PASSWORD CHECK

            if (!isStrongPassword(password)) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Password must contain:\n\n" +
                                "• At least 8 characters\n" +
                                "• One uppercase letter\n" +
                                "• One lowercase letter\n" +
                                "• One number\n" +
                                "• One special character",
                        "Weak Password",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            // HASH PASSWORD

            String hashedPassword =
                    PasswordUtil.hashPassword(
                            password
                    );


            Dealer dealer =
                    new Dealer(
                            0,
                            name,
                            phone,
                            email,
                            hashedPassword
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


    // =========================
    // LOGIN
    // =========================

    private void loginDealer() {

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );


        if (email.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter email and password.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        String query =
                "SELECT dealer_id, name, password " +
                        "FROM Dealer WHERE email = ?";


        try {

            Connection con =
                    DatabaseConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, email);

            ResultSet rs =
                    ps.executeQuery();


            if (rs.next()) {

                String storedHash =
                        rs.getString("password");


                boolean valid =
                        PasswordUtil.verifyPassword(
                                password,
                                storedHash
                        );


                if (valid) {

                    int dealerId =
                            rs.getInt("dealer_id");

                    String dealerName =
                            rs.getString("name");


                    JOptionPane.showMessageDialog(
                            this,
                            "Login successful! Welcome "
                                    + dealerName,
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );


                    MainGUI mainGUI =
                            new MainGUI(
                                    dealerId,
                                    dealerName
                            );

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
                    "Database Error: "
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }


    // =========================
    // FORGOT PASSWORD
    // =========================

    private void showForgotPasswordDialog() {

        JDialog dialog =
                new JDialog(
                        this,
                        "Forgot Password",
                        true
                );

        dialog.setSize(450, 230);
        dialog.setLocationRelativeTo(this);


        JPanel panel =
                new JPanel(
                        new GridBagLayout()
                );

        panel.setBackground(
                BACKGROUND_COLOR
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(10, 10, 10, 10);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        JLabel label =
                new JLabel(
                        "Enter your registered email:"
                );

        label.setForeground(TEXT_COLOR);


        JTextField emailInput =
                new JTextField(25);


        JButton sendButton =
                new JButton("Send OTP");

        styleButton(sendButton);


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        panel.add(label, gbc);


        gbc.gridy = 1;

        panel.add(
                emailInput,
                gbc
        );


        gbc.gridy = 2;

        panel.add(
                sendButton,
                gbc
        );


        sendButton.addActionListener(e -> {

            String email =
                    emailInput.getText().trim();


            if (!isValidEmail(email)) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Please enter a valid email.",
                        "Invalid Email",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            Integer dealerId =
                    findDealerIdByEmail(email);


            if (dealerId == null) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "No account found with this email.",
                        "Account Not Found",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }


            String otp =
                    generateOTP();


            saveOTP(
                    dealerId,
                    otp
            );

            try {

                EmailUtil.sendOTP(email, otp);

                JOptionPane.showMessageDialog(
                        dialog,
                        "OTP has been sent to your registered email.",
                        "OTP Sent",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dialog.dispose();
                showOTPDialog(
                        dealerId
                );

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Failed to send OTP email.\n" +
                                ex.getMessage(),
                        "Email Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });


        dialog.add(panel);

        dialog.setVisible(true);
    }


    // =========================
    // FIND DEALER
    // =========================

    private Integer findDealerIdByEmail(
            String email) {

        String query =
                "SELECT dealer_id FROM Dealer " +
                        "WHERE email = ?";


        try {

            Connection con =
                    DatabaseConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, email);

            ResultSet rs =
                    ps.executeQuery();


            if (rs.next()) {

                return rs.getInt(
                        "dealer_id"
                );
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return null;
    }


    // =========================
    // GENERATE OTP
    // =========================

    private String generateOTP() {

        int number =
                100000 +
                        (int) (
                                Math.random()
                                        * 900000
                        );

        return String.valueOf(number);
    }


    // =========================
    // SAVE OTP
    // =========================

    private void saveOTP(
            int dealerId,
            String otp) {

        try {

            Connection con =
                    DatabaseConnection.getConnection();


            // Hash OTP before storing

            String otpHash =
                    PasswordUtil.hashPassword(
                            otp
                    );


            String query =
                    "INSERT INTO PasswordReset " +
                            "(dealer_id, otp_hash, expires_at, used) " +
                            "VALUES (?, ?, DATE_ADD(NOW(), INTERVAL 10 MINUTE), FALSE)";


            PreparedStatement ps =
                    con.prepareStatement(query);


            ps.setInt(1, dealerId);

            ps.setString(
                    2,
                    otpHash
            );

            ps.executeUpdate();


        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }


    // =========================
    // OTP VERIFICATION
    // =========================

    private void showOTPDialog(
            int dealerId) {

        JDialog dialog =
                new JDialog(
                        this,
                        "Verify OTP",
                        true
                );

        dialog.setSize(450, 230);
        dialog.setLocationRelativeTo(this);


        JPanel panel =
                new JPanel(
                        new GridBagLayout()
                );

        panel.setBackground(
                BACKGROUND_COLOR
        );


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(10, 10, 10, 10);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        JLabel label =
                new JLabel(
                        "Enter the OTP:"
                );

        label.setForeground(TEXT_COLOR);


        JTextField otpField =
                new JTextField(15);


        JButton verifyButton =
                new JButton("Verify OTP");

        styleButton(verifyButton);


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        panel.add(
                label,
                gbc
        );


        gbc.gridy = 1;

        panel.add(
                otpField,
                gbc
        );


        gbc.gridy = 2;

        panel.add(
                verifyButton,
                gbc
        );


        verifyButton.addActionListener(e -> {

            String enteredOTP =
                    otpField.getText().trim();


            if (verifyOTP(
                    dealerId,
                    enteredOTP
            )) {

                dialog.dispose();

                showResetPasswordDialog(
                        dealerId
                );

            } else {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Invalid or expired OTP.",
                        "OTP Verification Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });


        dialog.add(panel);

        dialog.setVisible(true);
    }


    // =========================
    // VERIFY OTP
    // =========================

    private boolean verifyOTP(
            int dealerId,
            String otp) {

        String query =
                "SELECT reset_id, otp_hash " +
                        "FROM PasswordReset " +
                        "WHERE dealer_id = ? " +
                        "AND used = FALSE " +
                        "AND expires_at > NOW() " +
                        "ORDER BY reset_id DESC LIMIT 1";


        try {

            Connection con =
                    DatabaseConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(
                    1,
                    dealerId
            );


            ResultSet rs =
                    ps.executeQuery();


            if (rs.next()) {

                int resetId =
                        rs.getInt(
                                "reset_id"
                        );

                String storedHash =
                        rs.getString(
                                "otp_hash"
                        );


                if (PasswordUtil.verifyPassword(
                        otp,
                        storedHash
                )) {

                    String update =
                            "UPDATE PasswordReset " +
                                    "SET used = TRUE " +
                                    "WHERE reset_id = ?";


                    PreparedStatement updatePs =
                            con.prepareStatement(
                                    update
                            );

                    updatePs.setInt(
                            1,
                            resetId
                    );

                    updatePs.executeUpdate();

                    return true;
                }
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return false;
    }


    // =========================
    // RESET PASSWORD
    // =========================

    private void showResetPasswordDialog(
            int dealerId) {

        JDialog dialog =
                new JDialog(
                        this,
                        "Reset Password",
                        true
                );

        dialog.setSize(450, 280);
        dialog.setLocationRelativeTo(this);


        JPanel panel =
                new JPanel(
                        new GridBagLayout()
                );

        panel.setBackground(
                BACKGROUND_COLOR
        );


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(10, 10, 10, 10);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        JLabel label =
                new JLabel(
                        "Enter your new password:"
                );

        label.setForeground(
                TEXT_COLOR
        );


        JPasswordField newPassword =
                new JPasswordField(25);


        JPasswordField confirmPassword =
                new JPasswordField(25);


        JButton resetButton =
                new JButton(
                        "Reset Password"
                );

        styleButton(resetButton);


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        panel.add(
                label,
                gbc
        );


        gbc.gridwidth = 1;

        gbc.gridy = 1;
        gbc.gridx = 0;

        panel.add(
                new JLabel("New Password:"),
                gbc
        );


        gbc.gridx = 1;

        panel.add(
                newPassword,
                gbc
        );


        gbc.gridy = 2;
        gbc.gridx = 0;

        panel.add(
                new JLabel("Confirm Password:"),
                gbc
        );


        gbc.gridx = 1;

        panel.add(
                confirmPassword,
                gbc
        );


        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        panel.add(
                resetButton,
                gbc
        );


        resetButton.addActionListener(e -> {

            String password =
                    new String(
                            newPassword.getPassword()
                    );

            String confirm =
                    new String(
                            confirmPassword.getPassword()
                    );


            if (!isStrongPassword(password)) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Password must contain:\n\n" +
                                "• At least 8 characters\n" +
                                "• One uppercase letter\n" +
                                "• One lowercase letter\n" +
                                "• One number\n" +
                                "• One special character",
                        "Weak Password",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            if (!password.equals(confirm)) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Passwords do not match.",
                        "Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            String hashedPassword =
                    PasswordUtil.hashPassword(
                            password
                    );


            updatePassword(
                    dealerId,
                    hashedPassword
            );


            JOptionPane.showMessageDialog(
                    dialog,
                    "Password reset successfully!\nPlease login.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );


            dialog.dispose();
        });


        dialog.add(panel);

        dialog.setVisible(true);
    }


    // =========================
    // UPDATE PASSWORD
    // =========================

    private void updatePassword(
            int dealerId,
            String hashedPassword) {

        String query =
                "UPDATE Dealer SET password = ? " +
                        "WHERE dealer_id = ?";


        try {

            Connection con =
                    DatabaseConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(
                    1,
                    hashedPassword
            );

            ps.setInt(
                    2,
                    dealerId
            );

            ps.executeUpdate();


        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }


    // =========================
    // MAIN
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            DealerLoginGUI loginGUI =
                    new DealerLoginGUI();

            loginGUI.setVisible(true);
        });
    }
}
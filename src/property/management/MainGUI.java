package property.management;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    private final Color SIDEBAR_COLOR = new Color(30, 41, 59);
    private final Color BACKGROUND_COLOR = new Color(248, 250, 252);
    private final Color CARD_COLOR = Color.WHITE;

    public MainGUI() {

        setTitle("Property Management System");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createDashboard(), BorderLayout.CENTER);
    }

    // ================= SIDEBAR =================

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel();

        sidebar.setPreferredSize(new Dimension(220, 700));
        sidebar.setBackground(SIDEBAR_COLOR);

        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        // Project title
        JLabel title = new JLabel("  🏠 Property");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBorder(
                BorderFactory.createEmptyBorder(30, 10, 30, 10)
        );

        sidebar.add(title);

        // Menu buttons
        JButton dashboardButton = createMenuButton("Dashboard");
        JButton propertyButton = createMenuButton("Properties");
        JButton ownerButton = createMenuButton("Owners");
        JButton dealerButton = createMenuButton("Dealers");
        JButton visitButton = createMenuButton("Visits");

        sidebar.add(dashboardButton);
        sidebar.add(propertyButton);
        sidebar.add(ownerButton);
        sidebar.add(dealerButton);
        sidebar.add(visitButton);

        // Space
        sidebar.add(Box.createVerticalGlue());

        // Exit button
        JButton exitButton = createMenuButton("Exit");

        sidebar.add(exitButton);

        // Exit functionality
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        return sidebar;
    }

    // ================= MENU BUTTON =================

    private JButton createMenuButton(String text) {

        JButton button = new JButton(text);

        button.setMaximumSize(
                new Dimension(200, 50)
        );

        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        button.setForeground(Color.WHITE);
        button.setBackground(SIDEBAR_COLOR);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        return button;
    }

    // ================= DASHBOARD =================

    private JPanel createDashboard() {

        JPanel dashboard = new JPanel(
                new BorderLayout()
        );

        dashboard.setBackground(BACKGROUND_COLOR);

        // Header
        JPanel header = new JPanel(
                new BorderLayout()
        );

        header.setBackground(BACKGROUND_COLOR);
        header.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 30, 20, 30
                )
        );

        JLabel heading = new JLabel(
                "Dashboard"
        );

        heading.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        heading.setForeground(
                new Color(30, 41, 59)
        );

        JLabel welcome = new JLabel(
                "Property Management Overview"
        );

        welcome.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        welcome.setForeground(
                new Color(100, 116, 139)
        );

        header.add(heading, BorderLayout.NORTH);
        header.add(welcome, BorderLayout.SOUTH);

        dashboard.add(header, BorderLayout.NORTH);

        // Cards
        JPanel cardsPanel = new JPanel(
                new GridLayout(1, 4, 20, 20)
        );

        cardsPanel.setBackground(BACKGROUND_COLOR);

        cardsPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 30, 30, 30
                )
        );

        cardsPanel.add(
                createCard(
                        "Total Properties",
                        "0"
                )
        );

        cardsPanel.add(
                createCard(
                        "Available",
                        "0"
                )
        );

        cardsPanel.add(
                createCard(
                        "Sold",
                        "0"
                )
        );

        cardsPanel.add(
                createCard(
                        "Rented",
                        "0"
                )
        );

        dashboard.add(
                cardsPanel,
                BorderLayout.CENTER
        );

        return dashboard;
    }

    // ================= CARD =================

    private JPanel createCard(
            String title,
            String value
    ) {

        JPanel card = new JPanel();

        card.setBackground(CARD_COLOR);

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(226, 232, 240)
                        ),
                        BorderFactory.createEmptyBorder(
                                20, 20, 20, 20
                        )
                )
        );

        JLabel titleLabel = new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        titleLabel.setForeground(
                new Color(100, 116, 139)
        );

        JLabel valueLabel = new JLabel(value);

        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        32
                )
        );

        valueLabel.setForeground(
                new Color(30, 41, 59)
        );

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(15));
        card.add(valueLabel);

        return card;
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            MainGUI gui = new MainGUI();

            gui.setVisible(true);
        });
    }
}
package property.management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainGUI extends JFrame {

    private final Color SIDEBAR_COLOR = new Color(30, 41, 59);
    private final Color BACKGROUND_COLOR = new Color(248, 250, 252);
    private final Color CARD_COLOR = Color.WHITE;

    private JPanel contentPanel;
    private CardLayout cardLayout;

    public MainGUI() {

        setTitle("Property Management System");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Add pages
        contentPanel.add(createDashboard(), "Dashboard");
        contentPanel.add(createPropertiesPage(), "Properties");

        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    // =====================================================
    // SIDEBAR
    // =====================================================

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel();

        sidebar.setPreferredSize(new Dimension(220, 700));
        sidebar.setBackground(SIDEBAR_COLOR);

        sidebar.setLayout(
                new BoxLayout(sidebar, BoxLayout.Y_AXIS)
        );

        // Title
        JLabel title = new JLabel("  🏠 Property");

        title.setForeground(Color.WHITE);
        title.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 10, 30, 10
                )
        );

        sidebar.add(title);

        // Dashboard
        JButton dashboardButton =
                createMenuButton("Dashboard");

        dashboardButton.addActionListener(e ->
                cardLayout.show(
                        contentPanel,
                        "Dashboard"
                )
        );

        sidebar.add(dashboardButton);

        // Properties
        JButton propertyButton =
                createMenuButton("Properties");

        propertyButton.addActionListener(e ->
                cardLayout.show(
                        contentPanel,
                        "Properties"
                )
        );

        sidebar.add(propertyButton);

        // Owners
        JButton ownerButton =
                createMenuButton("Owners");

        sidebar.add(ownerButton);

        // Dealers
        JButton dealerButton =
                createMenuButton("Dealers");

        sidebar.add(dealerButton);

        // Visits
        JButton visitButton =
                createMenuButton("Visits");

        sidebar.add(visitButton);

        // Push buttons to bottom
        sidebar.add(
                Box.createVerticalGlue()
        );

        // Exit
        JButton exitButton =
                createMenuButton("Exit");

        exitButton.addActionListener(e ->
                System.exit(0)
        );

        sidebar.add(exitButton);

        return sidebar;
    }

    // =====================================================
    // MENU BUTTON
    // =====================================================

    private JButton createMenuButton(
            String text
    ) {

        JButton button = new JButton(text);

        button.setMaximumSize(
                new Dimension(200, 50)
        );

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        button.setForeground(Color.WHITE);
        button.setBackground(SIDEBAR_COLOR);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        return button;
    }

    // =====================================================
    // DASHBOARD
    // =====================================================

    private JPanel createDashboard() {

        JPanel dashboard =
                new JPanel(new BorderLayout());

        dashboard.setBackground(
                BACKGROUND_COLOR
        );

        // Header
        JPanel header =
                new JPanel(new BorderLayout());

        header.setBackground(
                BACKGROUND_COLOR
        );

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 30, 20, 30
                )
        );

        JLabel heading =
                new JLabel("Dashboard");

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        heading.setForeground(
                new Color(30, 41, 59)
        );

        JLabel welcome =
                new JLabel(
                        "Property Management Overview"
                );

        welcome.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        welcome.setForeground(
                new Color(100, 116, 139)
        );

        header.add(
                heading,
                BorderLayout.NORTH
        );

        header.add(
                welcome,
                BorderLayout.SOUTH
        );

        dashboard.add(
                header,
                BorderLayout.NORTH
        );

        // Cards
        JPanel cardsPanel =
                new JPanel(
                        new GridLayout(
                                1, 4, 20, 20
                        )
                );

        cardsPanel.setBackground(
                BACKGROUND_COLOR
        );

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

    // =====================================================
    // DASHBOARD CARD
    // =====================================================

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

        JLabel titleLabel =
                new JLabel(title);

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

        JLabel valueLabel =
                new JLabel(value);

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

        card.add(
                Box.createVerticalStrut(15)
        );

        card.add(valueLabel);

        return card;
    }

    // =====================================================
    // PROPERTIES PAGE
    // =====================================================

    private JPanel createPropertiesPage() {

        JPanel panel =
                new JPanel(new BorderLayout());

        panel.setBackground(
                BACKGROUND_COLOR
        );

        // ---------------- HEADER ----------------

        JPanel header =
                new JPanel(new BorderLayout());

        header.setBackground(
                BACKGROUND_COLOR
        );

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 15, 25
                )
        );

        JLabel title =
                new JLabel("Properties");

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(
                new Color(30, 41, 59)
        );

        header.add(
                title,
                BorderLayout.WEST
        );

        // ---------------- SEARCH ----------------

        JPanel searchPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        searchPanel.setBackground(
                BACKGROUND_COLOR
        );

        JTextField searchField =
                new JTextField(15);

        searchField.setPreferredSize(
                new Dimension(180, 35)
        );

        JButton searchButton =
                new JButton("Search");

        JButton availableButton =
                new JButton("Available");

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(availableButton);

        header.add(
                searchPanel,
                BorderLayout.EAST
        );

        panel.add(
                header,
                BorderLayout.NORTH
        );

        // ---------------- TABLE ----------------

        String[] columns = {
                "ID",
                "Property No",
                "Location",
                "Price",
                "Type",
                "Purpose",
                "Status",
                "Dealer ID",
                "Owner ID"
        };

        DefaultTableModel model =
                new DefaultTableModel(
                        columns,
                        0
                );

        JTable table =
                new JTable(model);

        table.setRowHeight(30);

        table.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                14
                        )
                );

        table.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(table);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder(
                        0, 25, 15, 25
                )
        );

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // ---------------- BOTTOM BUTTONS ----------------

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        bottomPanel.setBackground(
                BACKGROUND_COLOR
        );

        bottomPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        5, 25, 20, 25
                )
        );

        JButton addButton =
                new JButton("Add Property");

        JButton updateButton =
                new JButton("Update");

        JButton deleteButton =
                new JButton("Delete");

        bottomPanel.add(addButton);
        bottomPanel.add(updateButton);
        bottomPanel.add(deleteButton);

        panel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // ---------------- BUTTON ACTIONS ----------------

        addButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Add Property form will be connected in the next step.",
                    "Add Property",
                    JOptionPane.INFORMATION_MESSAGE
            );

        });

        updateButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Update functionality will be connected in the next step.",
                    "Update Property",
                    JOptionPane.INFORMATION_MESSAGE
            );

        });

        deleteButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Delete functionality will be connected in the next step.",
                    "Delete Property",
                    JOptionPane.INFORMATION_MESSAGE
            );

        });

        searchButton.addActionListener(e -> {

            String location =
                    searchField.getText();

            JOptionPane.showMessageDialog(
                    this,
                    "Search by location: " + location,
                    "Search",
                    JOptionPane.INFORMATION_MESSAGE
            );

        });

        availableButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Available properties filter will be connected in the next step.",
                    "Available",
                    JOptionPane.INFORMATION_MESSAGE
            );

        });

        return panel;
    }

    // =====================================================
    // MAIN
    // =====================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            MainGUI gui =
                    new MainGUI();

            gui.setVisible(true);
        });
    }
}
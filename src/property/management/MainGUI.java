package property.management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainGUI extends JFrame {

    private PropertyManagementSystem system;
    private final Color SIDEBAR_COLOR = new Color(30, 41, 59);
    private final Color BACKGROUND_COLOR = new Color(248, 250, 252);
    private final Color CARD_COLOR = Color.WHITE;

    private JPanel contentPanel;
    private CardLayout cardLayout;

    public MainGUI() {

        system = new PropertyManagementSystem();
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

            showAddPropertyForm();

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
    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JComponent component
    ) {

        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        gbc.gridx = 0;
        gbc.gridy = row;

        JLabel label = new JLabel(labelText);

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        panel.add(label, gbc);

        gbc.weightx = 0.7;
        gbc.gridx = 1;

        panel.add(component, gbc);
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




    // =====================================================
// ADD PROPERTY FORM
// =====================================================

    private void showAddPropertyForm() {

        JDialog dialog = new JDialog(
                this,
                "Add Property",
                true
        );

        dialog.setSize(550, 600);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 30, 20, 30
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fields
        JTextField idField = new JTextField();
        JTextField numberField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField priceField = new JTextField();

        JComboBox<String> typeBox =
                new JComboBox<>(
                        new String[]{
                                "FLAT",
                                "VILLA",
                                "PLOT",
                                "SHOP"
                        }
                );

        JComboBox<String> purposeBox =
                new JComboBox<>(
                        new String[]{
                                "SELL",
                                "RENT"
                        }
                );

        JTextField dealerField = new JTextField();
        JTextField ownerField = new JTextField();
        JTextField descriptionField = new JTextField();

        // Add rows
        addFormRow(panel, gbc, 0,
                "Property ID:", idField);

        addFormRow(panel, gbc, 1,
                "Property Number:", numberField);

        addFormRow(panel, gbc, 2,
                "Location:", locationField);

        addFormRow(panel, gbc, 3,
                "Price:", priceField);

        addFormRow(panel, gbc, 4,
                "Property Type:", typeBox);

        addFormRow(panel, gbc, 5,
                "Purpose:", purposeBox);

        addFormRow(panel, gbc, 6,
                "Dealer ID:", dealerField);

        addFormRow(panel, gbc, 7,
                "Owner ID:", ownerField);

        addFormRow(panel, gbc, 8,
                "Description:", descriptionField);

        // Save button
        JButton saveButton =
                new JButton("Save Property");

        saveButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.weightx = 1;

        panel.add(saveButton, gbc);

        // Save action
        saveButton.addActionListener(e -> {

            try {

                int id = Integer.parseInt(
                        idField.getText()
                );

                String propertyNumber =
                        numberField.getText();

                String location =
                        locationField.getText();

                long price = Long.parseLong(
                        priceField.getText()
                );

                int dealerId = Integer.parseInt(
                        dealerField.getText()
                );

                int ownerId = Integer.parseInt(
                        ownerField.getText()
                );

                String description =
                        descriptionField.getText();

                PropertyType type =
                        PropertyType.valueOf(
                                typeBox
                                        .getSelectedItem()
                                        .toString()
                        );

                PropertyPurpose purpose =
                        PropertyPurpose.valueOf(
                                purposeBox
                                        .getSelectedItem()
                                        .toString()
                        );

                Property property =
                        new Property(
                                id,
                                propertyNumber,
                                location,
                                price,
                                type,
                                purpose,
                                dealerId,
                                ownerId,
                                description
                        );

                system.addProperty(property);
                system.addPropertyToDB(property);

                JOptionPane.showMessageDialog(
                        dialog,
                        "Property added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dialog.dispose();

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Please enter valid numbers.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        dialog.add(panel);

        dialog.setVisible(true);
    }
}
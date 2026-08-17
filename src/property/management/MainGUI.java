package property.management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainGUI extends JFrame {

    private PropertyManagementSystem system;

    private DefaultTableModel propertyTableModel;
    private JTable propertyTable;

    private JLabel totalPropertiesLabel;
    private JLabel availableLabel;
    private JLabel soldLabel;
    private JLabel rentedLabel;

    private final Color SIDEBAR_COLOR = new Color(30, 41, 59);
    private final Color BACKGROUND_COLOR = new Color(248, 250, 252);
    private final Color CARD_COLOR = Color.WHITE;

    private JPanel contentPanel;
    private CardLayout cardLayout;

    // =====================================================
    // LOGGED IN DEALER
    // =====================================================

    private int loggedInDealerId = -1;
    private String loggedInDealerName = null;

    // =====================================================
    // NORMAL / ADMIN CONSTRUCTOR
    // =====================================================

    public MainGUI() {

        this.system = new PropertyManagementSystem();

        initializeGUI();
    }

    // =====================================================
    // DEALER CONSTRUCTOR
    // =====================================================

    public MainGUI(int dealerId, String dealerName) {

        this.loggedInDealerId = dealerId;
        this.loggedInDealerName = dealerName;

        this.system = new PropertyManagementSystem();

        initializeGUI();
    }

    // =====================================================
    // INITIALIZE GUI
    // =====================================================

    private void initializeGUI() {

        setTitle("Property Management System");

        setSize(1200, 700);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        cardLayout = new CardLayout();

        contentPanel = new JPanel(cardLayout);

        // Pages
        contentPanel.add(
                createDashboard(),
                "Dashboard"
        );

        contentPanel.add(
                createPropertiesPage(),
                "Properties"
        );
        contentPanel.add(createVisitsPage(), "Visits");
        contentPanel.add(createOwnersPage(), "Owners");

        refreshDashboard();

        setLayout(new BorderLayout());

        add(
                createSidebar(),
                BorderLayout.WEST
        );

        add(
                contentPanel,
                BorderLayout.CENTER
        );
    }

    // =====================================================
    // CHECK DEALER LOGIN
    // =====================================================

    private boolean isDealerLoggedIn() {

        return loggedInDealerId != -1;
    }

    // =====================================================
    // SIDEBAR
    // =====================================================

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel();

        sidebar.setPreferredSize(
                new Dimension(220, 700)
        );

        sidebar.setBackground(
                SIDEBAR_COLOR
        );

        sidebar.setLayout(
                new BoxLayout(
                        sidebar,
                        BoxLayout.Y_AXIS
                )
        );

        // Title
        JLabel title =
                new JLabel("  🏠 Property");

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        10,
                        30,
                        10
                )
        );

        sidebar.add(title);

        // =================================================
        // DEALER INFO
        // =================================================

        if (isDealerLoggedIn()) {

            JLabel dealerLabel =
                    new JLabel(
                            "  Dealer: "
                                    + loggedInDealerName
                    );

            dealerLabel.setForeground(
                    Color.LIGHT_GRAY
            );

            dealerLabel.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            13
                    )
            );

            dealerLabel.setBorder(
                    BorderFactory.createEmptyBorder(
                            0,
                            10,
                            20,
                            10
                    )
            );

            sidebar.add(dealerLabel);
        }

        // =================================================
        // DASHBOARD
        // =================================================

        JButton dashboardButton =
                createMenuButton("Dashboard");

        dashboardButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "Dashboard"
                )
        );

        sidebar.add(dashboardButton);

        // =================================================
        // PROPERTIES
        // =================================================

        JButton propertyButton =
                createMenuButton("Properties");

        propertyButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "Properties"
                )
        );

        sidebar.add(propertyButton);

        // =================================================
        // OWNERS
        // =================================================

        JButton ownerButton =
                createMenuButton("Owners");

        ownerButton.addActionListener(e ->
                cardLayout.show(
                        contentPanel,
                        "Owners"
                )
        );

        sidebar.add(ownerButton);

        // =================================================
        // DEALERS
        // =================================================

        // Dealer ko Dealers management ki zarurat nahi.
        // Admin ke liye button rahega.

        if (!isDealerLoggedIn()) {

            JButton dealerButton =
                    createMenuButton("Dealers");

            sidebar.add(dealerButton);
        }

        // =================================================
        // VISITS
        // =================================================

        JButton visitButton =
                createMenuButton("Visits");

        visitButton.addActionListener(e ->
                cardLayout.show(
                        contentPanel,
                        "Visits"
                )
        );

        sidebar.add(visitButton);

        // Push buttons down
        sidebar.add(
                Box.createVerticalGlue()
        );

        // =================================================
        // LOGOUT
        // =================================================

        if (isDealerLoggedIn()) {

            JButton logoutButton =
                    createMenuButton("Logout");

            logoutButton.addActionListener(
                    e -> logout()
            );

            sidebar.add(logoutButton);

        } else {

            JButton exitButton =
                    createMenuButton("Exit");

            exitButton.addActionListener(
                    e -> System.exit(0)
            );

            sidebar.add(exitButton);
        }

        return sidebar;
    }

    // =====================================================
    // LOGOUT
    // =====================================================

    private void logout() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice == JOptionPane.YES_OPTION) {

            dispose();

            DealerLoginGUI loginGUI =
                    new DealerLoginGUI();

            loginGUI.setVisible(true);
        }
    }

    // =====================================================
    // MENU BUTTON
    // =====================================================

    private JButton createMenuButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setMaximumSize(
                new Dimension(
                        200,
                        50
                )
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

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                SIDEBAR_COLOR
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        return button;
    }

    // =====================================================
    // DASHBOARD
    // =====================================================

    private JPanel createDashboard() {

        JPanel dashboard =
                new JPanel(
                        new BorderLayout()
                );

        dashboard.setBackground(
                BACKGROUND_COLOR
        );

        // Header
        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                BACKGROUND_COLOR
        );

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        30,
                        20,
                        30
                )
        );

        String headingText;

        if (isDealerLoggedIn()) {

            headingText =
                    "Welcome, "
                            + loggedInDealerName;

        } else {

            headingText =
                    "Dashboard";
        }

        JLabel heading =
                new JLabel(
                        headingText
                );

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        heading.setForeground(
                new Color(
                        30,
                        41,
                        59
                )
        );

        JLabel welcome =
                new JLabel(
                        isDealerLoggedIn()
                                ? "Your Property Management Overview"
                                : "Property Management Overview"
                );

        welcome.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        welcome.setForeground(
                new Color(
                        100,
                        116,
                        139
                )
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

        // =================================================
        // CARDS
        // =================================================

        JPanel cardsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                20,
                                20
                        )
                );

        cardsPanel.setBackground(
                BACKGROUND_COLOR
        );

        cardsPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        30,
                        30,
                        30
                )
        );

        JPanel totalCard =
                createCard(
                        "Total Properties",
                        "0"
                );

        JPanel availableCard =
                createCard(
                        "Available",
                        "0"
                );

        JPanel soldCard =
                createCard(
                        "Sold",
                        "0"
                );

        JPanel rentedCard =
                createCard(
                        "Rented",
                        "0"
                );

        totalPropertiesLabel =
                (JLabel) totalCard.getComponent(2);

        availableLabel =
                (JLabel) availableCard.getComponent(2);

        soldLabel =
                (JLabel) soldCard.getComponent(2);

        rentedLabel =
                (JLabel) rentedCard.getComponent(2);

        cardsPanel.add(totalCard);

        cardsPanel.add(availableCard);

        cardsPanel.add(soldCard);

        cardsPanel.add(rentedCard);

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

        JPanel card =
                new JPanel();

        card.setBackground(
                CARD_COLOR
        );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        226,
                                        232,
                                        240
                                )
                        ),
                        BorderFactory.createEmptyBorder(
                                20,
                                20,
                                20,
                                20
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
                new Color(
                        100,
                        116,
                        139
                )
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
                new Color(
                        30,
                        41,
                        59
                )
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
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(
                BACKGROUND_COLOR
        );

        // =================================================
        // HEADER
        // =================================================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                BACKGROUND_COLOR
        );

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        25,
                        15,
                        25
                )
        );

        JLabel title =
                new JLabel(
                        isDealerLoggedIn()
                                ? "My Properties"
                                : "Properties"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(
                new Color(
                        30,
                        41,
                        59
                )
        );

        header.add(
                title,
                BorderLayout.WEST
        );

        // =================================================
        // SEARCH
        // =================================================

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
                new Dimension(
                        180,
                        35
                )
        );

        JButton searchButton =
                new JButton("Search");

        JButton availableButton =
                new JButton("Available");

        JButton allButton =
                new JButton("All");

        searchPanel.add(searchField);

        searchPanel.add(searchButton);

        searchPanel.add(availableButton);

        searchPanel.add(allButton);

        header.add(
                searchPanel,
                BorderLayout.EAST
        );

        panel.add(
                header,
                BorderLayout.NORTH
        );

        // =================================================
        // TABLE
        // =================================================

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

        propertyTableModel =
                new DefaultTableModel(
                        columns,
                        0
                );

        propertyTable =
                new JTable(
                        propertyTableModel
                );

        propertyTable.setRowHeight(30);

        propertyTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                14
                        )
                );

        propertyTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        propertyTable
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        25,
                        15,
                        25
                )
        );

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =================================================
        // BOTTOM BUTTONS
        // =================================================

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
                        5,
                        25,
                        20,
                        25
                )
        );

        JButton addButton =
                new JButton(
                        "Add Property"
                );

        JButton updateButton =
                new JButton(
                        "Update"
                );

        JButton deleteButton =
                new JButton(
                        "Delete"
                );

        bottomPanel.add(addButton);

        bottomPanel.add(updateButton);

        bottomPanel.add(deleteButton);

        panel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // =================================================
        // ADD
        // =================================================

        addButton.addActionListener(
                e -> showAddPropertyForm()
        );

        // =================================================
        // UPDATE
        // =================================================

        updateButton.addActionListener(
                e -> updateSelectedProperty()
        );

        // =================================================
        // DELETE
        // =================================================

        deleteButton.addActionListener(
                e -> deleteSelectedProperty()
        );

        // =================================================
        // SEARCH
        // =================================================

        searchButton.addActionListener(e -> {

            String searchText =
                    searchField
                            .getText()
                            .trim()
                            .toLowerCase();

            if (searchText.isEmpty()) {

                refreshPropertyTable();

                return;
            }

            propertyTableModel.setRowCount(0);

            boolean found = false;

            for (Property p :
                    getVisibleProperties()) {

                String location =
                        p.getLocation()
                                .toLowerCase();

                String propertyNumber =
                        p.getPropertyNumber()
                                .toLowerCase();

                String price =
                        String.valueOf(
                                p.getPrice()
                        );

                // IMPORTANT:
                // Price exact match only
                if (location.contains(searchText)
                        || propertyNumber.contains(searchText)
                        || price.equals(searchText)) {

                    addPropertyToTable(p);

                    found = true;
                }
            }

            if (!found) {

                JOptionPane.showMessageDialog(
                        this,
                        "No property found for: "
                                + searchText,
                        "Search Result",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        // =================================================
        // AVAILABLE
        // =================================================

        availableButton.addActionListener(
                e -> {

                    propertyTableModel
                            .setRowCount(0);

                    for (Property p :
                            getVisibleProperties()) {

                        if (p.getStatus()
                                == PropertyStatus.AVAILABLE) {

                            addPropertyToTable(p);
                        }
                    }

                    if (propertyTableModel
                            .getRowCount() == 0) {

                        JOptionPane.showMessageDialog(
                                this,
                                "No available properties found.",
                                "Available Properties",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
        );

        // =================================================
        // ALL
        // =================================================

        allButton.addActionListener(
                e -> refreshPropertyTable()
        );

        refreshPropertyTable();

        return panel;
    }

    // =====================================================
    // GET VISIBLE PROPERTIES
    // =====================================================

    private java.util.List<Property>
    getVisibleProperties() {

        java.util.List<Property>
                visibleProperties =
                new java.util.ArrayList<>();

        for (Property p :
                system.getProperties()) {

            // Admin sees everything
            if (!isDealerLoggedIn()) {

                visibleProperties.add(p);

            }

            // Dealer sees only own properties
            else if (
                    p.getDealerId()
                            == loggedInDealerId
            ) {

                visibleProperties.add(p);
            }
        }

        return visibleProperties;
    }

    // =====================================================
    // ADD PROPERTY TO TABLE
    // =====================================================

    private void addPropertyToTable(
            Property p
    ) {

        Object[] row = {

                p.getPropertyId(),

                p.getPropertyNumber(),

                p.getLocation(),

                p.getPrice(),

                p.getType(),

                p.getPurpose(),

                p.getStatus(),

                p.getDealerId(),

                p.getOwnerId()
        };

        propertyTableModel.addRow(row);
    }

    // =====================================================
    // REFRESH PROPERTY TABLE
    // =====================================================

    private void refreshPropertyTable() {

        propertyTableModel.setRowCount(0);

        for (Property p :
                getVisibleProperties()) {

            addPropertyToTable(p);
        }
    }

    // =====================================================
    // UPDATE PROPERTY
    // =====================================================

    private void updateSelectedProperty() {

        int selectedRow =
                propertyTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a property to update.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String propertyNumber =
                propertyTableModel
                        .getValueAt(
                                selectedRow,
                                1
                        )
                        .toString();

        Property property =
                system.searchProperty(
                        propertyNumber
                );

        if (property == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Property not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Security check
        if (isDealerLoggedIn()
                && property.getDealerId()
                != loggedInDealerId) {

            JOptionPane.showMessageDialog(
                    this,
                    "You can only update your own properties.",
                    "Access Denied",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        String[] statuses = {

                "AVAILABLE",
                "SOLD",
                "RENTED"
        };

        JComboBox<String> statusBox =
                new JComboBox<>(
                        statuses
                );

        statusBox.setSelectedItem(
                property
                        .getStatus()
                        .toString()
        );

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        statusBox,
                        "Update Property Status",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result !=
                JOptionPane.OK_OPTION) {

            return;
        }

        PropertyStatus newStatus =
                PropertyStatus.valueOf(
                        statusBox
                                .getSelectedItem()
                                .toString()
                );

        // Memory
        system.updateProperty(
                propertyNumber,
                newStatus
        );

        // Database
        system.updatePropertyStatusInDB(
                property.getPropertyId(),
                newStatus
        );

        refreshPropertyTable();

        refreshDashboard();

        JOptionPane.showMessageDialog(
                this,
                "Property status updated successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =====================================================
    // DELETE PROPERTY
    // =====================================================

    private void deleteSelectedProperty() {

        int selectedRow =
                propertyTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a property to delete.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int propertyId =
                (int) propertyTableModel
                        .getValueAt(
                                selectedRow,
                                0
                        );

        String propertyNumber =
                propertyTableModel
                        .getValueAt(
                                selectedRow,
                                1
                        )
                        .toString();

        Property property =
                system.searchProperty(
                        propertyNumber
                );

        if (property == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Property not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Security check
        if (isDealerLoggedIn()
                && property.getDealerId()
                != loggedInDealerId) {

            JOptionPane.showMessageDialog(
                    this,
                    "You can only delete your own properties.",
                    "Access Denied",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete property "
                                + propertyNumber
                                + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (choice !=
                JOptionPane.YES_OPTION) {

            return;
        }

        system.deletePropertyFromDB(
                propertyId
        );

        system.getProperties()
                .remove(property);

        refreshPropertyTable();

        refreshDashboard();

        JOptionPane.showMessageDialog(
                this,
                "Property deleted successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =====================================================
    // ADD FORM ROW
    // =====================================================

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

        JLabel label =
                new JLabel(
                        labelText
                );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        panel.add(
                label,
                gbc
        );

        gbc.weightx = 0.7;

        gbc.gridx = 1;

        panel.add(
                component,
                gbc
        );
    }

    // =====================================================
    // ADD PROPERTY FORM
    // =====================================================

    private void showAddPropertyForm() {

        JDialog dialog =
                new JDialog(
                        this,
                        "Add Property",
                        true
                );

        dialog.setSize(
                550,
                600
        );

        dialog.setLocationRelativeTo(
                this
        );

        JPanel panel =
                new JPanel(
                        new GridBagLayout()
                );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        30,
                        20,
                        30
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        8,
                        8,
                        8,
                        8
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // =================================================
        // FIELDS
        // =================================================

        JTextField idField =
                new JTextField();

        JTextField numberField =
                new JTextField();

        JTextField locationField =
                new JTextField();

        JTextField priceField =
                new JTextField();

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

        // =================================================
        // DEALER
        // =================================================

        JComboBox<Dealer> dealerBox =
                new JComboBox<>();

        if (isDealerLoggedIn()) {

            Dealer loggedDealer =
                    system.findDealer(
                            loggedInDealerId
                    );

            if (loggedDealer != null) {

                dealerBox.addItem(
                        loggedDealer
                );
            }

            // Dealer cannot change dealer
            dealerBox.setEnabled(false);

        } else {

            for (Dealer dealer :
                    system.getDealers()) {

                dealerBox.addItem(
                        dealer
                );
            }
        }

        // =================================================
        // OWNER
        // =================================================

        JComboBox<Owner> ownerBox =
                new JComboBox<>();

        for (Owner owner :
                system.getOwners()) {

            ownerBox.addItem(owner);
        }

        JTextField descriptionField =
                new JTextField();

        // =================================================
        // ADD ROWS
        // =================================================

        addFormRow(
                panel,
                gbc,
                0,
                "Property ID:",
                idField
        );

        addFormRow(
                panel,
                gbc,
                1,
                "Property Number:",
                numberField
        );

        addFormRow(
                panel,
                gbc,
                2,
                "Location:",
                locationField
        );

        addFormRow(
                panel,
                gbc,
                3,
                "Price:",
                priceField
        );

        addFormRow(
                panel,
                gbc,
                4,
                "Property Type:",
                typeBox
        );

        addFormRow(
                panel,
                gbc,
                5,
                "Purpose:",
                purposeBox
        );

        addFormRow(
                panel,
                gbc,
                6,
                "Dealer:",
                dealerBox
        );

        addFormRow(
                panel,
                gbc,
                7,
                "Owner:",
                ownerBox
        );

        addFormRow(
                panel,
                gbc,
                8,
                "Description:",
                descriptionField
        );

        // =================================================
        // SAVE BUTTON
        // =================================================

        JButton saveButton =
                new JButton(
                        "Save Property"
                );

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

        panel.add(
                saveButton,
                gbc
        );

        // =================================================
        // SAVE ACTION
        // =================================================

        saveButton.addActionListener(
                e -> {

                    try {

                        int id =
                                Integer.parseInt(
                                        idField
                                                .getText()
                                                .trim()
                                );

                        String propertyNumber =
                                numberField
                                        .getText()
                                        .trim();

                        String location =
                                locationField
                                        .getText()
                                        .trim();

                        long price =
                                Long.parseLong(
                                        priceField
                                                .getText()
                                                .trim()
                                );

                        if (propertyNumber.isEmpty()
                                || location.isEmpty()) {

                            JOptionPane.showMessageDialog(
                                    dialog,
                                    "Please fill all required fields.",
                                    "Invalid Input",
                                    JOptionPane.WARNING_MESSAGE
                            );

                            return;
                        }

                        Dealer selectedDealer =
                                (Dealer)
                                        dealerBox
                                                .getSelectedItem();

                        Owner selectedOwner =
                                (Owner)
                                        ownerBox
                                                .getSelectedItem();

                        if (selectedDealer == null
                                || selectedOwner == null) {

                            JOptionPane.showMessageDialog(
                                    dialog,
                                    "Please select a Dealer and Owner.",
                                    "Invalid Selection",
                                    JOptionPane.ERROR_MESSAGE
                            );

                            return;
                        }

                        int dealerId =
                                selectedDealer
                                        .getDealerId();

                        int ownerId =
                                selectedOwner
                                        .getOwnerId();

                        // Extra security
                        if (isDealerLoggedIn()
                                && dealerId
                                != loggedInDealerId) {

                            JOptionPane.showMessageDialog(
                                    dialog,
                                    "You can only add property under your own dealer account.",
                                    "Access Denied",
                                    JOptionPane.ERROR_MESSAGE
                            );

                            return;
                        }

                        String description =
                                descriptionField
                                        .getText()
                                        .trim();

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

                        // Add to memory
                        system.addProperty(
                                property
                        );

                        // Check whether added
                        Property addedProperty =
                                system.searchProperty(
                                        propertyNumber
                                );

                        if (addedProperty != null) {

                            // Save to database
                            system.addPropertyToDB(
                                    property
                            );

                            refreshPropertyTable();

                            refreshDashboard();

                            JOptionPane.showMessageDialog(
                                    dialog,
                                    "Property added successfully!",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE
                            );

                            dialog.dispose();

                        } else {

                            JOptionPane.showMessageDialog(
                                    dialog,
                                    "Owner ID or Dealer ID not found!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }

                    } catch (
                            NumberFormatException ex
                    ) {

                        JOptionPane.showMessageDialog(
                                dialog,
                                "Please enter valid numbers.",
                                "Invalid Input",
                                JOptionPane.ERROR_MESSAGE
                        );

                    } catch (
                            Exception ex
                    ) {

                        JOptionPane.showMessageDialog(
                                dialog,
                                "Error: "
                                        + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );

                        ex.printStackTrace();
                    }
                }
        );

        dialog.add(panel);

        dialog.setVisible(true);
    }






    // =====================================================
    // REFRESH DASHBOARD
    // =====================================================

    private void refreshDashboard() {

        int total = 0;

        int available = 0;

        int sold = 0;

        int rented = 0;

        for (Property p :
                getVisibleProperties()) {

            total++;

            if (p.getStatus()
                    == PropertyStatus.AVAILABLE) {

                available++;

            } else if (
                    p.getStatus()
                            == PropertyStatus.SOLD
            ) {

                sold++;

            } else if (
                    p.getStatus()
                            == PropertyStatus.RENTED
            ) {

                rented++;
            }
        }

        if (totalPropertiesLabel != null) {

            totalPropertiesLabel.setText(
                    String.valueOf(total)
            );
        }

        if (availableLabel != null) {

            availableLabel.setText(
                    String.valueOf(available)
            );
        }

        if (soldLabel != null) {

            soldLabel.setText(
                    String.valueOf(sold)
            );
        }

        if (rentedLabel != null) {

            rentedLabel.setText(
                    String.valueOf(rented)
            );
        }
    }


// =====================================================
// ADD VISIT FORM
// =====================================================

    private void showAddVisitForm(DefaultTableModel visitTableModel) {

        JDialog dialog = new JDialog(
                this,
                "Add Property Visit",
                true
        );

        dialog.setSize(550, 550);
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

        // =================================================
        // PROPERTY DROPDOWN
        // =================================================

        JComboBox<String> propertyBox =
                new JComboBox<>();

        for (Property property : getVisibleProperties()) {

            propertyBox.addItem(
                    property.getPropertyId()
                            + " | "
                            + property.getPropertyNumber()
                            + " | "
                            + property.getLocation()
            );
        }

        // =================================================
        // AUTO PROPERTY DETAILS
        // =================================================

        JTextField propertyIdField =
                new JTextField();

        JTextField propertyNumberField =
                new JTextField();

        JTextField locationField =
                new JTextField();

        propertyIdField.setEditable(false);
        propertyNumberField.setEditable(false);
        locationField.setEditable(false);

        // =================================================
        // CLIENT DETAILS
        // =================================================

        JTextField clientNameField =
                new JTextField();

        JTextField clientPhoneField =
                new JTextField();

        JTextField visitDateField =
                new JTextField();

        visitDateField.setToolTipText(
                "Format: YYYY-MM-DD"
        );

        // =================================================
        // STATUS
        // =================================================

        JComboBox<String> statusBox =
                new JComboBox<>(
                        new String[]{
                                "SCHEDULED",
                                "COMPLETED",
                                "CANCELLED"
                        }
                );

        // =================================================
        // ADD ROWS
        // =================================================

        addFormRow(
                panel,
                gbc,
                0,
                "Property:",
                propertyBox
        );

        addFormRow(
                panel,
                gbc,
                1,
                "Property ID:",
                propertyIdField
        );

        addFormRow(
                panel,
                gbc,
                2,
                "Property No:",
                propertyNumberField
        );

        addFormRow(
                panel,
                gbc,
                3,
                "Location:",
                locationField
        );

        addFormRow(
                panel,
                gbc,
                4,
                "Client Name:",
                clientNameField
        );

        addFormRow(
                panel,
                gbc,
                5,
                "Client Phone:",
                clientPhoneField
        );

        addFormRow(
                panel,
                gbc,
                6,
                "Visit Date:",
                visitDateField
        );

        addFormRow(
                panel,
                gbc,
                7,
                "Status:",
                statusBox
        );

        // =================================================
        // PROPERTY SELECTION
        // =================================================

        propertyBox.addActionListener(e -> {

            int selectedIndex =
                    propertyBox.getSelectedIndex();

            if (selectedIndex == -1) {
                return;
            }

            Property selectedProperty =
                    getVisibleProperties()
                            .get(selectedIndex);

            propertyIdField.setText(
                    String.valueOf(
                            selectedProperty.getPropertyId()
                    )
            );

            propertyNumberField.setText(
                    selectedProperty.getPropertyNumber()
            );

            locationField.setText(
                    selectedProperty.getLocation()
            );
        });

        // =================================================
        // LOAD FIRST PROPERTY
        // =================================================

        if (propertyBox.getItemCount() > 0) {

            propertyBox.setSelectedIndex(0);

            Property firstProperty =
                    getVisibleProperties().get(0);

            propertyIdField.setText(
                    String.valueOf(
                            firstProperty.getPropertyId()
                    )
            );

            propertyNumberField.setText(
                    firstProperty.getPropertyNumber()
            );

            locationField.setText(
                    firstProperty.getLocation()
            );
        }

        // =================================================
        // SAVE BUTTON
        // =================================================

        JButton saveButton =
                new JButton("Save Visit");

        saveButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.weightx = 1;

        panel.add(
                saveButton,
                gbc
        );

        // =================================================
        // SAVE VISIT
        // =================================================

        saveButton.addActionListener(e -> {

            try {

                // -----------------------------------------
                // PROPERTY
                // -----------------------------------------

                int selectedIndex =
                        propertyBox.getSelectedIndex();

                if (selectedIndex == -1) {

                    JOptionPane.showMessageDialog(
                            dialog,
                            "Please select a property.",
                            "Invalid Selection",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

                Property selectedProperty =
                        getVisibleProperties()
                                .get(selectedIndex);

                // -----------------------------------------
                // CLIENT
                // -----------------------------------------

                String clientName =
                        clientNameField
                                .getText()
                                .trim();

                String clientPhone =
                        clientPhoneField
                                .getText()
                                .trim();

                String visitDate =
                        visitDateField
                                .getText()
                                .trim();

                if (clientName.isEmpty()
                        || clientPhone.isEmpty()
                        || visitDate.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            dialog,
                            "Please fill all required fields.",
                            "Invalid Input",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

                // -----------------------------------------
                // DATE FORMAT CHECK
                // -----------------------------------------

                if (!visitDate.matches(
                        "\\d{4}-\\d{2}-\\d{2}"
                )) {

                    JOptionPane.showMessageDialog(
                            dialog,
                            "Visit date must be in YYYY-MM-DD format.",
                            "Invalid Date",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

                // -----------------------------------------
                // STATUS
                // -----------------------------------------

                VisitStatus status =
                        VisitStatus.valueOf(
                                statusBox
                                        .getSelectedItem()
                                        .toString()
                        );

                // -----------------------------------------
                // CREATE VISIT
                // -----------------------------------------

                Visit visit =
                        new Visit(
                                0,
                                selectedProperty.getPropertyId(),
                                selectedProperty.getPropertyNumber(),
                                selectedProperty.getLocation(),
                                clientName,
                                clientPhone,
                                visitDate,
                                status
                        );

                // -----------------------------------------
                // SAVE TO MEMORY
                // -----------------------------------------

                system.addVisit(visit);

                // -----------------------------------------
                // SAVE TO DATABASE
                // -----------------------------------------

                system.addVisitToDB(visit);

                // -----------------------------------------
                // RELOAD FROM DATABASE
                // This gets the AUTO_INCREMENT visit_id
                // -----------------------------------------

                system.loadVisitsFromDB();

                // -----------------------------------------
                // REFRESH TABLE
                // -----------------------------------------

                visitTableModel.setRowCount(0);

                for (Visit v :
                        system.getVisits()) {

                    Object[] row = {

                            v.getVisitId(),

                            v.getPropertyId(),

                            v.getPropertyNumber(),

                            v.getLocation(),

                            v.getClientName(),

                            v.getClientPhone(),

                            v.getVisitDate(),

                            v.getStatus()
                    };

                    visitTableModel.addRow(row);
                }

                JOptionPane.showMessageDialog(
                        dialog,
                        "Visit added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dialog.dispose();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Error: "
                                + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                ex.printStackTrace();
            }
        });

        dialog.add(panel);

        dialog.setVisible(true);
    }


    //===========================================================
    // VIsit page
    //=============================================================

    private JPanel createVisitsPage() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(BACKGROUND_COLOR);

        // ================= HEADER =================

        JPanel header = new JPanel(new BorderLayout());

        header.setBackground(BACKGROUND_COLOR);

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 15, 25
                )
        );

        JLabel title = new JLabel("Property Visits");

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

        panel.add(
                header,
                BorderLayout.NORTH
        );


        // ================= TABLE =================

        String[] columns = {
                "Visit ID",
                "Property ID",
                "Property No",
                "Location",
                "Client Name",
                "Client Phone",
                "Visit Date",
                "Status"
        };

        DefaultTableModel visitTableModel =
                new DefaultTableModel(
                        columns,
                        0
                );

        JTable visitTable =
                new JTable(visitTableModel);

        visitTable.setRowHeight(30);

        visitTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                14
                        )
                );

        visitTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(visitTable);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder(
                        0, 25, 15, 25
                )
        );

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        // ================= BUTTONS =================

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

        JButton addVisitButton =
                new JButton("Add Visit");

        JButton updateButton =
                new JButton("Update Status");

        JButton deleteButton =
                new JButton("Delete");

        bottomPanel.add(addVisitButton);
        bottomPanel.add(updateButton);
        bottomPanel.add(deleteButton);

        panel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // ================= LOAD VISITS =================

        for (Visit visit : system.getVisits()) {

            Object[] row = {

                    visit.getVisitId(),

                    visit.getPropertyId(),

                    visit.getPropertyNumber(),

                    visit.getLocation(),

                    visit.getClientName(),

                    visit.getClientPhone(),

                    visit.getVisitDate(),

                    visit.getStatus()
            };

            visitTableModel.addRow(row);
        }


        // ================= ADD VISIT =================
        addVisitButton.addActionListener(e -> {
            showAddVisitForm(visitTableModel);
        });
        // ================= UPDATE STATUS =================

        updateButton.addActionListener(e -> {

            int selectedRow =
                    visitTable.getSelectedRow();

            if (selectedRow == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a visit."
                );

                return;
            }

            int visitId =
                    (int) visitTableModel
                            .getValueAt(
                                    selectedRow,
                                    0
                            );

            String[] statuses = {
                    "SCHEDULED",
                    "COMPLETED",
                    "CANCELLED"
            };

            JComboBox<String> statusBox =
                    new JComboBox<>(statuses);

            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            statusBox,
                            "Update Visit Status",
                            JOptionPane.OK_CANCEL_OPTION
                    );

            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            VisitStatus status =
                    VisitStatus.valueOf(
                            statusBox
                                    .getSelectedItem()
                                    .toString()
                    );

            system.updateVisitStatusInDB(
                    visitId,
                    status
            );

            visitTableModel.setValueAt(
                    status,
                    selectedRow,
                    7
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Visit status updated successfully!"
            );
        });


        // ================= DELETE =================

        deleteButton.addActionListener(e -> {

            int selectedRow =
                    visitTable.getSelectedRow();

            if (selectedRow == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a visit."
                );

                return;
            }

            int visitId =
                    (int) visitTableModel
                            .getValueAt(
                                    selectedRow,
                                    0
                            );

            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Delete this visit?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                    );

            if (choice != JOptionPane.YES_OPTION) {
                return;
            }

            system.deleteVisitFromDB(
                    visitId
            );

            visitTableModel.removeRow(
                    selectedRow
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Visit deleted successfully!"
            );
        });


        return panel;
    }





    // =====================================================
// OWNERS PAGE
// =====================================================

    private JPanel createOwnersPage() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);

        // ---------------- HEADER ----------------

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BACKGROUND_COLOR);

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 15, 25
                )
        );

        JLabel title = new JLabel("Owners");

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

        panel.add(
                header,
                BorderLayout.NORTH
        );

        // ---------------- TABLE ----------------

        String[] columns = {
                "Owner ID",
                "Name",
                "Phone",
                "Email",
                "Properties Owned"
        };

        DefaultTableModel ownerTableModel =
                new DefaultTableModel(columns, 0);

        JTable ownerTable =
                new JTable(ownerTableModel);

        ownerTable.setRowHeight(30);

        ownerTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        ownerTable.getTableHeader().setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(ownerTable);

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

        JButton addOwnerButton =
                new JButton("Add Owner");

        JButton deleteOwnerButton =
                new JButton("Delete Owner");

        JButton refreshButton =
                new JButton("Refresh");

        bottomPanel.add(addOwnerButton);
        bottomPanel.add(deleteOwnerButton);
        bottomPanel.add(refreshButton);

        panel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // =====================================================
        // LOAD OWNERS INTO TABLE
        // =====================================================

        Runnable refreshOwners = () -> {

            ownerTableModel.setRowCount(0);

            for (Owner owner : system.getOwners()) {

                StringBuilder propertiesOwned = new StringBuilder();

                for (Property property : system.getProperties()) {

                    if (property.getOwnerId() == owner.getOwnerId()) {

                        if (propertiesOwned.length() > 0) {
                            propertiesOwned.append(", ");
                        }

                        propertiesOwned.append(
                                property.getPropertyNumber()
                                        + " - "
                                        + property.getLocation()
                        );
                    }
                }

                if (propertiesOwned.length() == 0) {
                    propertiesOwned.append("No Property");
                }

                Object[] row = {
                        owner.getOwnerId(),
                        owner.getName(),
                        owner.getPhone(),
                        owner.getEmail(),
                        propertiesOwned.toString()
                };

                ownerTableModel.addRow(row);
            }
        };

        // Initial load
        refreshOwners.run();

        // =====================================================
        // ADD OWNER
        // =====================================================

        addOwnerButton.addActionListener(e -> {

            JDialog dialog =
                    new JDialog(
                            this,
                            "Add Owner",
                            true
                    );

            dialog.setSize(450, 350);
            dialog.setLocationRelativeTo(this);

            JPanel form =
                    new JPanel(
                            new GridBagLayout()
                    );

            form.setBorder(
                    BorderFactory.createEmptyBorder(
                            20, 30, 20, 30
                    )
            );

            GridBagConstraints gbc =
                    new GridBagConstraints();

            gbc.insets =
                    new Insets(10, 10, 10, 10);

            gbc.fill =
                    GridBagConstraints.HORIZONTAL;

            JTextField nameField =
                    new JTextField();

            JTextField phoneField =
                    new JTextField();

            JTextField emailField =
                    new JTextField();

            addFormRow(
                    form,
                    gbc,
                    0,
                    "Name:",
                    nameField
            );

            addFormRow(
                    form,
                    gbc,
                    1,
                    "Phone:",
                    phoneField
            );

            addFormRow(
                    form,
                    gbc,
                    2,
                    "Email:",
                    emailField
            );

            JButton saveButton =
                    new JButton("Save Owner");

            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;

            form.add(
                    saveButton,
                    gbc
            );

            // =================================================
            // SAVE OWNER
            // =================================================

            saveButton.addActionListener(event -> {

                String name =
                        nameField.getText().trim();

                String phone =
                        phoneField.getText().trim();

                String email =
                        emailField.getText().trim();

                if (name.isEmpty()
                        || phone.isEmpty()
                        || email.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            dialog,
                            "Please fill all fields.",
                            "Invalid Input",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

                try {

                    /*
                     * ID = 0 because database
                     * will generate the owner_id.
                     */

                    Owner owner =
                            new Owner(
                                    0,
                                    name,
                                    phone,
                                    email
                            );

                    // Save to database
                    system.addOwnerToDB(owner);

                    // Reload owners from database
                    system.loadOwnersFromDB();

                    // Refresh table
                    refreshOwners.run();

                    JOptionPane.showMessageDialog(
                            dialog,
                            "Owner added successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    dialog.dispose();

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(
                            dialog,
                            "Error: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                    ex.printStackTrace();
                }
            });

            dialog.add(form);
            dialog.setVisible(true);
        });


        // =====================================================
// DELETE OWNER
// =====================================================

        deleteOwnerButton.addActionListener(e -> {

            int selectedRow =
                    ownerTable.getSelectedRow();

            if (selectedRow == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select an owner to delete.",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            int ownerId =
                    (int) ownerTableModel.getValueAt(
                            selectedRow,
                            0
                    );

            String ownerName =
                    ownerTableModel.getValueAt(
                            selectedRow,
                            1
                    ).toString();

            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to delete owner "
                                    + ownerName + "?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

            if (choice != JOptionPane.YES_OPTION) {
                return;
            }

            try {

                Connection con =
                        DatabaseConnection.getConnection();

                String query =
                        "DELETE FROM Owner WHERE owner_id = ?";

                PreparedStatement ps =
                        con.prepareStatement(query);

                ps.setInt(1, ownerId);

                int rows =
                        ps.executeUpdate();

                if (rows > 0) {

                    // Reload owners from database
                    system.loadOwnersFromDB();

                    // Refresh table
                    refreshOwners.run();

                    JOptionPane.showMessageDialog(
                            this,
                            "Owner deleted successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Owner not found.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Cannot delete owner.\n"
                                + ex.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );

                ex.printStackTrace();
            }
        });

        // =====================================================
        // REFRESH BUTTON
        // =====================================================

        refreshButton.addActionListener(e -> {

            system.loadOwnersFromDB();

            refreshOwners.run();

            JOptionPane.showMessageDialog(
                    this,
                    "Owners refreshed successfully!",
                    "Refresh",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        return panel;
    }





    // =====================================================
    // MAIN
    // =====================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    MainGUI gui =
                            new MainGUI();

                    gui.setVisible(true);
                }
        );
    }
}
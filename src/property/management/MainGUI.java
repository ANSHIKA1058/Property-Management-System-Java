package property.management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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
        refreshDashboard();
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

        // ==============================
        // REAL PROPERTY COUNTS
        // ==============================

        int totalProperties =
                system.getProperties().size();

        int availableProperties = 0;
        int soldProperties = 0;
        int rentedProperties = 0;

        for (Property property : system.getProperties()) {

            if (property.getStatus() == PropertyStatus.AVAILABLE) {

                availableProperties++;

            } else if (property.getStatus() == PropertyStatus.SOLD) {

                soldProperties++;

            } else if (property.getStatus() == PropertyStatus.RENTED) {

                rentedProperties++;
            }
        }

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

        JPanel totalCard = createCard("Total Properties", "0");
        JPanel availableCard = createCard("Available", "0");
        JPanel soldCard = createCard("Sold", "0");
        JPanel rentedCard = createCard("Rented", "0");

        totalPropertiesLabel = (JLabel) totalCard.getComponent(2);
        availableLabel = (JLabel) availableCard.getComponent(2);
        soldLabel = (JLabel) soldCard.getComponent(2);
        rentedLabel = (JLabel) rentedCard.getComponent(2);

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

        propertyTableModel =
                new DefaultTableModel(
                        columns,
                        0
                );

        propertyTable =
                new JTable(propertyTableModel);

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
                new JScrollPane(propertyTable);


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

            int selectedRow = propertyTable.getSelectedRow();

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
                            .getValueAt(selectedRow, 1)
                            .toString();

            Property property =
                    system.searchProperty(propertyNumber);

            if (property == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Property not found.",
                        "Error",
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
                    new JComboBox<>(statuses);

            statusBox.setSelectedItem(
                    property.getStatus().toString()
            );

            int result = JOptionPane.showConfirmDialog(
                    this,
                    statusBox,
                    "Update Property Status",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            PropertyStatus newStatus =
                    PropertyStatus.valueOf(
                            statusBox.getSelectedItem().toString()
                    );

            // Update memory
            system.updateProperty(
                    propertyNumber,
                    newStatus
            );

            // Update database
            system.updatePropertyStatusInDB(
                    property.getPropertyId(),
                    newStatus
            );

            // Refresh table
            refreshPropertyTable();

            // Refresh dashboard
            refreshDashboard();

            JOptionPane.showMessageDialog(
                    this,
                    "Property status updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        });




        deleteButton.addActionListener(e -> {

            int selectedRow = propertyTable.getSelectedRow();

            // No property selected
            if (selectedRow == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a property to delete."
                );
                return;
            }

            // Get property ID from table
            int propertyId = (int) propertyTableModel.getValueAt(
                    selectedRow,
                    0
            );

            String propertyNumber =
                    propertyTableModel.getValueAt(
                            selectedRow,
                            1
                    ).toString();

            // Confirmation
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete property "
                            + propertyNumber + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (choice != JOptionPane.YES_OPTION) {
                return;
            }

            // Find property in memory
            Property property =
                    system.searchProperty(propertyNumber);

            if (property == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Property not found.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // Delete from database
            system.deletePropertyFromDB(propertyId);

            // Delete from memory
            system.getProperties().remove(property);

            // Refresh GUI
            refreshPropertyTable();
            refreshDashboard();

            JOptionPane.showMessageDialog(
                    this,
                    "Property deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });



        //search button
        searchButton.addActionListener(e -> {

            String searchText = searchField.getText().trim();

            // Empty search -> show all properties
            if (searchText.isEmpty()) {
                refreshPropertyTable();
                return;
            }

            propertyTableModel.setRowCount(0);

            boolean found = false;

            for (Property p : system.getProperties()) {

                String location = p.getLocation().toLowerCase();
                String propertyNumber = p.getPropertyNumber().toLowerCase();
                String price = String.valueOf(p.getPrice());

                if (location.contains(searchText.toLowerCase())
                        || propertyNumber.contains(searchText.toLowerCase())
                        || price.equals(searchText)) {

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
                    found = true;
                }
            }

            if (!found) {

                JOptionPane.showMessageDialog(
                        this,
                        "No property found for: " + searchText,
                        "Search Result",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });



        availableButton.addActionListener(e -> {

            propertyTableModel.setRowCount(0);

            for (Property p : system.getProperties()) {

                if (p.getStatus() == PropertyStatus.AVAILABLE) {

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
            }

            if (propertyTableModel.getRowCount() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "No available properties found.",
                        "Available Properties",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        refreshPropertyTable();
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







    private void refreshPropertyTable() {

        propertyTableModel.setRowCount(0);

        for (Property p : system.getProperties()) {

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

        JComboBox<Dealer> dealerBox =
                new JComboBox<>();

        for (Dealer dealer : system.getDealers()) {
            dealerBox.addItem(dealer);
        }

        JComboBox<Owner> ownerBox =
                new JComboBox<>();

        for (Owner owner : system.getOwners()) {
            ownerBox.addItem(owner);
        }


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
                "Dealer:", dealerBox);

        addFormRow(panel, gbc, 7,
                "Owner:", ownerBox);

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

                Dealer selectedDealer =
                        (Dealer) dealerBox.getSelectedItem();

                Owner selectedOwner =
                        (Owner) ownerBox.getSelectedItem();

                if (selectedDealer == null || selectedOwner == null) {

                    JOptionPane.showMessageDialog(
                            dialog,
                            "Please select a Dealer and Owner.",
                            "Invalid Selection",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                int dealerId = selectedDealer.getDealerId();
                int ownerId = selectedOwner.getOwnerId();

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

                if (system.searchProperty(propertyNumber) != null) {

                    system.addPropertyToDB(property);

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





    private void refreshDashboard() {

        int total = system.getProperties().size();

        int available = 0;
        int sold = 0;
        int rented = 0;

        for (Property p : system.getProperties()) {

            if (p.getStatus() == PropertyStatus.AVAILABLE) {
                available++;
            }
            else if (p.getStatus() == PropertyStatus.SOLD) {
                sold++;
            }
            else if (p.getStatus() == PropertyStatus.RENTED) {
                rented++;
            }
        }

        totalPropertiesLabel.setText(String.valueOf(total));
        availableLabel.setText(String.valueOf(available));
        soldLabel.setText(String.valueOf(sold));
        rentedLabel.setText(String.valueOf(rented));
    }


}
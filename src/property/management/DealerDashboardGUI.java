package property.management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DealerDashboardGUI extends JFrame {

    private int dealerId;
    private String dealerName;

    private PropertyManagementSystem system;
    private DefaultTableModel tableModel;
    private JTable propertyTable;

    public DealerDashboardGUI(int dealerId, String dealerName) {

        this.dealerId = dealerId;
        this.dealerName = dealerName;

        system = new PropertyManagementSystem();

        setTitle("Dealer Dashboard");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();
    }

    private void createGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 250, 252));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 41, 59));
        header.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        JLabel welcomeLabel = new JLabel(
                "Welcome, " + dealerName
        );

        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        JLabel idLabel = new JLabel(
                "Dealer ID: " + dealerId
        );

        idLabel.setForeground(Color.WHITE);
        idLabel.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        header.add(welcomeLabel, BorderLayout.WEST);
        header.add(idLabel, BorderLayout.EAST);

        mainPanel.add(header, BorderLayout.NORTH);

        // Title
        JLabel title = new JLabel("My Properties");

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 15, 25
                )
        );

        mainPanel.add(title, BorderLayout.CENTER);

        // Table
        String[] columns = {
                "ID",
                "Property No",
                "Location",
                "Price",
                "Type",
                "Purpose",
                "Status",
                "Owner ID"
        };

        tableModel = new DefaultTableModel(columns, 0);

        propertyTable = new JTable(tableModel);

        propertyTable.setRowHeight(30);

        propertyTable.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        propertyTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        JScrollPane scrollPane =
                new JScrollPane(propertyTable);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(
                new Color(248, 250, 252)
        );

        tablePanel.setBorder(
                BorderFactory.createEmptyBorder(
                        0, 25, 20, 25
                )
        );

        tablePanel.add(scrollPane);

        mainPanel.add(
                tablePanel,
                BorderLayout.SOUTH
        );

        loadDealerProperties();

        add(mainPanel);
    }

    private void loadDealerProperties() {

        tableModel.setRowCount(0);

        for (Property p : system.getProperties()) {

            if (p.getDealerId() == dealerId) {

                Object[] row = {
                        p.getPropertyId(),
                        p.getPropertyNumber(),
                        p.getLocation(),
                        p.getPrice(),
                        p.getType(),
                        p.getPurpose(),
                        p.getStatus(),
                        p.getOwnerId()
                };

                tableModel.addRow(row);
            }
        }
    }
}
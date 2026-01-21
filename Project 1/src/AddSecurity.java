package Project1.Icon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AddSecurity extends JFrame {

    Color primaryColor = new Color(3, 45, 48);
    Color secondaryColor = new Color(16, 108, 115);
    Color alertColor = new Color(255, 69, 0);
    DefaultTableModel logModel;
    JLabel failedAttemptLabel;

    public AddSecurity() {
        setTitle("System Security & Authentication Center");
        setSize(1100, 650); // Name column-er jonno width ektu barano hoyeche
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(primaryColor);
        header.setPreferredSize(new Dimension(1000, 70));
        header.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("🛡️ SECURITY & AUTHENTICATION HUB");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        header.add(title, BorderLayout.WEST);

        JLabel statusLabel = new JLabel("System Status: SECURED (SSL Enabled)");
        statusLabel.setForeground(new Color(0, 255, 127));
        header.add(statusLabel, BorderLayout.EAST);


        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        cardsPanel.setOpaque(false);
        cardsPanel.add(createSecurityCard("Total Logins", "System Active", "Authorized Access"));


        JPanel failedCard = new JPanel(new GridLayout(3, 1));
        failedCard.setBackground(secondaryColor);
        failedCard.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        failedCard.add(new JLabel("Failed Attempts", JLabel.CENTER)).setForeground(Color.LIGHT_GRAY);
        failedAttemptLabel = new JLabel("0", JLabel.CENTER);
        failedAttemptLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        failedAttemptLabel.setForeground(Color.WHITE);
        failedCard.add(failedAttemptLabel);
        failedCard.add(new JLabel("Immediate Attention Required", JLabel.CENTER)).setForeground(Color.YELLOW);
        cardsPanel.add(failedCard);

        cardsPanel.add(createSecurityCard("Database Encryption", "AES-256", "Active"));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder("System Authentication Logs"));


        String[] cols = {"Log ID", "User ID", "Student Name", "Action", "Timestamp", "IP Address", "Status"};
        logModel = new DefaultTableModel(cols, 0);
        JTable logTable = new JTable(logModel);
        logTable.setRowHeight(30);
        logTable.getTableHeader().setBackground(secondaryColor);
        logTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(logTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);


        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRefresh = new JButton("Refresh Logs");
        JButton btnBack = new JButton("Back to Dashboard");
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 12));

        actionPanel.add(btnRefresh);
        actionPanel.add(btnBack);

        mainPanel.add(cardsPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        add(header, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);


        btnBack.addActionListener(e -> {
            this.dispose();
            new admin().setVisible(true);
        });

        btnRefresh.addActionListener(e -> {
            fetchSecurityLogs();
            updateFailedCount();
        });

        fetchSecurityLogs();
        updateFailedCount();
        setVisible(true);
    }

    private JPanel createSecurityCard(String title, String value, String subText) {
        JPanel card = new JPanel(new GridLayout(3, 1));
        card.setBackground(secondaryColor);
        card.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        JLabel t = new JLabel(title, JLabel.CENTER); t.setForeground(Color.LIGHT_GRAY);
        JLabel v = new JLabel(value, JLabel.CENTER); v.setFont(new Font("Tahoma", Font.BOLD, 18)); v.setForeground(Color.WHITE);
        JLabel s = new JLabel(subText, JLabel.CENTER); s.setForeground(Color.YELLOW); s.setFont(new Font("Tahoma", Font.ITALIC, 11));
        card.add(t); card.add(v); card.add(s);
        return card;
    }

    private void fetchSecurityLogs() {
        logModel.setRowCount(0);
        try {
            con c = new con();

            String query = "SELECT s.*, u.name FROM security_logs s " +
                    "LEFT JOIN users u ON s.user_id = u.id " +
                    "ORDER BY s.log_id DESC";

            ResultSet rs = c.statement.executeQuery(query);

            while (rs.next()) {
                logModel.addRow(new Object[]{
                        rs.getString("log_id"),
                        rs.getString("user_id"),
                        rs.getString("name"),
                        rs.getString("action_performed"),
                        rs.getString("log_time"),
                        rs.getString("ip_address"),
                        rs.getString("status")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateFailedCount() {
        try {
            con c = new con();
            String query = "SELECT COUNT(*) AS total FROM security_logs WHERE status = 'Failed'";
            ResultSet rs = c.statement.executeQuery(query);
            if(rs.next()) {
                failedAttemptLabel.setText(String.valueOf(rs.getInt("total")));
            }
        } catch (Exception e) {
            failedAttemptLabel.setText("!");
        }
    }

    public static void main(String[] args) {
        new AddSecurity();
    }
}
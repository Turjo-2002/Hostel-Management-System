package Project1.Icon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AddComplaint extends JFrame {
    private JTable adminTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> statusUpdateBox;

    private final String DB_URL = "jdbc:mysql://localhost:3306/hostelms";
    private final String DB_USER = "root";
    private final String DB_PASS = "";

    public AddComplaint() {
        setTitle("Admin Dashboard - Manage Complaints");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));


        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(45, 0, 0));
        JLabel headerLabel = new JLabel("ADMIN CONTROL PANEL");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);


        tableModel = new DefaultTableModel(new String[]{"ID", "Student Name", "Category", "Description", "Status"}, 0);
        adminTable = new JTable(tableModel);
        add(new JScrollPane(adminTable), BorderLayout.CENTER);


        JPanel controlPanel = new JPanel();
        statusUpdateBox = new JComboBox<>(new String[]{"Submitted", "In Progress", "Resolved"});
        JButton updateBtn = new JButton("Update Status");
        JButton deleteBtn = new JButton("Delete");
        JButton refreshBtn = new JButton("Refresh");

        controlPanel.add(new JLabel("Update Selected:"));
        controlPanel.add(statusUpdateBox);
        controlPanel.add(updateBtn);
        controlPanel.add(deleteBtn);
        controlPanel.add(refreshBtn);
        add(controlPanel, BorderLayout.SOUTH);

        refreshTableFromDB();

        updateBtn.addActionListener(e -> updateStatus());
        deleteBtn.addActionListener(e -> deleteComplaint());
        refreshBtn.addActionListener(e -> refreshTableFromDB());
    }


    public void submitToDatabase(String id, String name, String category, String description) {
        String query = "INSERT INTO complaints (student_id, student_name, category, description, status) VALUES (?, ?, ?, ?, 'Submitted')";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, category);
            pstmt.setString(4, description);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Complaint recorded in Database!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
        }
    }


    public void refreshTableFromDB() {
        tableModel.setRowCount(0);

        String query = "SELECT student_id, student_name, category, description, status FROM complaints";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("student_id"),
                        rs.getString("student_name"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getString("status")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateStatus() {
        int row = adminTable.getSelectedRow();
        if (row != -1) {
            String id = adminTable.getValueAt(row, 0).toString();
            String status = (String) statusUpdateBox.getSelectedItem();
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement ps = con.prepareStatement("UPDATE complaints SET status=? WHERE student_id=?")) {
                ps.setString(1, status);
                ps.setString(2, id);
                ps.executeUpdate();
                refreshTableFromDB();
                JOptionPane.showMessageDialog(this, "Status Updated!");
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    private void deleteComplaint() {
        int row = adminTable.getSelectedRow();
        if (row != -1) {
            String id = adminTable.getValueAt(row, 0).toString();
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement ps = con.prepareStatement("DELETE FROM complaints WHERE student_id=?")) {
                ps.setString(1, id);
                ps.executeUpdate();
                refreshTableFromDB();
                JOptionPane.showMessageDialog(this, "Complaint Deleted!");
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddComplaint().setVisible(true));
    }
}
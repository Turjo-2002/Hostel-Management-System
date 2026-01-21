package Project1.Icon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AddFee extends JFrame {
    Color bgColor = new Color(0, 43, 43);
    DefaultTableModel model;
    JTextField txtSearch, txtId, txtPaid;

    public AddFee() {
        setTitle("Admin Portal - Fee Management (Smart Entry)");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(bgColor);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));


        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(bgColor);
        JLabel lblSearch = new JLabel("Search ID: ");
        lblSearch.setForeground(Color.WHITE);
        txtSearch = new JTextField(10);
        JButton btnSearch = new JButton("Search");
        JButton btnViewAll = new JButton("View All Records");
        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnViewAll);


        JPanel updatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        updatePanel.setBackground(new Color(0, 60, 60));
        updatePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Quick Entry (Add/Update)", 0, 0, null, Color.WHITE));

        JLabel lblId = new JLabel("Student ID:");
        lblId.setForeground(Color.WHITE);
        txtId = new JTextField(8);

        JLabel lblAmt = new JLabel("Amount (TK):");
        lblAmt.setForeground(Color.WHITE);
        txtPaid = new JTextField(10);

        JButton btnUpdate = new JButton("Confirm Entry");
        btnUpdate.setBackground(new Color(46, 139, 87));
        btnUpdate.setForeground(Color.WHITE);

        updatePanel.add(lblId);
        updatePanel.add(txtId);
        updatePanel.add(lblAmt);
        updatePanel.add(txtPaid);
        updatePanel.add(btnUpdate);

        JPanel topContainer = new JPanel(new GridLayout(2, 1, 5, 5));
        topContainer.setBackground(bgColor);
        topContainer.add(searchPanel);
        topContainer.add(updatePanel);


        String[] columns = {"Student ID", "Name", "Total Fee", "Paid Amount", "Due Amount", "Status"};
        model = new DefaultTableModel(null, columns);
        JTable feeTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(feeTable);


        btnViewAll.addActionListener(e -> loadFeeData("SELECT student_id, name, total_fee, paid_amount, (total_fee - paid_amount) as due_amount, status FROM fee_records"));

        btnSearch.addActionListener(e -> {
            String id = txtSearch.getText().trim();
            loadFeeData("SELECT student_id, name, total_fee, paid_amount, (total_fee - paid_amount) as due_amount, status FROM fee_records WHERE student_id = '" + id + "'");
        });

        btnUpdate.addActionListener(e -> {
            String id = txtId.getText().trim();
            String amount = txtPaid.getText().trim();
            if (id.isEmpty() || amount.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill ID and Amount");
                return;
            }
            updateFeeData(id, amount);
        });

        mainPanel.add(topContainer, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);
        loadFeeData("SELECT student_id, name, total_fee, paid_amount, (total_fee - paid_amount) as due_amount, status FROM fee_records");
        setVisible(true);
    }

    private void loadFeeData(String query) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostelms", "root", "")) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("student_id"), rs.getString("name"), rs.getString("total_fee"), rs.getString("paid_amount"), rs.getString("due_amount"), rs.getString("status")});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Load Error: " + ex.getMessage());
        }
    }

    private void updateFeeData(String id, String amount) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostelms", "root", "");
            double addedAmt = Double.parseDouble(amount);


            String sql = "INSERT INTO fee_records (student_id, name, total_fee, paid_amount, status) " +
                    "VALUES (?, 'New Student', 15000, ?, IF(? >= 15000, 'Paid', 'Partial')) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "paid_amount = paid_amount + ?, " +
                    "status = IF(paid_amount >= total_fee, 'Paid', 'Partial')";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            pst.setDouble(2, addedAmt);
            pst.setDouble(3, addedAmt);
            pst.setDouble(4, addedAmt);

            int row = pst.executeUpdate();
            if (row > 0) {
                JOptionPane.showMessageDialog(this, "Success! Record Updated for ID: " + id);
                txtId.setText("");
                txtPaid.setText("");
                loadFeeData("SELECT student_id, name, total_fee, paid_amount, (total_fee - paid_amount) as due_amount, status FROM fee_records");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric amount.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) { new AddFee(); }
}
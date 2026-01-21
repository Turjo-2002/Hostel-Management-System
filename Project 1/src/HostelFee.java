package Project1.Icon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.*;

public class HostelFee extends JFrame {
    Color bgColor = new Color(0, 43, 43);
    JLabel lblTotal, lblPaid, lblDue, lblStatus;
    JTextField txtPayAmount;
    JComboBox<String> comboMethod;


    String currentStudentID = UserSession.currentUserID;
    JFrame parentDashboard;

    public HostelFee(JFrame parent) {
        this.parentDashboard = parent;


        if (currentStudentID == null) {
            JOptionPane.showMessageDialog(null, "Access Denied! Please Login First.");


            if (currentStudentID == null) return;
        }

        setTitle("Student Fee Portal - ID: " + currentStudentID);
        setSize(750, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(bgColor);
        setLayout(null);


        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        header.setBackground(new Color(0, 60, 60));
        header.setBounds(0, 0, 750, 70);
        JLabel title = new JLabel("MY HOSTEL FEE & PAYMENT SYSTEM");
        title.setFont(new Font("Tahoma", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        header.add(title);
        add(header);


        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        infoPanel.setBounds(50, 100, 630, 220);
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(bgColor, 2), "Fee Details for " + currentStudentID,
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 14)));

        lblTotal = createStyledLabel("Total Hostel Fee: Loading...");
        lblPaid = createStyledLabel("Amount Paid: Loading...");
        lblDue = createStyledLabel("Amount Due: Loading...");
        lblStatus = createStyledLabel("Status: Loading...");

        infoPanel.add(lblTotal);
        infoPanel.add(lblPaid);
        infoPanel.add(lblDue);
        infoPanel.add(lblStatus);
        add(infoPanel);


        JPanel payPanel = new JPanel(null);
        payPanel.setBounds(50, 340, 630, 180);
        payPanel.setBackground(new Color(0, 70, 70));
        payPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE), "Make a Payment",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 12), Color.WHITE));

        JLabel lblAmt = new JLabel("Enter Amount (TK):");
        lblAmt.setForeground(Color.WHITE);
        lblAmt.setBounds(30, 40, 150, 30);
        payPanel.add(lblAmt);

        txtPayAmount = new JTextField();
        txtPayAmount.setBounds(190, 40, 180, 30);
        payPanel.add(txtPayAmount);

        JLabel lblM = new JLabel("Select Method:");
        lblM.setForeground(Color.WHITE);
        lblM.setBounds(30, 90, 150, 30);
        payPanel.add(lblM);

        String[] methods = {"bKash", "Nagad", "Rocket", "Bank Transfer"};
        comboMethod = new JComboBox<>(methods);
        comboMethod.setBounds(190, 90, 180, 30);
        payPanel.add(comboMethod);

        JButton btnPay = new JButton("Confirm Payment");
        btnPay.setBounds(400, 60, 180, 40);
        btnPay.setBackground(new Color(46, 139, 87));
        btnPay.setForeground(Color.WHITE);
        btnPay.setFont(new Font("Tahoma", Font.BOLD, 13));
        payPanel.add(btnPay);
        add(payPanel);


        JButton btnBack = new JButton("Back to Dashboard");
        btnBack.setBounds(275, 540, 200, 40);
        btnBack.setBackground(new Color(0, 80, 80));
        btnBack.setForeground(Color.WHITE);
        add(btnBack);


        btnBack.addActionListener(e -> dispose());
        btnPay.addActionListener(e -> processUserPayment());

        fetchStudentFeeDetails();
        setVisible(true);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setBorder(new EmptyBorder(0, 25, 0, 0));
        return label;
    }

    private void fetchStudentFeeDetails() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostelms", "root", "")) {
            String query = "SELECT total_fee, paid_amount, status FROM fee_records WHERE student_id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, currentStudentID);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                double total = rs.getDouble("total_fee");
                double paid = rs.getDouble("paid_amount");
                double due = total - paid;

                lblTotal.setText("Total Hostel Fee: " + total + " TK");
                lblPaid.setText("Amount Paid: " + paid + " TK");
                lblDue.setText("Amount Due: " + due + " TK");
                lblStatus.setText("Current Status: " + rs.getString("status"));

                lblDue.setForeground(due > 0 ? Color.RED : new Color(0, 150, 0));
            } else {
                lblStatus.setText("Status: No Record Found (Contact Admin)");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void processUserPayment() {
        String amountStr = txtPayAmount.getText().trim();
        if (amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter payment amount!");
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostelms", "root", "")) {
            double payment = Double.parseDouble(amountStr);


            String sql = "UPDATE fee_records SET paid_amount = paid_amount + ?, " +
                    "status = IF(paid_amount + ? >= total_fee, 'Paid', 'Partial') " +
                    "WHERE student_id = ?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDouble(1, payment);
            pst.setDouble(2, payment);
            pst.setString(3, currentStudentID);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Payment of " + payment + " TK Successful via " + comboMethod.getSelectedItem());
                txtPayAmount.setText("");
                fetchStudentFeeDetails();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric amount.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }


    public static void main(String[] args) {


        new HostelFee(null);
    }
}
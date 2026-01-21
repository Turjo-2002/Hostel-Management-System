package Project1.Icon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AddReports extends JFrame {
    Color mainColor = new Color(3, 45, 48);
    Color cardColor = new Color(16, 108, 115);
    DefaultTableModel model;

    public AddReports() {
        setTitle("Admin - All Update Reports");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));


        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(mainColor);
        headerPanel.setPreferredSize(new Dimension(1000, 80));
        JLabel titleLabel = new JLabel("HOSTEL MANAGEMENT SYSTEM - ALL REPORTS");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        headerPanel.add(titleLabel);



        JPanel centerPanel = new JPanel(new BorderLayout(15, 15));
        centerPanel.setBackground(new Color(240, 240, 240));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));



        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnAttendance = new JButton("Attendance Report");
        JButton btnFees = new JButton("Fee Status Report");
        JButton btnComplaints = new JButton("Complaints Report");
        JButton btnFeedback = new JButton("Feedback Report");
        JButton btnRefresh = new JButton("Refresh All");

        filterPanel.add(btnAttendance);
        filterPanel.add(btnFees);
        filterPanel.add(btnComplaints);
        filterPanel.add(btnFeedback);
        filterPanel.add(btnRefresh);



        String[] columns = {"Report Type", "Student ID", "Name / Category", "Status / Details", "Date/Time"};
        model = new DefaultTableModel(null, columns);
        JTable reportTable = new JTable(model);
        reportTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(reportTable);

        centerPanel.add(filterPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);



        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnBack = new JButton("Back to Admin Panel");
        btnBack.addActionListener(e -> this.dispose());
        bottomPanel.add(btnBack);



        btnAttendance.addActionListener(e -> loadData("SELECT 'Attendance', student_id, room_no, status, attendance_date FROM attendance"));
        btnFees.addActionListener(e -> loadData("SELECT 'Fee Record', student_id, name, status, paid_amount FROM fee_records"));
        btnComplaints.addActionListener(e -> loadData("SELECT 'Complaint', id, category, description, student_name FROM complaints"));



        btnFeedback.addActionListener(e -> loadData("SELECT 'Feedback', student_id, category, message, submission_date FROM feedback"));



        btnRefresh.addActionListener(e -> loadAllReports());

        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);



        loadAllReports();
        setVisible(true);
    }

    private void loadData(String query) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostelms", "root", "");
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5)
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadAllReports() {
        model.setRowCount(0);

        appendData("SELECT 'Attendance', student_id, room_no, status, attendance_date FROM attendance");
        appendData("SELECT 'Fee Record', student_id, name, status, paid_amount FROM fee_records");
        appendData("SELECT 'Complaint', id, category, student_name, description FROM complaints");
        appendData("SELECT 'Feedback', student_id, student_name, category, message FROM feedback");
    }

    private void appendData(String query) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostelms", "root", "");
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5)
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AddReports();
    }
}
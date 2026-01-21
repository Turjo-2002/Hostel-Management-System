package Project1.Icon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AddAttendance extends JPanel {


    private Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/hostelms";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    public AddAttendance(JFrame parentFrame) {
        this.setLayout(new BorderLayout(20, 20));
        this.setBackground(new Color(2, 35, 36));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setOpaque(false);

        JButton btnBack = new JButton("Back");
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.revalidate();
            parentFrame.repaint();
            JOptionPane.showMessageDialog(null, "Returning to Dashboard...");
        });

        JLabel titleLabel = new JLabel("Admin Panel: Student Attendance Tracker", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JPanel titleAndBack = new JPanel(new BorderLayout());
        titleAndBack.setOpaque(false);
        titleAndBack.add(btnBack, BorderLayout.WEST);
        titleAndBack.add(titleLabel, BorderLayout.CENTER);


        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        searchPanel.setOpaque(false);

        JTextField txtStudentId = new JTextField(8);
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        JComboBox<String> monthBox = new JComboBox<>(months);

        String[] years = {"2024", "2025", "2026"};
        JComboBox<String> yearBox = new JComboBox<>(years);

        JButton btnSearch = new JButton("View Attendance");
        JButton btnMark = new JButton("Mark Today's Attendance");
        JButton btnViewAll = new JButton("View All Attendance");

        searchPanel.add(new JLabel("ID:") {{ setForeground(Color.WHITE); }});
        searchPanel.add(txtStudentId);
        searchPanel.add(new JLabel("Month:") {{ setForeground(Color.WHITE); }});
        searchPanel.add(monthBox);
        searchPanel.add(new JLabel("Year:") {{ setForeground(Color.WHITE); }});
        searchPanel.add(yearBox);
        searchPanel.add(btnSearch);
        searchPanel.add(btnMark);
        searchPanel.add(btnViewAll);

        topPanel.add(titleAndBack, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);


        String[] columns = {"Student ID", "Date", "Status", "Room No", "Check-in Time"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);


        btnSearch.addActionListener(e -> {
            String id = txtStudentId.getText();
            int month = monthBox.getSelectedIndex() + 1;
            String year = (String) yearBox.getSelectedItem();
            if (id.isEmpty()) { JOptionPane.showMessageDialog(null, "Enter Student ID"); return; }

            String sql = "SELECT * FROM attendance WHERE student_id = ? AND MONTH(attendance_date) = ? AND YEAR(attendance_date) = ?";
            try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, id); pst.setInt(2, month); pst.setString(3, year);
                ResultSet rs = pst.executeQuery();
                model.setRowCount(0);
                while (rs.next()) {
                    model.addRow(new Object[]{rs.getString("student_id"), rs.getDate("attendance_date"), rs.getString("status"), rs.getString("room_no"), rs.getString("check_in_time")});
                }
            } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Search Error!"); }
        });


        btnMark.addActionListener(e -> {
            String id = txtStudentId.getText();
            if (id.isEmpty()) { JOptionPane.showMessageDialog(null, "Enter ID to mark present"); return; }
            String sql = "INSERT INTO attendance (student_id, attendance_date, status, room_no, check_in_time) VALUES (?, CURDATE(), 'Present', '101', CURTIME())";
            try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, id); pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Attendance Marked for ID: " + id);
            } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage()); }
        });


        btnViewAll.addActionListener(e -> {
            String sql = "SELECT student_id, attendance_date, status, room_no, check_in_time FROM attendance ORDER BY attendance_date DESC";
            try (Connection con = getConnection(); Statement st = con.createStatement()) {
                ResultSet rs = st.executeQuery(sql);
                model.setRowCount(0);
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getString("student_id"),
                            rs.getDate("attendance_date"),
                            rs.getString("status"),
                            rs.getString("room_no"),
                            rs.getString("check_in_time")
                    });
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());
            }
        });

        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(new JButton("Generate Yearly Report"), BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Attendance Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 600);
            frame.add(new AddAttendance(frame));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
package Project1.Icon;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AttendanceSubmission extends JFrame {


    private final String DB_URL = "jdbc:mysql://localhost:3306/hostelms";
    private final String DB_USER = "root";
    private final String DB_PASS = "";

    public AttendanceSubmission() {
        setTitle("Student Portal - Attendance Check-in");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(2, 35, 36));
        JLabel headerLabel = new JLabel("ATTENDANCE CHECK-IN");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);


        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("Enter Student ID:");
        idLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        JTextField idField = new JTextField(15);

        JLabel roomLabel = new JLabel("Room No:");
        roomLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        JTextField roomField = new JTextField(15);

        JButton checkInBtn = new JButton("Submit Attendance");
        checkInBtn.setBackground(new Color(0, 102, 51));
        checkInBtn.setForeground(Color.WHITE);
        checkInBtn.setFont(new Font("Arial", Font.BOLD, 14));


        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(idLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; inputPanel.add(idField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(roomLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; inputPanel.add(roomField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; inputPanel.add(checkInBtn, gbc);

        add(inputPanel, BorderLayout.CENTER);


        checkInBtn.addActionListener(e -> {
            String studentId = idField.getText().trim();
            String roomNo = roomField.getText().trim();

            if (studentId.isEmpty() || roomNo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill out all fields!");
            } else {
                submitAttendanceToDB(studentId, roomNo);
            }
        });
    }

    private void submitAttendanceToDB(String id, String room) {

        String sql = "INSERT INTO attendance (student_id, attendance_date, status, room_no, check_in_time) VALUES (?, CURDATE(), 'Present', ?, CURTIME())";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, id);
            pst.setString(2, room);

            int result = pst.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Attendance marked successfully at " +
                        LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AttendanceSubmission().setVisible(true));
    }
}
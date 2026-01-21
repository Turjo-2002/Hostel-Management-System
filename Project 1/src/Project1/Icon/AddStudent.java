package Project1.Icon;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddStudent extends JFrame {
    private JTextField searchIdField, nameField, idField, deptField, contactField, roomNoField, emailField, addressField;
    private JButton searchBtn, updateBtn, clearBtn, backBtn;
    private JTextArea infoDisplayArea;

    private final String URL = "jdbc:mysql://localhost:3306/hostelms";
    private final String USER = "root";
    private final String PASS = "";

    public AddStudent() {
        setTitle("Admin - Student Information Search");
        setSize(700, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(0, 45, 45));


        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        searchPanel.setBackground(new Color(0, 60, 60));

        JLabel sLabel = new JLabel("Enter Student ID to Search:");
        sLabel.setForeground(Color.WHITE);
        sLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

        searchIdField = new JTextField(15);
        searchBtn = new JButton("SEARCH STUDENT");
        searchBtn.setBackground(new Color(0, 102, 204));
        searchBtn.setForeground(Color.WHITE);

        searchPanel.add(sLabel);
        searchPanel.add(searchIdField);
        searchPanel.add(searchBtn);


        JPanel detailPanel = new JPanel(new GridBagLayout());
        detailPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        idField = new JTextField(20); idField.setEditable(false);
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        deptField = new JTextField(20);
        contactField = new JTextField(20);
        roomNoField = new JTextField(20);
        addressField = new JTextField(20);

        addFormField("Found ID:", idField, detailPanel, gbc, 0);
        addFormField("Full Name:", nameField, detailPanel, gbc, 1);
        addFormField("Email:", emailField, detailPanel, gbc, 2);
        addFormField("Department:", deptField, detailPanel, gbc, 3);
        addFormField("Contact:", contactField, detailPanel, gbc, 4);
        addFormField("Room Number:", roomNoField, detailPanel, gbc, 5);
        addFormField("Address:", addressField, detailPanel, gbc, 6);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        updateBtn = new JButton("Update Record");
        clearBtn = new JButton("Clear Search");
        backBtn = new JButton("Back");

        styleButton(updateBtn, new Color(0, 153, 76));
        styleButton(clearBtn, Color.GRAY);
        styleButton(backBtn, new Color(204, 0, 0));

        bottomPanel.add(updateBtn);
        bottomPanel.add(clearBtn);
        bottomPanel.add(backBtn);


        searchBtn.addActionListener(e -> searchStudentData());
        clearBtn.addActionListener(e -> clearAllFields());
        backBtn.addActionListener(e -> this.dispose());
        updateBtn.addActionListener(e -> updateStudentData());

        add(searchPanel, BorderLayout.NORTH);
        add(detailPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void addFormField(String label, JTextField field, JPanel p, GridBagConstraints g, int r) {
        g.gridx = 0; g.gridy = r;
        JLabel l = new JLabel(label);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Tahoma", Font.BOLD, 13));
        p.add(l, g);
        g.gridx = 1;
        p.add(field, g);
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Tahoma", Font.BOLD, 13));
        btn.setFocusPainted(false);
    }

    private void searchStudentData() {
        String searchId = searchIdField.getText().trim();
        if (searchId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ID first!");
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String query = "SELECT * FROM users WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, searchId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                idField.setText(rs.getString("id"));
                nameField.setText(rs.getString("name"));
                emailField.setText(rs.getString("email"));
                deptField.setText(rs.getString("department"));
                contactField.setText(rs.getString("contact"));
                roomNoField.setText(rs.getString("room_no"));
                addressField.setText(rs.getString("address"));
            } else {
                JOptionPane.showMessageDialog(this, "No student found with ID: " + searchId);
                clearAllFields();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private void updateStudentData() {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String sql = "UPDATE users SET name=?, email=?, department=?, contact=?, room_no=?, address=? WHERE id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, nameField.getText());
            pst.setString(2, emailField.getText());
            pst.setString(3, deptField.getText());
            pst.setString(4, contactField.getText());
            pst.setString(5, roomNoField.getText());
            pst.setString(6, addressField.getText());
            pst.setString(7, idField.getText());

            int updated = pst.executeUpdate();
            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "Student Profile Updated Successfully!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void clearAllFields() {
        searchIdField.setText("");
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        deptField.setText("");
        contactField.setText("");
        roomNoField.setText("");
        addressField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddStudent().setVisible(true));
    }
}
package Project1.Icon;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class Feedback extends JFrame {
    private JTextField idField, nameField;
    private JTextArea messageArea;

    private JCheckBox cbFood, cbStaff, cbClean, cbRoom, cbOther;

    public Feedback() {
        setTitle("Student Feedback & Suggestions (Multiple Choice)");
        setSize(550, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));


        JPanel header = new JPanel();
        header.setBackground(new Color(3, 45, 48));
        JLabel title = new JLabel("SUBMIT YOUR FEEDBACK");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Tahoma", Font.BOLD, 18));
        header.add(title);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        mainPanel.add(new JLabel("Student ID:"));
        idField = new JTextField();
        mainPanel.add(idField);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(new JLabel("Full Name:"));
        nameField = new JTextField();
        mainPanel.add(nameField);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));


        mainPanel.add(new JLabel("Select Categories (You can pick multiple):"));
        JPanel checkPanel = new JPanel(new GridLayout(3, 2));
        cbFood = new JCheckBox("Food Quality");
        cbStaff = new JCheckBox("Staff Behavior");
        cbClean = new JCheckBox("Cleanliness");
        cbRoom = new JCheckBox("Room Service");
        cbOther = new JCheckBox("Other Issues");

        checkPanel.add(cbFood); checkPanel.add(cbStaff);
        checkPanel.add(cbClean); checkPanel.add(cbRoom);
        checkPanel.add(cbOther);
        mainPanel.add(checkPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));


        mainPanel.add(new JLabel("Your Detailed Feedback:"));
        messageArea = new JTextArea(6, 20);
        messageArea.setLineWrap(true);
        mainPanel.add(new JScrollPane(messageArea));


        JButton submitBtn = new JButton("Submit All Feedback");
        submitBtn.setBackground(new Color(16, 108, 115));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(header, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(submitBtn, BorderLayout.SOUTH);

        submitBtn.addActionListener(e -> saveFeedback());
    }

    private void saveFeedback() {

        if (idField.getText().isEmpty() || messageArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill Student ID and Message!");
            return;
        }


        ArrayList<String> selectedCategories = new ArrayList<>();
        if (cbFood.isSelected()) selectedCategories.add("Food");
        if (cbStaff.isSelected()) selectedCategories.add("Staff");
        if (cbClean.isSelected()) selectedCategories.add("Cleanliness");
        if (cbRoom.isSelected()) selectedCategories.add("Room");
        if (cbOther.isSelected()) selectedCategories.add("Other");

        if (selectedCategories.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one category!");
            return;
        }

        String finalCategories = String.join(", ", selectedCategories);

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostelms", "root", "");
             PreparedStatement ps = con.prepareStatement("INSERT INTO feedback (student_id, student_name, category, message, submission_date) VALUES (?, ?, ?, ?, CURDATE())")) {

            ps.setString(1, idField.getText());
            ps.setString(2, nameField.getText());
            ps.setString(3, finalCategories);
            ps.setString(4, messageArea.getText());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Multiple Feedback submitted to Admin!");
            this.dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Feedback().setVisible(true);
    }
}
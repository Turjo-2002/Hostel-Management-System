package Project1.Icon;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import Project1.Icon.AddComplaint;

public class ComplaintSubmission extends JFrame {
    private JTextField studentIdField, studentNameField;
    private JComboBox<String> categoryBox;
    private JTextArea descriptionArea;
    private JTable statusTable;
    private DefaultTableModel tableModel;

    private final String DB_URL = "jdbc:mysql://localhost:3306/hostelms";
    private final String DB_USER = "root";
    private final String DB_PASS = "";

    public ComplaintSubmission() {
        setTitle("Hostel Management - Complaint Portal");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));


        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 25, 25));
        JLabel headerLabel = new JLabel("Complaint and Maintenance Portal");
        headerLabel.setForeground(Color.ORANGE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);


        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 15, 15));


        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createTitledBorder("Submit New Complaint"));

        formPanel.add(new JLabel("Student ID:"));
        studentIdField = new JTextField();
        formPanel.add(studentIdField);

        formPanel.add(new JLabel("Student Name:"));
        studentNameField = new JTextField();
        formPanel.add(studentNameField);

        formPanel.add(new JLabel("Category:"));
        String[] cats = {"Water", "Electricity", "Cleanliness", "Furniture", "Other"};
        categoryBox = new JComboBox<>(cats);
        formPanel.add(categoryBox);

        formPanel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea(5, 20);
        formPanel.add(new JScrollPane(descriptionArea));

        JButton submitBtn = new JButton("Submit Complaint");
        submitBtn.setBackground(new Color(0, 120, 215));
        submitBtn.setForeground(Color.WHITE);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(submitBtn);


        JPanel trackPanel = new JPanel(new BorderLayout());
        trackPanel.setBorder(BorderFactory.createTitledBorder("My Complaint Status"));
        tableModel = new DefaultTableModel(new String[]{"ID", "Category", "Status"}, 0);
        statusTable = new JTable(tableModel);
        trackPanel.add(new JScrollPane(statusTable));

        mainPanel.add(formPanel);
        mainPanel.add(trackPanel);
        add(mainPanel, BorderLayout.CENTER);

        submitBtn.addActionListener(e -> submitAction());


        loadStatusTable();
    }

    private void submitAction() {
        String id = studentIdField.getText();
        String name = studentNameField.getText();
        String cat = (String) categoryBox.getSelectedItem();
        String desc = descriptionArea.getText();

        if (id.isEmpty() || name.isEmpty() || desc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }


        new AddComplaint().submitToDatabase(id, name, cat, desc);


        studentIdField.setText("");
        studentNameField.setText("");
        descriptionArea.setText("");
        loadStatusTable();
    }

    private void loadStatusTable() {
        tableModel.setRowCount(0);
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, category, status FROM complaints")) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3)});
            }
        } catch (SQLException e) {
            System.out.println("Wait for table creation: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ComplaintSubmission().setVisible(true));
    }
}
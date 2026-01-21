package Project1.Icon;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AuthenticationLogin extends JFrame {
    private JTextField idField, regIdField, regNameField, regEmailField, regDeptField, regContactField, regAddressField;
    private JPasswordField passField, regPassField;
    private JPanel mainPanel;
    private CardLayout cardLayout;


    private final String URL = "jdbc:mysql://localhost:3306/hostelms";
    private final String USER = "root";
    private final String PASS = "";

    public AuthenticationLogin() {
        setTitle("Hostel System - User Portal");
        setSize(550, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        createLoginPanel();
        createRegisterPanel();

        add(mainPanel);
        setVisible(true);
    }

    private void createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
        loginPanel.setBackground(new Color(0, 45, 45));

        JLabel title = new JLabel("STUDENT LOGIN", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        idField = new JTextField();
        idField.setBorder(BorderFactory.createTitledBorder("User ID"));
        passField = new JPasswordField();
        passField.setBorder(BorderFactory.createTitledBorder("Password"));

        JButton loginBtn = new JButton("LOGIN");
        JButton goToRegBtn = new JButton("Create New Account");

        loginPanel.add(title);
        loginPanel.add(idField);
        loginPanel.add(passField);
        loginPanel.add(loginBtn);
        loginPanel.add(goToRegBtn);

        loginBtn.addActionListener(e -> handleLogin());
        goToRegBtn.addActionListener(e -> cardLayout.show(mainPanel, "register"));

        mainPanel.add(loginPanel, "login");
    }

    private void createRegisterPanel() {
        JPanel regPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        regPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        regIdField = new JTextField();
        regPassField = new JPasswordField();
        regNameField = new JTextField();
        regEmailField = new JTextField();
        regDeptField = new JTextField();
        regContactField = new JTextField();
        regAddressField = new JTextField();

        regPanel.add(new JLabel("Student ID:")); regPanel.add(regIdField);
        regPanel.add(new JLabel("Password:")); regPanel.add(regPassField);
        regPanel.add(new JLabel("Full Name:")); regPanel.add(regNameField);
        regPanel.add(new JLabel("Email:")); regPanel.add(regEmailField);
        regPanel.add(new JLabel("Department:")); regPanel.add(regDeptField);
        regPanel.add(new JLabel("Contact:")); regPanel.add(regContactField);
        regPanel.add(new JLabel("Address:")); regPanel.add(regAddressField);

        JButton submitBtn = new JButton("Register Now");
        JButton backBtn = new JButton("Back to Login");

        regPanel.add(submitBtn);
        regPanel.add(backBtn);

        submitBtn.addActionListener(e -> handleRegistration());
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        mainPanel.add(regPanel, "register");
    }

    private void handleRegistration() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String sql = "INSERT INTO users (id, password, name, email, department, contact, address) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, regIdField.getText());
            pst.setString(2, new String(regPassField.getPassword()));
            pst.setString(3, regNameField.getText());
            pst.setString(4, regEmailField.getText());
            pst.setString(5, regDeptField.getText());
            pst.setString(6, regContactField.getText());
            pst.setString(7, regAddressField.getText());

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registration Successful! Please login.");
            cardLayout.show(mainPanel, "login");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void handleLogin() {
        String id = idField.getText().trim();
        String pass = new String(passField.getPassword()).trim();

        if (id.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both ID and Password");
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String query = "SELECT * FROM users WHERE id = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, id);
            pst.setString(2, pass);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                UserSession.isLoggedIn = true;
                UserSession.currentUserID = rs.getString("id");
                UserSession.currentUserName = rs.getString("name");

                JOptionPane.showMessageDialog(this, "Login Successful! Welcome " + UserSession.currentUserName);


                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid ID or Password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AuthenticationLogin());
    }
}
package Project1.Icon;

import javax.swing.*;
import java.awt.*;

public class MainLogin extends JFrame {

    public MainLogin() {
        setTitle("Scholars Haven - Hostel Management System");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JPanel header = new JPanel();
        header.setBackground(new Color(0, 45, 45));
        JLabel title = new JLabel("HOSTEL MANAGEMENT SYSTEM");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        header.add(title);


        JPanel btnPanel = new JPanel(new GridBagLayout());
        btnPanel.setBackground(new Color(240, 240, 240));

        JButton adminBtn = new JButton("ADMIN LOGIN");
        adminBtn.setPreferredSize(new Dimension(200, 150));
        adminBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        adminBtn.setFocusPainted(false);
        adminBtn.setBackground(Color.WHITE);
        adminBtn.setBorder(BorderFactory.createLineBorder(new Color(0, 45, 45), 2));


        adminBtn.addActionListener(e -> {
            String pass = JOptionPane.showInputDialog(this, "Enter Admin Password:");
            if ("admin123".equals(pass)) {
                dispose();
                new admin().setVisible(true);
            } else if (pass != null) {
                JOptionPane.showMessageDialog(this, "Incorrect Password!", "Access Denied", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnPanel.add(adminBtn);

        add(header, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new MainLogin().setVisible(true));
    }
}
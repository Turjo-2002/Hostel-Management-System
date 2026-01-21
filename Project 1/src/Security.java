package Project1.Icon;

import javax.swing.*;
import java.awt.*;

public class Security extends JFrame {

    public Security() {

        setTitle("Scholars Haven - Data Privacy & Security");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(3, 45, 48));
        headerPanel.setPreferredSize(new Dimension(600, 70));
        JLabel headerLabel = new JLabel("DATA PRIVACY AND SECURITY");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        headerPanel.add(headerLabel);


        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        contentPanel.setBackground(Color.WHITE);


        addSecurityPoint(contentPanel, "1. Personalized Data Access",
                "Students can access only their own data to ensure individual privacy.");

        addSecurityPoint(contentPanel, "2. Secure Authentication",
                "Multi-level authorization mechanisms prevent unauthorized login attempts.");

        addSecurityPoint(contentPanel, "3. Sensitive Information Protection",
                "Full protection of personal, financial, and academic records using encryption.");

        addSecurityPoint(contentPanel, "4. Database Integrity",
                "Restricted query permissions ensure that student records remain tamper-proof.");


        JButton btnClose = new JButton("I Understand & Close");
        btnClose.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnClose.setBackground(new Color(3, 45, 48));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFocusPainted(false);
        btnClose.addActionListener(e -> this.dispose());


        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(contentPanel), BorderLayout.CENTER);

        JPanel footerPanel = new JPanel();
        footerPanel.add(btnClose);
        add(footerPanel, BorderLayout.SOUTH);
    }


    private void addSecurityPoint(JPanel panel, String title, String description) {
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitle.setForeground(new Color(16, 108, 115));

        JTextArea txtDesc = new JTextArea(description);
        txtDesc.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        txtDesc.setEditable(false);
        txtDesc.setBackground(Color.WHITE);
        txtDesc.setBorder(BorderFactory.createEmptyBorder(5, 10, 20, 0));

        panel.add(lblTitle);
        panel.add(txtDesc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Security().setVisible(true));
    }
}
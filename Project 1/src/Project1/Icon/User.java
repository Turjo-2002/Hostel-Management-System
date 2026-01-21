package Project1.Icon;

import javax.swing.*;
import java.awt.*;

public class User extends JFrame {


    public User() {

        setTitle("Scholars Haven - User Dashboard");
        setLayout(null);
        setSize(1950, 1090);
        getContentPane().setBackground(Color.WHITE);


        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(500, 5, 1238, 900);
        panel.setBackground(new Color(3, 45, 48));
        add(panel);


        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(5, 5, 488, 900);
        panel1.setBackground(new Color(3, 45, 48));
        add(panel1);


        try {
            ImageIcon i111 = new ImageIcon(getClass().getResource("/Project1/Icon/Scholars Haven.gif"));
            Image i22 = i111.getImage().getScaledInstance(1238, 900, Image.SCALE_DEFAULT);
            JLabel label11 = new JLabel(new ImageIcon(i22));
            label11.setBounds(3, 3, 1238, 900);
            panel.add(label11);

            ImageIcon i11 = new ImageIcon(getClass().getResource("/Project1/Icon/login.gif"));
            Image i2 = i11.getImage().getScaledInstance(516, 360, Image.SCALE_DEFAULT);
            JLabel label1 = new JLabel(new ImageIcon(i2));
            label1.setBounds(2, 570, 481, 323);
            panel1.add(label1);
        } catch (Exception e) {
            System.out.println("Image path not found! Make sure images are in the correct folder.");
        }


        JButton btnAuth = new JButton("User login and Authentication");
        btnAuth.setBounds(30, 60, 300, 30);
        btnAuth.setBackground(Color.BLACK);
        btnAuth.setForeground(Color.WHITE);
        panel1.add(btnAuth);
        btnAuth.addActionListener(e -> new AuthenticationLogin());


        JButton btnRoom = new JButton("Room Allocation and Details");
        btnRoom.setBounds(30, 120, 300, 30);
        btnRoom.setBackground(Color.BLACK);
        btnRoom.setForeground(Color.WHITE);
        panel1.add(btnRoom);
        btnRoom.addActionListener(e -> new Project1.Icon.RoomAllocation());


        JButton btnFee = new JButton("Hostel Fee and Payment info");
        btnFee.setBounds(30, 180, 300, 30);
        btnFee.setBackground(Color.BLACK);
        btnFee.setForeground(Color.WHITE);
        panel1.add(btnFee);
        btnFee.addActionListener(e -> {
            new HostelFee(this);
            this.setVisible(false);
        });


        JButton btnComplaint = new JButton("Complaint and Maintenance");
        btnComplaint.setBounds(30, 240, 300, 30);
        btnComplaint.setBackground(Color.BLACK);
        btnComplaint.setForeground(Color.WHITE);
        panel1.add(btnComplaint);
        btnComplaint.addActionListener(e -> {
            new ComplaintSubmission().setVisible(true);
        });


        JButton btnAttend = new JButton("Attendance Management");
        btnAttend.setBounds(30, 300, 300, 30);
        btnAttend.setBackground(Color.BLACK);
        btnAttend.setForeground(Color.WHITE);
        panel1.add(btnAttend);
        btnAttend.addActionListener(e -> {
            new AttendanceSubmission().setVisible(true);
        });


        JButton btnFeedback = new JButton("Feedback and Suggestions");
        btnFeedback.setBounds(30, 360, 300, 30);
        btnFeedback.setBackground(Color.BLACK);
        btnFeedback.setForeground(Color.WHITE);
        panel1.add(btnFeedback);
        btnFeedback.addActionListener(e -> {
            new Feedback().setVisible(true);
        });


        JButton btnPrivacy = new JButton("Data Privacy and Security");
        btnPrivacy.setBounds(30, 420, 300, 30);
        btnPrivacy.setBackground(Color.BLACK);
        btnPrivacy.setForeground(Color.WHITE);
        panel1.add(btnPrivacy);
        btnPrivacy.addActionListener(e -> {
            new Security().setVisible(true);
        });


        JButton btnBack = new JButton("Back to Main Dashboard");
        btnBack.setBounds(30, 480, 300, 30);
        btnBack.setBackground(new Color(200, 0, 0));
        btnBack.setForeground(Color.WHITE);
        panel1.add(btnBack);
        btnBack.addActionListener(e -> {
            new Dashboard();
            this.dispose();
        });


        setVisible(true);
    }

    public static void main(String[] args) {
        new User();
    }
}
package Project1.Icon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame implements ActionListener {
    JButton add;
    JButton user;

    public Dashboard() {
        super("HEAVEN HOSTEL - MAIN DASHBOARD");


        user = new JButton("USER");
        user.setBounds(600, 510, 200, 40);
        user.setFont(new Font("Tahoma", Font.BOLD, 15));
        user.setBackground(new Color(61, 172, 200));
        user.setForeground(Color.BLACK);
        user.addActionListener(this);
        add(user);


        add = new JButton("ADMIN LOGIN");
        add.setBounds(1050, 510, 200, 40);
        add.setFont(new Font("Tahoma", Font.BOLD, 15));
        add.setBackground(new Color(61, 172, 200));
        add.setForeground(Color.red);
        add.addActionListener(this);
        add(add);


        try {
            ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("Project1/Icon/boss.png"));
            Image i2 = i11.getImage().getScaledInstance(200, 195, Image.SCALE_DEFAULT);
            JLabel label1 = new JLabel(new ImageIcon(i2));
            label1.setBounds(1050, 300, 200, 195);
            add(label1);

            ImageIcon i111 = new ImageIcon(ClassLoader.getSystemResource("Project1/Icon/reception.png"));
            Image i22 = i111.getImage().getScaledInstance(200, 195, Image.SCALE_DEFAULT);
            JLabel label11 = new JLabel(new ImageIcon(i22));
            label11.setBounds(570, 310, 200, 195);
            add(label11);

            ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("Project1/Icon/Dashboard.jpg"));
            Image i1 = imageIcon.getImage().getScaledInstance(1950, 1090, Image.SCALE_DEFAULT);
            JLabel label = new JLabel(new ImageIcon(i1));
            label.setBounds(0, 0, 1950, 1090);
            add(label);
        } catch (Exception e) {
            System.out.println("Images not found: " + e.getMessage());
        }

        setLayout(null);
        setSize(1950, 1090);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == user) {

            if (UserSession.isLoggedIn) {

                new User().setVisible(true);
                this.setVisible(false);
            } else {

                JOptionPane.showMessageDialog(this, "Access Denied! Please Login or Register first.");
                new AuthenticationLogin().setVisible(true);

            }

        } else if (e.getSource() == add) {

            new Project1.Icon.MainLogin().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}
package Project1.Icon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Project1.Icon.AddAttendance;
import Project1.Icon.AddStudent;

public class admin extends JFrame implements ActionListener {

  JButton add_Student, add_Room, add_Fee, add_Attendance, add_Report, add_Complaint, add_Security, logout, back;

  admin() {
    add_Student = new JButton("STUDENT MANAGMENT");
    add_Student.setBounds(250, 60, 200, 30);
    add_Student.setBackground(Color.white);
    add_Student.setForeground(Color.black);
    add_Student.setFont(new Font("Tahoma", Font.BOLD, 15));
    add_Student.addActionListener(this);
    add(add_Student);

    add_Room = new JButton("ROOM MANAGMENT");
    add_Room.setBounds(250, 180, 180, 30);
    add_Room.setBackground(Color.white);
    add_Room.setForeground(Color.black);
    add_Room.setFont(new Font("Tahoma", Font.BOLD, 15));
    add_Room.addActionListener(this);
    add(add_Room);

    add_Fee = new JButton(" FEE MANAGMENT");
    add_Fee.setBounds(250, 290, 200, 30);
    add_Fee.setBackground(Color.white);
    add_Fee.setForeground(Color.black);
    add_Fee.setFont(new Font("Tahoma", Font.BOLD, 15));
    add_Fee.addActionListener(this);
    add(add_Fee);


    add_Attendance = new JButton(" ATTENDANCE MANAGMENT");
    add_Attendance.setBounds(250, 390, 200, 30);
    add_Attendance.setBackground(Color.white);
    add_Attendance.setForeground(Color.black);
    add_Attendance.setFont(new Font("Tahoma", Font.BOLD, 15));
    add_Attendance.addActionListener(this);
    add(add_Attendance);


    add_Report = new JButton(" REPORTS & DASHBOARD ");
    add_Report.setBounds(250, 490, 200, 30);
    add_Report.setBackground(Color.white);
    add_Report.setForeground(Color.black);
    add_Report.setFont(new Font("Tahoma", Font.BOLD, 15));
    add_Report.addActionListener(this);
    add(add_Report);

    add_Complaint = new JButton("COMPLAINT & MAINTENANCE MANAGMENT ");
    add_Complaint.setBounds(250, 590, 200, 30);
    add_Complaint.setBackground(Color.white);
    add_Complaint.setForeground(Color.black);
    add_Complaint.setFont(new Font("Tahoma", Font.BOLD, 15));
    add_Complaint.addActionListener(this);
    add(add_Complaint);


    add_Security = new JButton("SECURITY AUTHENTICATION  ");
    add_Security.setBounds(250, 690, 200, 30);
    add_Security.setBackground(Color.white);
    add_Security.setForeground(Color.black);
    add_Security.setFont(new Font("Tahoma", Font.BOLD, 15));
    add_Security.addActionListener(this);
    add(add_Security);


    logout = new JButton("logout");
    logout.setBounds(10, 890, 95, 30);
    logout.setBackground(Color.black);
    logout.setForeground(Color.white);
    logout.setFont(new Font("Tahoma", Font.BOLD, 15));
    logout.addActionListener(this);
    add(logout);


    back = new JButton("back");
    back.setBounds(120, 890, 95, 30);
    back.setBackground(Color.black);
    back.setForeground(Color.white);
    back.setFont(new Font("Tahoma", Font.BOLD, 15));
    back.addActionListener(this);
    add(back);

    ImageIcon l1 = new ImageIcon(ClassLoader.getSystemResource("Project1/Icon/student.png"));
    Image l11 = l1.getImage().getScaledInstance(100, 80, Image.SCALE_DEFAULT);
    ImageIcon imageIcon = new ImageIcon(l11);
    JLabel label = new JLabel(imageIcon);
    label.setBounds(70, 22, 250, 120);
    add(label);


    ImageIcon imageIcon1 = new ImageIcon(ClassLoader.getSystemResource("Project1/Icon/room.png"));
    Image image = imageIcon1.getImage().getScaledInstance(70, 80, Image.SCALE_DEFAULT);
    ImageIcon imageIcon11 = new ImageIcon(image);
    JLabel label1 = new JLabel(imageIcon11);
    label1.setBounds(140, 140, 100, 100);
    add(label1);

    ImageIcon imageIcon2 = new ImageIcon(ClassLoader.getSystemResource("Project1/Icon/dollar.png"));
    Image image1 = imageIcon2.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
    ImageIcon imageIcon12 = new ImageIcon(image1);
    JLabel label2 = new JLabel(imageIcon12);
    label2.setBounds(170, 270, 50, 80);
    add(label2);


    ImageIcon imageIcon3 = new ImageIcon(ClassLoader.getSystemResource("Project1/Icon/attendence.png"));
    Image image2 = imageIcon3.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
    ImageIcon imageIcon13 = new ImageIcon(image2);
    JLabel label3 = new JLabel(imageIcon13);
    label3.setBounds(170, 380, 50, 80);
    add(label3);

    ImageIcon imageIcon4 = new ImageIcon(ClassLoader.getSystemResource("Project1/Icon/reports.png"));
    Image image3 = imageIcon4.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
    ImageIcon imageIcon14 = new ImageIcon(image3);
    JLabel label4 = new JLabel(imageIcon14);
    label4.setBounds(170, 480, 50, 80);
    add(label4);


    ImageIcon imageIcon5 = new ImageIcon(ClassLoader.getSystemResource("Project1/Icon/complaint.png"));
    Image image4 = imageIcon5.getImage().getScaledInstance(140, 140, Image.SCALE_DEFAULT);
    ImageIcon imageIcon15 = new ImageIcon(image4);
    JLabel label5 = new JLabel(imageIcon15);
    label5.setBounds(170, 580, 50, 80);
    add(label5);

    ImageIcon imageIcon6 = new ImageIcon(ClassLoader.getSystemResource("Project1/Icon/security.png"));
    Image image5 = imageIcon6.getImage().getScaledInstance(140, 140, Image.SCALE_DEFAULT);
    ImageIcon imageIcon16 = new ImageIcon(image5);
    JLabel label6 = new JLabel(imageIcon16);
    label6.setBounds(170, 690, 50, 80);
    add(label6);

    ImageIcon imageIcon7 = new ImageIcon(ClassLoader.getSystemResource("Project1/Icon/Scholars Haven.gif"));
    Image image6 = imageIcon7.getImage().getScaledInstance(1000, 700, Image.SCALE_DEFAULT);
    ImageIcon imageIcon17 = new ImageIcon(image6);
    JLabel label7 = new JLabel(imageIcon17);
    label7.setBounds(500, 200, 400, 457);
    add(label7);


    getContentPane().setBackground(new Color(3, 45, 48));
    setLayout(null);
    setSize(1100, 1090);
    setVisible(true);
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource()==add_Student){
      new AddStudent().setVisible(true);

    } else if (e.getSource() == add_Room) {
      new AddRoom();

    } else if (e.getSource() == add_Fee) {
      new Project1.Icon.AddFee().setVisible(true);


    } else if (e.getSource() == add_Attendance) {
      AddAttendance attendancePanel = new AddAttendance(this);
      JFrame attendanceFrame = new JFrame("Attendance Management");
      attendanceFrame.add(attendancePanel);
      attendanceFrame.setSize(1000,600);
      attendanceFrame.setLocationRelativeTo(null);
      attendanceFrame.setVisible(true);





    } else if (e.getSource() == add_Report) {
     new Project1.Icon.AddReports().setVisible(true);


    } else if (e.getSource() == add_Complaint) {
      new Project1.Icon.AddComplaint().setVisible(true);






    } else if (e.getSource() == add_Security) {
      new Project1.Icon.AddSecurity().setVisible(true);


    } else if (e.getSource() == logout) {
      System.exit(404);

    } else if (e.getSource() == back) {

      setVisible(false);
    }
  }



    public static void main(String[] args) {
        new admin();

    }
}

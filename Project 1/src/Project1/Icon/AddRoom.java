package Project1.Icon;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AddRoom extends JFrame {
    JTextField txtRoomNo, txtPrice;
    JComboBox<String> comboType, comboStatus, comboClass, comboFloor, comboWashroom;
    DefaultTableModel model;
    JTable table;

    public AddRoom() {
        setTitle("Admin - Room Management with Fee Tracking");
        setSize(1200, 700);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 245, 245));

        int labelX = 30, fieldX = 140, width = 150, height = 30;


        addLabel("Room No:", labelX, 30);
        txtRoomNo = new JTextField();
        txtRoomNo.setBounds(fieldX, 30, width, height);
        add(txtRoomNo);

        addLabel("AC/Non-AC:", labelX, 70);
        comboType = new JComboBox<>(new String[]{"AC", "Non-AC"});
        comboType.setBounds(fieldX, 70, width, height);
        add(comboType);

        addLabel("Single/Double:", labelX, 110);
        comboClass = new JComboBox<>(new String[]{"Single", "Double"});
        comboClass.setBounds(fieldX, 110, width, height);
        add(comboClass);

        addLabel("Floor:", labelX, 150);
        comboFloor = new JComboBox<>(new String[]{"1st Floor", "2nd Floor", "3rd Floor"});
        comboFloor.setBounds(fieldX, 150, width, height);
        add(comboFloor);

        addLabel("Washroom:", labelX, 190);
        comboWashroom = new JComboBox<>(new String[]{"Attached", "Common"});
        comboWashroom.setBounds(fieldX, 190, width, height);
        add(comboWashroom);

        addLabel("Room Fee:", labelX, 230);
        txtPrice = new JTextField();
        txtPrice.setBounds(fieldX, 230, width, height);
        add(txtPrice);

        addLabel("Status:", labelX, 270);
        comboStatus = new JComboBox<>(new String[]{"Available", "Occupied"});
        comboStatus.setBounds(fieldX, 270, width, height);
        add(comboStatus);


        JButton btnAdd = new JButton("Add Room");
        btnAdd.setBounds(30, 330, 120, 35);
        btnAdd.setBackground(new Color(3, 45, 48));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 12));
        add(btnAdd);

        JButton btnRefresh = new JButton("Refresh Data");
        btnRefresh.setBounds(170, 330, 120, 35);
        btnRefresh.setBackground(Color.WHITE);
        add(btnRefresh);


        String[] cols = {"Room No", "Type", "Class", "Floor", "Washroom", "Fee", "Status", "Student Name"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(30);


        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String status = table.getValueAt(row, 6).toString();
                if ("Occupied".equals(status)) {
                    c.setBackground(new Color(255, 220, 220)); // হালকা লাল
                } else {
                    c.setBackground(Color.WHITE);
                }
                if (isSelected) c.setBackground(new Color(184, 207, 229));
                return c;
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(340, 30, 800, 580);
        sp.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Master Room List (Live Tracking)",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 14)));
        add(sp);


        btnAdd.addActionListener(e -> {
            if(txtRoomNo.getText().trim().isEmpty() || txtPrice.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Error: All fields are mandatory!");
                return;
            }
            try {
                con c = new con();
                String query = "INSERT INTO room (room_no, type, room_class, floor, washroom, price, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = c.connection.prepareStatement(query);
                pst.setString(1, txtRoomNo.getText());
                pst.setString(2, (String) comboType.getSelectedItem());
                pst.setString(3, (String) comboClass.getSelectedItem());
                pst.setString(4, (String) comboFloor.getSelectedItem());
                pst.setString(5, (String) comboWashroom.getSelectedItem());
                pst.setString(6, txtPrice.getText());
                pst.setString(7, (String) comboStatus.getSelectedItem());

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "New Room Added Successfully!");

                txtRoomNo.setText("");
                txtPrice.setText("");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());
            }
        });

        btnRefresh.addActionListener(e -> loadData());

        loadData();
        setVisible(true);
    }

    private void addLabel(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setBounds(x, y, 120, 30);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 13));
        add(lbl);
    }


    public void loadData() {
        model.setRowCount(0);
        try {
            con c = new con();

            String query = "SELECT r.room_no, r.type, r.room_class, r.floor, r.washroom, r.price, r.status, " +
                    "IFNULL(u.name, '---') as student_name " +
                    "FROM room r " +
                    "LEFT JOIN users u ON r.booked_by = u.id " +
                    "ORDER BY r.room_no ASC";

            ResultSet rs = c.statement.executeQuery(query);
            while(rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("room_no"),
                        rs.getString("type"),
                        rs.getString("room_class"),
                        rs.getString("floor"),
                        rs.getString("washroom"),
                        rs.getString("price") + " TK",
                        rs.getString("status"),
                        rs.getString("student_name")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Loading Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        new AddRoom();
    }
}
package Project1.Icon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.*;

public class RoomAllocation extends JFrame {
    DefaultTableModel model;
    JTable table;
    JComboBox<String> floorFilter, typeFilter;

    public RoomAllocation() {

        if (!UserSession.isLoggedIn) {
            JOptionPane.showMessageDialog(null, "Please Login First!");
            new AuthenticationLogin();
            this.dispose();
            return;
        }

        setTitle("Scholars Haven - Room Allocation (Booking for: " + UserSession.currentUserName + ")");
        setSize(1150, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(3, 45, 48));
        header.setPreferredSize(new Dimension(1100, 100));

        JLabel title = new JLabel("🎓 SELECT AND BOOK YOUR ROOM", JLabel.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Tahoma", Font.BOLD, 22));
        header.add(title, BorderLayout.NORTH);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        filterPanel.setOpaque(false);

        JLabel lblFloor = new JLabel("Filter by Floor:");
        lblFloor.setForeground(Color.WHITE);
        floorFilter = new JComboBox<>(new String[]{"All Floors", "1st Floor", "2nd Floor", "3rd Floor"});

        JLabel lblType = new JLabel("Room Type:");
        lblType.setForeground(Color.WHITE);
        typeFilter = new JComboBox<>(new String[]{"All Types", "AC", "Non-AC"});

        JButton btnSearch = new JButton("Apply Filter");

        filterPanel.add(lblFloor);
        filterPanel.add(floorFilter);
        filterPanel.add(lblType);
        filterPanel.add(typeFilter);
        filterPanel.add(btnSearch);

        header.add(filterPanel, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);


        String[] columns = {"Room No", "AC/Non-AC", "Single/Double", "Floor", "Washroom", "Fee (Monthly)", "Status"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(20, 30, 20, 30));
        add(scrollPane, BorderLayout.CENTER);


        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton btnBook = new JButton("Confirm Booking Now");
        btnBook.setBackground(new Color(46, 139, 87));
        btnBook.setForeground(Color.WHITE);
        btnBook.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnBook.setPreferredSize(new Dimension(250, 45));

        JButton btnBack = new JButton("Back to User Panel");
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnBack.setPreferredSize(new Dimension(200, 45));

        footer.add(btnBook);
        footer.add(btnBack);
        add(footer, BorderLayout.SOUTH);



        btnSearch.addActionListener(e -> applyFilter());

        btnBook.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                String roomNo = table.getValueAt(row, 0).toString();
                String fee = table.getValueAt(row, 5).toString();

                int confirm = JOptionPane.showConfirmDialog(null,
                        "Confirm Booking for Room: " + roomNo + "\nFee: " + fee + " TK\nStudent Name: " + UserSession.currentUserName,
                        "Booking Confirmation", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    bookRoom(roomNo);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a room from the table first!");
            }
        });

        btnBack.addActionListener(e -> {
            new User().setVisible(true);
            this.dispose();
        });

        loadAvailableRooms();
        setVisible(true);
    }

    public void loadAvailableRooms() {
        model.setRowCount(0);
        try {
            con c = new con();

            String query = "SELECT room_no, type, room_class, floor, washroom, price, status FROM room WHERE status = 'Available'";
            ResultSet rs = c.statement.executeQuery(query);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void applyFilter() {
        String selectedFloor = (String) floorFilter.getSelectedItem();
        String selectedType = (String) typeFilter.getSelectedItem();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        java.util.List<RowFilter<Object, Object>> filters = new java.util.ArrayList<>();
        if (!selectedFloor.equals("All Floors")) filters.add(RowFilter.regexFilter(selectedFloor, 3));
        if (!selectedType.equals("All Types")) filters.add(RowFilter.regexFilter(selectedType, 1));
        sorter.setRowFilter(RowFilter.andFilter(filters));
    }


    private void bookRoom(String roomNo) {
        try {
            con c = new con();

            String query = "UPDATE room SET status = 'Occupied', booked_by = ? WHERE room_no = ?";
            PreparedStatement pst = c.connection.prepareStatement(query);

            pst.setString(1, UserSession.currentUserID);
            pst.setString(2, roomNo);

            int result = pst.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Congratulations " + UserSession.currentUserName + "!\nRoom " + roomNo + " has been successfully booked.");
                loadAvailableRooms();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Booking Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new RoomAllocation();
    }
}
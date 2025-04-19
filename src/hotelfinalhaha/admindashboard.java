/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hotelfinalhaha;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
/**
 *
 * @author Admin
 */
public class admindashboard extends javax.swing.JFrame {
Connection con;
    PreparedStatement pat;
    DefaultTableModel d;
    /**
     * Creates new form admindashboard
     */
    public admindashboard() {
      initComponents();
    con = connector.Connect();

    Load_client();
    Load_room();
    displayLatestRoom();
    Load_reservation();
    loadDashboardStats();

    // Style tables
    styleTableHeader(); // Applies to jTable1, jTable2, jTable3

    // jTable1 settings
    jTable1.setRowHeight(25);
    jTable1.getTableHeader().setPreferredSize(new Dimension(0, 25));
    jTable1.setDefaultEditor(Object.class, null);
    jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jTable1.setFocusable(false);
    jTable1.setRowSelectionAllowed(true);
    jTable1.setColumnSelectionAllowed(false);

    // jTable2 settings
    jTable2.setRowHeight(25);
    jTable2.getTableHeader().setPreferredSize(new Dimension(0, 25));
    jTable2.setDefaultEditor(Object.class, null);
    jTable2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jTable2.setFocusable(false);
    jTable2.setRowSelectionAllowed(true);
    jTable2.setColumnSelectionAllowed(false);

    // ✅ jTable3 settings
    jTable3.setRowHeight(25);
    jTable3.getTableHeader().setPreferredSize(new Dimension(0, 25));
    jTable3.setDefaultEditor(Object.class, null);
    jTable3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jTable3.setFocusable(false);
    jTable3.setRowSelectionAllowed(true);
    jTable3.setColumnSelectionAllowed(false);

    // Style comboboxes
    styleComboBox(jComboBox1);
    styleComboBox(jComboBox2);
    // Border style
    applyTableBorder(jTable1);
    applyTableBorder(jTable2);
    applyTableBorder(jTable3);

    }
  private void styleTableHeader() {
    // Header styling function (shared logic)
    TableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel lbl = new JLabel(value.toString());
            lbl.setOpaque(true);
            lbl.setBackground(new Color(32, 136, 203));
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            lbl.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            return lbl;
        }
    };

    // Data cell center renderer
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

    // Apply to jTable1
    JTableHeader header1 = jTable1.getTableHeader();
    header1.setDefaultRenderer(headerRenderer);
    for (int i = 0; i < jTable1.getColumnCount(); i++) {
        jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    // Apply to jTable2
    JTableHeader header2 = jTable2.getTableHeader();
    header2.setDefaultRenderer(headerRenderer);
    for (int i = 0; i < jTable2.getColumnCount(); i++) {
        jTable2.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    // ✅ Now also apply to jTable3
    JTableHeader header3 = jTable3.getTableHeader();
    header3.setDefaultRenderer(headerRenderer);
    for (int i = 0; i < jTable3.getColumnCount(); i++) {
        jTable3.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
}

  private void applyTableBorder(JTable table) {
    // Create a border with thickness and color
    Border outerBorder = BorderFactory.createLineBorder(new Color(32, 136, 203), 3); // Blue border with thickness 3
    table.setBorder(outerBorder); // Apply the border to the table
    
    // If you want to apply rounded corners (optional)
    Border roundedBorder = BorderFactory.createCompoundBorder(
            outerBorder,
            BorderFactory.createEmptyBorder(5, 5, 5, 5) // Adds padding inside the table border
    );
    table.setBorder(roundedBorder); // Apply the compound border with padding
}
  private void styleComboBox(JComboBox comboBox) {
    // Set background color to match your table header
    comboBox.setBackground(Color.WHITE); // White background
    comboBox.setForeground(Color.BLACK); // Black text
    comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Clean font
    comboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Thin black border

    comboBox.setUI(new BasicComboBoxUI() {
        @Override
        protected JButton createArrowButton() {
            JButton arrowButton = super.createArrowButton();
            arrowButton.setBackground(Color.WHITE);
            arrowButton.setBorder(BorderFactory.createEmptyBorder());
            return arrowButton;
        }
    });

}



   private void displayLatestRoom() {
    try {
        // Query to get all room numbers ordered by room number
        String query = "SELECT roomNumber FROM Rooms ORDER BY roomNumber ASC";
        PreparedStatement pst = con.prepareStatement(query);
        ResultSet rs = pst.executeQuery();

        int nextRoomNumber = 1; // Default to 1 if no rooms exist

        // Check for the first gap in the room numbers
        while (rs.next()) {
            int existingRoomNumber = rs.getInt("roomNumber");
            if (existingRoomNumber != nextRoomNumber) {
                // We found a gap, so use the next available room number
                break;
            }
            nextRoomNumber++;
        }

        // Set the next available room number
        String labelText = " " + nextRoomNumber;
        jLabel13.setText(labelText);

        rs.close();
        pst.close();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error fetching latest room: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

public void Load_client() {
    try {
        // Use a parameterized query to prevent SQL injection
        pat = con.prepareStatement("SELECT * FROM users");
      
        ResultSet rs = pat.executeQuery();

        // Get table model and clear existing rows
        d = (DefaultTableModel) jTable3.getModel();
        d.setRowCount(0);

        while (rs.next()) {
            // Create a new row with values for the current reservation
            Vector<String> v2 = new Vector<>();
            v2.add(rs.getString("userID"));
            v2.add(rs.getString("username"));
            v2.add(rs.getString("firstName"));
            v2.add(rs.getString("lastName"));
            v2.add(rs.getString("gender"));
             v2.add(rs.getString("password"));

            // Add the row to the table model
            d.addRow(v2);
        }
    } catch (SQLException ex) {
        Logger.getLogger(admindashboard.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Error loading reservations: " + ex.getMessage());
    }
}

public void Load_room() {
    try {
        // Join Rooms with RoomTypes to get full details
        pat = con.prepareStatement("""
            SELECT r.roomNumber AS RoomId, r.roomStatus,  rt.type AS RoomType, rt.occupancy AS BedType, rt.price AS Amount, rt.roomTypeID
            FROM Rooms r
            JOIN RoomTypes rt ON r.roomTypeID = rt.roomTypeID
        """);

        ResultSet rs = pat.executeQuery();
        ResultSetMetaData rsd = rs.getMetaData();
        int c = rsd.getColumnCount();

        d = (DefaultTableModel) jTable2.getModel();
        d.setRowCount(0);

        while (rs.next()) {
            Vector<String> v2 = new Vector<>();

            v2.add(rs.getString("roomId"));    // roomNumber
            v2.add(rs.getString("roomType"));  // A/C or NON A/C
            v2.add(rs.getString("Bedtype"));   // Single or Double
            v2.add(rs.getString("Amount"));// price
            v2.add(rs.getString("roomStatus"));
             v2.add(rs.getString("roomTypeID"));
            d.addRow(v2);
        }

    } catch (SQLException ex) {
        Logger.getLogger(admindashboard.class.getName()).log(Level.SEVERE, null, ex);
    }
}
public void loadDashboardStats() {
    try {
        // Total Reservations
        String totalReservationsQuery = "SELECT COUNT(*) FROM reservations";
        pat = con.prepareStatement(totalReservationsQuery);
        ResultSet rs = pat.executeQuery();
        if (rs.next()) {
            lblTotalReservations.setText(String.valueOf(rs.getInt(1)));
        }

        // Total Rooms
        String totalRoomsQuery = "SELECT COUNT(*) FROM rooms";
        pat = con.prepareStatement(totalRoomsQuery);
        rs = pat.executeQuery();
        if (rs.next()) {
            lblTotalRooms.setText(String.valueOf(rs.getInt(1)));
        }

        // Total Clients
        String totalClientsQuery = "SELECT COUNT(*) FROM users";
        pat = con.prepareStatement(totalClientsQuery);
        rs = pat.executeQuery();
        if (rs.next()) {
            lblTotalClients.setText(String.valueOf(rs.getInt(1)));
        }

        // Total Income (sum of all paid reservationdetails)
        String totalIncomeQuery = "SELECT SUM(amount) FROM reservationdetails rd " +
                                  "JOIN reservations r ON rd.reservationID = r.reservationID " +
                                  "WHERE r.status = 'Approved'";
        pat = con.prepareStatement(totalIncomeQuery);
        rs = pat.executeQuery();
        if (rs.next()) {
            double totalIncome = rs.getDouble(1);
            lblTotalIncome.setText("₱" + String.format("%.2f", totalIncome));
        }

    } catch (SQLException ex) {
        Logger.getLogger(admindashboard.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Error loading dashboard stats: " + ex.getMessage());
    }
}

public void Load_reservation() {
    try {
        // 1. Move past reservations to history
        String moveToHistoryQuery = "INSERT INTO reservation_history (reservationID, reservationNo, userID, checkIn, checkOut, status, roomTypeID, amount) " +
            "SELECT r.reservationID, r.reservationNo, r.userID, r.checkIn, r.checkOut, r.status, r.roomTypeID, rd.amount " +
            "FROM reservations r " +
            "JOIN reservationdetails rd ON r.reservationID = rd.reservationID " +
            "WHERE r.checkOut < CURDATE()";
        
        pat = con.prepareStatement(moveToHistoryQuery);
        pat.executeUpdate();

        // 2. Delete moved reservations from current table
        String deleteOldQuery = "DELETE FROM reservations WHERE checkOut < CURDATE()";
        pat = con.prepareStatement(deleteOldQuery);
        pat.executeUpdate();

        // 3. Now load active reservations
        String query = "SELECT r.reservationID, r.reservationNo, r.userID, r.checkIn, r.checkOut, r.status, r.roomTypeID, rt.type AS roomType, rd.amount " +
                       "FROM reservations r " +
                       "JOIN reservationdetails rd ON r.reservationID = rd.reservationID " +
                       "JOIN roomtypes rt ON r.roomTypeID = rt.roomTypeID";  // Join with roomtypes to get the type

        pat = con.prepareStatement(query);
        ResultSet rs = pat.executeQuery();

        // Clear table first
        d = (DefaultTableModel) jTable1.getModel();
        d.setRowCount(0);

        while (rs.next()) {
            Vector<String> v2 = new Vector<>();
            v2.add(rs.getString("reservationID"));
            v2.add(rs.getString("reservationNo"));
            v2.add(rs.getString("userID"));
            v2.add(rs.getString("checkIn"));
            v2.add(rs.getString("checkOut"));
            v2.add(rs.getString("status"));
            v2.add(rs.getString("roomType"));
            v2.add(rs.getString("roomTypeID")); // Room type now accessed correctly from roomtypes table
            v2.add(rs.getString("amount"));
            d.addRow(v2);
        }
    } catch (SQLException ex) {
        Logger.getLogger(admindashboard.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Error loading reservations: " + ex.getMessage());
    }
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        k2 = new com.k33ptoo.components.KGradientPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        k1 = new com.k33ptoo.components.KGradientPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        k3 = new com.k33ptoo.components.KGradientPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        k4 = new com.k33ptoo.components.KGradientPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        kGradientPanel5 = new com.k33ptoo.components.KGradientPanel();
        jLabel3 = new javax.swing.JLabel();
        lblTotalReservations = new javax.swing.JLabel();
        kGradientPanel8 = new com.k33ptoo.components.KGradientPanel();
        jLabel4 = new javax.swing.JLabel();
        lblTotalRooms = new javax.swing.JLabel();
        kGradientPanel9 = new com.k33ptoo.components.KGradientPanel();
        jLabel5 = new javax.swing.JLabel();
        lblTotalClients = new javax.swing.JLabel();
        kGradientPanel10 = new com.k33ptoo.components.KGradientPanel();
        jLabel6 = new javax.swing.JLabel();
        lblTotalIncome = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField2 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        kButton1 = new com.k33ptoo.components.KButton();
        kButton2 = new com.k33ptoo.components.KButton();
        kButton3 = new com.k33ptoo.components.KButton();
        kButton4 = new com.k33ptoo.components.KButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jTextField3 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel2.setPreferredSize(new java.awt.Dimension(300, 600));

        k2.setBackground(new java.awt.Color(255, 255, 255));
        k2.setkBorderRadius(20);
        k2.setkEndColor(new java.awt.Color(128, 222, 234));
        k2.setkStartColor(new java.awt.Color(100, 181, 246));
        k2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                k2MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Manage Room");

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONS/room_751611.png"))); // NOI18N

        javax.swing.GroupLayout k2Layout = new javax.swing.GroupLayout(k2);
        k2.setLayout(k2Layout);
        k2Layout.setHorizontalGroup(
            k2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, k2Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(16, 16, 16))
        );
        k2Layout.setVerticalGroup(
            k2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, k2Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(k2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel2))
                .addGap(24, 24, 24))
        );

        k1.setBackground(new java.awt.Color(255, 255, 255));
        k1.setkBorderRadius(20);
        k1.setkEndColor(new java.awt.Color(182, 160, 229));
        k1.setkStartColor(new java.awt.Color(167, 199, 231));
        k1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                k1MouseClicked(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Overview");

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONS/search_1665652.png"))); // NOI18N

        javax.swing.GroupLayout k1Layout = new javax.swing.GroupLayout(k1);
        k1.setLayout(k1Layout);
        k1Layout.setHorizontalGroup(
            k1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(k1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        k1Layout.setVerticalGroup(
            k1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, k1Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(k1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel1))
                .addGap(22, 22, 22))
        );

        k3.setBackground(new java.awt.Color(255, 255, 255));
        k3.setkBorderRadius(20);
        k3.setkEndColor(new java.awt.Color(255, 140, 148));
        k3.setkStartColor(new java.awt.Color(255, 182, 185));
        k3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                k3MouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setText("Client");

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONS/customer_3126647.png"))); // NOI18N

        javax.swing.GroupLayout k3Layout = new javax.swing.GroupLayout(k3);
        k3.setLayout(k3Layout);
        k3Layout.setHorizontalGroup(
            k3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(k3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        k3Layout.setVerticalGroup(
            k3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(k3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(k3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel19))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        k4.setBackground(new java.awt.Color(255, 255, 255));
        k4.setkBorderRadius(20);
        k4.setkEndColor(new java.awt.Color(247, 229, 183));
        k4.setkStartColor(new java.awt.Color(178, 245, 234));
        k4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                k4MouseClicked(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel17.setText("Payments");

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONS/payment-method_4564877.png"))); // NOI18N

        javax.swing.GroupLayout k4Layout = new javax.swing.GroupLayout(k4);
        k4.setLayout(k4Layout);
        k4Layout.setHorizontalGroup(
            k4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, k4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addGap(66, 66, 66))
        );
        k4Layout.setVerticalGroup(
            k4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(k4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(k4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel17))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONS/sr2f38b6b22b6aws3-ezgif.com-resize (1).gif"))); // NOI18N

        jLabel7.setText("LOGOUT");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(k2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(k4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(k1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(k3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(jLabel8))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addComponent(jLabel7)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(k1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(k2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(k3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(k4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 600));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.CardLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(null);

        kGradientPanel5.setBackground(new java.awt.Color(255, 255, 255));
        kGradientPanel5.setkBorderRadius(30);
        kGradientPanel5.setkEndColor(new java.awt.Color(37, 117, 252));
        kGradientPanel5.setkGradientFocus(300);
        kGradientPanel5.setkStartColor(new java.awt.Color(106, 17, 203));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Reservation");

        lblTotalReservations.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lblTotalReservations.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalReservations.setText(".");

        javax.swing.GroupLayout kGradientPanel5Layout = new javax.swing.GroupLayout(kGradientPanel5);
        kGradientPanel5.setLayout(kGradientPanel5Layout);
        kGradientPanel5Layout.setHorizontalGroup(
            kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel5Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTotalReservations, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        kGradientPanel5Layout.setVerticalGroup(
            kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalReservations)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel3.add(kGradientPanel5);
        kGradientPanel5.setBounds(22, 30, 215, 101);

        kGradientPanel8.setBackground(new java.awt.Color(255, 255, 255));
        kGradientPanel8.setkBorderRadius(30);
        kGradientPanel8.setkEndColor(new java.awt.Color(56, 239, 125));
        kGradientPanel8.setkGradientFocus(300);
        kGradientPanel8.setkStartColor(new java.awt.Color(17, 153, 142));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Rooms");

        lblTotalRooms.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lblTotalRooms.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalRooms.setText(".");

        javax.swing.GroupLayout kGradientPanel8Layout = new javax.swing.GroupLayout(kGradientPanel8);
        kGradientPanel8.setLayout(kGradientPanel8Layout);
        kGradientPanel8Layout.setHorizontalGroup(
            kGradientPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel8Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(kGradientPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(lblTotalRooms, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        kGradientPanel8Layout.setVerticalGroup(
            kGradientPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalRooms)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel3.add(kGradientPanel8);
        kGradientPanel8.setBounds(255, 30, 215, 100);

        kGradientPanel9.setBackground(new java.awt.Color(255, 255, 255));
        kGradientPanel9.setkBorderRadius(30);
        kGradientPanel9.setkEndColor(new java.awt.Color(110, 72, 170));
        kGradientPanel9.setkGradientFocus(300);
        kGradientPanel9.setkStartColor(new java.awt.Color(157, 80, 187));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Clients");

        lblTotalClients.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lblTotalClients.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalClients.setText(".");

        javax.swing.GroupLayout kGradientPanel9Layout = new javax.swing.GroupLayout(kGradientPanel9);
        kGradientPanel9.setLayout(kGradientPanel9Layout);
        kGradientPanel9Layout.setHorizontalGroup(
            kGradientPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel9Layout.createSequentialGroup()
                .addContainerGap(88, Short.MAX_VALUE)
                .addGroup(kGradientPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalClients, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(62, 62, 62))
        );
        kGradientPanel9Layout.setVerticalGroup(
            kGradientPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalClients)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel3.add(kGradientPanel9);
        kGradientPanel9.setBounds(488, 30, 215, 100);

        kGradientPanel10.setBackground(new java.awt.Color(255, 255, 255));
        kGradientPanel10.setkBorderRadius(30);
        kGradientPanel10.setkEndColor(new java.awt.Color(255, 210, 0));
        kGradientPanel10.setkGradientFocus(300);
        kGradientPanel10.setkStartColor(new java.awt.Color(247, 151, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Total Income");

        lblTotalIncome.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lblTotalIncome.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalIncome.setText(".");

        javax.swing.GroupLayout kGradientPanel10Layout = new javax.swing.GroupLayout(kGradientPanel10);
        kGradientPanel10.setLayout(kGradientPanel10Layout);
        kGradientPanel10Layout.setHorizontalGroup(
            kGradientPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel10Layout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addGroup(kGradientPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalIncome, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(51, 51, 51))
        );
        kGradientPanel10Layout.setVerticalGroup(
            kGradientPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalIncome)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel3.add(kGradientPanel10);
        kGradientPanel10.setBounds(721, 30, 215, 100);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ReservationID", "ReserveNo", "UserID", "CheckIn", "CheckOut", "Status", "Type", "RoomTypeID", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(0, 0, 0));
        jTable1.setRowHeight(25);
        jTable1.setSelectionBackground(new java.awt.Color(232, 57, 95));
        jTable1.setShowHorizontalLines(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel3.add(jScrollPane1);
        jScrollPane1.setBounds(22, 207, 914, 376);

        jTextField2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField2FocusLost(evt);
            }
        });
        jTextField2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField2MouseClicked(evt);
            }
        });
        jPanel3.add(jTextField2);
        jTextField2.setBounds(20, 150, 210, 40);

        jPanel1.add(jPanel3, "card2");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "RoomNumber", "RoomType", "Occupancy", "Amount", "RoomStatus", "RoomTypeID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable2.setGridColor(new java.awt.Color(0, 0, 0));
        jTable2.setRowHeight(25);
        jTable2.setSelectionBackground(new java.awt.Color(232, 57, 95));
        jTable2.setShowHorizontalLines(true);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setText("Room No");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel13.setText("jLabel13");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "NON/AC", " " }));
        jComboBox1.setBorder(null);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboBox2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Single", "Double" }));
        jComboBox2.setBorder(null);

        kButton1.setText("ADD");
        kButton1.setkBorderRadius(26);
        kButton1.setkEndColor(new java.awt.Color(56, 249, 215));
        kButton1.setkHoverEndColor(new java.awt.Color(0, 0, 0));
        kButton1.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        kButton1.setkHoverStartColor(new java.awt.Color(144, 238, 144));
        kButton1.setkPressedColor(new java.awt.Color(56, 215, 100));
        kButton1.setkSelectedColor(new java.awt.Color(50, 200, 100));
        kButton1.setkStartColor(new java.awt.Color(67, 233, 123));
        kButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton1ActionPerformed(evt);
            }
        });

        kButton2.setText("EDIT");
        kButton2.setkBorderRadius(26);
        kButton2.setkEndColor(new java.awt.Color(47, 128, 237));
        kButton2.setkHoverEndColor(new java.awt.Color(0, 0, 255));
        kButton2.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        kButton2.setkHoverStartColor(new java.awt.Color(135, 206, 250));
        kButton2.setkPressedColor(new java.awt.Color(255, 255, 255));
        kButton2.setkSelectedColor(new java.awt.Color(255, 255, 255));
        kButton2.setkStartColor(new java.awt.Color(86, 204, 242));
        kButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton2ActionPerformed(evt);
            }
        });

        kButton3.setText("CLEAR");
        kButton3.setkBorderRadius(26);
        kButton3.setkEndColor(new java.awt.Color(44, 62, 80));
        kButton3.setkHoverEndColor(new java.awt.Color(169, 169, 169));
        kButton3.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        kButton3.setkHoverStartColor(new java.awt.Color(211, 211, 211));
        kButton3.setkPressedColor(new java.awt.Color(255, 255, 255));
        kButton3.setkSelectedColor(new java.awt.Color(255, 255, 255));
        kButton3.setkStartColor(new java.awt.Color(189, 195, 199));
        kButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton3ActionPerformed(evt);
            }
        });

        kButton4.setText("DELETE");
        kButton4.setkBorderRadius(26);
        kButton4.setkEndColor(new java.awt.Color(255, 75, 43));
        kButton4.setkHoverEndColor(new java.awt.Color(255, 0, 0));
        kButton4.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        kButton4.setkHoverStartColor(new java.awt.Color(255, 160, 122));
        kButton4.setkPressedColor(new java.awt.Color(255, 255, 255));
        kButton4.setkSelectedColor(new java.awt.Color(255, 255, 255));
        kButton4.setkStartColor(new java.awt.Color(255, 106, 106));
        kButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton4ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel14.setText("Room Type");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel15.setText("Occupancy");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel16.setText("Amount");

        jTextField1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel13))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 921, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(kButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(108, 108, 108)
                                .addComponent(jLabel15))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, 0))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel12))
                    .addComponent(jLabel13))
                .addGap(6, 6, 6)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addGap(6, 6, 6)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jComboBox2)
                    .addComponent(jTextField1))
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel5, "card4");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "UserID", "Username", "Firstname", "Lastname", "Gender", "Password"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable3.setGridColor(new java.awt.Color(0, 0, 0));
        jTable3.setRowHeight(25);
        jTable3.setSelectionBackground(new java.awt.Color(232, 57, 95));
        jTable3.setShowHorizontalLines(true);
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jTextField3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jTextField3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField3FocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 915, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, "card3");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "UserID", "Username", "PaymentMethod", "Amount", "PaymentDate"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable4);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 918, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(142, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );

        jPanel1.add(jPanel6, "card5");

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 960, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void k1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_k1MouseClicked
        // TODO add your handling code here:
          jPanel1.removeAll();
      jPanel1.add(jPanel3);
       jPanel1.repaint();
        jPanel1.revalidate();
       
    }//GEN-LAST:event_k1MouseClicked

    private void k2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_k2MouseClicked
        // TODO add your handling code here:
        jPanel1.removeAll();
      jPanel1.add(jPanel5);
       jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_k2MouseClicked

    private void k3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_k3MouseClicked
        // TODO add your handling code here:
        jPanel1.removeAll();
      jPanel1.add(jPanel4);
       jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_k3MouseClicked

    private void kButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton1ActionPerformed
        // TODO add your handling code here:
       String roomType = jComboBox1.getSelectedItem().toString(); // A/C or NON A/C
    String occupancy = jComboBox2.getSelectedItem().toString(); // Single or Double
    String amount = jTextField1.getText();

    PreparedStatement pat = null;
    ResultSet rs = null;

    try {
        // 1. Check if RoomType already exists
        pat = con.prepareStatement("SELECT roomTypeID FROM RoomTypes WHERE type = ? AND occupancy = ? AND price = ?");
        pat.setString(1, roomType);
        pat.setString(2, occupancy);
        pat.setString(3, amount);
        rs = pat.executeQuery();

        int roomTypeID = -1;

        if (rs.next()) {
            roomTypeID = rs.getInt("roomTypeID");
        } else {
            // 2. Insert new RoomType if not found
            pat = con.prepareStatement("INSERT INTO roomtypes (type, occupancy, price) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            pat.setString(1, roomType);
            pat.setString(2, occupancy);
            pat.setString(3, amount);
            pat.executeUpdate();

            ResultSet generatedKeys = pat.getGeneratedKeys();
            if (generatedKeys.next()) {
                roomTypeID = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to get roomTypeID.");
            }
        }

        // 3. Get the lowest available room number (reuse gaps)
        pat = con.prepareStatement("SELECT roomNumber FROM Rooms ORDER BY roomNumber ASC");
        rs = pat.executeQuery();
        int expectedRoomNumber = 1;
        while (rs.next()) {
            int existingRoomNumber = rs.getInt("roomNumber");
            if (existingRoomNumber != expectedRoomNumber) {
                break; // Found a gap to reuse
            }
            expectedRoomNumber++;
        }

        // 4. Insert new Room with fetched roomTypeID, the calculated room number, and set roomStatus to 'Available'
        pat = con.prepareStatement("INSERT INTO rooms (roomTypeID, roomNumber, roomStatus) VALUES (?, ?, ?)");
        pat.setInt(1, roomTypeID);
        pat.setInt(2, expectedRoomNumber);
        pat.setString(3, "Available");  // Set room status to Available when added
        pat.executeUpdate();

        JOptionPane.showMessageDialog(this, "ROOM ADDED");
        displayLatestRoom();
        Load_room();
        loadDashboardStats();
        jComboBox1.setSelectedIndex(-1);
        jComboBox2.setSelectedIndex(-1);
        jTextField1.setText("");

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        Logger.getLogger(admindashboard.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            if (rs != null) rs.close();
            if (pat != null) pat.close();
        } catch (SQLException e) {
            Logger.getLogger(admindashboard.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    }//GEN-LAST:event_kButton1ActionPerformed

    private void kButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton2ActionPerformed
        // TODO add your handling code here:
    String roomType = jComboBox1.getSelectedItem().toString(); // A/C or NON A/C
    String occupancy = jComboBox2.getSelectedItem().toString(); // Single or Double
    String amount = jTextField1.getText();
    String roomNumberStr = jLabel13.getText(); // Room number (remains unchanged)

    if (roomNumberStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Room number is missing.");
        return;
    }

    int roomNumber = Integer.parseInt(roomNumberStr.trim());

    PreparedStatement pat = null;
    ResultSet rs = null;

    try {
        // Get roomTypeID from Rooms table based on roomNumber
        pat = con.prepareStatement("SELECT roomTypeID FROM Rooms WHERE roomNumber = ?");
        pat.setInt(1, roomNumber);
        rs = pat.executeQuery();

        int roomTypeID = -1;

        if (rs.next()) {
            roomTypeID = rs.getInt("roomTypeID");
        } else {
            JOptionPane.showMessageDialog(this, "Room number not found in database.");
            return;
        }

        // Update the existing RoomTypes record
        pat = con.prepareStatement("UPDATE RoomTypes SET type = ?, occupancy = ?, price = ? WHERE roomTypeID = ?");
        pat.setString(1, roomType);
        pat.setString(2, occupancy);
        pat.setString(3, amount);
        pat.setInt(4, roomTypeID);
        int updatedRoomType = pat.executeUpdate();

        if (updatedRoomType > 0) {
            JOptionPane.showMessageDialog(this, "Room and type updated successfully.");
            displayLatestRoom();
            Load_room();
            jComboBox1.setSelectedIndex(-1);
            jComboBox2.setSelectedIndex(-1);
            jTextField1.setText("");
            jLabel13.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update Room Type.");
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        Logger.getLogger(admindashboard.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            if (rs != null) rs.close();
            if (pat != null) pat.close();
        } catch (SQLException e) {
            Logger.getLogger(admindashboard.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    }//GEN-LAST:event_kButton2ActionPerformed

    private void kButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton3ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_kButton3ActionPerformed

    private void kButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton4ActionPerformed
        // TODO add your handling code here: String roomNumberStr = jLabel13.getText(); // Room number from label or selection
     String roomNumberStr = jLabel13.getText(); // Room number from label or selection
    if (roomNumberStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Room number is missing.");
        return;
    }

    int roomNumber = Integer.parseInt(roomNumberStr.trim());

    PreparedStatement pat = null;

    try {
        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this room?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Step 1: Get the roomTypeID associated with the roomNumber
        String getRoomTypeIDQuery = "SELECT roomTypeID FROM Rooms WHERE roomNumber = ?";
        pat = con.prepareStatement(getRoomTypeIDQuery);
        pat.setInt(1, roomNumber);
        ResultSet rs = pat.executeQuery();

        int roomTypeID = -1;
        if (rs.next()) {
            roomTypeID = rs.getInt("roomTypeID"); // Fetch associated roomTypeID
        }
        rs.close(); // Close the ResultSet

        // Step 2: Delete the room from the Rooms table
        if (roomTypeID != -1) {
            String deleteRoomQuery = "DELETE FROM Rooms WHERE roomNumber = ?";
            pat = con.prepareStatement(deleteRoomQuery);
            pat.setInt(1, roomNumber);
            int deletedRoom = pat.executeUpdate();

            if (deletedRoom > 0) {
                JOptionPane.showMessageDialog(this, "Room deleted successfully.");

                // Step 3: Delete the related room type from roomtypes table
                String deleteRoomTypesQuery = "DELETE FROM roomtypes WHERE roomTypeID = ?";
                pat = con.prepareStatement(deleteRoomTypesQuery);
                pat.setInt(1, roomTypeID);
                int deletedRoomTypes = pat.executeUpdate();

                if (deletedRoomTypes > 0) {
                    JOptionPane.showMessageDialog(this, "Related room types data deleted successfully.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Room number not found.", "Delete Failed", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Room type not found for this room.", "Delete Failed", JOptionPane.WARNING_MESSAGE);
        }

        // Refresh UI after deletion
        displayLatestRoom();  
        Load_room();
        jComboBox1.setSelectedIndex(-1);
        jComboBox2.setSelectedIndex(-1);
        jTextField1.setText("");
     
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        Logger.getLogger(admindashboard.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            if (pat != null) pat.close();
        } catch (SQLException e) {
            Logger.getLogger(admindashboard.class.getName()).log(Level.SEVERE, null, e);
        }
    }
        
    }//GEN-LAST:event_kButton4ActionPerformed

    private void k4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_k4MouseClicked
        // TODO add your handling code here:
        jPanel1.removeAll();
      jPanel1.add(jPanel6);
       jPanel1.repaint();
        jPanel1.revalidate();
        
    }//GEN-LAST:event_k4MouseClicked
private int lastSelectedRow = -1;
    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
      int row = jTable2.getSelectedRow();

    if (row == lastSelectedRow) {
        // User clicked the same row again – clear selection and fields
        jTable2.clearSelection();
       
        jComboBox1.setSelectedIndex(-1);
        jComboBox2.setSelectedIndex(-1);
        jTextField1.setText("");
        jLabel13.setText("");  // Clear the displayed room number when unselected

        lastSelectedRow = -1; // reset tracker

        // Fetch the latest room number (next available) and display it
        displayLatestRoom();

    } else if (row >= 0) {
        // New row selected – populate fields
        String roomNumber = jTable2.getValueAt(row, 0).toString();
        String roomType = jTable2.getValueAt(row, 1).toString();
        String bedType = jTable2.getValueAt(row, 2).toString();
        String price = jTable2.getValueAt(row, 3).toString();

        jLabel13.setText(roomNumber);
        jComboBox1.setSelectedItem(roomType);
        jComboBox2.setSelectedItem(bedType);
        jTextField1.setText(price);
        lastSelectedRow = row; // update tracker
    }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusGained
        // TODO add your handling code here:
        if(jTextField2.getText().equals("Search")){
            jTextField2.setText("");
            jTextField2.setForeground(new Color(0,0,0));
        }
    }//GEN-LAST:event_jTextField2FocusGained

    private void jTextField2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusLost
        // TODO add your handling code here:
        if(jTextField2.getText().equals("")){
            jTextField2.setText("Search");
            jTextField2.setForeground(new Color(153,153,153));
        }
    }//GEN-LAST:event_jTextField2FocusLost

    private void jTextField2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2MouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
         // TODO add your handling code here:
      int row = jTable1.rowAtPoint(evt.getPoint());

    // Double-click to open dialog
    if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
        if (row >= 0) {
            String reserveID =jTable1.getValueAt(row, 0).toString();
            String checkIn = jTable1.getValueAt(row, 3).toString();
            String checkOut = jTable1.getValueAt(row, 4).toString();
            String status = jTable1.getValueAt(row, 5).toString();
            String amount = jTable1.getValueAt(row, 8).toString();

            ReserveDialog dialog = new ReserveDialog(this, true);
            dialog.setDetails(reserveID, checkIn, checkOut, status, amount);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }
    }

    // Right-click to deselect the row (unclick)
    if (evt.getButton() == MouseEvent.BUTTON3) { // Right-click
        jTable1.clearSelection();
    }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        Login ad = new Login();
        ad.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
         int row = jTable3.rowAtPoint(evt.getPoint());

    // Double-click to open dialog
    if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
        if (row >= 0) {
            String userID =jTable3.getValueAt(row, 0).toString();
            String username = jTable3.getValueAt(row, 1).toString();
            String firstname = jTable3.getValueAt(row, 2).toString();
            String lastname = jTable3.getValueAt(row, 3).toString();
            String gender = jTable3.getValueAt(row, 4).toString();
            String password = jTable3.getValueAt(row, 5).toString();
            
        }
    }

    // Right-click to deselect the row (unclick)
    if (evt.getButton() == MouseEvent.BUTTON3) { // Right-click
        jTable3.clearSelection();
    }
    }//GEN-LAST:event_jTable3MouseClicked

    private void jTextField3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField3FocusGained
        // TODO add your handling code here:
         if(jTextField3.getText().equals("Search")){
            jTextField3.setText("");
            jTextField3.setForeground(new Color(0,0,0));
        }
        
    }//GEN-LAST:event_jTextField3FocusGained

    private void jTextField3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField3FocusLost
        // TODO add your handling code here:
        if(jTextField3.getText().equals("")){
            jTextField3.setText("Search");
            jTextField3.setForeground(new Color(153,153,153));
        }
    }//GEN-LAST:event_jTextField3FocusLost


    /**a
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(admindashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(admindashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(admindashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(admindashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new admindashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private com.k33ptoo.components.KGradientPanel k1;
    private com.k33ptoo.components.KGradientPanel k2;
    private com.k33ptoo.components.KGradientPanel k3;
    private com.k33ptoo.components.KGradientPanel k4;
    private com.k33ptoo.components.KButton kButton1;
    private com.k33ptoo.components.KButton kButton2;
    private com.k33ptoo.components.KButton kButton3;
    private com.k33ptoo.components.KButton kButton4;
    private com.k33ptoo.components.KGradientPanel kGradientPanel10;
    private com.k33ptoo.components.KGradientPanel kGradientPanel5;
    private com.k33ptoo.components.KGradientPanel kGradientPanel8;
    private com.k33ptoo.components.KGradientPanel kGradientPanel9;
    private javax.swing.JLabel lblTotalClients;
    private javax.swing.JLabel lblTotalIncome;
    private javax.swing.JLabel lblTotalReservations;
    private javax.swing.JLabel lblTotalRooms;
    // End of variables declaration//GEN-END:variables

    private static class Drag {

        public Drag(JPanel jPanel2) {
        }
    }
}

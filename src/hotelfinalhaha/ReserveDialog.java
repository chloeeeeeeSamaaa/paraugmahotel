/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package hotelfinalhaha;
import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;import com.toedter.calendar.JDateChooser;
import hotelfinalhaha.admindashboard;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 *
 * @author Admin
 */
public class ReserveDialog extends javax.swing.JDialog {
  private JDateChooser jDateChooserCheckIn;  // Declare JDateChooser for check-in
    private JDateChooser jDateChooserCheckOut; // Declare JDateChooser for check-out
 private admindashboard admindashboard;

   
   
    Connection con;
    PreparedStatement pat;
    DefaultTableModel d;
    public ReserveDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        con = connector.Connect();
        loadRoomTypes();
        styleComboBox(roomTypeComboBox);
        jDateChooserCheckIn = new com.toedter.calendar.JDateChooser();
jDateChooserCheckOut = new com.toedter.calendar.JDateChooser();

    }
   private void loadRoomTypes() {
    try {
        // SQL query to fetch roomTypeID from roomtypes
        String query = "SELECT roomTypeID FROM roomtypes";
        pat = con.prepareStatement(query);
        ResultSet rs = pat.executeQuery();

        // Clear any existing items in the ComboBox
        roomTypeComboBox.removeAllItems();

        // Add a default "Select Room Type" option
        roomTypeComboBox.addItem("Select Room Type");

        // Loop through the result set and add each roomTypeID to the ComboBox
        while (rs.next()) {
            // Ensure the column name matches the case in the database (roomTypeID)
            int roomTypeID = rs.getInt("roomTypeID");

            // Convert roomTypeID to String before adding it to the ComboBox
            roomTypeComboBox.addItem(String.valueOf(roomTypeID));  // Converts int to String
        }
    } catch (SQLException ex) {
        Logger.getLogger(admindashboard.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Error loading room types: " + ex.getMessage());
    }
}



public void setDetails(String reserveID, String in, String out, String status, String amount) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // adjust to your date format
    
    try {
        Date checkInDate = sdf.parse(in);
        Date checkOutDate = sdf.parse(out);
        jLabel5.setText(reserveID);
        jTextFieldCheckIn.setDate(checkInDate);
        jTextFieldCheckOut.setDate(checkOutDate);
    } catch (ParseException e) {
        e.printStackTrace();
    }

    jLabel8.setText(status);
    jLabel7.setText(amount);
}
 private void styleComboBox(JComboBox comboBox) {
    // Set background color to match your dashboard (white)
    comboBox.setBackground(Color.WHITE);
    comboBox.setForeground(new Color(0, 0, 0)); // Black text for clarity
    comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Clean, modern font
    comboBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200))); // Light gray border

    comboBox.setUI(new BasicComboBoxUI() {
        @Override
        protected JButton createArrowButton() {
            JButton arrowButton = super.createArrowButton();
            arrowButton.setBackground(Color.WHITE); // White background to match combo box
            arrowButton.setBorder(BorderFactory.createEmptyBorder()); // Remove border around the arrow
            arrowButton.setIcon(UIManager.getIcon("ComboBox.buttonDownIcon")); // Default combo box arrow icon
            return arrowButton;
        }
    });

    comboBox.setFocusable(false); // Optional: removes blue focus ring when clicked
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextFieldCheckIn = new com.toedter.calendar.JDateChooser();
        jTextFieldCheckOut = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        roomTypeComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        kButton1 = new com.k33ptoo.components.KButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel1.setText("CheckIn");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel2.setText("CheckOut");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel3.setText("Status");

        roomTypeComboBox.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel4.setText("Amount");

        kButton1.setText("UPDATE");
        kButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("jLabel5");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel6.setText("ReserveID");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("jLabel7");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setText("jLabel8");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2)
                        .addComponent(jLabel1)
                        .addComponent(jTextFieldCheckIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldCheckOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addComponent(roomTypeComboBox, 0, 213, Short.MAX_VALUE))
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(134, Short.MAX_VALUE)
                .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(131, 131, 131))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldCheckIn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldCheckOut, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(12, 12, 12)
                .addComponent(roomTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addGap(28, 28, 28)
                .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton1ActionPerformed
        // TODO add your handling code here:
     String selectedRoomTypeID = (String) roomTypeComboBox.getSelectedItem();

    if ("Select Room Type".equals(selectedRoomTypeID)) {
        JOptionPane.showMessageDialog(this, "Please select a valid room type.");
        return;
    }

    String reservationID = jLabel5.getText();
    java.util.Date checkInDate = jTextFieldCheckIn.getDate();
    java.util.Date checkOutDate = jTextFieldCheckOut.getDate();

    if (checkInDate == null || checkOutDate == null) {
        JOptionPane.showMessageDialog(this, "Please select valid check-in and check-out dates.");
        return;
    }

    // 🛑 Check-in date must be today or later
    LocalDate today = LocalDate.now();
    LocalDate checkInLocal = checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate checkOutLocal = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    if (checkInLocal.isBefore(today)) {
        JOptionPane.showMessageDialog(this, "Check-in date cannot be before today.");
        return;
    }

    if (!checkOutLocal.isAfter(checkInLocal)) {
        JOptionPane.showMessageDialog(this, "Check-out must be after check-in.");
        return;
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String checkIn = dateFormat.format(checkInDate);
    String checkOut = dateFormat.format(checkOutDate);

    try {
        // Check for conflicts with other reservations
        String checkQuery = "SELECT * FROM reservations " +
            "WHERE roomTypeID = ? AND reservationID != ? AND (" +
            "(? BETWEEN checkIn AND checkOut) OR " +
            "(? BETWEEN checkIn AND checkOut) OR " +
            "(checkIn BETWEEN ? AND ?) OR " +
            "(checkOut BETWEEN ? AND ?))";

        pat = con.prepareStatement(checkQuery);
        pat.setString(1, selectedRoomTypeID);
        pat.setString(2, reservationID);  // exclude current reservation
        pat.setString(3, checkIn);
        pat.setString(4, checkOut);
        pat.setString(5, checkIn);
        pat.setString(6, checkOut);
        pat.setString(7, checkIn);
        pat.setString(8, checkOut);
        ResultSet checkRs = pat.executeQuery();

        if (checkRs.next()) {
            JOptionPane.showMessageDialog(this, "Another reservation already exists with this room type and date range.");
            return;
        }

        // Proceed to get price and update
        String roomDetailsQuery = "SELECT price FROM roomtypes WHERE roomTypeID = ?";
        pat = con.prepareStatement(roomDetailsQuery);
        pat.setString(1, selectedRoomTypeID);
        ResultSet rs = pat.executeQuery();

        if (rs.next()) {
            double price = rs.getDouble("price");
            long diffInMillies = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if (diffInDays == 0) diffInDays = 1;
            double amount = price * diffInDays;

            // Update reservations
            String updateReservationQuery = "UPDATE reservations SET roomTypeID = ?, checkIn = ?, checkOut = ? WHERE reservationID = ?";
            pat = con.prepareStatement(updateReservationQuery);
            pat.setString(1, selectedRoomTypeID);
            pat.setString(2, checkIn);
            pat.setString(3, checkOut);
            pat.setString(4, reservationID);

            int rowsUpdated = pat.executeUpdate();
            if (rowsUpdated > 0) {
                String updateDetailsQuery = "UPDATE reservationdetails SET amount = ? WHERE reservationID = ?";
                pat = con.prepareStatement(updateDetailsQuery);
                pat.setDouble(1, amount);
                pat.setString(2, reservationID);
                pat.executeUpdate();

                if (admindashboard != null) {
                    admindashboard.Load_reservation();
                }

                Window dialog = SwingUtilities.getWindowAncestor(this);
                if (dialog != null) {
                    dialog.dispose();
                }

                JOptionPane.showMessageDialog(this, "Reservation updated successfully!");
                admindashboard ac = new admindashboard();
                ac.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Reservation update failed.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Room type not found.");
        }

    } catch (SQLException ex) {
        Logger.getLogger(admindashboard.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Error updating reservation: " + ex.getMessage());
    }
    }//GEN-LAST:event_kButton1ActionPerformed

    /**
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
            java.util.logging.Logger.getLogger(ReserveDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReserveDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReserveDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReserveDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ReserveDialog dialog = new ReserveDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private com.toedter.calendar.JDateChooser jTextFieldCheckIn;
    private com.toedter.calendar.JDateChooser jTextFieldCheckOut;
    private com.k33ptoo.components.KButton kButton1;
    private javax.swing.JComboBox<String> roomTypeComboBox;
    // End of variables declaration//GEN-END:variables

    void setDetails(String reservationID, String reservationNo, String userID, String checkIn, String checkOut, String status, String amount) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

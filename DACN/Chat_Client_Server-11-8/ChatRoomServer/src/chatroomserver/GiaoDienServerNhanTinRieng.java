
import chatroomserver.ClientManager;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author TrongHao
 */
public class GiaoDienServerNhanTinRieng extends javax.swing.JFrame {
    
    private GiaoDienServer form;
    /**
     * Creates new form GiaoDienServerNhanTinRieng
     */
    public GiaoDienServerNhanTinRieng(GiaoDienServer form) {
        initComponents();
        this.form = form;
        setLocationRelativeTo(this.form);
        setVisible(true);
        
        cbxName.removeAllItems();
        
        for(ClientManager item : Server.listSK)
        {
            cbxName.addItem(item.getName());
        }
        
        JRootPane rootPane = SwingUtilities.getRootPane(btnGui); 
        rootPane.setDefaultButton(btnGui);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbxName = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        taHienThi = new javax.swing.JTextArea();
        tfTinNhan = new javax.swing.JTextField();
        btnGui = new javax.swing.JButton();
        btnSelectName = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLName = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Chat riêng");
        setBackground(new java.awt.Color(153, 255, 204));
        setResizable(false);

        cbxName.setBackground(new java.awt.Color(204, 204, 255));
        cbxName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cbxName.setForeground(new java.awt.Color(102, 51, 255));
        cbxName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        taHienThi.setEditable(false);
        taHienThi.setColumns(20);
        taHienThi.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        taHienThi.setLineWrap(true);
        taHienThi.setRows(5);
        taHienThi.setWrapStyleWord(true);
        jScrollPane1.setViewportView(taHienThi);

        tfTinNhan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        btnGui.setBackground(new java.awt.Color(255, 204, 0));
        btnGui.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGui.setText("Gửi");
        btnGui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuiActionPerformed(evt);
            }
        });

        btnSelectName.setBackground(new java.awt.Color(255, 204, 0));
        btnSelectName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSelectName.setText("Chọn");
        btnSelectName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectNameActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Đang nhắn tin riêng với:");

        jLName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLName.setText("NONE");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 153));
        jLabel2.setText("Chat riêng");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbxName, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSelectName))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(tfTinNhan)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnGui)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLName))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(jLabel2)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfTinNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGui))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void setTextHienThi(String sms)
    {
        taHienThi.setText(taHienThi.getText() + "\n" + sms);
    }
    private void btnGuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuiActionPerformed
        // TODO add your handling code here:
        if(tfTinNhan.getText().equals(""))
        {
            JOptionPane jO = new JOptionPane();
            jO.showMessageDialog(rootPane, "Vui lòng nhập nội dung tin nhắn!");
        }
        else if(jLName.getText().equals("NONE"))
        {
            JOptionPane jO = new JOptionPane();
            jO.showMessageDialog(rootPane, "Chưa chọn người gửi");
        }
        else
        {
            DataOutputStream dos;
            String sms = tfTinNhan.getText();
            for(ClientManager item : Server.listSK)
            {
                if(item.getName().equals(jLName.getText()))
                {
                    try {
                        dos = new DataOutputStream(item.getSocket().getOutputStream());
                        dos.writeUTF("PrivateServer!%Server" + ": " + sms);
                        System.out.println("Co vao day!");
                    } catch (IOException ex) {

                    }

                }

            }
            taHienThi.setText(taHienThi.getText() + "\nServer >> " + jLName.getText() + ":   " + sms);
            tfTinNhan.setText("");

            cbxName.removeAllItems();

            for(ClientManager item : Server.listSK)
            {
                cbxName.addItem(item.getName());
            }
        }
    }//GEN-LAST:event_btnGuiActionPerformed

    private void btnSelectNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectNameActionPerformed
        // TODO add your handling code here:
        jLName.setText(cbxName.getSelectedItem().toString());
    }//GEN-LAST:event_btnSelectNameActionPerformed
    
    public void capNhatCbx()
    {
        cbxName.removeAllItems();

        for(ClientManager item : Server.listSK)
        {
            cbxName.addItem(item.getName());
        }
    }
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
            java.util.logging.Logger.getLogger(GiaoDienServerNhanTinRieng.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GiaoDienServerNhanTinRieng.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GiaoDienServerNhanTinRieng.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GiaoDienServerNhanTinRieng.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new GiaoDienServerNhanTinRieng().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGui;
    private javax.swing.JButton btnSelectName;
    private javax.swing.JComboBox cbxName;
    private javax.swing.JLabel jLName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea taHienThi;
    private javax.swing.JTextField tfTinNhan;
    // End of variables declaration//GEN-END:variables
}
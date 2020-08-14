/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




import chatroom.FileInfo;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author TrongHao
 */
public class GiaoDienClient2 extends javax.swing.JFrame {
    
    private Client client;
    private Socket socket;
    private ReadClient read;
    public  ArrayList<FormChatRieng> chatRiengList = new ArrayList<>();
    private GiaoDienChatRieng formServer;

    
    public GiaoDienClient2(Client client, Socket socket) {
        initComponents();
        this.client = client;
        this.socket = socket;
        jlName.setText(this.client.name);
        setLocationRelativeTo(null);
        JRootPane rootPane = SwingUtilities.getRootPane(btnGui); 
        rootPane.setDefaultButton(btnGui);
        
        formServer = new GiaoDienChatRieng(this, -1);
        formServer.setNameConnect("Server");
        formServer.setVisible(false);
        
        read = new ReadClient(socket, this);
        read.start();
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                
            }
            public void windowClosing(WindowEvent e) {
                DataOutputStream dos;
                try {
                    dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF("exit");
                    dos.flush();
                } catch (IOException ex) {

                }
            }
        });
    }
    
    public void moChatRiengServer()
    {
        formServer.setLocationRelativeTo(this);
        formServer.setVisible(true);
        formServer.openConnectServer();
    }
    
    public void setTextChatRiengServer(String sms)
    {
        this.moChatRiengServer();
        formServer.setTextHienThi(sms);
    }
    
    public String getJLName()
    {
        return jlName.getText();
    }
    public void setTextHienThiCuaGiaoDienChatRieng(String sms, int idForm)
    {
        for(FormChatRieng item : chatRiengList)
        {
            if(item.getId() == idForm)
            {
                item.setTextHienThiCuaGiaoDienChatRieng(sms);
                break;
            }
        }
    }
    
    public Client getClient()
    {
        return client;
    }
    
    public Socket getSocket()
    {
        return socket;
    }
    
    public String getTextArea()
    {
        return taHienThi.getText();
    }
    
    public void setTextArea(String sms)
    {
        taHienThi.setText(taHienThi.getText() + "\n" + sms);
    }
    
    public void setTextAreaUser(String sms)
    {
        taUser.setText(sms);
    }
    
    public String getTextAreaUser()
    {
        return taUser.getText();
    }
    public void serverKick()
    {
        JOptionPane mess = new JOptionPane();
        mess.showMessageDialog(rootPane, "Bạn đã bị server Kick!");
        System.exit(0);
    }
    
    public void changeName(String changeYourName)
    {
        JOptionPane mess = new JOptionPane();
        jlName.setText(changeYourName);
        client.name = changeYourName;
        mess.showMessageDialog(rootPane, "NickName bị trùng, server đã đổi nickname của bạn thành " + changeYourName + "!");     
    }
    public void ThongBaoAccepted(String name, int idForm)
    {   
        for(FormChatRieng item : chatRiengList)
        {
            if(item.getId() == idForm)
            {
                item.openAccept(name);
                break;
            }
        }
        
    }
    
    public void ThongBaoNotAccepted(String name, int idForm)
    {   
        for(FormChatRieng item : chatRiengList)
        {
            if(item.getId() == idForm)
            {
                item.tuChoiKetNoi(name);
                break;
            }
        }
    }
    
    public void ThongBaoGuiFileThanhCong() {
        JOptionPane mess = new JOptionPane();
        mess.showMessageDialog(rootPane, "Đã gửi file thành công!");
    }
    
    public void closeFormChatRieng(int idForm)
    {
        for(FormChatRieng item : chatRiengList)
        {
            if(item.getId() == idForm)
            {
                item.closeForm();
                break;
            }
        }
    }
    
    public void showAccept(String name, int idFormGui)
    {
        JOptionPane optionPane = new JOptionPane(name + " muốn chat riêng với bạn!\n" + "Bạn có muốn chat riêng Không?",JOptionPane.QUESTION_MESSAGE,JOptionPane.YES_NO_OPTION);
       
        JDialog getTopicDialog =  optionPane.createDialog(null, "New Topic");
        getTopicDialog.pack();
        getTopicDialog.setLocationRelativeTo(this);
        getTopicDialog.setVisible(true);        

        Object selectedValue = optionPane.getValue();
        int n = -1;


        if(selectedValue == null)
            n = JOptionPane.CLOSED_OPTION;      
        else
            n = Integer.parseInt(selectedValue.toString());


        if (n == JOptionPane.YES_OPTION){
            
            DataOutputStream dos;
            try {
                dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("AcceptConnect: !%" + idFormGui + "!%" + chatRiengList.size() + "!%" + name);
                dos.flush();
            } catch (IOException ex) {

            }
            FormChatRieng item = new FormChatRieng(this, chatRiengList.size());
            item.openAccept(name);
            chatRiengList.add(item);

        } else if (n == JOptionPane.NO_OPTION){
            
            DataOutputStream dos;
            try {
                dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("AcceptNotConnect: !%" + idFormGui + "!%" + name);
                dos.flush();
                System.out.println(idFormGui);
            } catch (IOException ex) {

            }
        } else if (n == JOptionPane.CLOSED_OPTION){
           DataOutputStream dos;
            try {
                dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("NotAcceptConnect: !%" + idFormGui + "!%" + name);
                dos.flush();
            } catch (IOException ex) {

            }
        }   
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings({"unchecked", "Convert2Lambda"})
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jlName = new javax.swing.JLabel();
        btnGui = new javax.swing.JButton();
        tfTinNhan = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        taHienThi = new javax.swing.JTextArea();
        btnThoat = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        taUser = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        btnNhanTinRieng = new javax.swing.JButton();
        txt_duong_dan = new javax.swing.JTextField();
        btn_chon_file = new javax.swing.JButton();
        btn_gui_file = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat Room");
        setResizable(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chatroom/images.png"))); // NOI18N

        jlName.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlName.setText("Duy");

        btnGui.setBackground(new java.awt.Color(255, 204, 51));
        btnGui.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnGui.setText("Gửi");
        btnGui.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuiActionPerformed(evt);
            }
        });

        tfTinNhan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tfTinNhan.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        taHienThi.setEditable(false);
        taHienThi.setColumns(20);
        taHienThi.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        taHienThi.setRows(5);
        jScrollPane1.setViewportView(taHienThi);

        btnThoat.setBackground(new java.awt.Color(255, 204, 153));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThoat.setForeground(new java.awt.Color(255, 0, 0));
        btnThoat.setText("Thoát");
        btnThoat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        taUser.setEditable(false);
        taUser.setColumns(20);
        taUser.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        taUser.setLineWrap(true);
        taUser.setRows(5);
        taUser.setWrapStyleWord(true);
        jScrollPane3.setViewportView(taUser);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 255));
        jLabel4.setText("Người dùng đang online:");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnNhanTinRieng.setBackground(new java.awt.Color(255, 255, 204));
        btnNhanTinRieng.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNhanTinRieng.setForeground(new java.awt.Color(51, 51, 255));
        btnNhanTinRieng.setText("Chat riêng");
        btnNhanTinRieng.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNhanTinRieng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanTinRiengActionPerformed(evt);
            }
        });

        txt_duong_dan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_duong_danActionPerformed(evt);
            }
        });

        btn_chon_file.setText("Chọn FIle");
        btn_chon_file.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_chon_fileActionPerformed(evt);
            }
        });

        btn_gui_file.setText("Gửi File");
        btn_gui_file.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_gui_fileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlName))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(tfTinNhan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGui, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txt_duong_dan, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_chon_file)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_gui_file))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnNhanTinRieng)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(btnThoat))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jlName)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_chon_file)
                        .addComponent(btn_gui_file)
                        .addComponent(txt_duong_dan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGui)
                    .addComponent(tfTinNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThoat)
                    .addComponent(btnNhanTinRieng))
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuiActionPerformed
        // TODO add your handling code here:
        if(tfTinNhan.getText().equals(""))
        {
            JOptionPane jO = new JOptionPane();
            jO.showMessageDialog(rootPane, "Vui lòng nhập nội dung tin nhắn!");
        }
        else
        {
            taHienThi.setText(taHienThi.getText() + "\nTôi: " + tfTinNhan.getText());
            DataOutputStream dos;
            try
            {
                dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF(client.name + ": " + tfTinNhan.getText());
                    dos.flush();	
            }
            catch(IOException e)
            {
                JOptionPane.showMessageDialog(rootPane, "Kết Nối Đến Server Bị Lỗi");
            }
            tfTinNhan.setText("");
        }
    }//GEN-LAST:event_btnGuiActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
        DataOutputStream dos;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("exit");
            dos.flush();
        } catch (IOException ex) {
           
        }
        System.exit(0);
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnNhanTinRiengActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanTinRiengActionPerformed
        // TODO add your handling code here:
        FormChatRieng item = new FormChatRieng(this, chatRiengList.size());
        chatRiengList.add(item);
    }//GEN-LAST:event_btnNhanTinRiengActionPerformed

    private void btn_chon_fileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_chon_fileActionPerformed
        // TODO add your handling code here:
         JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);
		// int returnValue = jfc.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			txt_duong_dan.setText(selectedFile.getAbsolutePath());
		}
    }//GEN-LAST:event_btn_chon_fileActionPerformed

    private void btn_gui_fileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_gui_fileActionPerformed
        // TODO add your handling code here:
        try{
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("guifile!@#" + txt_duong_dan.getText());
            dos.flush();
        }catch(Exception ex) {
            
        }
    }//GEN-LAST:event_btn_gui_fileActionPerformed

    private void txt_duong_danActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_duong_danActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_duong_danActionPerformed
    
    private FileInfo getFileInfo(String sourceFilePath, String destinationDir) {
        FileInfo fileInfo = null;
        BufferedInputStream bis = null;
        try {
            File sourceFile = new File(sourceFilePath);
            bis = new BufferedInputStream(new FileInputStream(sourceFile));
            fileInfo = new FileInfo();
            byte[] fileBytes = new byte[(int) sourceFile.length()];
            // get file info
            bis.read(fileBytes, 0, fileBytes.length);
            fileInfo.setFilename(sourceFile.getName());
            fileInfo.setDataBytes(fileBytes);
            fileInfo.setDestinationDirectory(destinationDir);
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
        return fileInfo;
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
            java.util.logging.Logger.getLogger(GiaoDienClient2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GiaoDienClient2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GiaoDienClient2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GiaoDienClient2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            //new GiaoDienClient2().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGui;
    private javax.swing.JButton btnNhanTinRieng;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btn_chon_file;
    private javax.swing.JButton btn_gui_file;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel jlName;
    private javax.swing.JTextArea taHienThi;
    private javax.swing.JTextArea taUser;
    private javax.swing.JTextField tfTinNhan;
    private javax.swing.JTextField txt_duong_dan;
    // End of variables declaration//GEN-END:variables
}

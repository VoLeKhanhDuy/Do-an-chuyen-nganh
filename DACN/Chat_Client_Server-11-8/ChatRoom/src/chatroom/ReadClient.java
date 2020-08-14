/**
 * @(#)ReadClient.java
 *
 *
 * @author 
 * @version 1.00 2019/7/12
 */
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

import vn.file.common.FileInfo;

public class ReadClient extends Thread{
    private Socket socket;
    private GiaoDienClient2 form2;
    private JTextArea textAreaLog;
    
    
    public ReadClient(Socket client, GiaoDienClient2 form) 
    {
    	this.socket = client;
        this.form2 = form;
    }
    
   
    public void run()
    {
    	DataInputStream dis = null;
    	try
    	{
    		dis = new DataInputStream(socket.getInputStream());
    		while(true)
    		{
    			String sms = dis.readUTF();
                        String manyName;
                        String changeNickName;
                        if(sms.contains("Server send to client data user: "))
                        {
                            System.out.println("da vao 1");
                            manyName = sms.substring(33);
                            form2.setTextAreaUser(manyName);
                            continue;
                        }
                        
                        if(sms.equals("Server Kick!"))
                        {
                            System.out.println("da vao 2");
                            form2.serverKick();
                            continue;
                        }
                        
                        if(sms.contains("change Name!"))
                        {
                            System.out.println("da vao 3");
                            changeNickName = sms.substring(12);
                            form2.changeName(changeNickName);
                            continue;
                        }
                        
                        if(sms.contains("AcceptConnectToClient:"))
                        {
                            String data[] = sms.split("!%");
                            String nameClientGui = data[2];
                            int idFormGui = Integer.parseInt(data[1]);
                            System.out.println("Name:" + nameClientGui);
                            form2.showAccept(nameClientGui, idFormGui);
                            continue;
                        }
                        
                        if(sms.contains("ClientAccepted:"))
                        {
                            System.out.println("da vao clientAccepted");
                            String data[] = sms.split("!%");
                            int idFormGui = Integer.parseInt(data[1]);
                            String nameCLientXacNhanKetNoi = data[2];
                            form2.ThongBaoAccepted(nameCLientXacNhanKetNoi, idFormGui);
                            continue;
                        }
                        
                        if(sms.contains("ClientNotAccepted: "))
                        {
                            System.out.println("da vao 5");
                            String data[] = sms.split("!%");
                            int idFormGui = Integer.parseInt(data[1]);
                            String nameCLientXacNhanKetNoi = data[2];
                            System.out.println(idFormGui);
                            form2.ThongBaoNotAccepted(nameCLientXacNhanKetNoi, idFormGui);
                            continue;
                        }
                        
                        if(sms.contains("PrivateClient!%"))
                        {
                            String[] data = sms.split("!%");
                            int idForm = Integer.parseInt(data[1]);
                            String tinNhan = data[2];
                            form2.setTextHienThiCuaGiaoDienChatRieng(tinNhan, idForm);
                            continue;
                        }
                        
                        if(sms.contains("PrivateServer!%"))
                        {
                            System.out.println("co vao day");
                            String[] data = sms.split("!%");
                            String tinNhan = data[1];
                            form2.setTextChatRiengServer(tinNhan);
                            continue;
                        }
                        
                        if(sms.contains("closeKetNoi"))
                        {
                            String data[] = sms.split("!%");
                            int idForm = Integer.parseInt(data[1]);
                            form2.closeFormChatRieng(idForm);
                            continue;
                        }
                        
                        System.out.println("da vao 6");
    			form2.setTextArea(sms);
    		}
    	}
    	catch(IOException e)
    	{
    		try
    		{
    			//dis.close();
    			socket.close();
    		}
    		catch(IOException ex)
    		{
    			System.out.println("Ngat ket noi den Server");
    		}
    	}
    }
    
    public void sendFile(String sourceFilePath, String destinationDir) {
  
        ObjectOutputStream oos = null;

 
        try {

 
            // get file info
            FileInfo fileInfo = getFileInfo(sourceFilePath, destinationDir);
 
            // send file
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(fileInfo);
 
            // get confirmation
           
        } catch (IOException ex) {
           
        } finally {
            // close all stream

        }
    }
    
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
        } finally {
            closeStream(bis);
        }
        return fileInfo;
    }
     
     public void closeSocket() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
     public void closeStream(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
     
     public void closeStream(OutputStream outputStream) {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
 
}
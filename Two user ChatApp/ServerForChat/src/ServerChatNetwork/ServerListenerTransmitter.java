/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerChatNetwork;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sachin AKA Scavy
 */
public class ServerListenerTransmitter extends Thread{
    static ServerSocket serverSocket;
    static Socket socket;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    String port;
    IWritableGUI gui;
    Boolean loopControl = true;
    public ServerListenerTransmitter(String port,IWritableGUI gui){
        this.port = port;
        this.gui = gui;
        try {
            serverSocket = new ServerSocket(Integer.parseInt(port));
        } catch (IOException ex) {
            Logger.getLogger(ServerListenerTransmitter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    @Override
    public void run(){
        String message ="";
        try{
            while(true){
                socket = serverSocket.accept();
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                messageRead();
                
                
            }    
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void messageRead(){
        String message = "";
        try{
            
            while(!message.equals("exit")){   
            
                gui.writeMessage(dataInputStream.readUTF());
            }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        //return message;
    }
    public void messageTransmit(String messageToTransmit){
        try {
            dataOutputStream.writeUTF(messageToTransmit);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
   
    // This functions closes the socket which is open
    public void closeThePort(){
       
        try {
            serverSocket.close();
            socket.close();
            //Thread.currentThread().destroy();
        } catch (IOException ex) {
            Logger.getLogger(ServerListenerTransmitter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

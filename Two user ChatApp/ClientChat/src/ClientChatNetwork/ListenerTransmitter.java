/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientChatNetwork;

import GUI.chat_client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sachin Shinde
 */


public class ListenerTransmitter extends Thread{
    
    static Socket socket;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    IWritableGUI gui;
    String host,port;
    String message="";
    
        
    public ListenerTransmitter(String host,String port, IWritableGUI gui) {
        this.host = host;
        this.port = port;
        this.gui = gui;
        
    }
    
    @Override
    public void run(){
        try{
            //socket = new Socket("10.0.0.13",8878);
            socket = new Socket(host,Integer.parseInt(port));
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            
            messageRead();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    // Function to read message from DataInputStream and pass it to Interface method which is implemented by GUI class of JFrame
    
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
   
    public void disconnect(){
       
        try {
            socket.close();
            //Thread.currentThread().destroy();
        } catch (IOException ex) {
            Logger.getLogger(ListenerTransmitter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

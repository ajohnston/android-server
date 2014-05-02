/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fordham.cis.remoteconnect.client;

import edu.fordham.cis.remoteconnect.protocol.RCProtocol;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Andrew
 */
public class UDPClient {
    
    private InetAddress SERVER_ADDRESS;
    private String      MAGIC_PHRASE;
    private String      AUTH_STRING;
    
    public UDPClient() {
        try {
            SERVER_ADDRESS = InetAddress.getByName("localhost");
        } catch (UnknownHostException ex) {
            Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        MAGIC_PHRASE   = "DEBUG";
        try {
            this.authenticate();
            AUTH_STRING = RCProtocol.AUTH_CMD + MAGIC_PHRASE + "\n";
        } catch (AuthenticationFailedException ex) {
            Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public UDPClient(InetAddress ip, String magic) {
        SERVER_ADDRESS = ip;
        MAGIC_PHRASE   = magic;
        try {
            this.authenticate();
            AUTH_STRING = RCProtocol.AUTH_CMD + MAGIC_PHRASE + "\n";
        } catch (AuthenticationFailedException ex) {
            Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
     public void sendMouseCommand(int dir) {
            String direction;
            switch(dir) {
            case 0:
            	direction = RCProtocol.MOUSE_UP;
            	break;
            case 1:
            	direction = RCProtocol.MOUSE_DOWN;
            	break;
            case 2:
            	direction = RCProtocol.MOUSE_LEFT;
            	break;
            default: //If it's #3. Used default instead of case 3 to remove compiler errors
            	direction = RCProtocol.MOUSE_RIGHT;
            	
            }
    		String coordinates = RCProtocol.MOUSE_CMD + direction + "\n" + AUTH_STRING;
            byte[] sendData    = coordinates.getBytes();
            this.sendByteArray(sendData);
    }
    
    public void sendKeyCommand(char key) {
        int keycode = (int) key;
        String cmd = RCProtocol.KEY_CMD + keycode + "\n" + AUTH_STRING;
        byte[] sendData = cmd.getBytes();
        this.sendByteArray(sendData);
    }
    
    public void authenticate() throws AuthenticationFailedException {
        String auth = RCProtocol.AUTH_CMD + MAGIC_PHRASE + "\n";
        byte[] sendData = auth.getBytes();
        this.sendByteArray(sendData);
    }
    
    void sendByteArray(byte[] sendData) {
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            DatagramPacket sendPacket = 
                    new DatagramPacket(sendData,sendData.length, SERVER_ADDRESS, 4444);        
            clientSocket.send(sendPacket);
        //For debugging purposes only. Can remove rest of try statement
        //once we're sure it works
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(UDPClient.class.getName()).log(Level.WARNING, null, ex);
        }
    }    

    

    
}

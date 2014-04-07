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
    }
    
    public void sendMouseCommand(double x, double y) {
            String coordinates = RCProtocol.MOUSE_CMD + x + "," + y + "\n" + AUTH_STRING;
            byte[] sendData    = coordinates.getBytes();
            this.sendByteArray(sendData);
    }
    
    public void sendKeyCommand(int keycode) {
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
        byte[] receiveData = new byte[1024];
        DatagramPacket pkt = 
                new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(pkt);
        System.out.println(new String(pkt.getData()));
        } catch (IOException ex) {
            Logger.getLogger(UDPClient.class.getName()).log(Level.WARNING, null, ex);
        }
    }    

    

    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fordham.cis.remoteconnect.test;

import edu.fordham.cis.remoteconnect.client.AuthenticationFailedException;
import edu.fordham.cis.remoteconnect.client.UDPClient;
import edu.fordham.cis.remoteconnect.server.UDPServer;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrew
 */
public class DriverClass implements Observer, Runnable{
        
    private static InetAddress SERVER = null;
    
    public DriverClass() {
        
    }
    
    
    @Override
    public void run() {
        Thread SERVER_THREAD = new Thread(new UDPServer("DEBUG"));
        
        SERVER_THREAD.start();
        boolean first = true;
        while(SERVER == null) {
            if (first) {
                System.out.println("Waiting for server to get IP...");
                first = false;
            }
        }
        //Now we have the IP Address of the Server
        UDPClient client = new UDPClient(SERVER, "DEBUG");
        try {
            client.authenticate();
        } catch (AuthenticationFailedException ex) {
            Logger.getLogger(DriverClass.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        System.out.println("Successfully authenticated to the Server");
        
        
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof InetAddress) {
            InetAddress server = (InetAddress) arg;
            System.out.println("Received IP Address of " + Arrays.toString(server.getAddress()));
            SERVER = server;
        }
    }


    
}

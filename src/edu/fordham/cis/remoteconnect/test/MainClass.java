/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fordham.cis.remoteconnect.test;

import edu.fordham.cis.remoteconnect.client.UDPClient;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrew
 */
public class MainClass {

    
    public static void main(String[] args) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        UDPClient cli = new UDPClient();
        cli.sendKeyCommand('A'); //Should send an 'A'
        
    }
    
}

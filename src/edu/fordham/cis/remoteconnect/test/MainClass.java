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
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        UDPClient cli = new UDPClient();
        String test = "~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:ZXCVBNM<>?1234567890";
        char[] arr = test.toCharArray();
        for (char c : arr) {
            cli.sendKeyCommand(c);
        }
        for (int i = 0; i < 5000; i++) {
            cli.sendMouseCommand(0);
        }

        
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fordham.cis.remoteconnect.test;

import edu.fordham.cis.remoteconnect.client.UDPClient;

/**
 *
 * @author Andrew
 */
public class TestServer {
    
    public static void main(String args) {
        UDPClient cli = new UDPClient();
        cli.sendKeyCommand('A'); //Should send an 'A'
    }
    
    
}

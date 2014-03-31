/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fordham.cis.remoteconnect.server;

import java.net.InetAddress;

/**
 *
 * @author Andrew
 */
public class ClientNotification {
    
    private InetAddress address;
    private boolean isAuthed = false;
    private int port;
    public ClientNotification(InetAddress addr, boolean auth, int p) {
        address = addr;
        isAuthed = auth;
        port = p;
    }
    
    public boolean isAuthenticated() {
        return isAuthed;
    }
    
    public void setAuthenticated(boolean auth) {
        isAuthed = auth;
    }
    
    public InetAddress getInetAddress() {
        return address;
    }
    
    public int getPort() {
        return port;
    }

    
}



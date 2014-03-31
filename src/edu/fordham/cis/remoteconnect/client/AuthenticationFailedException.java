/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fordham.cis.remoteconnect.client;

/**
 *
 * @author Andrew
 */
public class AuthenticationFailedException extends Exception {

    private static final long serialVersionUID = 1L;

    public AuthenticationFailedException() {
        super();
    }

    public AuthenticationFailedException(String msg) {
        System.out.println("ERROR: Authentication Failed: " + msg);
    }
}

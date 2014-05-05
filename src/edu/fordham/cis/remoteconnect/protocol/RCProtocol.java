/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fordham.cis.remoteconnect.protocol;

/**
 *
 * @author Andrew
 */
public class RCProtocol {
    //State. Used Server and Client Side
    public static final int PRE_AUTH = 1;
    public static final int POST_AUTH = 2;
    public static int STATE = PRE_AUTH;
    //Successful Command Return Strings
    public static final String KEY_SUCCESS   = "KEY_SUCCESS";
    public static final String MOUSE_SUCCESS = "MOUSE SUCCESS";
    public static final String AUTH_SUCCESS  = "AUTH SUCCESS";
    public static final String CLICK_SUCCESS = "CLICK SUCCESS";
    //Valid Commands
    public static final String AUTH_CMD  = "AUTH:";
    public static final String MOUSE_CMD = "MOUSE:";
    public static final String KEY_CMD   = "KEY:";
    //Error Codes
    public static final String AUTH_ERR = "AUTH ERR";
    public static final String COMMAND_NOT_SUPPORTED = "BAD CMD";
    //Mouse Arguments
    public static final String MOUSE_UP    = "UP";
    public static final String MOUSE_DOWN  = "DOWN";
    public static final String MOUSE_LEFT  = "LEFT";
    public static final String MOUSE_RIGHT = "RIGHT"; 
    //Click Commands
    public static final String RCLICK_CMD = "RCLICK:";
    public static final String LCLICK_CMD = "LCLICK:";
    
    public static final String RCLICK_ARG = "ARGCLICK";
    public static final String LCLICK_ARG = "ARGCLICK";
    
    public static final String BACKSPACE_CMD = "BACK:";
    public static final String BACKSPACE_ARG = "ARG";
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fordham.cis.remoteconnect.server;
import edu.fordham.cis.remoteconnect.protocol.RCProtocol;
import edu.fordham.cis.remoteconnect.gui.MainGUI;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Observable;
/**
 *
 * @author Andrew
 */
public class UDPServer extends Observable implements Runnable {
    //Server config information
    public final int PORT = 4444;
    //For future versions, this should be set in a constructor
    //or at least randomized and gettable
    public String AUTH_KEY = "DEBUG";
    
    private DatagramSocket SERVER_SOCKET;
    //Sender information
    private InetAddress SENDER_IP;
    private int SENDER_PORT = -1; //Placeholder
    private Robot robot;
    
    //Packets are in the form of the following:
    //KEY:55 [Key code]
    //AUTH: DEBUG [Auth code]
    //With the exception of the Auth packet which is just the AUTH line
    
    //Default Constructor. Used for debug mode.
    public UDPServer() {
        AUTH_KEY ="DEBUG";
        
        try {
            robot = new Robot();
        }
        catch (Exception e) {
            //Do nothing
        }
    }
    
    //Custom Constructor: Pass an Auth Key
    public UDPServer(String auth) {
        AUTH_KEY = auth;
        try {
            robot = new Robot();
        }
        catch (Exception e) {
            //Do nothing
        }
    }
    
    @Override
    public void run() {
        try {
            SERVER_SOCKET = new DatagramSocket(PORT);
            InetAddress serv_addr = InetAddress.getLocalHost();
            this.setChanged();
            this.notifyObservers(serv_addr);
            byte receiveData[] = new byte[1024];
            while(!MainGUI.STOP_SERVER) {
                DatagramPacket receivePacket = 
                        new DatagramPacket(receiveData, receiveData.length);
                SERVER_SOCKET.receive(receivePacket);
                String datum = new String(receivePacket.getData());
                //Grab Sender details now while it's convenient
                //But only the first time
                if (SENDER_IP == null && SENDER_PORT == -1) {
                    SENDER_IP = receivePacket.getAddress();
                    SENDER_PORT = receivePacket.getPort();
                    ClientNotification cli = 
                            new ClientNotification(SENDER_IP, false, SENDER_PORT);
                    this.setChanged();
                    this.notifyObservers(cli);
                }
                Command[] cmdArr = parseReceivedData(datum);
                if (RCProtocol.STATE == RCProtocol.PRE_AUTH && !cmdArr[0].getCommand().equals(RCProtocol.AUTH_CMD)) {
                    this.sendString(RCProtocol.AUTH_ERR);
                }
                else if (RCProtocol.STATE == RCProtocol.PRE_AUTH && cmdArr[0].getCommand().equals(RCProtocol.AUTH_CMD)) {
                    //If authentication is successfull
                    if (checkAuth(cmdArr[0].getArg())) {
                        ClientNotification nowAuthed = 
                                new ClientNotification(SENDER_IP, true, SENDER_PORT);
                        this.setChanged();
                        this.notifyObservers(nowAuthed);                        
                        RCProtocol.STATE = RCProtocol.POST_AUTH;
                        this.sendString(RCProtocol.AUTH_SUCCESS);
                    }
                    else { //If Auth failed
                        this.sendString(RCProtocol.AUTH_ERR);
                    }
                }
                //At this point we are in POST_AUTH stage
                else if (cmdArr.length > 1 && cmdArr[0].getCommand().equals(RCProtocol.KEY_CMD) && checkAuth(cmdArr[1].getArg())) {
                    this.doKeyCommand(cmdArr[0].getArg()); //Key would be the command, auth is last
                    this.sendString(RCProtocol.KEY_SUCCESS); 
                }
                else if (cmdArr.length > 1 && cmdArr[0].getCommand().equals(RCProtocol.MOUSE_CMD) && checkAuth(cmdArr[1].getArg())) {
                    this.doMouseCommand(cmdArr[0].getArg());
                    this.sendString(RCProtocol.MOUSE_SUCCESS);
                }
                else if (cmdArr.length > 1 && cmdArr[0].getCommand().equals(RCProtocol.RCLICK_CMD) && checkAuth(cmdArr[1].getArg())) {
                    this.doRightClick();
                    this.sendString(RCProtocol.CLICK_SUCCESS);
                }
                else if (cmdArr.length > 1 && cmdArr[0].getCommand().equals(RCProtocol.LCLICK_CMD) && checkAuth(cmdArr[1].getArg())) {
                    this.doLeftClick();
                    this.sendString(RCProtocol.CLICK_SUCCESS);
                }
                else if (!checkAuth(cmdArr[0].getArg())) {
                    this.sendString(RCProtocol.AUTH_ERR);
                }
                else {
                    this.sendString(RCProtocol.COMMAND_NOT_SUPPORTED);
                }   
            }
        } catch (SocketException ex) {
            Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void sleepThread(long sleepTime){
        
        try{
            Thread.sleep(sleepTime);
        }catch(InterruptedException e){
            System.err.println(e.getLocalizedMessage());
        }
    }
    
    //Mouse commands are now the following:
    //MOUSE:UP|DOWN|LEFT|RIGHT, obviously only one
    public void doMouseCommand(String arg) {
        final int AMOUNT = 1;
        String direction = arg.trim();
        Point position = MouseInfo.getPointerInfo().getLocation();
        int xPos = position.x;
        int yPos = position.y;
        switch (direction) {
            case RCProtocol.MOUSE_UP:
                robot.mouseMove(xPos, (yPos - AMOUNT));
                //sleepThread(15);
                break;
            case RCProtocol.MOUSE_DOWN:
                robot.mouseMove(xPos, (yPos + AMOUNT));
                //sleepThread(15);
                break;
            case RCProtocol.MOUSE_LEFT:
                robot.mouseMove((xPos - AMOUNT), yPos);
                //sleepThread(15);
                break;
            case RCProtocol.MOUSE_RIGHT:
                robot.mouseMove((xPos + AMOUNT), yPos);
                //sleepThread(15);
                break;
        }
        
    }
    
    public void doRightClick() {
        robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
    }
    
    public void doLeftClick() {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);  
    }
    
   public void doKeyCommand(String keycode) {
        int keycode_int = Integer.parseInt(keycode);
        int key = getModifiedKeyCode(keycode_int);
        if (isShiftedKey(keycode_int)) {
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(key);
            robot.keyRelease(key);
            robot.keyRelease(KeyEvent.VK_SHIFT);
        }
        else {
            robot.keyPress(key);
            robot.keyRelease(key);            
        }
    }
    
    public boolean isShiftedKey(int keycode) {
        char[] specialShifted = new String("~!@#$%^&*()_+{}|:\"<>?").toCharArray();
        char c = (char) keycode;
        for (char k : specialShifted) {
            if (c == k) {
                return true;
            }
        }
        return Character.isUpperCase(c);
    }
    
    public int getModifiedKeyCode(int keycode) {
        char[] special = (new String("~!@#$%^&*()_-+{}|:<>?\"`[]\\,.")).toCharArray();
        char c = (char) keycode;
        switch(c) {
            case '~':
                return KeyEvent.VK_BACK_QUOTE;
            case '`':
                return KeyEvent.VK_BACK_QUOTE;
            case '!':
                return KeyEvent.VK_1;
            case '@':
                return KeyEvent.VK_2;
            case '#':
                return KeyEvent.VK_3;
            case '$':
                return KeyEvent.VK_4;
            case '%':
                return KeyEvent.VK_5;
            case '^':
                return KeyEvent.VK_6;
            case '&':
                return KeyEvent.VK_7;
            case '*':
                return KeyEvent.VK_8;
            case '(':
                return KeyEvent.VK_9;
            case ')':
                return KeyEvent.VK_0;
            case '_':
                return KeyEvent.VK_MINUS;
            case '-':
                return KeyEvent.VK_MINUS;
            case '+':
                return KeyEvent.VK_EQUALS;
            case '=':
                return KeyEvent.VK_EQUALS;
            case '{':
                return KeyEvent.VK_OPEN_BRACKET;
            case '[':
                return KeyEvent.VK_OPEN_BRACKET;
            case '}':
                return KeyEvent.VK_CLOSE_BRACKET;
            case ']':
                return KeyEvent.VK_CLOSE_BRACKET;
            case '|':
                return KeyEvent.VK_BACK_SLASH;
            case '\\':
                return KeyEvent.VK_BACK_SLASH;
            case ':':
                return KeyEvent.VK_SEMICOLON;
            case ';':
                return KeyEvent.VK_SEMICOLON;
            case '"':
                return KeyEvent.VK_QUOTE;
            case '\'':
                return KeyEvent.VK_QUOTE;
            case '<':
                return KeyEvent.VK_COMMA;
            case ',':
                return KeyEvent.VK_COMMA;
            case '>':
                return KeyEvent.VK_PERIOD;
            case '.':
                return KeyEvent.VK_PERIOD;
            case '?':
                return KeyEvent.VK_SLASH;
            case '/':
                return KeyEvent.VK_SLASH;
            default:
                int k = KeyEvent.getExtendedKeyCodeForChar(keycode);
                return k;
        }              
    }
    
    public boolean checkAuth(String user) {
        user = user.trim();
        if (user.equals(AUTH_KEY)) {
            return true;
        }
        return false;
    }
    
    public void sendString(String msg) throws IOException{
        byte[] sendData = msg.getBytes();
        DatagramPacket sendPkt = 
                new DatagramPacket(sendData, sendData.length, SENDER_IP, SENDER_PORT);
        SERVER_SOCKET.send(sendPkt);
    }
    
    private Command[] parseReceivedData(String data) {
        Command[] resultCommands = new Command[5]; //Let's have a buffer
        data = data.trim(); //This should remove unused space
        System.err.println("Recieved data: " + data);
        String[] cmd_parts = data.split("\n"); //Split on the newline
        int index = 0;
        for (String cmd : cmd_parts) {
            if (cmd.isEmpty() || cmd.length() < 5) {
                continue;
            }
            else {
                String command = this.getCommand(cmd.trim());
                String argument = this.getArgument(cmd.trim());
                resultCommands[index] = new Command(command, argument);
                index++;
            }
        }
        return resultCommands;
    }
    
    private String getCommand(String block) {
        String[] split = block.split(":");
        return split[0] + ":";
    }
    
    private String getArgument(String block) {
        String[] split = block.split(":");
        String tst;
        try {
            tst = split[1];
        }  catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Index out of bounds in arg");
            
        } catch (NullPointerException e) {
            System.out.println("Null Pointer in arg");
        }
        return split[1];
    }
    
    private static class Command {
        
        public String command;
        public String argument;
        
        public Command(String cmd, String arg) {
            command = cmd;
            argument = arg;
        }
        
        public String getCommand() {
            return command;
        }
        
        public String getArg() {
            return argument;
        }
        
    }
}

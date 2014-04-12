/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fordham.cis.remoteconnect.gui;

import edu.fordham.cis.remoteconnect.server.ClientNotification;
import edu.fordham.cis.remoteconnect.server.UDPServer;
import java.net.InetAddress;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrew
 */
public class MainGUI extends javax.swing.JFrame implements Observer{

    private static String phrase;
    private static UDPServer server;
    private static final long serialVersionUID = 1L;
    private static Thread SERVER_THREAD;
    public static volatile boolean STOP_SERVER = false;   
    /**
     * Creates new form MainGUI
     */
    public MainGUI() {
        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblStatus = new javax.swing.JLabel();
        lblIP = new javax.swing.JLabel();
        lblPort = new javax.swing.JLabel();
        lblStatusUpdate = new javax.swing.JLabel();
        lblIPUpdate = new javax.swing.JLabel();
        lblPortUpdate = new javax.swing.JLabel();
        lblMagicPhrase = new javax.swing.JLabel();
        lblMagicPhraseUpdate = new javax.swing.JLabel();
        lblCopyright = new javax.swing.JLabel();
        buttonStopServer = new javax.swing.JToggleButton();
        mainMenuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuOptions = new javax.swing.JMenu();
        menuHelp = new javax.swing.JMenu();
        menuAbout = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lblStatus.setText("Server Status:");

        lblIP.setText("IP Address:");

        lblPort.setText("Port:");

        lblStatusUpdate.setText("Waiting for Connection...");

        lblIPUpdate.setText("No Network Connection");

        lblPortUpdate.setText("4444");

        lblMagicPhrase.setText("Ready to Connect? Here's your Magic Phrase:");

        lblMagicPhraseUpdate.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblMagicPhraseUpdate.setText("11111");

        lblCopyright.setText("(C) Company Name. All Rights Reserved. ");

        buttonStopServer.setText("Stop Server");
        buttonStopServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStopServerActionPerformed(evt);
            }
        });

        menuFile.setText("File");
        mainMenuBar.add(menuFile);

        menuOptions.setText("Options");
        mainMenuBar.add(menuOptions);

        menuHelp.setText("Help");
        mainMenuBar.add(menuHelp);

        menuAbout.setText("About");
        mainMenuBar.add(menuAbout);

        setJMenuBar(mainMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCopyright)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonStopServer))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblIP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblPort))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMagicPhrase)
                            .addComponent(lblPortUpdate)
                            .addComponent(lblStatusUpdate)
                            .addComponent(lblIPUpdate))
                        .addGap(0, 84, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addComponent(lblMagicPhraseUpdate)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatusUpdate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblIP)
                    .addComponent(lblIPUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPort)
                    .addComponent(lblPortUpdate))
                .addGap(87, 87, 87)
                .addComponent(lblMagicPhrase)
                .addGap(4, 4, 4)
                .addComponent(lblMagicPhraseUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCopyright)
                    .addComponent(buttonStopServer))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        lblMagicPhraseUpdate.setText(phrase);

    }//GEN-LAST:event_formWindowOpened

    private void buttonStopServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStopServerActionPerformed
        STOP_SERVER = true;
        System.exit(0);
    }//GEN-LAST:event_buttonStopServerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainGUI m = new MainGUI();
                phrase = getMagicPhrase();
                System.out.println(phrase);
                server = new UDPServer("DEBUG"); //Changed from phrase
                server.addObserver(m);
                m.setVisible(true);
                SERVER_THREAD = new Thread(server);
                SERVER_THREAD.start();
            }
        });
    }
    
    public static String getMagicPhrase() {
        Random rand = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyz"
                + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789";
        char[] retarr = new char[5];
        for (int i = 0; i < 5; ++i) {
            retarr[i] = alphabet.charAt(rand.nextInt(alphabet.length()));
        }
        
        return new String(retarr);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof InetAddress) {
            InetAddress addr = (InetAddress) arg;
            lblIPUpdate.setText(addr.getHostAddress());
        }
        else if (arg instanceof ClientNotification) {
            ClientNotification cli = (ClientNotification) arg;
            if (cli.isAuthenticated()) {
                lblStatusUpdate.setText("Connected to " + 
                        cli.getInetAddress().getHostAddress());
            }
                Logger.getLogger(MainGUI.class.getName()).log(Level.INFO, 
                        "\nReceived Packet from {0}", 
                        cli.getInetAddress().getHostAddress());
                String str = (cli.isAuthenticated())? "Yes" : "No";
                Logger.getLogger(MainGUI.class.getName()).log(
                        Level.INFO, "Is Client Authed? {0}", str);
        }

        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton buttonStopServer;
    private javax.swing.JLabel lblCopyright;
    private javax.swing.JLabel lblIP;
    private javax.swing.JLabel lblIPUpdate;
    private javax.swing.JLabel lblMagicPhrase;
    private javax.swing.JLabel lblMagicPhraseUpdate;
    private javax.swing.JLabel lblPort;
    private javax.swing.JLabel lblPortUpdate;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblStatusUpdate;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JMenu menuAbout;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenu menuOptions;
    // End of variables declaration//GEN-END:variables
}

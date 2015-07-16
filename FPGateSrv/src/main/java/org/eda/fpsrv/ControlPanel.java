/*
 * Copyright (C) 2015 EDA Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.eda.fpsrv;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import org.restlet.ext.crypto.DigestUtils;
import javax.swing.UIManager;        
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Dimitar Angelov
 */
public class ControlPanel extends javax.swing.JFrame {

    private static int logViewCapacity = 300; // lines
    private static int logViewCapacityDelta = 30; // lines
    /**
     * Creates new form ControlPanel
     */
    public ControlPanel() {
//        this.setLayout(new BorderLayout());        
        initComponents();
/*        
        String appVersion = "0.0.0";
        String appBuild = "";
        try {
            Enumeration<URL> resources = getClass().getClassLoader().getResources("META-INF/MANIFEST.MF");
            while (resources.hasMoreElements()) {
                try {
                  Manifest manifest = new Manifest(resources.nextElement().openStream());
                  // check that this is your manifest and do what you need or get the next one
                  Attributes attr = manifest.getMainAttributes();
                  appVersion = attr.getValue("Implementation-Version");
                  if (appVersion == null) appVersion = "N/A";
                  appBuild = attr.getValue("Implementation-Build-TimeStamp");
                  if (appBuild == null) appBuild = "N/A";
                } catch (IOException E) {
                  // handle
//                  JOptionPane.showMessageDialog(this, E.getMessage());  
                }
            }        
        } catch (IOException E) {
          // handle
//          JOptionPane.showMessageDialog(this, E.getMessage());  
        }
*/        
        jlAppVersion.setText("Version: "+FPServer.application.getVersion());
        jlBuildInfo.setText("Build: "+FPServer.application.getBuildNumber());
        FPServer.mainComponent.getLogger().addHandler(new LogHandler(this));
        // Catch System out
        System.setOut(new SysOutInterceptor(System.out, this));
        System.setErr(new SysOutInterceptor(System.err, this));
        System.out.print("Control Panel attached to system out.");
        System.err.print("Control Panel attached to system err.");
        jtaLog.append("Application base path:"+FPServer.application.getAppBasePath()+ "\n");
        hideToSystemTray();
    }

    private void adjustTextAreaToCapacity(javax.swing.JTextArea ta) {
        int lines = ta.getLineCount();
        if (lines > ControlPanel.logViewCapacity+ControlPanel.logViewCapacityDelta) {
            try {
                int lineOffset = ta.getLineEndOffset(lines-ControlPanel.logViewCapacity);
                ta.replaceRange("", 0, lineOffset);
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        }    
    }
    
    private class LogHandler extends Handler {
        private ControlPanel panel;

        public LogHandler() {}

        public LogHandler(ControlPanel panel) {
            this.panel = panel;
        }

        @Override
        public void publish(LogRecord lr) {
            panel.jtaLog.append(lr.getMessage() + "\n");
            panel.adjustTextAreaToCapacity(panel.jtaLog);
            panel.jtaLog.setCaretPosition(panel.jtaLog.getDocument().getLength());
            
            panel.adjustControls();
        }

        @Override
        public void flush() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void close() throws SecurityException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    private class SysOutInterceptor extends PrintStream {
        private final ControlPanel panel;
        
        public SysOutInterceptor(OutputStream out, ControlPanel panel) {
            super(out, true);
            this.panel = panel;
        }

        @Override
        public void print(String s) {
            // TODO: Remove this ugly patch when ormlite will be fixed!
            if (!s.contains("you seem to not be using the Xerial SQLite driver")) {
                panel.jtaSysOut.append(s + "\n");
                panel.adjustTextAreaToCapacity(panel.jtaSysOut);
                panel.jtaSysOut.setCaretPosition(panel.jtaSysOut.getDocument().getLength());
            }    
            super.print(s);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlAppName = new javax.swing.JLabel();
        jlAppVersion = new javax.swing.JLabel();
        jlBuildInfo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jbStart = new javax.swing.JButton();
        jbStop = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaLog = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaSysOut = new javax.swing.JTextArea();
        jpSettings = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtServerPort = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtAdminName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jcbDisableAnonymousPrint = new javax.swing.JCheckBox();
        jSeparator2 = new javax.swing.JSeparator();
        jbSave = new javax.swing.JButton();
        jbCancel = new javax.swing.JButton();
        jtAdminPassword = new javax.swing.JPasswordField();
        jtAdminPassword2 = new javax.swing.JPasswordField();
        jtServerAddr = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jlGoAdmin = new javax.swing.JLabel();
        jcbUseSSL = new javax.swing.JCheckBox();
        jtServerPortSSL = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("EDA FPGate Server");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jlAppName.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jlAppName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo_eda_s.gif")));
        jlAppName.setText("EDA Fiscal Printer Gateway");

        jlAppVersion.setText("Version: 1.0.0");
        jlAppVersion.setToolTipText("");

        jlBuildInfo.setForeground(new java.awt.Color(153, 153, 153));
        jlBuildInfo.setText("build ...");

        jbStart.setText("Start");
        jbStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbStartActionPerformed(evt);
            }
        });

        jbStop.setText("Stop");
        jbStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbStopActionPerformed(evt);
            }
        });

        jTabbedPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTabbedPane1FocusGained(evt);
            }
        });

        jtaLog.setEditable(false);
        jtaLog.setBackground(new java.awt.Color(0, 0, 0));
        jtaLog.setColumns(20);
        jtaLog.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jtaLog.setForeground(new java.awt.Color(204, 204, 204));
        jtaLog.setRows(5);
        jtaLog.setText("FP Gateway");
        jScrollPane1.setViewportView(jtaLog);

        jTabbedPane1.addTab("Access Log", jScrollPane1);

        jtaSysOut.setEditable(false);
        jtaSysOut.setBackground(new java.awt.Color(0, 0, 0));
        jtaSysOut.setColumns(20);
        jtaSysOut.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jtaSysOut.setForeground(new java.awt.Color(204, 204, 204));
        jtaSysOut.setLineWrap(true);
        jtaSysOut.setRows(5);
        jScrollPane2.setViewportView(jtaSysOut);

        jTabbedPane1.addTab("System Out", jScrollPane2);

        jLabel1.setLabelFor(jtServerPort);
        jLabel1.setText("Server Port");

        jtServerPort.setText("8182");
        jtServerPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtServerPortActionPerformed(evt);
            }
        });

        jLabel2.setLabelFor(jtAdminName);
        jLabel2.setText("Remote Admin Name");

        jtAdminName.setText("fpgadmin");
        jtAdminName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtAdminNameActionPerformed(evt);
            }
        });

        jLabel3.setLabelFor(jtAdminPassword);
        jLabel3.setText("Remote Admin Password");

        jLabel4.setLabelFor(jtAdminPassword2);
        jLabel4.setText("Confirm Password");

        jcbDisableAnonymousPrint.setText("Disable anonymous printing");

        jbSave.setText("Save");
        jbSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSaveActionPerformed(evt);
            }
        });

        jbCancel.setText("Cancel");

        jtServerAddr.setText("127.0.0.1");
        jtServerAddr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtServerAddrActionPerformed(evt);
            }
        });

        jLabel5.setLabelFor(jtServerAddr);
        jLabel5.setText("Server Address");
        jLabel5.setToolTipText("Leave empty to bind on all addresses.");

        jlGoAdmin.setForeground(new java.awt.Color(51, 102, 255));
        jlGoAdmin.setText("Go to Admin");
        jlGoAdmin.setToolTipText("Open administration panel");
        jlGoAdmin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlGoAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlGoAdminMouseClicked(evt);
            }
        });

        jcbUseSSL.setText("Use SSL");

        jtServerPortSSL.setText("8183");

        javax.swing.GroupLayout jpSettingsLayout = new javax.swing.GroupLayout(jpSettings);
        jpSettings.setLayout(jpSettingsLayout);
        jpSettingsLayout.setHorizontalGroup(
            jpSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSettingsLayout.createSequentialGroup()
                .addGroup(jpSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpSettingsLayout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(jcbDisableAnonymousPrint)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jpSettingsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(21, 21, 21)
                        .addGroup(jpSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtServerAddr)
                            .addComponent(jtServerPort, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(jtAdminName, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(jtAdminPassword)
                            .addComponent(jtAdminPassword2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbUseSSL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtServerPortSSL, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                        .addComponent(jlGoAdmin)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpSettingsLayout.createSequentialGroup()
                .addGroup(jpSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2)
                    .addGroup(jpSettingsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbCancel)))
                .addGap(6, 6, 6))
        );

        jpSettingsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jtAdminName, jtServerPort});

        jpSettingsLayout.setVerticalGroup(
            jpSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlGoAdmin)
                    .addComponent(jcbUseSSL)
                    .addComponent(jtServerPortSSL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jpSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtServerAddr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtAdminName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtAdminPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jtAdminPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbDisableAnonymousPrint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                .addGroup(jpSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbSave)
                    .addComponent(jbCancel))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Settings", jpSettings);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(419, 419, 419)
                .addComponent(jbStart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbStop)
                .addGap(8, 8, 8))
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlBuildInfo)
                    .addComponent(jlAppVersion)))
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jlAppName))
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jlAppName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlAppVersion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlBuildInfo)
                .addGap(5, 5, 5)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbStart)
                    .addComponent(jbStop))
                .addGap(13, 13, 13))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void adjustControls() {
        jbStart.setEnabled(FPServer.mainComponent.isStopped());
        jbStop.setEnabled(FPServer.mainComponent.isStarted());
        adjustSysTray();
    }
    
    private void startServer() {
        FPServer.application.startServer();
        adjustControls();
    }
    
    private void stopServer() {
        FPServer.application.stopServer();
        adjustControls();
    }
    
    private void jbStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbStartActionPerformed
        startServer();
    }//GEN-LAST:event_jbStartActionPerformed

    private void jbStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbStopActionPerformed
        stopServer();
    }//GEN-LAST:event_jbStopActionPerformed

    private void jtServerPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtServerPortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtServerPortActionPerformed

    private void jtAdminNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtAdminNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtAdminNameActionPerformed

    private void jTabbedPane1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTabbedPane1FocusGained
        Properties prop = FPServer.application.getAppProperties();
        jtServerPort.setText(prop.getProperty("ServerPort"));
        jtServerPortSSL.setText(prop.getProperty("ServerPortSSL"));
        jtServerAddr.setText(prop.getProperty("ServerAddr"));
        jtAdminName.setText(prop.getProperty("AdminUser"));
        jtAdminPassword.setText("");
        jtAdminPassword2.setText("");
        jcbDisableAnonymousPrint.setSelected(prop.getProperty("DisableAnonymous").equals("1"));
        jcbUseSSL.setSelected(prop.getProperty("UseSSL").equals("1"));
    }//GEN-LAST:event_jTabbedPane1FocusGained

    private void jbSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSaveActionPerformed
        Properties prop = FPServer.application.getAppProperties();
        prop.setProperty("ServerPort", jtServerPort.getText());
        prop.setProperty("ServerPortSSL", jtServerPortSSL.getText());
        prop.setProperty("ServerAddr", jtServerAddr.getText());
        prop.setProperty("AdminUser", jtAdminName.getText());
        String pass1 = String.copyValueOf(jtAdminPassword.getPassword());
        String pass2 = String.copyValueOf(jtAdminPassword2.getPassword());
        if (pass1.length() > 0) {
            if (pass1.equals(pass2)) {
                prop.setProperty("AdminPass", DigestUtils.toMd5(pass1));
            } else
                JOptionPane.showMessageDialog(null, "Passwords doesn't match!");
        }
        prop.setProperty("DisableAnonymous", jcbDisableAnonymousPrint.isSelected()?"1":"0");
        prop.setProperty("UseSSL", jcbUseSSL.isSelected()?"1":"0");
        FPServer.application.updateProperties();
    }//GEN-LAST:event_jbSaveActionPerformed

    private void jtServerAddrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtServerAddrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtServerAddrActionPerformed

    private void jlGoAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlGoAdminMouseClicked
        // TODO add your handling code here:
        try {
            Desktop.getDesktop().browse(FPServer.application.getAdminURL());
        } catch (URISyntaxException | IOException ex) {
            //It looks like there's a problem
        }        
    }//GEN-LAST:event_jlGoAdminMouseClicked

    private void exitApplication(boolean requreConfirmation) {
        int confirmed = JOptionPane.YES_OPTION;
        if (requreConfirmation) {
            confirmed = JOptionPane.showConfirmDialog(
                null
                , "Are you sure you want to exit FPGateServer application?"
                , "User Confirmation"
                , JOptionPane.YES_NO_OPTION
            );
        }     
        if (confirmed == JOptionPane.YES_OPTION)
            System.exit(0);
    }
    private void exitApplication() {
        exitApplication(true);
    }
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        exitApplication();
    }//GEN-LAST:event_formWindowClosing

    
    public void notifyChange() {
        adjustControls();
    }
    
    TrayIcon trayIcon;
    SystemTray tray;
    Image imagePrinterOn = null;
    Image imagePrinterOff = null;
    MenuItem menuItemStart = null;
    MenuItem menuItemStop = null;
    
    public void hideToSystemTray(){
/*
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            System.out.println("Unable to set LookAndFeel");
        }
*/        
        
        try {
            imagePrinterOn = ImageIO.read(getClass().getResource("/images/printer-on-512.png"));
            imagePrinterOff = ImageIO.read(getClass().getResource("/images/printer-off-512.png"));
        } catch (IOException ex) {
            Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(SystemTray.isSupported()){
            System.out.println("System tray supported");
            tray=SystemTray.getSystemTray();

//            Image image=Toolkit.getDefaultToolkit().getImage("/media/faisal/DukeImg/Duke256.png");
            PopupMenu popup=new PopupMenu();
            final ActionListener openListener = 
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            setVisible(true);
                            setExtendedState(JFrame.NORMAL);
                        }
                    };
                    
            MenuItem defaultItem = new MenuItem("EDA FPGate Server Control Panel") {{
                addActionListener(openListener);
            }};
            defaultItem.setFont(getFont().deriveFont(Font.BOLD));
            popup.add(defaultItem);
/*
            popup.add(
                new MenuItem("EDA FPGate Server Control Panel") {{
                    setFont(getFont().deriveFont(Font.BOLD));
                    addActionListener(openListener);
                }}
            );
*/        
            popup.addSeparator();
/*            
            popup.add(
                new MenuItem("Open") {{
                    addActionListener(openListener);
                }}
            );
            popup.addSeparator();
*/        
            menuItemStart = new MenuItem("Start") {{
                    addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            startServer();
                        }
                    });
            }};
            popup.add(menuItemStart);
            menuItemStop = new MenuItem("Stop") {{
                    addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            stopServer();
                        }
                    });
            }};
            popup.add(menuItemStop);
            popup.addSeparator();
            popup.add(
                new MenuItem("Exit") {{
                    addActionListener(
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                exitApplication();
                            }
                        }
                    );
                }}
            );
            trayIcon=new TrayIcon(FPServer.application.isStarted()?imagePrinterOn:imagePrinterOff, "EDA FPGate Server", popup);
            trayIcon.addActionListener(openListener);
            trayIcon.setImageAutoSize(true);
        }else{
            System.out.println("system tray not supported");
        }
        addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent e) {
                if(e.getNewState()==ICONIFIED){
                    try {
                        tray.add(trayIcon);
                        setVisible(false);
                        System.out.println("added to SystemTray");
                    } catch (AWTException ex) {
                        System.out.println("unable to add to tray");
                    }
                }
        if(e.getNewState()==7){
                    try{
            tray.add(trayIcon);
            setVisible(false);
            System.out.println("added to SystemTray");
            }catch(AWTException ex){
            System.out.println("unable to add to system tray");
        }
            }
        if(e.getNewState()==MAXIMIZED_BOTH){
                    tray.remove(trayIcon);
                    setVisible(true);
                    System.out.println("Tray icon removed");
                }
                if(e.getNewState()==NORMAL){
                    tray.remove(trayIcon);
                    setVisible(true);
                    System.out.println("Tray icon removed");
                }
            }
        });
//        setIconImage(Toolkit.getDefaultToolkit().getImage("Duke256.png"));
//        setIconImage(FPServer.application.isStarted()?imagePrinterOn:imagePrinterOff);
        adjustSysTray();
        setVisible(true);
//        setSize(300, 200);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void adjustSysTray() {
        trayIcon.setImage(FPServer.application.isStarted()?imagePrinterOn:imagePrinterOff);
        setIconImage(FPServer.application.isStarted()?imagePrinterOn:imagePrinterOff);
        menuItemStart.setEnabled(!FPServer.application.isStarted());
        menuItemStop.setEnabled(FPServer.application.isStarted());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbCancel;
    private javax.swing.JButton jbSave;
    private javax.swing.JButton jbStart;
    private javax.swing.JButton jbStop;
    private javax.swing.JCheckBox jcbDisableAnonymousPrint;
    private javax.swing.JCheckBox jcbUseSSL;
    private javax.swing.JLabel jlAppName;
    private javax.swing.JLabel jlAppVersion;
    private javax.swing.JLabel jlBuildInfo;
    private javax.swing.JLabel jlGoAdmin;
    private javax.swing.JPanel jpSettings;
    private javax.swing.JTextField jtAdminName;
    private javax.swing.JPasswordField jtAdminPassword;
    private javax.swing.JPasswordField jtAdminPassword2;
    private javax.swing.JTextField jtServerAddr;
    private javax.swing.JTextField jtServerPort;
    private javax.swing.JTextField jtServerPortSSL;
    private javax.swing.JTextArea jtaLog;
    private javax.swing.JTextArea jtaSysOut;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LeadPanel.java
 *
 * Created on Mar 23, 2010, 12:18:54 PM
 */

package main.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import main.Client;
import main.daemon.Command;
import main.daemon.Daemon.RESULT;
import main.daemon.CommandListener;
import main.daemon.UnknownCommandException;

/**
 *
 * @author sandro
 */
public class LeadPanel extends javax.swing.JPanel {
    private Client client;
    private DefaultListModel pathCommands = new DefaultListModel();
    private PathExecutor pathExecutor;

    /** Creates new form LeadPanel */
    public LeadPanel() {
        initComponents();
    }

    LeadPanel(Client c) {
        this();
        this.client = c;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loadedFile = new javax.swing.JTextField();
        loadPath = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        commandsList = new javax.swing.JList();
        runpath = new javax.swing.JButton();

        loadedFile.setText("No File Currently Loaded");
        loadedFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loadedFileMouseClicked(evt);
            }
        });
        loadedFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadedFileActionPerformed(evt);
            }
        });

        loadPath.setText("Load Path");
        loadPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadPathActionPerformed(evt);
            }
        });

        commandsList.setModel(pathCommands);
        commandsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        commandsList.setToolTipText("The commands being run");
        commandsList.setFocusable(false);
        commandsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                commandsListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(commandsList);

        runpath.setText("Run Path");
        runpath.setEnabled(false);
        runpath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runpathActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(loadedFile, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadPath))
                    .addComponent(runpath))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(loadedFile, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loadPath, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(runpath)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void loadPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadPathActionPerformed
        new Thread(){
            @Override
            public void run() {
                JFileChooser pathFileChooser = new JFileChooser(ClientFrame.lastDirectory);
                if (JFileChooser.APPROVE_OPTION == pathFileChooser.showOpenDialog(LeadPanel.this)) {
                    File pathFile = pathFileChooser.getSelectedFile();
                    if (pathFile.exists() == false) {
                        JOptionPane.showMessageDialog(LeadPanel.this, "Error loading file", "File: " + pathFile.getAbsolutePath() + " does not exist.", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    ClientFrame.lastDirectory = pathFile.getParentFile();
                    try{
                        BufferedReader pathReader = new BufferedReader(new FileReader(pathFile));
                        String line;
                        while( (line = pathReader.readLine())  != null){
                            try {
                                pathCommands.addElement(Command.parseCommand(line));
                            } catch (UnknownCommandException ex) {
                                JOptionPane.showMessageDialog(LeadPanel.this,
                                                              "Bad Path File", "The path file loaded seems to have a bad command in it, please load another file:\n" + line, JOptionPane.ERROR_MESSAGE);
                                pathCommands.clear();
                                pathReader.close();
                                loadedFile.setText("No File Currently Loaded");
                                break;
                            }
                        }
                        runpath.setEnabled(true);
                        loadedFile.setText(pathFile.getAbsolutePath());
                        pathReader.close();
                    }catch(IOException ioe){
                        ioe.printStackTrace();
                    }
                }
            }
        }.start();
    }//GEN-LAST:event_loadPathActionPerformed

    private void loadedFileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loadedFileMouseClicked
        loadPathActionPerformed(null);
    }//GEN-LAST:event_loadedFileMouseClicked

    private void commandsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_commandsListValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_commandsListValueChanged

    private void loadedFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadedFileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_loadedFileActionPerformed

    private void runpathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runpathActionPerformed
        String runPath = "Run Path";
        String stopPath = "Stop Path";
        if (runpath.getText().equals(runPath)) {
            //start going through the commands
            pathExecutor = new PathExecutor(pathCommands);
            pathExecutor.start();
            runpath.setText(stopPath);
        }else if (runpath.getText().equals(stopPath)){
            pathExecutor.cancel();
            pathCommands.clear();
            runpath.setText(runPath);
        }else{
            new RuntimeException("Invalid text in run button: " + runpath.getText());
        }

    }//GEN-LAST:event_runpathActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList commandsList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton loadPath;
    private javax.swing.JTextField loadedFile;
    private javax.swing.JButton runpath;
    // End of variables declaration//GEN-END:variables

    class PathExecutor extends Thread {

        private DefaultListModel commands;
        Command currentCommand;

        public PathExecutor(DefaultListModel pathCommands) {
            this.commands = pathCommands;
        }

        @Override
        public void run(){
            Object[] obcoms = new Object[commands.size()];
            commands.copyInto(obcoms);
            Command[] coms = new Command[obcoms.length];
            System.arraycopy(obcoms, 0, coms, 0, obcoms.length);
            for (int i = 0; i < coms.length; i++) {
                try {
                    currentCommand = coms[i];
                    commandsList.setSelectedValue(currentCommand, true);
                    RESULT result = client.getDaemon().sendCommandAndWait(currentCommand);
                    if (result != RESULT.SUCCESS) {
                        JOptionPane.showMessageDialog(LeadPanel.this,
                                                      "Command Could not be run",
                                                      "The current command could not be executed",
                                                      JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (InterruptedException ex) {
                    System.err.println("Path was canceled");
                }
            }
            runpath.setText("Run Path");
        }

        public void cancel(){
            interrupt();
        }
    }

}

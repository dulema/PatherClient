/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ClientFrame.java
 *
 * Created on Mar 22, 2010, 4:39:43 PM
 */

package main.ui;

import java.awt.CardLayout;
import java.io.File;
import main.Client;
import main.daemon.Command;
import main.daemon.SensoryListener;
import main.sphinx.SphinxCommandThread;
import main.tts.TextToSpeech;

/**
 *
 * @author Sandro Badame <a href="mailto:s.badame@gmail.com">s.badame&amp;gmail.com</a>
 */
public class ClientFrame extends javax.swing.JFrame{

    public static File lastDirectory = new File("");

    Client client;
    SphinxCommandThread sphinxthread;

    CardLayout layout;
    LearnPanel testPanel;
    LeadPanel leadPanel;

    String learnName = "learn";
    String leadName = "lead";

    public ClientFrame(){
        this(null);
    }

    /** Creates new form ClientFrame */
    public ClientFrame(Client c) {
        this.client = c;

        initComponents();

        testPanel = new LearnPanel(c);
        leadPanel = new LeadPanel(c);

        layout = (CardLayout) modePanel.getLayout();
        modePanel.add(testPanel, learnName);
        modePanel.add(leadPanel,leadName );
        modePanel.setLayout(layout);
        layout.show(modePanel, learnName);

        client.getDaemon().addSensoryListener(new SensoryListener(){

            public void odometerUpdate(int tick) {
                odoLabel.setText("Ticks: " + tick);
            }

            public void headingUpdate(float heading) {
                headingLabel.setText("Heading: " + String.format("%.2f", heading));
            }

        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttongroup = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        infoArea = new javax.swing.JTextArea();
        learnbutton = new javax.swing.JRadioButton();
        leadbutton = new javax.swing.JRadioButton();
        modePanel = new javax.swing.JPanel();
        voicerecognition = new javax.swing.JCheckBox();
        tts = new javax.swing.JCheckBox();
        odoLabel = new javax.swing.JLabel();
        headingLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PatherBot");

        infoArea.setColumns(20);
        infoArea.setEditable(false);
        infoArea.setRows(5);
        jScrollPane1.setViewportView(infoArea);

        buttongroup.add(learnbutton);
        learnbutton.setSelected(true);
        learnbutton.setText("Learn");
        learnbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                learnbuttonActionPerformed(evt);
            }
        });

        buttongroup.add(leadbutton);
        leadbutton.setText("Lead");
        leadbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leadbuttonActionPerformed(evt);
            }
        });

        modePanel.setLayout(new java.awt.CardLayout());

        voicerecognition.setText("Use Voice Recognition");
        voicerecognition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                voicerecognitionActionPerformed(evt);
            }
        });

        tts.setText("Use Vocal Acknowledgement");

        odoLabel.setText("Ticks:");

        headingLabel.setText("Heading: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(modePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(learnbutton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(leadbutton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 405, Short.MAX_VALUE)
                                .addComponent(voicerecognition))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(497, Short.MAX_VALUE)
                        .addComponent(tts))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(odoLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 605, Short.MAX_VALUE)
                        .addComponent(headingLabel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(learnbutton)
                    .addComponent(voicerecognition)
                    .addComponent(leadbutton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tts)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(odoLabel)
                    .addComponent(headingLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void voicerecognitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_voicerecognitionActionPerformed
        final String good = "Use Voice Recognition";
        new Thread(){
            @Override
            public void run(){
                if (voicerecognition.isSelected()) {
                    if (sphinxthread == null) {
                        voicerecognition.setText(good + " Loading...");
                        sphinxthread = new SphinxCommandThread(){
                            @Override
                            public void wordDetected(String word){
                                 ClientFrame.this.sphinxWordDetected(word);
                            }
                        };
                        sphinxthread.start();
                        voicerecognition.setText(good);
                    }else{
                        sphinxthread.resumeListening();
                    }
                }else{
                    if (sphinxthread != null) {
                        sphinxthread.pauseListening();
                    }else{
                        System.err.println("Sphinx thread is null, but we're turning it off!");
                    }
                }
            }
        }.start();
    }//GEN-LAST:event_voicerecognitionActionPerformed

    private void sphinxWordDetected(String word) {
        if (learnbutton.isSelected()) {
            testPanel.sphinxWordDetected(word);
        }
    }


    private void leadbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leadbuttonActionPerformed
        layout.show(modePanel, leadName);
    }//GEN-LAST:event_leadbuttonActionPerformed

    private void learnbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_learnbuttonActionPerformed
        layout.show(modePanel, learnName);
}//GEN-LAST:event_learnbuttonActionPerformed

    public void logInfo(String info){
        infoArea.append(info + "\n" );
        infoArea.setCaretPosition(infoArea.getDocument().getLength());
    }
    
    public void commandSent(final Command c) {
        logInfo(c.toString());
        if (tts.isSelected()) {
            new Thread(){
                @Override
               public void run(){
                  TextToSpeech.speak(c.toString());
               }
            }.start();
        }
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttongroup;
    private javax.swing.JLabel headingLabel;
    private javax.swing.JTextArea infoArea;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton leadbutton;
    private javax.swing.JRadioButton learnbutton;
    private javax.swing.JPanel modePanel;
    private javax.swing.JLabel odoLabel;
    private javax.swing.JCheckBox tts;
    private javax.swing.JCheckBox voicerecognition;
    // End of variables declaration//GEN-END:variables


}

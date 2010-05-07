package main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.ui.ClientFrame;
import main.ui.ConnectionDialog;
import javax.swing.JOptionPane;
import main.daemon.Command;
import main.daemon.Daemon;

/**
 *
 * @author Sandro Badame <a href="mailto:s.badame@gmail.com">s.badame&amp;gmail.com</a>
 */
public class Client {

    private Daemon daemon;
    private ClientFrame frame;

    public Client(){
        try {
            //See if the daemon is already running
            daemon = new Daemon();
        } catch (Exception ex) {
            //Try to start the daemon our selves
            String basedir = Client.class.getResource("").getFile();
            File filebase = new File(new File(basedir).getParentFile().getParentFile(), "daemon");
            System.out.println(filebase);
            try {
                Process p = Runtime.getRuntime().exec(new String[]{"/bin/bash", "daemon-starter.sh"},
                                          new String[]{},
                                          filebase);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex1) {
                }
                daemon = new Daemon();
            } catch (IOException pe){
                pe.printStackTrace();
                ConnectionDialog d;
                do{
                    d = new ConnectionDialog();
                    if (d.showDialog() == false) {
                        System.err.println("User canceled");
                        System.exit(0);
                    }
                }while(d.getPort() == -1 || d.getAddress().isEmpty()); //Keep trying until we get it

                try {
                    daemon = new Daemon(d.getAddress(), d.getPort());
                } catch (Exception ce) {
                    JOptionPane.showMessageDialog(null, "Couldn't connect to daemon");
                    System.exit(0);
                }

            }


            
        }

        frame = new ClientFrame(this);
    }

    public void start(){
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Daemon getDaemon(){
        return daemon;
    }

    public void sendCommand(Command c){
        getDaemon().sendCommand(c);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Client().start();
    }

}

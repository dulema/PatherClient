package main.daemon;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class Daemon {

    /*public enum Command {
       Beacon,Proximity,Keypad,Distance,Move,Turn,Cancel
    }

    public enum Response {
        Busy,Error,Ok,Success
    }*/

    public static final int DEFAULT_PORT = 21567;
    public static final String DEFAULT_HOST = "localhost";

    private ArrayList<CommandListener> listeners = new ArrayList<CommandListener>();

    private Socket socket;
    private PrintWriter socketwriter;

    public Daemon() throws UnknownHostException, IOException{
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public Daemon(String host) throws UnknownHostException, IOException{
        this(host, DEFAULT_PORT);
    }

    public Daemon(int port) throws UnknownHostException, IOException{
        this(DEFAULT_HOST, port);
    }

    public Daemon(String host, int port) throws UnknownHostException, IOException{
        this.socket = new Socket(host, port);
        socketwriter = new PrintWriter(socket.getOutputStream(), true);
    }

    public void sendCommand(Command c){
        socketwriter.println(c.getCommand());
        for (CommandListener commandListener : listeners) {
            commandListener.commandSent(c);
        }
    }

    public void addListener(CommandListener listener){
        listeners.add(listener);
    }
}

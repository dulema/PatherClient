package main.daemon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class Daemon {



    public enum RESULT {BUSY, ERROR, SUCCESS};

    public static final int DEFAULT_PORT = 21567;
    public static final String DEFAULT_HOST = "localhost";

    private Socket socket;
    private PrintWriter socketwriter;
    private BufferedReader socketreader;

    //Values to keep in mind
    private int odometer = 0;
    private float heading = 0;

    //Listeners who want to be updated on the state of affairs
    ArrayList<CommandListener> commandListeners = new ArrayList<CommandListener>();
    ArrayList<SensoryListener> sensoryListeners = new ArrayList<SensoryListener>();

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
        socketreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //Start the thread that reads in the info from the daemon
        new Thread(){
            @Override
            public void run(){
                daemonreader();
            } 
        }.start();
    }

    public void sendCommand(Command c){
        System.out.println("Sending: " + c);
        socketwriter.println(c.getCommand());
    }

    public int getOdometerCount() {
        return odometer;
    }

    public float getHeading() {
        return heading;
    }

    //Make sure call this in a new thread
    private void daemonreader(){
        String in;
        try {
            while ((in = socketreader.readLine()) != null) {
                final String[] splitted = in.split(",");
                if (splitted[0].equals("Odo")) {
                    odometer = new Integer(splitted[1]);
                    new Thread(){
                        @Override
                        public void run(){
                            for (SensoryListener sensoryListener : sensoryListeners) {
                                sensoryListener.odometerUpdate(odometer);
                            }
                        }
                    }.start();
                }else if (splitted[0].equals("Heading")){
                    heading = new Float(splitted[1]);
                    new Thread(){
                        @Override
                        public void run(){
                            for (SensoryListener sensoryListener : sensoryListeners) {
                                sensoryListener.headingUpdate(heading);
                            }
                        }
                    }.start();
                }else if (splitted[0].equals("Busy")){
                    new Thread(){
                        @Override
                        public void run(){
                            for (CommandListener daemonListener : commandListeners) {
                                daemonListener.busyCommand(new Integer(splitted[1]));
                            }
                        }
                    }.start();
                }else if (splitted[0].equals("Success")){
                    new Thread(){
                        @Override
                        public void run(){
                            for (CommandListener daemonListener : commandListeners) {
                                daemonListener.sucessfulCommand(new Integer(splitted[1]));
                            }
                        }
                    }.start();
                }else{
                    System.err.println("Got unknown message: " + in);
                }
            }
            socketreader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void addCommandListener(CommandListener dl){
        commandListeners.add(dl);
    }


    public void addSensoryListener(SensoryListener sensoryListener) {
        sensoryListeners.add(sensoryListener);
    }


    public RESULT sendCommandAndWait(final Command command) throws InterruptedException {
        sendCommand(command);
        CommandWaiter cw = new CommandWaiter(command);
        addCommandListener(cw);
        synchronized (command){
            command.wait();
        }
        return cw.r;
    }

    private class CommandWaiter implements CommandListener{
            public RESULT r = null;
            final private Command c;

            public CommandWaiter(final Command c){
                this.c = c;
            }

            public void busyCommand(int commandID) {
                if (commandID == c.getID()) {
                    r = RESULT.BUSY;
                    synchronized(c){
                        c.notify();
                    }
                }
            }

            public void sucessfulCommand(int commandID) {
                if (commandID == c.getID()) {
                    r = RESULT.SUCCESS;
                    synchronized(c){
                        c.notify();
                    }
                }
            }
    }
}

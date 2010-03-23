package main.daemon;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class Daemon {

    public static final int DEFAULT_PORT = 21567;
    public static final String DEFAULT_HOST = "localhost";

    private Socket socket;

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
    }


}

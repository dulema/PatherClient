package main.daemon;

public class UnknownCommandException extends Exception{

    public UnknownCommandException(String msg) {
        super(msg);
    }

}

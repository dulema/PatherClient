package main.daemon;

public interface CommandListener {
    void busyCommand(int commandID);
    void sucessfulCommand(int commandID);
}
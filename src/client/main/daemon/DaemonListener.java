package main.daemon;

public interface DaemonListener {
    void busyCommand(int commandID);
    void sucessfulCommand(int commandID);
}
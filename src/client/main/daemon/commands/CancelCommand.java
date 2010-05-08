package main.daemon.commands;

import main.daemon.Command;

public class CancelCommand extends Command{

    Command commandToStop = null;

    public CancelCommand(Command c){
        commandToStop = c;
    }

    @Override
    public String getCommand() {
        return "Cancel," + commandToStop.getID();
    }

    public String getStorableString() {
        return getCommand();
    }

}
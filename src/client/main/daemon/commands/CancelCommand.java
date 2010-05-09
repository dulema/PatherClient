package main.daemon.commands;

import main.daemon.Command;

public class CancelCommand extends Command{

    Command commandToStop = null;

    public CancelCommand(Command commandToStop){
        this.commandToStop = commandToStop;
    }

    @Override
    public String getCommand() {
        return "Cancel," + commandToStop.getID();
    }

    @Override
    public String getStorableString() {
        return getCommand();
    }

}
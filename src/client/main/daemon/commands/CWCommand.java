package main.daemon.commands;

import main.daemon.Command;

public class CWCommand extends Command{

    @Override
    public String getCommand() {
        return "CW," + getID();
    }

    @Override
    public String getStorableString() {
        return getCommand();
    }

    @Override
    public String toString(){
        return "Rotate Clockwise until a Stop Command is issued";
    }

}

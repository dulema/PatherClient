package main.daemon.commands;

import main.daemon.Command;

public class CWCommand extends Command{

    @Override
    public String getCommand() {
        return "CW," + getID();
    }

    public String getStorableString() {
        return getCommand();
    }

}

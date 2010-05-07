package main.daemon.commands;

import main.daemon.Command;

public class CCWCommand extends Command{

    @Override
    public String getCommand() {
        return "CCW," + getID();
    }

}

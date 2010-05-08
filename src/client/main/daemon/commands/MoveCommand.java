package main.daemon.commands;

import main.daemon.Command;

public class MoveCommand extends Command{
    int ticks;

    public MoveCommand(int ticks) {
        super();
        this.ticks = ticks;
    }

    @Override
    public String getCommand() {
        return getStorableString()+","+getID();
    }

    @Override
    public String getStorableString() {
        return "Move,"+ticks;
    }

}

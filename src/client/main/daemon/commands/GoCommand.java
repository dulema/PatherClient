package main.daemon.commands;

import main.daemon.Command;

public class GoCommand extends Command{

    @Override
    public String getStorableString(){
        return "GoCommand [Not really storable]";
    }

    @Override
    public String getCommand() {
        return "Go," + getID();
    }

    @Override
    public String toString(){
        return "Go forward until a Stop command is issued";
    }

}

package main.daemon.commands;

import main.daemon.Command;

public class CCWCommand extends Command{

    @Override
    public String getStorableString(){
        return "CCW [Not really storable]";
    }


    @Override
    public String getCommand() {
        return "CCW," + getID();
    }

    @Override
    public String toString(){
        return "Rotate Counter Clock Wise until a Stop command is issued";
    }

}

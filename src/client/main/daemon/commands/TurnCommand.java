package main.daemon.commands;

import main.daemon.Command;

public class TurnCommand extends Command{
    
    //<todo> Update this for the compass
    public static final int EAST = 90;
    public static final int NORTH = 0;
    public static final int SOUTH = 180;
    public static final int WEST = 270;

    private int direction;

    public TurnCommand(int direction){
        this.direction = direction;
    }

    @Override
    public String getCommand() {
        String dir = String.valueOf(direction);
        switch(direction){
            case EAST: dir = "EAST"; break;
            case WEST: dir = "WEST"; break;
            case NORTH: dir = "NORTH"; break;
            case SOUTH: dir = "SOUTH"; break;
        }
        return "Turn: " + dir;
    }
}

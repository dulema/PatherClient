package main.daemon;

import main.daemon.commands.FaceCommand;
import main.daemon.commands.MoveCommand;

public abstract class Command {

    private static int id_count = 1;

    private int id;
    public Command(){
        this.id = id_count;
        id_count++;
    }

    public String getStorableString(){
        return getCommand();
    }

    /**
     * @return string to feed to the UBW32
     */
    public abstract String getCommand();

    @Override
    public String toString(){
        return getCommand();
    }

    public int getID() {
        return id;
    }

    public static Command parseCommand(String commandstring) throws UnknownCommandException{
        String[] split = commandstring.split(",");
        if (split.length != 2) {
            throw new UnknownCommandException(commandstring);
        }
        if (split[0].equals("Move")) {
            return new MoveCommand(Integer.parseInt(split[1]));
        }else if (split[0].equals("Face")){
            return new FaceCommand(new Float(split[1]));
        }else{
            throw new UnknownCommandException(commandstring);
        }
    }
}

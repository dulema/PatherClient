package main.daemon.commands;

import main.daemon.Command;

/**
 *
 * @author sandro
 */
public class FaceCommand extends Command{

    float heading;

    public FaceCommand(float heading){
       this.heading = heading;
    }

    @Override
    public String getCommand() {
        return getStorableString()+","+getID();
    }

    @Override
    public String getStorableString(){
        return "Face,"+String.format("%.2f", heading);
    }

    @Override
    public String toString(){
        return "Rotate to face " + String.format("%.2f", heading) + "° from North";
    }

    public float getHeading() {
        return heading;
    }

}

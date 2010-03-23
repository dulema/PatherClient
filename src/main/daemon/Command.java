package main.daemon;

public abstract class Command {

    /**
     * @return string to feed to the UBW32
     */
    public abstract String getCommand();
    
    @Override
    public String toString(){
        return getCommand();
    }
}

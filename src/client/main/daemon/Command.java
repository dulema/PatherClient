package main.daemon;

public abstract class Command {

    private static int id_count = 1;

    private int id;
    public Command(){
        this.id = id_count;
        id_count++;
    }

    /**
     * @return string to feed to the UBW32
     */
    public abstract String getCommand();
    
    public String getFileCommand(){
        return getCommand();
    }

    @Override
    public String toString(){
        return getCommand();
    }

    public int getID() {
        return id;
    }

}

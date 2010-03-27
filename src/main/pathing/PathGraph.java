package main.pathing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Client;
import main.daemon.Command;

/**
 * Loads a path graph to find out where the connections are and what
 * paths exist.
 */
public class PathGraph {

    private static PathGraph pg;

    public static PathGraph getPathGraph(){
        if (pg == null)
            pg = new PathGraph();
        return pg;
    }

    HashMap<Integer, HashMap<Integer, Integer>> matrix = new HashMap<Integer, HashMap<Integer, Integer>>();
    HashMap<String, Integer> nametoid = new HashMap<String, Integer>();
    HashMap<Integer, String> idtoname = new HashMap<Integer, String>();
    HashMap<Integer, HashMap<Integer, List<Command>>> node2node = new HashMap<Integer, HashMap<Integer, List<Command>>>();

    //IO Stuff
    boolean dirty = false;
    File file;

    private PathGraph(){
       URL url = Client.class.getResource("pathgraph.data");
       file = new File(url.getFile());
       load();
    }

    public void setPath(String start, String end, List<Command> commands) {
        int s = nametoid.get(start);
        int e = nametoid.get(end);

        HashMap<Integer, List<Command>> startingFromS= node2node.get(s);
        if (startingFromS == null) {
            startingFromS = new HashMap<Integer, List<Command>>();
            node2node.put(s, startingFromS);
        }
        startingFromS.put(e, commands);
    }

    public String ID2Name(int id){
       return idtoname.get(id);
    }

    public int Name2ID(String name){
        return nametoid.get(name);
    }

    private void load(){
       if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                if (!line.startsWith("Matrix")){
                   System.err.println("Bad file: " + file + "\nFirst line is: " + line);
                   return;
                }
                while((line = reader.readLine()) != null && !line.startsWith("Matrix")){
                    try{
                        String[] sdata = line.split(" ");
                        final int[] data = new int[sdata.length];
                        for (int i = 0; i < data.length; i++) {
                            data[i] = new Integer(sdata[i]);
                        }
                        //Hack hack, too lazy to make a new map and add crap to it
                        //Lets just abuse OO instead...
                        matrix.put(data[0], new HashMap<Integer, Integer>(){{put(data[1], data[2]);}} );
                    }catch(NumberFormatException nfe){
                        System.err.println("Bad data: line = " + line);
                        matrix.clear();
                        return;
                    }
                }
                reader.close();
            } catch (FileNotFoundException ex) {
                System.err.println("Couldn't find file pathgraph.data file: " + file.getAbsolutePath());
                ex.printStackTrace();
            } catch (IOException ioe){
                System.err.println("Couldn't read file pathgraph.data file: " + file.getAbsolutePath());
                ioe.printStackTrace();
            }
       }

    }

    public void save(){
        PrintWriter writer = null;
        try {
            if (!dirty) {
                return;
            }
            writer = new PrintWriter(file);
            writer.println("Matrix start: [Start Node] [End Node] [Distance]");
            for (Integer start : node2node.keySet()) {
                for (Integer end : node2node.get(start).keySet()) {
                    writer.println(start + " " + end + matrix.get(start).get(end));
                }
            }
            writer.println("Matrix end");
            writer.close();
            dirty = false;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            writer.close();
        }
    }
}

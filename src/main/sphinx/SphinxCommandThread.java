package main.sphinx;

public class SphinxCommandThread extends Thread{

    Sphinx sphinx;
    final Object lock = new Object();
    boolean pause = false;
    boolean run = true;

    public SphinxCommandThread(){
        this(new Sphinx());
    }

    public SphinxCommandThread(Sphinx s){
        this.sphinx = s;
    }

    @Override
    public void run(){
        while(run){
            final String word = sphinx.listen();
            if (pause || word == null || word.isEmpty())
                continue;
            else
                new Thread(){
                    @Override
                    public void run(){
                        wordDetected(word);
                    }
                }.start();
        }
    }

    public void pauseListening(){
        if (pause == false) {
            pause = true;
        }
    }

    public void resumeListening(){
        pause = false;
        synchronized(lock){
            lock.notifyAll();
        }
    }

    public void killThread(){
        run = false;
    }

    protected void wordDetected(String word){
    }
}

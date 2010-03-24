/**
 * 
 */
package main.sphinx;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

/**
 * @author Deniz Ulema
 *
 */
public class Sphinx {

	Recognizer recognizer;
	boolean micworks = true;
	public Sphinx(){
        System.out.println("Do work0.");
        ConfigurationManager cm;
        cm = new ConfigurationManager(Sphinx.class.getResource("helloworld.config.xml"));

        recognizer = (Recognizer) cm.lookup("recognizer");
        recognizer.allocate();

        Microphone microphone = (Microphone) cm.lookup("microphone");
        if (!microphone.startRecording()) {
            recognizer.deallocate();
            micworks = false;
        }
        System.out.println("Do work.");
	}
	
	public String listen(){
		Result result = recognizer.recognize();
		return result.getBestFinalResultNoFiller();
	}
	
	public boolean isMicWorking(){
		return micworks;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

package main.tts;

import java.io.IOException;

public class TextToSpeech {

    public static void speak(String s){
        try {
            String[] command = {"/bin/sh", "-c", "espeak --stdout \""+ s +"\"| aplay"};
			Process p = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}

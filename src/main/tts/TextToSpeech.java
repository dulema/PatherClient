package main.tts;

import java.io.IOException;

public class TextToSpeech {

    public static void speak(String s){
        try {
			Runtime.getRuntime().exec("espeak --stdout \"" + s + "\" | aplay");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

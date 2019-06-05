package bugHunter;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Run {
	
	// Runs an instance of Display
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		Display d = new Display();
		d.createObjects();
		d.gameRun();
	}
}
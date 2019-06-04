package bugHunter;

import java.io.FileNotFoundException;

public class Run {
	
	// Runs an instance of Display
	public static void main(String[] args) throws FileNotFoundException {
		Display d = new Display();
		d.createObjects();
		d.gameRun();
	}
}
/*
 * Andrew Balaschak, Hassan Farooq
 * 2018-2019 APCS P.5 Semester 2 Final
 * 
 * RunGame Class - creates an instance of Display, used to run the program
 */

package bugHunter;

import java.io.FileNotFoundException;

public class RunGame {
	
	// Runs an instance of Display
	public static void main(String[] args) throws FileNotFoundException {
		// Creates an instance of Display
		Display d = new Display(); 
		d.createObjects();
		d.gameRun();
	}
}
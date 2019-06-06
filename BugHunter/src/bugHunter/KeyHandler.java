/*
 * Hassan Farooq, Andrew Balaschak
 * 2018-2019 APCS P.5 Semester 2 Final
 * 
 * KeyHandler Class - sends a Display object info about key presses, extends KeyAdapter
 */

package bugHunter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {

	// Class constants
	private final int LEFT = KeyEvent.VK_LEFT;			// Left key ID
	private final int RIGHT = KeyEvent.VK_RIGHT;		// Right key ID
	private final int SHOOT = KeyEvent.VK_SPACE;		// Shoot key ID
	private final int ESC = KeyEvent.VK_ESCAPE;			// Escape key ID
	private final int ENTER = KeyEvent.VK_ENTER;		// Enter key ID
	
	// Instance variables
	private Display disp;								// Display object that creates the window
	
	// Constructor
	public KeyHandler(Display display) {
		disp = display;
	}
	
	// Runs when a key is pressed
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode(); // Stores key code
		
		// Alters booleans in the Display class
		if(key == LEFT)
			disp.left(true);
		else if(key == RIGHT)
			disp.right(true);
		else if(key == SHOOT)
			disp.shoot(true);
		else if(key == ESC)
			disp.esc();
		else if(key == ENTER)
			disp.enter();
	}
	
	// Runs when a key is released
	public void keyReleased(KeyEvent event) {
		int key = event.getKeyCode(); // Stores key code
		
		// Alters booleans in the Display class
		if(key == LEFT)
			disp.left(false);
		else if(key == RIGHT)
			disp.right(false);
		else if(key == SHOOT)
			disp.shoot(false);
	}
}
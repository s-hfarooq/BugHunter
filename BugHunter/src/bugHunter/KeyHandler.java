package bugHunter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {

	// Class constants
	private final int LEFT = KeyEvent.VK_LEFT;
	private final int RIGHT = KeyEvent.VK_RIGHT;
	private final int SHOOT = KeyEvent.VK_SPACE;
	
	// Instance variables
	private Display disp;
	
	public KeyHandler(Display display) {
		disp = display;
	}
	
	// Runs when a key is pressed
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode();
		
		// Alters booleans in the Display class
		if(key == LEFT)
			disp.left(true);
		else if(key == RIGHT)
			disp.right(true);
		else if(key == SHOOT)
			disp.shoot(true);
	}
	
	// Runs when a key is released
	public void keyReleased(KeyEvent event) {
		int key = event.getKeyCode();
		
		// Alters booleans in the Display class
		if(key == LEFT)
			disp.left(false);
		else if(key == RIGHT)
			disp.right(false);
		else if(key == SHOOT)
			disp.shoot(false);
	}
}
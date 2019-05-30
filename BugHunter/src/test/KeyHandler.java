package test;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {

	private final int LEFT = KeyEvent.VK_LEFT;
	private final int RIGHT = KeyEvent.VK_RIGHT;
	
	private Display disp;
	
	public KeyHandler(Display display) {
		disp = display;
	}
	
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode();
		
		if(key == LEFT)
			disp.left(true);
		else if(key == RIGHT)
			disp.right(true);
	}
	
	public void keyReleased(KeyEvent event) {
		int key = event.getKeyCode();
		
		if(key == LEFT)
			disp.left(false);
		else if(key == RIGHT)
			disp.right(false);
	}
}

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {

	private final int RIGHT = KeyEvent.VK_RIGHT;
	private final int LEFT = KeyEvent.VK_LEFT;
	private final int SHOOT = KeyEvent.VK_SPACE;

	private Display disp;
	
	public KeyHandler(Display display) {
		disp = display;
	}
	
	// Changes Display boolean variable to true for whatever key is currently pressed
	public void keyPressed(KeyEvent event) {
		int keyPress = event.getKeyCode();
		
		if(keyPress == RIGHT) {
			disp.changeRight(true);
		} else if(keyPress == LEFT) {
			disp.changeLeft(true);
		} else if(keyPress == SHOOT) {
			disp.changeShoot(true);
		}
	}
	
	// Changes Display boolean variable to false when key let go
	public void keyReleased(KeyEvent event) {
		int keyPress = event.getKeyCode();
		
		if(keyPress == RIGHT) {
			disp.changeRight(false);
		} else if(keyPress == LEFT) {
			disp.changeLeft(false);
		} else if(keyPress == SHOOT) {
			disp.changeShoot(false);
		}
	}

}

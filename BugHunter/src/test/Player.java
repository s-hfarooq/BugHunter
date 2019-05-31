package test;

public class Player extends Character {
	
	Display disp;
	
	public Player(Display display, CharacterImg image, int locX) {
		super(image, locX, display.getSize().height - 70);
		disp = display;
	}
	
	// Moves the Player image within set boundaries
	public void move(int xAmnt) {
//		if((xAmnt < 0 && getX() < 20) || (xAmnt > 0 && getX() > disp.getSize().width - 70))
//			xAmnt = 0;
//		
//		super.moveX(xAmnt);
		
		super.move();
	}
}
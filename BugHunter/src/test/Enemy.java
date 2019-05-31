package test;

public class Enemy extends Character {
	
	Display disp;
	
	public Enemy(Display display, CharacterImg image, int locX, int locY) {
		super(image, locX, locY);
		disp = display;
	}
	
	// Moves the Enemy image within set boundaries
	public void move(int xAmnt, int yAmnt) {
//		if((xAmnt < 0 && getX() < 20) || (xAmnt > 0 && getX() > disp.getSize().width - 70))
//			xAmnt = 0;
//		if((yAmnt < 0 && getX() < 20) || (yAmnt > 0 && getX() > disp.getSize().height - 70))
//			yAmnt = 0;
//		
//		super.moveX(xAmnt);
//		super.moveY(yAmnt);
		super.move();
	}
}

package test;

public class Bullet extends Character {
	
	private Display disp;

	public Bullet(Display display, CharacterImg image, int locX) {
		super(image, locX, display.getSize().height - 70);
		disp = display;
	}
	
	// Moves the Bullet image within set boundaries
	public void move(int yAmnt) {
//		if((yAmnt < 0 && getX() < 20) || (yAmnt > 0 && getX() > disp.getSize().height - 70))
//			yAmnt = 0;
		
		super.move();
	}
}
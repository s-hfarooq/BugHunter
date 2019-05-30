package test;

public class Player extends Character {
	
	private Display disp;
	
	public Player(Display display, CharacterImg image, int locX) {
		super(image, locX);
		disp = display;
	}
	
	public void move(int amnt) {
		if((amnt < 0 && getX() < 20) || (amnt > 0 && getX() > 380))
			amnt = 0;
		
		super.move(amnt);
	}
}

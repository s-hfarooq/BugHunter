package bugHunter;

public class Enemy extends Character {
	
	Display disp;
	
	public Enemy(Display display, CharacterImg image, int locX, int locY, int velX, int velY) {
		super(display, image, locX, locY, velX, velY);
		disp = display;
	}
	
}

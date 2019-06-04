package bugHunter;

public class Bullet extends Character {
	
	private Display disp;

	// Constructor
	public Bullet(Display display, CharacterImg image, int locX, int locY, int velX, int velY) {
		super(display, image, locX, locY, velX, velY, 1);
		disp = display;
	}
}
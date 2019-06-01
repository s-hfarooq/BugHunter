package bugHunter;

public class Bullet extends Character {
	
	private Display disp;

	// Constructor
	public Bullet(Display display, CharacterImg image, int locX, int velX, int velY) {
		super(image, locX, display.getSize().height - 70, velX, velY, 1);
		disp = display;
	}
}
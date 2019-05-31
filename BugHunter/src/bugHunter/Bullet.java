package bugHunter;

public class Bullet extends Character {
	
	private Display disp;

	public Bullet(Display display, CharacterImg image, int locX, int velX, int velY) {
		super(display, image, locX, display.getSize().height - 70, velX, velY);
		disp = display;
	}
}
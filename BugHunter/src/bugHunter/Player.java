package bugHunter;

public class Player extends Character {
	
	private Display disp;
	
	// Constructor
	public Player(Display display, CharacterImg image, int locX) {
		super(image, locX, display.getSize().height - 70, 0, 0, 3);
		disp = display;
	}
}
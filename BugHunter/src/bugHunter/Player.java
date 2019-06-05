package bugHunter;

public class Player extends Character {
	
	// Constructor
	public Player(Display display, CharacterImg image, int locX) {
		super(display, image, locX, display.getSize().height - 70, 0, 0, 3);
	}
}
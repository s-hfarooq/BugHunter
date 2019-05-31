package bugHunter;

public class Player extends Character {
	
	Display disp;
	
	public Player(Display display, CharacterImg image, int locX) {
		super(image, locX, display.getSize().height - 70, 0, 0, 3);
		disp = display;
	}
}
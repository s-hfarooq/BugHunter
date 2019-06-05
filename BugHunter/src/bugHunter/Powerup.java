package bugHunter;

public class Powerup extends Character {
	
	// Instance variables
	private int pUType; // 1 = health boost, 2 = decrease time between shots
	
	// Constructor
	public Powerup(Display display, CharacterImg image, int locX, int locY, int type) {
		super(display, image, locX, locY, 0, 0, 1);
		
		pUType = type;
		if(pUType < 1)
			pUType = 1;
		else if(pUType > 2)
			pUType = 2;
	}
	
	// Getter methods
	public int getType() {
		return pUType;
	}
}

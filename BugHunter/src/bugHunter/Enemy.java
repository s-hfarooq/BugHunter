package bugHunter;

public class Enemy extends Character {
	
	// Constructor
	public Enemy(CharacterImg image, int locX, int locY, int velX, int velY) {
		super(image, locX, locY, velX, velY, 1);
	}
}
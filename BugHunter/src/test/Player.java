package test;

public class Player extends Character {
	
	public Player(CharacterImg image, int locX) {
		super(image, locX);
	}
	
	// Moves the Player image within set boundaries
	public void move(int amnt) {
		if((amnt < 0 && getX() < 20) || (amnt > 0 && getX() > 380))
			amnt = 0;
		
		super.move(amnt);
	}
}
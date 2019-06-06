/*
 * Andrew Balaschak, Hassan Farooq
 * 2018-2019 APCS P.5 Semester 2 Final
 * 
 * Player Class - creates a player object, extends Character
 */

package bugHunter;

public class Player extends Character {
	
	// Constructor
	public Player(Display display, CharacterImg image, int locX) {
		super(display, image, locX, display.getSize().height - 70, 0, 0, 3);
	}
}
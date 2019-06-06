/*
 * Hassan Farooq, Andrew Balaschak
 * 2018-2019 APCS P.5 Semester 2 Final
 * 
 * PowerUp Class - creates a powerup object, extends Character
 */

package bugHunter;

public class Powerup extends Character {
	
	// Instance variables
	private int pUType; 				// 1 = health boost, 2 = decrease time between shots
	
	// Constructor
	public Powerup(Display display, CharacterImg image, int locX, int locY, int type) {
		super(display, image, locX, locY, 0, 0, 1);
		
		// Make sure that pUType is either 1 or 2
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

/*
 * Hassan Farooq, Andrew Balaschak
 * 2018-2019 APCS P.5 Semester 2 Final
 * 
 * Enemy Class - creates an enemy object, extends Character
 */

package bugHunter;

public class Enemy extends Character {
	
	private int type;			// 1 = 1hp enemy, 2 = 3hp enemy
	
	// Constructor
	public Enemy(Display display, CharacterImg image, int locX, int locY, int velX, int velY, int health, int newType) {
		super(display, image, locX, locY, velX, velY, health);
		
		type = newType;
		if(type < 1)
			type = 1;
		else if(type > 2)
			type = 2;
	}
	
	// Getter methods
	public int getType() {
		return type;
	}
}
/*
 * Hassan Farooq, Andrew Balaschak
 * 2018-2019 APCS P.5 Semester 2 Final
 * 
 * Enemy Class - creates an enemy object, extends Character
 */

package bugHunter;

public class Enemy extends Character {
	
	// Constructor
	public Enemy(Display display, CharacterImg image, int locX, int locY, int velX, int velY) {
		super(display, image, locX, locY, velX, velY, 1);
	}
}
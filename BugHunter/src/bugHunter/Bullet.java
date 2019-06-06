/*
 * Andrew Balaschak, Hassan Farooq
 * 2018-2019 APCS P.5 Semester 2 Final
 * 
 * Bullet Class - creates a bullet object, extends Character
 */

package bugHunter;

public class Bullet extends Character {

	// Constructor
	public Bullet(Display display, CharacterImg image, int locX, int locY, int velX, int velY) {
		super(display, image, locX, locY, velX, velY, 1);
	}
}
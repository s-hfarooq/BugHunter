package bugHunter;

import java.awt.Graphics;

public class Character {
	
	// Class constants
	private final int COLLIDE_LIMIT = 25;	// Range the two images must be within to count as a collision
	
	// Instance variables
	private Display disp;
	private CharacterImg img;
	private int x;
	private int y;
	private int xVelocity;
	private int yVelocity;
	private int health;
	private boolean isDead;
	
	// Constructor
	public Character(Display display, CharacterImg image, int locX, int locY, int velX, int velY, int totalHealth) {
		disp = display;
		img = image;
		x = locX;
		y = locY;
		xVelocity = velX;
		yVelocity = velY;
		health = totalHealth;
		isDead = false;
	}
	
	// Draws the object on the window
	public void draw(Graphics g) {
		img.draw(g, x, y);
	}
	
	// Changes the velocity and limits it to a maximum/minimum of 3/-3
	public void changeVelocity(int x, int y) {
		xVelocity += x;
		if(xVelocity > 3) 
			xVelocity = 3;
		else if(xVelocity < -3)
			xVelocity = -3;
		
		yVelocity += y;
		if(yVelocity > 3)
			yVelocity = 3;
		else if(yVelocity < -3)
			yVelocity = -3;
	}
	
	// Alters the current location to move the character according to velocity
	public void move() {
		x += xVelocity;
		y += yVelocity;
	}
	
	// Flips the X velocity
	public void flipX() {
		xVelocity = -xVelocity;
	}
	
	// Lower the total health by one
	public void lowerHP() {
		health--;
		if(health < 1)
			isDead = true;
	}
	
	// Sets the Character to 0 HP
	public void kill() {
		health = 0;
		isDead = true;
	}
	
	// Changes image of object
	public void changeImg(CharacterImg newImg) {
		img = newImg;
	}
	
	// Returns true if this and other intersect each other, false otherwise
	public boolean collide(Character other) {
		boolean hit = false;
		if(Math.abs(x - other.getX()) < COLLIDE_LIMIT && Math.abs(y - other.getY()) < COLLIDE_LIMIT)
			hit = true;
		return hit;
	}
	
	public void moveToBottom() {
		y = disp.getSize().height - 70;
	}
	
	// Getter methods
	public int getXVelocity() {
		return xVelocity;
	}
	
	public int getYVelocity() {
		return yVelocity;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getHP() {
		return health;
	}
	
	public boolean isDead() {
		return isDead;
	}
}
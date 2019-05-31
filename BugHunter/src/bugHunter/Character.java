package bugHunter;

import java.awt.Graphics;

public abstract class Character {
	
	// Instance variables
	private Display disp;
	private CharacterImg img;
	private int x;
	private int y;
	private int xVelocity;
	private int yVelocity;
	
	public Character(Display display, CharacterImg image, int locX, int locY, int velX, int velY) {
		disp = display;
		img = image;
		x = locX;
		y = locY;
		xVelocity = velX;
		yVelocity = velY;
	}
	
	// Draws the object on the window
	public void draw(Graphics g) {
		img.draw(g, x, y);
	}
	
	public void changeVelocity(int x, int y) {
		xVelocity += x;
		if(xVelocity > 3) xVelocity = 3;
		else if(xVelocity < -3) xVelocity = -3;
		yVelocity += y;
		if(yVelocity > 3) yVelocity = 3;
		else if(yVelocity < -3) yVelocity = -3;
	}
	
	// Alters the current location to move the character according to velocity
	public void move() {
		x += xVelocity;
		y += yVelocity;
//		if(x < 20)
//			x = 20;
//		else if(x > disp.getSize().width - 70)
//			x = disp.getSize().width - 70;
	}
	
	public void flipX() {
		xVelocity = -xVelocity;
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
}
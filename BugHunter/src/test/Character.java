package test;

import java.awt.Graphics;

public class Character {
	
	// Instance variables
	private CharacterImg img;
	private int x;
	private int y;
	private int xVelocity;
	private int yVelocity;
	
	public Character(CharacterImg image, int locX, int locY) {
		img = image;
		x = locX;
		y = locY;
		xVelocity = 0;
		yVelocity = 0;
	}
	
	// Draws the object on the window
	public void draw(Graphics g) {
		img.draw(g, x, y);
	}
	
	public void changeVelocity(int x, int y) {
		xVelocity += x;
		yVelocity += y;
	}
	
	// Alters the current location to move the character according to velocity
	public void move() {
		x += xVelocity;
		y += yVelocity;
	}
	
	// Getter methods
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
package test;

import java.awt.Graphics;

public class Character {
	
	// Instance variables
	private CharacterImg img;
	private int x;
	
	public Character(CharacterImg image, int locX) {
		img = image;
		x = locX;
	}
	
	// Draws the object on the window
	public void draw(Graphics g) {
		img.draw(g, x, 10);
	}
	
	// Alters the current x location to move the character
	public void move(int amnt) {
		x += amnt;
	}
	
	// Getter methods
	public int getX() {
		return x;
	}
}
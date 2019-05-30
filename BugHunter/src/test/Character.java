package test;

import java.awt.Graphics;

public class Character {
	
	private CharacterImg img;
	private int x;
	
	public Character(CharacterImg image, int locX) {
		img = image;
		x = locX;
	}
	
	public void draw(Graphics g) {
		img.draw(g, x, 10);
	}
	
	public void move(int amnt) {
		x += amnt;
	}
	
	public int getX() {
		return x;
	}
}

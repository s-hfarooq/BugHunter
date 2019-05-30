package test;

import java.awt.Graphics;
import java.awt.Image;

public class CharacterImg {
	
	// Instance variables
	private Image img;
	
	public CharacterImg(Image image) {
		img = image;
	}
	
	// Draws the image on the window
	public void draw(Graphics g, int locX, int locY) {
		g.drawImage(img,  locX,  locY,  null);
	}
}
/*
 * Hassan Farooq, Andrew Balaschak
 * 2018-2019 APCS P.5 Semester 2 Final
 * 
 * CharacterImg Class - creates an object that can be drawn onto the window
 */

package bugHunter;

import java.awt.Graphics;
import java.awt.Image;

public class CharacterImg {
	
	// Instance variables
	private Image img;					// Stores the image file
	
	// Constructor
	public CharacterImg(Image image) {
		img = image;
	}
	
	// Draws the image on the window
	public void draw(Graphics g, int locX, int locY) {
		g.drawImage(img,  locX,  locY,  null);
	}
}
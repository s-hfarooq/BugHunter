package test;

import java.awt.Graphics;
import java.awt.Image;

public class CharacterImg {
	
	private Image img;
	
	public CharacterImg(Image image) {
		img = image;
	}
	
	public void draw(Graphics g, int locX, int locY) {
		g.drawImage(img,  locX,  locY,  null);
	}
}

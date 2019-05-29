import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Display extends JPanel {
	
	private Point p1 = new Point(100, 100);
	private boolean drawing;
	
	public Display() {
		this.setPreferredSize(new Dimension(640, 480));
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(new File("src/th.jpg"));
		} catch(IOException e) {
			
		}
		
		g.drawImage(img, p1.x, p1.y, this);
	}
}

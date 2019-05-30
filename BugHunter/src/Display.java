import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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

	private final String NAME = "Bug Hunter v0.01";
	private Point p = new Point(100, 100);
	
	// Runs the program, calls the function to create window
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Display().show();
			}
		});
	}
	
	// Sets basic window preferences
	public Display() {
		setPreferredSize(new Dimension(640, 480));
	}
	
	// Sets other window characteristics
	public void show() {
		JFrame frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.add(new Controls(), BorderLayout.SOUTH);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	// Adds images to window
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("src/th.jpg"));
		} catch(IOException e) {
			
		}
		
		g.drawImage(img,  p.x,  p.y, this);
	}
	
	// Sets up arrow keys to control left/right movement
	private class Controls extends JPanel {
		public Controls() {
			new MButton("left", KeyEvent.VK_LEFT, -10);
			new MButton("right", KeyEvent.VK_RIGHT, 10);
		}
		
		// Commands to control movement of the sprites with arrow keys
		private class MButton extends JButton {
			private KeyStroke key;
			
			public MButton(String name, int type, int changeX) {
				super(name);
				key = KeyStroke.getKeyStroke(type, 0);
				setAction(new AbstractAction(getText()) {
					public void actionPerformed(ActionEvent e) {
						Display.this.p.translate(changeX, 0);
						Display.this.repaint();
					}
				});
				
				Controls.this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(key, key.toString());
				Controls.this.getActionMap().put(key.toString(), new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						MButton.this.doClick();
					}
				});
			}
		}
	}
}
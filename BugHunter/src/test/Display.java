package test;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display extends Canvas {
	
	private final String NAME = "Bug Hunter v0.0.1";
	private final int SCALE = 50;
	
	private boolean left;
	private boolean right;
	
	private Player player;
	private ArrayList<Character> characters;
	
	private BufferStrategy bS;
	private Dimension size;
	
	public Display() {
		left = false;
		right = false;
		characters = new ArrayList<Character>();
		
		JFrame frame = new JFrame(NAME);
		JPanel panel = (JPanel) frame.getContentPane();
		size = new Dimension(16 * SCALE, 9 * SCALE);
		panel.setPreferredSize(size);
		
		setBounds(0, 0, size.width, size.height);
		panel.add(this);
		
		frame.pack();
		frame.setVisible(true);
		
		addKeyListener(new KeyHandler(this));
		requestFocus();
		
		createBufferStrategy(2);
		bS = getBufferStrategy();
		
		start();
	}
	
	public void start() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		CharacterImg img = new CharacterImg(toolkit.getImage("src/test/player.png"));
		player = new Player(this, img, 20);
		characters.add(player);
	}
	
	public void gameRun() {
		while(true) {
			Graphics2D g = (Graphics2D) bS.getDrawGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, size.width, size.height);
			
			player.draw(g);
			g.dispose();
			bS.show();
			
			if(left)
				player.move(-10);
			else if(right)
				player.move(10);
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void left(boolean newLeft) {
		left = newLeft;
	}
	
	public void right(boolean newRight) {
		right = newRight;
	}
}

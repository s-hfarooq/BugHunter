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
	
	// Class constants
	private final String NAME = "Bug Hunter v0.0.1";
	private final int SCALE = 50;
	
	// Instance variables
	private boolean left;
	private boolean right;
	
	private Player player;
	private ArrayList<Character> characters;
	
	private BufferStrategy bS;
	private Dimension size;
	
	// Display constructor - creates the layout and defines characteristics of the window
	public Display() {
		// Sets instance variables
		left = false;
		right = false;
		characters = new ArrayList<Character>();
		
		// Creates window, defines characteristics
		JFrame frame = new JFrame(NAME);
		JPanel panel = (JPanel) frame.getContentPane();
		size = new Dimension(16 * SCALE, 9 * SCALE);
		panel.setPreferredSize(size);
		
		setBounds(0, 0, size.width, size.height);
		panel.add(this);
		
		frame.pack();
		frame.setVisible(true);
		
		// Key press listener
		addKeyListener(new KeyHandler(this));
		requestFocus();
		
		createBufferStrategy(2);
		bS = getBufferStrategy();
		
		start();
	}
	
	// Creates character objects (player and enemies)
	public void start() {
		// Create a player image and object
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		CharacterImg playerImg = new CharacterImg(toolkit.getImage("src/test/player.png"));
		player = new Player(playerImg, 20);
		characters.add(player);
		
		// TODO: create enemies
	}
	
	// While loop for game - updates the display
	public void gameRun() {
		while(true) {
			// Sets up the graphics to be displayed
			Graphics2D g = (Graphics2D) bS.getDrawGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, size.width, size.height);
			
			// Draws player, shows all objects on window
			player.draw(g);
			g.dispose();
			bS.show();
			
			// Moves player if keys are being pressed
			if(left)
				player.move(-10);
			else if(right)
				player.move(10);
			
			// Delay so that things don't move too fast
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Alters left boolean when the left arrow key is pressed or let go
	public void left(boolean newLeft) {
		left = newLeft;
	}
	
	// Alters right boolean when the right arrow key is pressed or let go
	public void right(boolean newRight) {
		right = newRight;
	}
}
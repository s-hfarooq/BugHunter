package bugHunter;

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
	private final String BUG_IMAGE = "src/bugHunter/bug.png";
	private final String BULLET_IMAGE = "src/bugHunter/bullet.png";
	private final String PLAYER_IMAGE = "src/bugHunter/player.png";
	private final int SCALE = 70;
	
	// Instance variables
	private boolean left;
	private boolean right;
	
	private Player player;
	private ArrayList<ArrayList<Enemy>> enemies;
	
	private BufferStrategy bS;
	private Dimension size;
	
	// Display constructor - creates the layout and defines characteristics of the window
	public Display() {
		// Sets instance variables
		left = false;
		right = false;
		enemies = new ArrayList<ArrayList<Enemy>>();
		for(int i = 0; i < 3; i++)
			enemies.add(new ArrayList<Enemy>());
		
		// Creates window, defines characteristics
		JFrame frame = new JFrame(NAME);
		JPanel panel = (JPanel) frame.getContentPane();
		size = new Dimension(16 * SCALE, 9 * SCALE);
		panel.setPreferredSize(size);
		
		setBounds(0, 0, size.width, size.height);
		panel.add(this);
		
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		
		// Key press listener
		addKeyListener(new KeyHandler(this));
		requestFocus();
		
		// Stops program when x button pressed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		createBufferStrategy(2);
		bS = getBufferStrategy();
		
		start();
	}
	
	// Creates character objects (player and enemies)
	public void start() {
		// Create a player image and object
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		CharacterImg playerImg = new CharacterImg(toolkit.getImage(PLAYER_IMAGE));
		CharacterImg enemyImg = new CharacterImg(toolkit.getImage(BUG_IMAGE));
		player = new Player(this, playerImg, 20);
		
		// Creates enemies
		for(int r = 0; r < enemies.size(); r++) {
			for(int c = 0; c < 10; c++) {
				enemies.get(r).add(new Enemy(this, enemyImg, 15 + c * 70, 15 + r * 70, 0, 0));
			}
		}
	}
	
	// While loop for game - updates the display
	public void gameRun() {
		int frames = 0;

		while(1 < 2) {
			// Sets up the graphics to be displayed
			Graphics2D g = (Graphics2D) bS.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, size.width + 50, size.height + 50);
			
			// Draws player, shows all objects on window
			player.draw(g);
			
			// Draw and move enemies
			for(int r = 0; r < enemies.size(); r++) {
				for(int c = 0; c < enemies.get(r).size(); c++) {
					Enemy current = enemies.get(r).get(c);
					
					//start movement
					if(frames == 0) {
						current.changeVelocity(1, 0);
					}
					
					// Moves down
					if(frames % 20 == 0)
						current.changeVelocity(0, 1);
					else if(current.getYVelocity() == 1)
						current.changeVelocity(0, -1);
					
					//moves left and right
					if(frames % 5 == 0) {
						if(current.getX() > size.width - 70) {
							current.flipX();
						}else if(current.getX() < 0) {
							current.flipX();
						}
						
					}
					
					//apply movement
					current.move();
				
					// Draw current
					current.draw(g);
				}
			}
			
			g.dispose();
			bS.show();
			
			// Moves player if keys are being pressed
			if(left && player.getX() > 20)
				player.changeVelocity(-1, 0);
			else if(right && player.getX() < size.width - 70)
				player.changeVelocity(1, 0);
			else if(!left && player.getXVelocity() > 0)
				player.changeVelocity(-1, 0);
			else if(!right && player.getXVelocity() < 0)
				player.changeVelocity(1, 0);
			player.move();
			
			// Delay so that things don't move too fast
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			frames++;
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
	
	public Dimension getSize() {
		return size;
	}
}
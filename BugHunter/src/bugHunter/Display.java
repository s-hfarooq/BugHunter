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
	private final double TIME_BETWEEN_SHOTS = 500;
	private final int SCALE = 70;
	
	// Instance variables
	private boolean left;
	private boolean right;
	private boolean shoot;
	private boolean runGame;
	
	private double delayPerFrame;
	
	private Player player;
	private ArrayList<Bullet> bullets;
	private ArrayList<ArrayList<Enemy>> enemies;
	private BufferStrategy bS;
	
	// Display constructor - creates the layout and defines characteristics of the window
	public Display() {
		// Set instance variables
		left = false;
		right = false;
		shoot = false;
		runGame = true;
		
		delayPerFrame = 5.0;
		
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<ArrayList<Enemy>>();
		
		// Creates window, defines characteristics
		JFrame frame = new JFrame(NAME);
		JPanel panel = (JPanel) frame.getContentPane();
		Dimension size = new Dimension(16 * SCALE, 9 * SCALE);
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
	}
	
	// Creates character objects (player and enemies)
	public void createObjects() {
		// Create a player image and object
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		CharacterImg playerImg = new CharacterImg(toolkit.getImage(PLAYER_IMAGE));
		CharacterImg enemyImg = new CharacterImg(toolkit.getImage(BUG_IMAGE));
		player = new Player(this, playerImg, 20);
		
		// Creates enemies
		for(int r = 0; r < 3; r++) {
			enemies.add(new ArrayList<Enemy>());
			
			for (int c = 0; c < 10; c++)
				enemies.get(r).add(new Enemy(enemyImg, 15 + c * 70, 15 + r * 70, 0, 0));
		}
	}
	
	// Game loop - updates the display
	public void gameRun() {
		// Single CharacterImg object created for all bullet objects
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		CharacterImg bulletImg = new CharacterImg(toolkit.getImage(BULLET_IMAGE));
		
		int frames = 0;
		long firstTime = System.currentTimeMillis();
		
		while(runGame) {
			// Sets up the graphics to be displayed
			Graphics2D g = (Graphics2D) bS.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, getSize().width + 50, getSize().height + 50);
			
			// Draws player
			player.draw(g);
			
			// Draw and move enemies
			for(int r = 0; r < enemies.size(); r++) {
				for(int c = 0; c < enemies.get(r).size(); c++) {
					Enemy current = enemies.get(r).get(c);
					
					// Start movement
					if(frames == 0)
						current.changeVelocity(1, 0);
					
					// Moves down
					if(frames % 20 == 0)
						current.changeVelocity(0, 1);
					else if(current.getYVelocity() == 1)
						current.changeVelocity(0, -1);
					
					// Moves left and right
					if(frames % 5 == 0) {
						if(current.getX() > getSize().width - 70)
							current.flipX();
						else if(current.getX() < 0)
							current.flipX();
					}
					
					// Lower enemy health when hit by bullet
					for(int i = bullets.size() - 1; i > -1; i--) {
						if(current.collide(bullets.get(i))) {
							// Delete bullet and lower HP of current enemy
							bullets.remove(i);
							current.lowerHP();
							
							// Delete enemy if it's dead
							if(current.isDead())
								enemies.get(r).remove(c);
						}
					}
					
					// Apply movement
					current.move();
				
					// Draw current enemy
					current.draw(g);
				}
			}
			
			// Draw and move bullets
			for(int i = bullets.size() - 1; i > -1; i--) {
				Bullet current = bullets.get(i);
				
				if(current.getY() > -50) {
					// Move and draw bullet if still in view
					current.move();
					current.draw(g);
				} else {
					// Remove bullet if it's outside the view of the window
					bullets.remove(i);
				}
			}
			
			// Update view
			g.dispose();
			bS.show();
			
			// Moves player if keys are being pressed
			if(left && player.getX() > 20)
				player.changeVelocity(-1, 0);
			else if(right && player.getX() < getSize().width - 70)
				player.changeVelocity(1, 0);
			else if(!left && player.getXVelocity() > 0)
				player.changeVelocity(-1, 0);
			else if(!right && player.getXVelocity() < 0)
				player.changeVelocity(1, 0);
			player.move();
			
			// Shoot if space pressed and enough time has passed since the previous shot
			long currTime = System.currentTimeMillis();
			if(shoot && currTime - firstTime > TIME_BETWEEN_SHOTS) {
				shootBullet(bulletImg, player, -1);
				shoot = !shoot;
				firstTime = currTime;
			}
			
			// Exit game loop when player dies or exit game loop when all enemies are dead

			if(player.isDead()) {
				runGame = false;
				playerDead();
			} else if(enemies.get(0).size() < 1 && enemies.get(1).size() < 1 && enemies.get(2).size() < 1) {
				runGame = false;
				playerWon();
			}
			
			frames++;
			
			// Delay so that things don't move too fast
			try {
				Thread.sleep((long) delayPerFrame);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Creates a new Bullet object and adds it to the bullets ArrayList
	public void shootBullet(CharacterImg bulletImg, Character creator, int direction) {
		Bullet b = new Bullet(this, bulletImg, creator.getX(), 0, 0);
		b.changeVelocity(0, direction);
		bullets.add(b);
	}
	
	// Runs once the player dies
	public void playerDead() {
		System.out.println("END GAME");
	}
	
	// Runs if all enemies are dead and player is still alive
	public void playerWon() {
		System.out.println("All enemies dead");
	}
	
	// Alters left boolean when the left arrow key is pressed or let go
	public void left(boolean newLeft) {
		left = newLeft;
	}
	
	// Alters right boolean when the right arrow key is pressed or let go
	public void right(boolean newRight) {
		right = newRight;
	}
	
	// Alters shoot boolean when the space bar is pressed or let go
	public void shoot(boolean newShoot) {
		shoot = newShoot;
	}
}
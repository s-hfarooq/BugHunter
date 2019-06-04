package bugHunter;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Display extends Canvas {
	
	// Class constants
	private final String NAME = "Bug Hunter v0.0.1";					// Name of program
	private final String BUG_IMAGE = "src/bugHunter/Bug.png";			// File location for bug image
	private final String BULLET_IMAGE = "src/bugHunter/Bullet.png";		// File location for bullet image
	private final String PLAYER_IMAGE = "src/bugHunter/Ship.png";		// File location for player image
	private final String PLAYER_RIGHT = "src/bugHunter/ShipR.png";		// File location for the player image leaning right
	private final String PLAYER_LEFT = "src/bugHunter/ShipL.png";		// File location for the player image leaning left
	private final String ICON_IMAGE = "src/bugHunter/Ship.png";			// File location for icon image
	private final String SCORE_FILE = "src/bugHunter/HighScores.txt";	// File location for high scores
	private final double TIME_BETWEEN_SHOTS = 1200;						// Delay between shooting bullets by the player (in ms)
	private final int SCALE = 70;										// Scale factor for window
	
	// Instance variables
	private boolean left;
	private boolean right;
	private boolean shoot;
	private boolean runGame;
	private long currentScore;
	
	private double delayPerFrame;
	
	private Player player;
	private ArrayList<Bullet> playerBullets;
	private ArrayList<Bullet> enemyBullets;
	private ArrayList<ArrayList<Enemy>> enemies;
	private ArrayList<Score> highScores;
	private BufferStrategy bS;
	
	// Display constructor - creates the layout and defines characteristics of the window
	public Display() {
		// Set instance variables
		left = false;
		right = false;
		shoot = false;
		runGame = true;
		
		currentScore = 0;
		delayPerFrame = 5.0;
		
		playerBullets = new ArrayList<Bullet>();
		enemyBullets = new ArrayList<Bullet>();
		enemies = new ArrayList<ArrayList<Enemy>>();
		highScores = new ArrayList<Score>();
		
		// Creates window, defines characteristics
		JFrame frame = new JFrame(NAME);
		JPanel panel = (JPanel) frame.getContentPane();
		Dimension size = new Dimension(16 * SCALE, 9 * SCALE);
		panel.setPreferredSize(size);
		
		setBounds(0, 0, size.width, size.height);
		panel.add(this);
		
		frame.pack();
		frame.setVisible(true);
		
		// Key press listener
		addKeyListener(new KeyHandler(this));
		requestFocus();
		
		// Stops program when x button pressed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set icon image
		frame.setIconImage(new ImageIcon(ICON_IMAGE).getImage());
		
		createBufferStrategy(4);
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
				enemies.get(r).add(new Enemy(this, enemyImg, (70 * c) + 15, (70 * r) + 15, 0, 0));
		}
	}
	
	// Game loop - updates the display and moves everything
	public void gameRun() throws FileNotFoundException {
		// Single CharacterImg object created for all bullet objects
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		CharacterImg bulletImg = new CharacterImg(toolkit.getImage(BULLET_IMAGE));
		CharacterImg playerRight = new CharacterImg(toolkit.getImage(PLAYER_RIGHT));
		CharacterImg playerLeft = new CharacterImg(toolkit.getImage(PLAYER_LEFT));
		CharacterImg playerCenter = new CharacterImg(toolkit.getImage(PLAYER_IMAGE));
		
		// Create list of high scores
		File scores = new File(SCORE_FILE);
		Scanner fileScan = new Scanner(scores);
		
		while(fileScan.hasNext())
			highScores.add(new Score(fileScan.next(), fileScan.nextLong()));
		
		fileScan.close();
		
		int frames = 0;
		long firstTime = System.currentTimeMillis();
		
		while(runGame) {
			// Sets up the graphics to be displayed
			Graphics2D g = (Graphics2D) bS.getDrawGraphics();
			
			g.setColor(Color.black);
			g.fillRect(0, 0, getSize().width + 50, getSize().height + 50);
			
			// Draws player
			player.draw(g);
			
			// Display health and high score
			g.setColor(Color.white);
			String lives = "Lives: " + player.getHP();
			String score = "Score: " + currentScore;
			g.drawString(lives, getSize().width - g.getFontMetrics().stringWidth(lives) - 25, 25);
			g.drawString(score, getSize().width - g.getFontMetrics().stringWidth(score) - 25, 50);
			
			// Draw and move enemies
			boolean first = true;
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
						if(current.getX() < 0 || current.getX() > getSize().width - 70)
							current.flipX();
					}
					
					// Lower enemy health when hit by bullet
					for(int i = playerBullets.size() - 1; i > -1; i--) {
						if(current.collide(playerBullets.get(i))) {
							// Delete bullet and lower HP of current enemy
							playerBullets.remove(i);
							current.lowerHP();
							currentScore += 100;
							
							// Delete enemy if it's dead
							if(current.isDead())
								enemies.get(r).remove(c);
						}
					}
					
					// Kill player if enemies collide with them
					if(first && current.getY() > getSize().height - 70)
						player.kill();
					
					// Apply movement
					current.move();
				
					// Draw current enemy
					current.draw(g);
				}
			}
			
			// Draw and move bullets from player
			for(int i = playerBullets.size() - 1; i > -1; i--) {
				Bullet current = playerBullets.get(i);
				
				if(current.getY() > -50) {
					// Move and draw bullet if still in view
					current.move();
					current.draw(g);
				} else {
					// Remove bullet if it's outside the view of the window
					playerBullets.remove(i);
				}
			}
			
			// Create enemy bullets and check for collisions with the player
			enemyShoot(bulletImg, g);
			
			// Update view
			g.dispose();
			bS.show();
			
			// Shoot if space pressed and enough time has passed since the previous shot
			long currTime = System.currentTimeMillis();
			if(shoot && currTime - firstTime > TIME_BETWEEN_SHOTS) {
				shootBullet(bulletImg, player, -4, playerBullets);
				shoot = !shoot;
				firstTime = currTime;
			}
			
			// Move player and check to see if the game should end
			player.moveToBottom();
			movePlayer(playerLeft, playerRight, playerCenter);	
			checkEndGame();
			
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
	public void shootBullet(CharacterImg bulletImg, Character creator, int direction, ArrayList<Bullet> listToAdd) {
		Bullet b = new Bullet(this, bulletImg, creator.getX(), creator.getY(), 0, 0);
		b.changeVelocity(0, direction);
		listToAdd.add(b);
	}
	
	// Creates enemy bullets
	public void enemyShoot(CharacterImg bulletImg, Graphics2D g) {
		// Find lowest set of enemies		
		int lowest = enemies.size() - 1;
		while(lowest > -1 && enemies.get(lowest).size() == 0)
			lowest--;
		
		System.out.println(lowest);
		
		int max = enemies.get(lowest).size();
		if(max > 5)
			max = 5;
		
		while(enemyBullets.size() < max) {				
			// Have a random enemy shoot the bullets
			int randEnemy = (int)(Math.random() * enemies.get(lowest).size());
			shootBullet(bulletImg, enemies.get(lowest).get(randEnemy), 4, enemyBullets);
		}
		
		// Draw and move enemy bullets
		for(int i = enemyBullets.size() - 1; i > -1; i--) {
			Bullet current = enemyBullets.get(i);
			
			if(current.getY() < getSize().height + 50) {
				current.move();
				current.draw(g);
			} else {
				enemyBullets.remove(i);
			}
		}
		
		// Lower player health when hit by enemy bullet
		for(int i = enemyBullets.size() - 1; i > -1; i--) {
			if(enemyBullets.get(i).collide(player)) {
				player.lowerHP();
				enemyBullets.remove(i);
			}
		}		
	}
	
	// Moves player if keys are being pressed
	public void movePlayer(CharacterImg leftImg, CharacterImg rightImg, CharacterImg straightImg) {
		if(left && player.getX() > 20) {
			player.changeVelocity(-1, 0);
			player.changeImg(leftImg);
		} else if(right && player.getX() < getSize().width - 70) {
			player.changeVelocity(1, 0);
			player.changeImg(rightImg);
		} else if(!left && player.getXVelocity() > 0) {
			player.changeVelocity(-1, 0);
			player.changeImg(straightImg);
		} else if(!right && player.getXVelocity() < 0) {
			player.changeVelocity(1, 0);
			player.changeImg(straightImg);
		}
		player.move();
	}
	
	// Exit game loop when player dies / exit game loop when all enemies are dead
	public void checkEndGame() {
		String over = "GAME OVER";
		
		if(player.isDead()) {
			runGame = false;
		} else {
			boolean noEnemies = true;
			for(int i = 0; i < enemies.size(); i++) {
				if(enemies.get(i).size() > 0)
					noEnemies = false;
			}
			
			if(noEnemies) {
				runGame = false;
				currentScore += 500;
				over = "YOU WON!";
			}
		}
		
		if(!runGame) {
			// Save scores to file
			try {
				saveScores();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Graphics2D g = (Graphics2D) bS.getDrawGraphics();
			
			g.setColor(Color.black);
			g.fillRect(0, 0, getSize().width + 50, getSize().height + 50);
			
			// Display end screen
			g.setColor(Color.white);
			String score = "Final score: " + currentScore;
			Score sHigh = getHighestScore();
			String highScore = "High score - Name: " + sHigh.getName() + ", Score - " + sHigh.getScore();
			g.drawString(over, (getSize().width - g.getFontMetrics().stringWidth(over)) / 2, (getSize().height / 2) - 25);			
			g.drawString(score, (getSize().width - g.getFontMetrics().stringWidth(score)) / 2, getSize().height / 2);
			g.drawString(highScore, (getSize().width - g.getFontMetrics().stringWidth(highScore)) / 2, (getSize().height / 2) + 25);

			g.dispose();
			bS.show();
		}
	}
	
	// Save high scores to file
	public void saveScores() throws IOException {
		highScores.add(new Score("NAME", currentScore));
		File scores = new File(SCORE_FILE);
		PrintStream writer = new PrintStream(scores);
		
		for(int i = 0; i < highScores.size(); i++)
			writer.println(highScores.get(i).toString());
		
		writer.close();
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
	
	public Score getHighestScore() {
		Score max = new Score("MIN", Long.MIN_VALUE);
		
		for(int i = 0; i < highScores.size(); i++) {
			if(highScores.get(i).getScore() > max.getScore())
				max = highScores.get(i);
		}
		
		return max;
	}
}
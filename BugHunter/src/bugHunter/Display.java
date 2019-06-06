/*
 * Andrew Balaschak, Hassan Farooq
 * 2018-2019 APCS P.5 Semester 2 Final
 * 
 * Display Class - creates a window, draws everything in it, also handles all object movement and collision detection
 */

package bugHunter;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display extends Canvas {
	
	// Class constants
	private final String NAME = "Bug Hunter v0.1.0";					// Name of program
	private final String BUG_IMAGE = "src/bugHunter/Bug.png";			// File location for bug image
	private final String BUG2_IMAGE = "src/bugHunter/Bug_2.png";		// File location for the alternative bug image
	private final String BULLET_IMAGE = "src/bugHunter/Bullet.png";		// File location for bullet image
	private final String PLAYER_IMAGE = "src/bugHunter/Ship.png";		// File location for player image
	private final String PLAYER_RIGHT = "src/bugHunter/ShipR.png";		// File location for the player image leaning right
	private final String PLAYER_LEFT = "src/bugHunter/ShipL.png";		// File location for the player image leaning left
	private final String ICON_IMAGE = "src/bugHunter/Ship.png";			// File location for icon image
	private final String POWERUP_IMAGE = "src/bugHunter/PowerUp.png";	// File location for power up image
	private final String HEALTHUP_IMAGE = "src/bugHunter/HealthUp.png"; // File location for the health increase power up
	private final String SCORE_FILE = "src/bugHunter/HighScores.txt";	// File location for high scores
	private final int SCALE = 70;										// Scale factor for window
	private final double POWERUP_CHANCE = 0.0001;						// Chance per frame that a power up will appear
	
	// Instance variables
	private boolean left;												// True if left arrow key currently pressed, false otherwise
	private boolean right;												// True if right arrow key currently pressed, false otherwise
	private boolean shoot;												// True if space bar currently pressed, false otherwise
	private boolean esc;												// Toggles when escape key pressed
	private boolean enter;												// Toggles when enter key pressed
	private boolean runGame;											// True if the player is alive and is in the main game
	private long currentScore;											// Current score for the player (+100 for every enemy killed, +500 for every level cleared)
	private int level;													// Current level (increases whenever all enemies in the current stage die)
	private int timeBetweenShots;										// Minimum cooldown for shooting by the player (decreases with powerups)
	private int bulletSpeed;											// The speed that the bullets move (increases as levels increase)
	
	private double delayPerFrame;										// Delay between drawing each frame (decrease to speed up all parts of the game)
	
	private Player player;												// Player object
	private ArrayList<Bullet> playerBullets;							// ArrayList of all bullets the player has shot and are within view of the window
	private ArrayList<Bullet> enemyBullets;								// ArrayList of all bullets enemies have shot and are within view of the window
	private ArrayList<Powerup> powerups;								// ArrayList of all powerups currently in view of the window
	private ArrayList<ArrayList<Enemy>> enemies;						// ArrayList of ArrayList of enemies
	private ArrayList<Score> highScores;								// ArrayList of all scores from SCORE_FILE
	private BufferStrategy bS;											// Used to draw images/text on the window
	
	private JPanel panel;												// Panel which everything is drawn onto
	
	// Display constructor - creates the layout and defines characteristics of the window
	public Display() {
		// Set instance variables
		left = false;
		right = false;
		shoot = false;
		esc = false;
		enter = false;
		runGame = true;
		
		currentScore = 0;
		level = 1;
		timeBetweenShots = 1200;
		bulletSpeed = 4;
		delayPerFrame = 5.0;
		
		playerBullets = new ArrayList<Bullet>();
		enemyBullets = new ArrayList<Bullet>();
		powerups = new ArrayList<Powerup>();
		enemies = new ArrayList<ArrayList<Enemy>>();
		highScores = new ArrayList<Score>();
		
		// Creates window, defines characteristics
		JFrame frame = new JFrame(NAME);
		panel = (JPanel) frame.getContentPane();
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
	public void createObjects() throws FileNotFoundException {
		// Create a player image and object
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		CharacterImg playerImg = new CharacterImg(toolkit.getImage(PLAYER_IMAGE));
		CharacterImg enemyImg = new CharacterImg(toolkit.getImage(BUG_IMAGE));
		CharacterImg enemyImg2 = new CharacterImg(toolkit.getImage(BUG2_IMAGE));

		player = new Player(this, playerImg, 20);
		
		// Creates enemies
		for(int r = 0; r < 3; r++) {
			enemies.add(new ArrayList<Enemy>());
			
			for (int c = 0; c < 10; c++) {
				double randAmnt = Math.random();
				if(randAmnt < 0.95)
					enemies.get(r).add(new Enemy(this, enemyImg, (70 * c) + 15, (70 * r) + 15, 0, 0, 1, 1));
				else
					enemies.get(r).add(new Enemy(this, enemyImg2, (70 * c) + 15, (70 * r) + 15, 0, 0, 3, 2));
			}
		}
		
		createHighScoreList();
	}
	
	/*
	 * Game loop
	 * Updates the display and is in charge of moving the player, enemies, bullets, and powerups
	 * Main while loop runs until either the player dies or all the enemies have been killed
	 */
	public void gameRun() throws FileNotFoundException {
		// Single CharacterImg object created for objects
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		CharacterImg bulletImg = new CharacterImg(toolkit.getImage(BULLET_IMAGE));
		CharacterImg playerRight = new CharacterImg(toolkit.getImage(PLAYER_RIGHT));
		CharacterImg playerLeft = new CharacterImg(toolkit.getImage(PLAYER_LEFT));
		CharacterImg playerCenter = new CharacterImg(toolkit.getImage(PLAYER_IMAGE));
		
		startScreen();
		
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
			String lvl = "Level " + level;
			
			// Cooldown timer display
			long timeLeft = timeBetweenShots + firstTime - System.currentTimeMillis();
			if(timeLeft < 0)
				timeLeft = 0;
			String timeShots = "Cooldown: " + timeLeft;
			
			g.drawString(lives, 25, 25);
			g.drawString(score, 3 * (getSize().width - g.getFontMetrics().stringWidth(score)) / 4, 25);
			g.drawString(lvl, (getSize().width - g.getFontMetrics().stringWidth(lvl)) / 4, 25);
			g.drawString(timeShots, getSize().width - g.getFontMetrics().stringWidth(timeShots) - 25, 25);
			
			// Move enemies right, left, or down
			moveEnemies(frames, g);
			
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
			
			// Create power up
			powerUp(toolkit, g);
			
			// Create enemy bullets and check for collisions with the player
			enemyShoot(bulletImg, g);
			
			// Update view
			g.dispose();
			bS.show();
			
			// Shoot if space pressed and enough time has passed since the previous shot
			long currTime = System.currentTimeMillis();
			if(shoot && currTime - firstTime > timeBetweenShots) {
				shootBullet(bulletImg, player, -bulletSpeed, playerBullets);
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
			
			// Pause game if escape key is pressed
			while(esc) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * Moves player if keys are being pressed
	 * @param leftImg - this is the image for the left leaning player, passed in so multiple don't have to be created
	 * @param rightImg - this is the image for the right leaning player, passed in so multiple don't have to be created
	 * @param straightImg - this is the image for the straight player, passed in so multiple don't have to be created
	 */
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
	
	/*
	 * Moves enemies right, left, or down
	 * Moves right until it hits the right side of display, then flips to left (and vice versa)
	 * Moves down every 20th frame
	 * Also checks if enemies have been hit by a bullet - lower health/kill them if hit
	 * Kills player if the enemies get to low/collide with player
	 * @param frames - number of frames that have been drawn, used to determine if enemies should move down slightly
	 * @param g - the Graphics2D object that is displaying everything
	 */
	public void moveEnemies(int frames, Graphics2D g) {
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
				if(current.getY() > getSize().height - 125)
					player.kill();
				
				// Apply movement
				current.move();
			
				// Draw current enemy
				current.draw(g);
			}
		}
	}
	
	/*
	 * Creates a new Bullet object and adds it to the bullets ArrayList
	 * @param bulletImg - this is the image for the bullet, passed in so multiple don't have to be created
	 * @param creator - used to determine location the new bullet should be at
	 * @param xSpeed - used to set the X velocity of the bullet (- if going away from the player, + if going towards the player)
	 * @param listToAdd - adds the new Bullet object to the correct ArrayList (either enemyBullets or playerBullets)
	 */
	public void shootBullet(CharacterImg bulletImg, Character creator, int xSpeed, ArrayList<Bullet> listToAdd) {
		Bullet b = new Bullet(this, bulletImg, creator.getX(), creator.getY(), 0, 0);
		b.changeVelocity(0, xSpeed);
		listToAdd.add(b);
	}
	
	/*
	 * Creates and draws enemy bullets
	 * Alters player health if hit by a bullet
	 * @param bulletImg - this is the image for the bullet, passed in so multiple don't have to be created 
	 * @param g - the Graphics2D object that is displaying everything
	 */
	public void enemyShoot(CharacterImg bulletImg, Graphics2D g) {
		// Find lowest set of enemies
		int lowest = 2;
		if(enemies.get(2).size() == 0)
			lowest = 1;
		if(enemies.get(1).size() == 0)
			lowest = 0;
		
		int max = 0;
		if(enemies.size() > 0) {
			max = enemies.get(lowest).size();
			if(max > 5)
				max = 5;
		}
		
		// Have a random enemies shoot bullets (up to 5)
		while(enemyBullets.size() < max) {				
			int randEnemy = (int)(Math.random() * enemies.get(lowest).size());
			
			// Faster bullet speed for more powerful enemies
			int bSpeed = bulletSpeed;
			if(enemies.get(lowest).get(randEnemy).getType() == 2)
				bSpeed *= 3;
			
			shootBullet(bulletImg, enemies.get(lowest).get(randEnemy), bSpeed, enemyBullets);
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
	
	/*
	 * Creates a powerup randomly - based on the value of POWERUP_CHANCE
	 * Draws powerups if any exist
	 * @param toolkit - needed to get info about the display
	 * @param g - the Graphics2D object that is displaying everything
	 */
	public void powerUp(Toolkit toolkit, Graphics2D g) {
		double randPowerUp = Math.random();
		if(randPowerUp < POWERUP_CHANCE) {
			double randType = Math.random();
			
			CharacterImg powerupImg;
			if(randType < 0.5)
				powerupImg = new CharacterImg(toolkit.getImage(POWERUP_IMAGE));
			else
				powerupImg = new CharacterImg(toolkit.getImage(HEALTHUP_IMAGE));

			powerups.add(new Powerup(this, powerupImg, (int)(Math.random() * (getSize().width - 50)) + 50, -50, (int)(Math.random() * 2) + 1));
			powerups.get(powerups.size() - 1).changeVelocity(0, 1);
		}
		
		for(int i = powerups.size() - 1; i > -1; i--) {
			Powerup current = powerups.get(i);
			current.move();
			current.draw(g);
			if(current.collide(player)) {
				if(current.getType() == 1 && player.getHP() < 10)
					player.increaseHP();
				else if(current.getType() == 2 && timeBetweenShots > 100)
					timeBetweenShots /= 2;
				powerups.remove(i);
			}
		}
	}
	
	/*
	 * Exit the game loop when  either a player dies or when all enemies die
	 * Creates a new instance of everything (with some changes) if the player is still alive (new level)
	 * Loads end screen if the player died
	 */
	public void checkEndGame() throws FileNotFoundException {
		boolean isOver = false;
		if(player.isDead()) {
			runGame = false;
			isOver = true;
		} else {
			boolean noEnemies = true;
			for(int i = 0; i < enemies.size(); i++) {
				if(enemies.get(i).size() > 0)
					noEnemies = false;
			}
			
			// Move onto next round
			if(noEnemies) {
				currentScore += 500;
				
				// Speeds up game every round
				delayPerFrame -= 0.2;
				if(delayPerFrame < 0.3)
					delayPerFrame = 0.3;
				
				// Speeds up bullets every other round
				if((level + 1) % 2 == 0 && bulletSpeed < 15)
					bulletSpeed++;
				
				level++;
				createObjects();
				gameRun();
			}
		}
		
		if(isOver) {
			// Save scores to file
			try {
				saveScores();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Display end screen			
			endScreen();
		}
	}
	
	// Creates a start screen so the game doesn't jump directly into gameplay
	public void startScreen() {
		while(!enter) {
			// Create graphics for start screen
			Graphics2D g = (Graphics2D) bS.getDrawGraphics();
			
			g.setColor(Color.black);
			g.fillRect(0, 0, getSize().width + 50, getSize().height + 50);
			g.setColor(Color.white);
			
			String authors = "Hassan Farooq, Andrew Balacshak";
			String start = "PRESS ENTER TO START";
			g.drawString(NAME, (getSize().width - g.getFontMetrics().stringWidth(NAME)) / 2, 125);
			g.drawString(authors, (getSize().width - g.getFontMetrics().stringWidth(authors)) / 2, 175);
			g.drawString(start, (getSize().width - g.getFontMetrics().stringWidth(start)) / 2, (getSize().height / 2) + 50);

			// Update view
			g.dispose();
			bS.show();
			
			// Delay between frames
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Creates the ending screen with the user's score and top 5 scores achieved
	public void endScreen() {
		while(true) {
			Graphics2D g = (Graphics2D) bS.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, getSize().width + 50, getSize().height + 50);

			g.setColor(Color.white);
			String score = "Final score: " + currentScore;
			String over = "GAME OVER!";
			String high = "HIGH SCORES";

			g.drawString(over, (getSize().width - g.getFontMetrics().stringWidth(over)) / 2, (getSize().height / 2) - 25);		
			g.drawString(score, (getSize().width - g.getFontMetrics().stringWidth(score)) / 2, getSize().height / 2);
			g.drawString(high, (getSize().width - g.getFontMetrics().stringWidth(high)) / 2, (getSize().height / 2) + 50);
			
			// Display high scores
			int scoresShown = 0;
			Collections.sort(highScores);
			while(highScores.size() > scoresShown && scoresShown < 5) {
				String highScore = (1 + scoresShown) + " - " + highScores.get(scoresShown).getName() + ": " + highScores.get(scoresShown).getScore();
				g.drawString(highScore, (getSize().width - g.getFontMetrics().stringWidth(highScore)) / 2, (getSize().height / 2) + (25 * (scoresShown + 3)));
				scoresShown++;
			}
			
			// Update view
			g.dispose();
			bS.show();
		}
	}
	
	// Creates the ArrayList of high scores
	public void createHighScoreList() throws FileNotFoundException {
		File scores = new File(SCORE_FILE);
		Scanner fileScan = new Scanner(scores);
		
		while(fileScan.hasNext())
			highScores.add(new Score(fileScan.next(), fileScan.nextLong()));
		
		fileScan.close();
	}
	
	// Save high scores to file
	public void saveScores() throws IOException {
		highScores.add(new Score("Player" + (highScores.size() + 1), currentScore));
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
	
	// Alters esc boolean when the escape key is pressed
	public void esc() {
		esc = !esc;
	}
	
	// Alters enter boolean when the enter key is pressed
	public void enter() {
		enter = !enter;
	}
}
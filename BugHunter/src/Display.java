
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Display extends JFrame {
	
	private final int SCALE_FACTOR = 1;
	private final String NAME = "Bug Hunter v0.0.1";
	private boolean right;
	private boolean left;
	private boolean shoot;
	private Player player;
	private ArrayList<Character> characters;
	
	public static void main(String[] args) {
		
		Display display = new Display();
		display.runGame();
		
		// Centers the thing on the screen
//		EventQueue.invokeLater(() -> {
//			Display ex = new Display();
//			ex.setVisible(true);
//		});

	}
	
	// Sets up window and defines its characteristics
	public Display() {
		// Sets up class variables used to detect key presses
		right = false;
		left = false;
		shoot = false;
		
		// Basic window characteristics
		JFrame window = new JFrame(NAME);
		JPanel panelDisplayed = (JPanel) window.getContentPane();
		Dimension size = new Dimension(400 * SCALE_FACTOR, 300 * SCALE_FACTOR);
		panelDisplayed.setPreferredSize(size);
		
		// Sets boundaries on the window
		setBounds(0, 0, (int) size.getWidth(), (int) size.getHeight());
		panelDisplayed.add(this);
		
		window.pack();
		window.setVisible(true);

		// Creates a key listener to detect key presses on window
		addKeyListener(new KeyHandler(this));
		requestFocus();
		
		initUI();
	}
	
	// Adds elements to GUI
	private void initUI() {
		characters = new ArrayList<Character>();
		player = new Player(0);
		characters.add(player);
		
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 10; j++) {
				Bug bug = new Bug(100 * j, 100 * i, 10);
				characters.add(bug);
			}
		}
		
//		add(new Main());
//		
//		// Sets window characteristics
//		setSize(192 * SCALE_FACTOR, 108 * SCALE_FACTOR);
//		setTitle("Bug Hunter v0.0.1");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setLocationRelativeTo(null);
//		setResizable(false);
	}
	
	private void runGame() {
		while(true) {
			// Do game stuff
		}
	}
	
	public void changeRight(boolean newRight) {
		right = newRight;
	}
	
	public void changeLeft(boolean newLeft) {
		left = newLeft;
	}
	
	public void changeShoot(boolean newShoot) {
		shoot = newShoot;
	}
}
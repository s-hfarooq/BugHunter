
public class Player {
	private int health;
	private int lives;
	private int score;
	private int damageAmount;
	private int currXLocation;
	private int currYLocation;
	private File spriteFile;
	
	public Player(int startingX) {
		health = 3;
		lives = 2;
		score = 0;
		damageAmount = 1;
		currXLocation = startingX;
	}
	
	public int move(int amount) {
		currXLocation += amount;
		return currXLocation;
	}
	
	public void shoot() {
		
	}
	
	public int decreaseHP(int amount) {
		health -= Math.abs(amount);
		return health;
	}
	
	public int increaseHP(int amount) {
		health += Math.abs(amount);
		return health;
	}
	
	public int getHP() {
		return health;
	}
}


// Abstract class for player and enemy classes
public class Character {
	private int currXLocation;
	private int currYLocation;
	private Image img;
	
	public Character(int newX, int newY) {
		currXLocation = newX;
		currYLocation = newY;
	}
	
	public int moveX(int amnt) {
		currXLocation += amnt;
		return getCurrX();
	}
	
	public int moveY(int amnt) {
		currYLocation += amnt;
		return getCurrY();
	}
	
	public int getCurrX() {
		return currXLocation;
	}
	
	public int getCurrY() {
		return currYLocation;
	}
}

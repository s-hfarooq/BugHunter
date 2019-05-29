public class Bug extends Character {
	private int health;
	private int damageAmount;
	private int speed;
	
	public Bug(int newX, int newY, int moveSpeed) {
		super(newX, newY);
		speed = moveSpeed;
	}
	
	public void move(int amnt) {
		move(amnt);
		
		if(getCurrX() < 20 && amnt < 0) {
			// move stuff
		} else if(getCurrX() > 400 && amnt > 0) {
			// other move stuff
		}
	}
	
	public int decreaseHP(int amount) {
		health -= Math.abs(amount);
		return health;
	}
	
	public int getHP() {
		return health;
	}
}
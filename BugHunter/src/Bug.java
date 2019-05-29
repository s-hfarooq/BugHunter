public class Bug extends Character {
	private int health;
	private int damageAmount;
	private int speed;
	
	public Bug(int newX, int newY, int moveSpeed) {
		super(newX, newY);
		speed = moveSpeed;
	}
	
	public int decreaseHP(int amount) {
		health -= Math.abs(amount);
		return health;
	}
	
	public int getHP() {
		return health;
	}
}
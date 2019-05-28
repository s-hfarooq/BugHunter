public abstract class Bug {
	private int health;
	private int damageAmount;
	
	public int decreaseHP(int amount) {
		health -= Math.abs(amount);
		return health;
	}
	
	public int getHP() {
		return health;
	}
}
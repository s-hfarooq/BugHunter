package bugHunter;

public class Score {
	public String name;
	public long score;
	
	public Score(String name, long score) {
		this.name = name;
		this.score = score;
	}
	
	public String getName() {
		return name;
	}
	
	public long getScore() {
		return score;
	}
	
	public String toString() {
		return name + " " + score;
	}
}
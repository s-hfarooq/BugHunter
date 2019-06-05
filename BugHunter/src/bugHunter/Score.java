package bugHunter;

public class Score implements Comparable {
	public String name;
	public long score;
	
	public Score(String name, long score) {
		this.name = name;
		this.score = score;
	}
	
	// Allows us to sort the scores before printing out highest scores/saving to file
	public int compareTo(Object other) {
		int returnVal = 0;
		if(other instanceof Score) {
			Score temp = (Score) other;
			if(temp.getScore() > getScore())
				returnVal = 1;
			else if(temp.getScore() < getScore())
				returnVal = -1;
		}
		
		return returnVal;
	}
	
	// Getter methods	
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
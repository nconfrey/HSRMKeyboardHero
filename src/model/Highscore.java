package model;

import java.io.Serializable;

public class Highscore implements Comparable<Highscore>, Serializable {
	
	private String name;
	private int score;
		
	public Highscore(int score, String name) {
		this.score = score;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	@Override
	public int compareTo(Highscore o) {
		return o.getScore() - getScore();
	}
	
	public String toString() {
		return getScore() + " - " + getName();
	}

}

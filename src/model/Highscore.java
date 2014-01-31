/**
 * Highscore Model of a single score
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package model;

import java.io.Serializable;

public class Highscore implements Comparable<Highscore>, Serializable {

	private static final long serialVersionUID = -3955711964526156861L;
	private String name;
	private int score;

	/**
	 * Instantiates a new highscore.
	 * 
	 * @param score the score
	 * @param name the name
	 */
	public Highscore(int score, String name) {
		this.score = score;
		this.name = name;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the score.
	 * 
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Highscore o) {
		return o.getScore() - getScore();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getScore() + " - " + getName();
	}

}

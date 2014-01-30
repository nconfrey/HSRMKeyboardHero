/**
 * Model that holds all Highscores for a track
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;

public class HighscoreSet extends AbstractListModel<Highscore> {

	private static final long serialVersionUID = -6204551776079832861L;
	private List<Highscore> highscores;

	/**
	 * Instantiates a new highscore set.
	 */
	public HighscoreSet() {
		this.highscores = new ArrayList<>();
	}

	/**
	 * Adds the high score.
	 * 
	 * @param highscore the highscore
	 */
	public void addHighScore(Highscore highscore) {
		this.highscores.add(highscore);
		Collections.sort(highscores);
	}

	/**
	 * Gets the best score.
	 * 
	 * @return the best score
	 */
	public Highscore getBestScore() {
		if (this.highscores.size() <= 0)
			return null;
		return this.highscores.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return highscores.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Highscore getElementAt(int index) {
		// TODO Auto-generated method stub
		return highscores.get(index);
	}

}

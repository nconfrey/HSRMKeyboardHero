/**
 * Score Model that calculates the Scores of a Game
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package model;

public class Score extends AbstractBindableModel {

	private long score;
	private int combo;

	private static final int STROKEHIT = 10;
	private static final int COMBOBONUS = 100;
	public static final int MAXCOMBO = 5;
	private static final float COMBO_MULTIPLIER = 0.2f;

	/**
	 * Instantiates a new score.
	 */
	public Score() {
		this.score = 0;
		this.combo = 0;
	}

	/**
	 * Raise.
	 * 
	 * @return the long
	 */
	public long raise() {
		long oldScore = score;

		score = (int) (score + STROKEHIT + (STROKEHIT * (combo * COMBO_MULTIPLIER)));
		firePropertyChange("score", oldScore, score);
		return score;
	}

	/**
	 * Reset.
	 */
	public void reset() {
		long oldScore = score;
		score = 0;
		comboReset();
		firePropertyChange("score", oldScore, score);
	}

	/**
	 * Resets the combo multiplier.
	 */
	public void comboReset() {
		int oldCombo = combo;
		combo = 0;
		firePropertyChange("combo", oldCombo, combo);
	}

	/**
	 * Increase combo.
	 */
	public void increaseCombo() {
		if (combo < MAXCOMBO) {
			firePropertyChange("combo", combo, ++combo);
		}
	}

	/**
	 * Gets the score.
	 * 
	 * @return the score
	 */
	public long getScore() {
		return score;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "" + score;
	}

	/**
	 * Gets the combo.
	 * 
	 * @return the combo
	 */
	public int getCombo() {
		return combo;
	}
}

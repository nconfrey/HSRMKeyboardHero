package model;

public class Score extends AbstractBindableModel  {
	
	private long score;
	private static int combo;
	
	private static final int STROKEHIT   = 10;
	private static final int COMBOBONUS  = 100;
	private static final int STROKEMISS  = 1;
	
	public Score(){
		this.score = 0;
		Score.combo = 0;
	}
	
	public long raise(){
		long oldScore = score;
		combo++; 	
		if (combo == 10){	// bonus system for combos
			score = score + STROKEHIT + COMBOBONUS;
			combo = 0;
			return score;
		}
	
		score = score + STROKEHIT;
		
		firePropertyChange("score", oldScore, score);
		return score;
	}
	
	public long decrease(){
		long oldScore = score;
		combo = 0;
		
		if (score < 10){
			return score;	// no negative scores ...
		}
		
		score = score - STROKEMISS;
		
		firePropertyChange("score", oldScore, score);
		return score;
	}

	public long getScore() {
		return score;
	}
	
	@Override
	public String toString() {
		return "" + score;
	}

}

package model;

public class Score {
	
	private long score;
	private static int combo;
	
	private static final int STROKEHIT   = 100;
	private static final int COMBOBONUS  = 1000;
	private static final int STROKEMISS  = 10;
	
	public Score(){
		this.score = 0;
		Score.combo = 0;
	}
	
	public long raise(){
		combo++; 	
		
		if (combo == 10){	// bonus system for combos
			score = score + STROKEHIT + COMBOBONUS;
			combo = 0;
			return score;
		}
		
		score = score + STROKEHIT;
		return score;
	}
	
	public long decrease(){
		combo = 0;
		
		if (score < 10){
			return score;	// no negative scores ...
		}
		
		score = score - STROKEMISS;
		
		return score;
	}

	public long getScore() {
		return score;
	}

}

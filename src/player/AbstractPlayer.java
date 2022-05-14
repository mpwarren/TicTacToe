package player;

import board.Board;

public abstract class AbstractPlayer {
	private char symbol;
	
	private int[][] turnLog;
	
	int turnsPlayed;
	
	public AbstractPlayer(char symbol) {
		turnLog = new int[5][2];
		this.symbol = symbol;
		turnsPlayed = 0;
	}
	
	public char getSymbol() {
		return symbol;
	}
	
	public int[][] getTurnLog(){
		return turnLog;
	}
	
	public void addToLog(int i, int j) {
		int[] play = {i, j};
		turnLog[turnsPlayed] = play;
		turnsPlayed++;
	}
	
	public int getTurnsPlayed() {
		return turnsPlayed;
	}
	
	public abstract void turn(Board board, int turn);
}

package player;

import board.Board;

public abstract class AbstractPlayer {
	private char symbol;
	
	int turnsPlayed;
	
	public AbstractPlayer(char symbol) {
		this.symbol = symbol;
		turnsPlayed = 0;
	}
	
	public char getSymbol() {
		return symbol;
	}
	
	public int getTurnsPlayed() {
		return turnsPlayed;
	}
	
	public void play(Board board, int turn, int i, int j, char Symbol) {
		board.place(i, j, getSymbol());
		board.addToLog(turn, i, j);
		board.removeFromRemaining(i, j);
	}
	
	public abstract void turn(Board board, int turn);
}

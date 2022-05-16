package player;

import board.Board;

public abstract class AbstractPlayer {
	private char symbol;
	
	public AbstractPlayer(char symbol) {
		this.symbol = symbol;
	}
	
	public char getSymbol() {
		return symbol;
	}
	
	public void play(Board board, int turn, int i, int j, char Symbol) {
		board.place(i, j, getSymbol());
		board.addToLog(turn, i, j);
		board.removeFromRemaining(i, j);
	}
	
	public abstract void turn(Board board, int turn);
}

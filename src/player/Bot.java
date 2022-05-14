package player;

import java.util.Random;

import board.Board;

public class Bot extends AbstractPlayer {
	
	private Random rand;
	
	public Bot(char symbol) {
		super(symbol);
		rand = new Random();
	}

	@Override
	public void turn(Board board, int turn) {
		if(turn == 1) {
			//Place in a random corner
			int i = rand.nextBoolean() ? 0 : 2;
			int j = rand.nextBoolean() ? 0 : 2;
			board.place(i, j, getSymbol());
			addToLog(i, j);
		}
		else if(turn == 3) {
			if(board.getPosition(1, 1) == 0) {
				
			}
		}
	}
	
}

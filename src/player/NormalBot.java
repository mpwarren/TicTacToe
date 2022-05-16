package player;

import board.Board;

public class NormalBot extends AbstractBot {

	public NormalBot(char symbol) {
		super(symbol);
	}

	@Override
	public void turn(Board board, int turn) {
		//If the bot can win on this turn
		int[] pos = board.canWin(getSymbol());
		if(pos != null) {
			board.place(pos[0], pos[1], getSymbol());
			return;
		}
		
		//Block opponent if they can win on this turn
		pos = board.canWin(getOppositeSymbol());
		if(pos != null) {
			board.place(pos[0], pos[1], getSymbol());
			return;
		}
		
		playInRandomSpot(board, turn);

	}

}

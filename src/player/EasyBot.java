package player;

import board.Board;

public class EasyBot extends AbstractBot {

	public EasyBot(char symbol) {
		super(symbol);
	}

	@Override
	public void turn(Board board, int turn) {
		playInRandomSpot(board, turn);
	}

}

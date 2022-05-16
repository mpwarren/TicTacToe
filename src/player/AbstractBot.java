package player;

import java.util.Random;

import board.Board;
import board.Square;

public abstract class AbstractBot extends AbstractPlayer {

	public Random rand;
	
	public AbstractBot(char symbol) {
		super(symbol);
		rand = new Random();
	}
	
	public void playInRandomSpot(Board board, int turn) {
		Square sq = board.getRandomRemainingSpot();
		play(board, turn, sq.getIPos(), sq.getJPos(), getSymbol());
	}
	
	public void playWhereOpponentHasToBlock(Board board, int turn) {
		//Try and find somewhere to play where the opponent would have to block
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(board.getPosition(i, j) == 0) {
					board.place(i, j, getSymbol());
					if(board.countWinnablePlays(getSymbol()) > 0){
						board.getSquare(i, j).setSymbol((char)0);
						play(board, turn, i, j, getSymbol());
						return;
					}
					board.getSquare(i, j).setSymbol((char)0);
				}
			}
		}
		
		//otherwise, just play randomly
		playInRandomSpot(board, turn);
	}
	
	public char getOppositeSymbol() {
		return getSymbol() == 'X' ? 'O' : 'X';
	}


}

package player;

import java.util.Random;

import board.Board;
import board.Square;

public class Bot extends AbstractPlayer {
	
	private Random rand;
	
	public Bot(char symbol) {
		super(symbol);
		rand = new Random();
	}
	
	private char getOppositeSymbol() {
		return getSymbol() == 'X' ? 'O' : 'X';
	}

	@Override
	public void turn(Board board, int turn) {
		
		System.out.print("Bot is thinking");
		for(int i = 0; i < 3; i++) {	
			System.out.print(".");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
		
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
		
		//if we can place where it sets up 2 places to win, do it
		pos = board.canMakeWinnablePlay(getSymbol());
		if(pos != null) {
			board.place(pos[0], pos[1], getSymbol());
			return;
		}
		
		
		//Assuming the 4 blocks above work, the bot should never loose. 
		//All thats left is to set up winnable scenarios when it can play
		
		if(turn == 1) {
			//Place in a random corner
			playInRandCorner(board, turn);
			return;
		}
		
		Square prevPlay = board.getPastPlay(turn - 1);
		
		if(turn == 2) {
			//they played in the middle
			if(prevPlay.getIPos() == 1 && prevPlay.getJPos() == 1){
				playInRandCorner(board, turn);
			}
			else {
				play(board, turn, 1, 1, getSymbol());
			}
			//they played in a side
			return;
		}
		
		Square myPrevPlay = board.getPastPlay(turn - 2);
		
		if(turn == 3) {
			//if they didn't play in the middle
			if(board.getPosition(1, 1) == 0) {
				//if the other player played on the opposite row OR they played in the middle row right above/below the first play
				if((Math.abs(myPrevPlay.getIPos() - 2) == prevPlay.getIPos()) || (prevPlay.getIPos() == 1 && prevPlay.getJPos() == myPrevPlay.getJPos())) {
					//play in the other corner on the same row
					play(board, turn, myPrevPlay.getIPos(), Math.abs(myPrevPlay.getJPos() - 2), getSymbol());
				}
				//otherwise, play in the corner in the same column
				else {
				    play(board, turn, Math.abs(myPrevPlay.getIPos() - 2), myPrevPlay.getJPos(), getSymbol());
				}
			}
			//they played in the middle
			else {
				//play in the opposite corner
				play(board, turn, Math.abs(myPrevPlay.getIPos() - 2), Math.abs(myPrevPlay.getJPos() - 2), getSymbol());
			}
		}
		else if(turn == 4) {
			
			Square firstPlay = board.getPastPlay(1);
			Square lastPlay = board.getPastPlay(3);
			//if they played in opposite corners
			if(firstPlay.isCorner() && lastPlay.isCorner() && firstPlay.getIPos() != lastPlay.getIPos() && firstPlay.getJPos() != lastPlay.getJPos()) {
				//play in a side
				play(board, turn, 1, (rand.nextBoolean() ? 0 : 2), getSymbol());
				return;
			}
			
			//if opponent can place where it sets up 2 places to win, block
			pos = board.canMakeWinnablePlay(getOppositeSymbol());
			if(pos != null) {
				board.place(pos[0], pos[1], getSymbol());
				return;
			}
			else {
				playWhereOpponentHasToBlock(board, turn);
			}
			
		}
		
		else if(turn == 6) {
			//if opponent can place where it sets up 2 places to win, block
			pos = board.canMakeWinnablePlay(getOppositeSymbol());
			if(pos != null) {
				board.place(pos[0], pos[1], getSymbol());
				return;
			}
			else {
				playWhereOpponentHasToBlock(board, turn);
			}
		}
		
		//Turn 5 and 7 is not needed because the bot went first and set up the board
		//Either the players will be blocking each other or a winnable play can be made
		
		else if(turn == 8 || turn == 9) {
			playInRandomSpot(board, turn);
		}
		
	}
	
	private void playWhereOpponentHasToBlock(Board board, int turn) {
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
	
	private void playInRandCorner(Board board, int turn) {
		int i = rand.nextBoolean() ? 0 : 2;
		int j = rand.nextBoolean() ? 0 : 2;
		play(board, turn, i, j, getSymbol());
	}
	
	private void playInRandomSpot(Board board, int turn) {
		Square sq = board.getRandomRemainingSpot();
		play(board, turn, sq.getIPos(), sq.getJPos(), getSymbol());
	}
	
}

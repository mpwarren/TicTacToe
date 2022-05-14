package driver;

import java.util.Scanner;

import board.Board;
import player.AbstractPlayer;
import player.Bot;
import player.Human;

public class Driver {
	
	private static AbstractPlayer player1;
	
	private static AbstractPlayer player2;
	
	public static void main(String args[]) {
		Board board = new Board();
		
		Scanner scnr = new Scanner(System.in);
		
		player1 = new Human('X', scnr);
		player2 = new Human('O', scnr);
		int turn = 1;
		boolean p1Turn = true;
		char winner = 0;
		while(turn < 10 && winner == 0) {
			if(p1Turn) {
				player1.turn(board, turn);
				p1Turn = false;
			}
			else {
				player2.turn(board, turn);
				p1Turn = true;
			}
			board.printBoard();
			winner = board.checkWinner();
			System.out.println("Winner is: " + winner);
			turn++;
		}
		
		scnr.close();
	}
}

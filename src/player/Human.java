package player;

import java.util.InputMismatchException;
import java.util.Scanner;

import board.Board;

public class Human extends AbstractPlayer {
	
	private static final int[][] inputMap = {{0,0},{0,1},{0,2},{1,0},{1,1},{1,2},{2,0},{2,1},{2,2}};
	
	private Scanner scnr;
	
	public Human(char symbol, Scanner scnr) {
		super(symbol);
		this.scnr = scnr;
	}

	@Override
	public void turn(Board board, int turn) {
		int userInput = 0;
		while(userInput < 1 || userInput > 9) {
			System.out.print("Enter a valid input (1-9): ");
			try {
				userInput = scnr.nextInt();
			}catch(InputMismatchException e) {
				System.out.println("Not a int");
				scnr.nextLine();
				continue;
			}
			
			if(userInput < 1 || userInput > 9) {
				System.out.println("Out of range");
				continue;
			}
			
			if(!board.isOpen(inputMap[userInput - 1][0], inputMap[userInput - 1][1])) {
				System.out.println("Spot already taken");
				continue;
			}
		}
		
		play(board, turn, inputMap[userInput - 1][0], inputMap[userInput - 1][1], getSymbol());
	}

}

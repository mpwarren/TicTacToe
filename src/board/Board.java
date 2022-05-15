package board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * 
 *  Board Coordinates: (i, j)
 *  
 *   (0,0) | (0,1) | (0,2)  
 *  -----------------------
 *   (1,0) | (1,1) | (1,2)  
 *  -----------------------
 *   (2,0) | (2,1) | (2,2)  
 *   
 *   
 *   Board indexes for user to type in:
 *   
 *   1 | 2 | 3  
 *  -----------
 *   4 | 5 | 6  
 *  -----------
 *   7 | 8 | 9  
 *  
 *  Class to work with the game board
 * @author Matthew Warren
 *
 */
public class Board {
    
	/** length and width of a tic tac toe board */
	private static final int BOARD_LEN = 3;
	
	/** the array repersentation of the game board */
    private Square[][] board;
    
    /** the remaining squares left that have not been placed in on the board */
    private ArrayList<Square> remainingSpots;
    
    /** the log of squares that have been placed in */
    private Square[] log;
    
    /**
     * Constructor to create a new game board
     * Creates the square objects and fills in the array
     */
    public Board(){
        board = new Square[BOARD_LEN][BOARD_LEN];
        remainingSpots = new ArrayList<Square>();
        int spotsIdx = 0;
        for(int i = 0; i < BOARD_LEN; i++) {
        	for(int j = 0; j < BOARD_LEN; j++) {
        		Square sq;
        		if((i == 0 || i == 2) && (j == 0 || j == 2)) {
        			sq = new Square(i, j, true);
        			board[i][j] = sq;
        		}
        		else {
        			sq =  new Square(i, j, false);
        			board[i][j] = sq;
        		}
        		remainingSpots.add(sq);
        	}
        }
        log = new Square[9];
    }
    
    /**
     * gets a random spot left on the board
     * @return a reference to a random square left open on the board
     */
    public Square getRandomRemainingSpot() {
    	int index = (int)(Math.random() * remainingSpots.size());
    	return remainingSpots.get(index);
    }
    
    /**
     * gets a square from the board
     * @param i the row to get from
     * @param j the col to get from
     * @return a reference to the square at (i, j)
     */
    public Square getSquare(int i, int j) {
    	return board[i][j];
    }
    
    /**
     * gets a square that was played in previously
     * @param turn the turn to get the square from
     * @return the square played in at the given turn
     */
    public Square getPastPlay(int turn) {
    	return log[turn - 1];
    }
    
    /**
     * adds a play to the log
     * @param turn the turn of the play
     * @param i the row to get from
     * @param j the col to get from
     */
    public void addToLog(int turn, int i, int j) {
    	log[turn - 1] = board[i][j];
    }
    
    /**
     * tells if the square is open at the indexes i, j
     * @param i the row to get from
     * @param j the col to get from
     * @return true if square is open, false otherwise
     */
    public boolean isOpen(int i, int j) {
    	return board[i][j].getSymbol() == 0;
    }
    
    /**
     * plays in the square at i, j
     * @param i the row to get from
     * @param j the col to get from
     * @param symbol the symbol to place
     * @throws IllegalArgumentException if the spot is already played in
     */
    public void place(int i, int j, char symbol) {
    	if(!isOpen(i, j)) {
    		throw new IllegalArgumentException("Spot already has already been placed in");
    	}
    	
    	board[i][j].setSymbol(symbol);
    	remainingSpots.remove(getSquare(i, j));
    }
    
    /**
     * prints out the game board in its current state
     */
    public void printBoard(){
        for(int i = 0; i < BOARD_LEN; i++) {
        	for(int j = 0; j < BOARD_LEN; j++) {
        		System.out.print(" " + (board[i][j].getSymbol() == 0 ? " " : board[i][j].getSymbol()));
        		if(j != 2) {
        			System.out.print(" |");
        		}
        	}
        	if(i != 2) {
        		System.out.println("\n-----------");
        	}
        }
        System.out.println("\n");
    }
    
    /**
     * checks if someone has won the game
     * @return the symbol of the winning player, 0 if no one won
     */
    public char checkWinner() {
    	//Check rows
    	for(int i = 0; i < BOARD_LEN; i++) {
    		Square sq = board[i][0];
    		if(sq.getSymbol() != 0 && board[i][1].equals(sq) && board[i][2].equals(sq)) {
    			return sq.getSymbol();
    		}
    	}
    	
    	//Check cols
    	for(int i = 0; i < BOARD_LEN; i++) {
    		Square sq = board[0][i];
    		if(sq.getSymbol() != 0 && board[1][i].equals(sq) && board[2][i].equals(sq)) {
    			return sq.getSymbol();
    		}
    	}
    	
    	//check top left to bottom right
    	if(board[0][0].getSymbol() != 0 && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) {
    		return board[0][0].getSymbol();
    	}
    	
    	//check top right to bottom left
    	if(board[0][2].getSymbol() != 0 && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0])) {
    		return board[0][2].getSymbol();
    	}
    	
    	return 0;
    }
    
    /**
     * gets the symbol at position i, j
     * @param i the row to get from
     * @param j the col to get from
     * @return the character at that symbol
     */
    public char getPosition(int i, int j) {
    	return board[i][j].getSymbol();
    }
    
    /**
     * Determines if the given symbol can win on this turn
     * @param symbol the symbol to check and see if they can win
     * @return an array of the coordinates they could win in: [i, j], null if one can't be made
     */
    public int[] canWin(char symbol) {
    	int symbolCount = 0;
    	int spaceCount = 0;
    	int[] spacePos = new int[2];
    	
    	//check rows
    	for(int i = 0; i < BOARD_LEN; i++) {
    		for(int j = 0; j < BOARD_LEN; j++) {
    			if(board[i][j].getSymbol() == symbol) {
    				symbolCount++;
    			}
    			else if(board[i][j].getSymbol() == 0) {
    				spaceCount++;
    				spacePos[0] = i;
    				spacePos[1] = j;
    			}
    		}
    		if(isWinnable(symbolCount, spaceCount)) {
    			return spacePos;
    		}
        	symbolCount = 0;
        	spaceCount = 0;
        	spacePos = new int[2];
    	}
    	
    	//check cols
    	for(int i = 0; i < BOARD_LEN; i++) {
    		for(int j = 0; j < BOARD_LEN; j++) {
    			if(board[j][i].getSymbol() == symbol) {
    				symbolCount++;
    			}
    			else if(board[j][i].getSymbol() == 0) {
    				spaceCount++;
    				spacePos[0] = j;
    				spacePos[1] = i;
    			}
    		}
    		if(isWinnable(symbolCount, spaceCount)) {
    			return spacePos;
    		}
        	symbolCount = 0;
        	spaceCount = 0;
        	spacePos = new int[2];
    	}
    	
    	//check top left to bottom right
    	for(int i = 0; i < BOARD_LEN; i++) {
			if(board[i][i].getSymbol() == symbol) {
				symbolCount++;
			}
			else if(board[i][i].getSymbol() == 0) {
				spaceCount++;
				spacePos[0] = i;
				spacePos[1] = i;
			}
    	}
		if(isWinnable(symbolCount, spaceCount)) {
			return spacePos;
		}
    	symbolCount = 0;
    	spaceCount = 0;
    	spacePos = new int[2];
    	
    	//check top right to bottom left
    	int j = 2;
    	for(int i = 0; i < BOARD_LEN; i++) {
			if(board[j][i].getSymbol() == symbol) {
				symbolCount++;
			}
			else if(board[j][i].getSymbol() == 0) {
				spaceCount++;
				spacePos[0] = j;
				spacePos[1] = i;
			}
			j--;
    	}
		if(isWinnable(symbolCount, spaceCount)) {
			return spacePos;
		}
		
		return null;
    }
    
    /**
     * determines if there is a winnable position in this set of 3 spaces
     * it is winnable if there are 2 of the same symbol and 1 space
     * @param symbolCount the number of symbols on the row
     * @param spaceCount the number of spaces on the row
     * @return true if they can win, false otherwise
     */
    private boolean isWinnable(int symbolCount, int spaceCount) {
    	return symbolCount == 2 && spaceCount == 1;
    }
    
    /**
     * determines if a winnable play can be made
     * a winnable play is one where if they play in a certain spot, it will open up 2 chances to win
     * @param symbol the symbol to see if there is a winnable play
     * @return the coordinates of where a winnable play can be made [i, j], null if one can't be made
     */
    public int[] canMakeWinnablePlay(char symbol) {   	
    	for(int i = 0; i < 3; i++) {
    		for(int j = 0; j < 3; j++) {
    			if(getPosition(i, j) != 0) {
    				continue;
    			}
    			
    			place(i, j, symbol);
    			
    			if(countWinnablePlays(symbol) >= 2) {
    				board[i][j].setSymbol((char)0);
    				int[] pos = new int[2];
    				pos[0] = i;
    				pos[1] = j;
    				return pos;
    			}
    			else {
    				board[i][j].setSymbol((char)0);
    			}
    		}
    	}
    	
    	return null;
    	
    }
    
    /**
     * counts how many winnable plays can be made
     * @param symbol the symbol to check
     * @return how many winnable playes can be made
     */
    public int countWinnablePlays(char symbol) {
    	int winnablePlays = 0;
    	int symbolCount = 0;
    	int spaceCount = 0;
    	
    	//check rows
    	for(int i = 0; i < BOARD_LEN; i++) {
    		for(int j = 0; j < BOARD_LEN; j++) {
    			if(board[i][j].getSymbol() == symbol) {
    				symbolCount++;
    			}
    			else if(board[i][j].getSymbol() == 0) {
    				spaceCount++;
    			}
    			
    		}
    		
    		if(isWinnable(symbolCount, spaceCount)) {
    			winnablePlays++;
    		}

        	symbolCount = 0;
        	spaceCount = 0;
    	}
    	
    	//check cols
    	for(int i = 0; i < BOARD_LEN; i++) {
    		for(int j = 0; j < BOARD_LEN; j++) {
    			if(board[j][i].getSymbol() == symbol) {
    				symbolCount++;
    			}
    			else if(board[j][i].getSymbol() == 0) {
    				spaceCount++;
    			}
    			

    		}
    		
    		if(isWinnable(symbolCount, spaceCount)) {
    			winnablePlays++;
    		}

        	symbolCount = 0;
        	spaceCount = 0;
    	}
    	
    	//check top left to bottom right
    	for(int i = 0; i < BOARD_LEN; i++) {
			if(board[i][i].getSymbol() == symbol) {
				symbolCount++;
			}
			else if(board[i][i].getSymbol() == 0) {
				spaceCount++;
			}
			
    	}
    	
		if(isWinnable(symbolCount, spaceCount)) {
			winnablePlays++;
		}
    	symbolCount = 0;
    	spaceCount = 0;
    	
    	//check top right to bottom left
    	int j = 2;
    	for(int i = 0; i < BOARD_LEN; i++) {
			if(board[j][i].getSymbol() == symbol) {
				symbolCount++;
			}
			else if(board[j][i].getSymbol() == 0) {
				spaceCount++;
			}
			j--;
    	}
		if(isWinnable(symbolCount, spaceCount)) {
			winnablePlays++;
		}
    	
    	return winnablePlays;
    }
    
}

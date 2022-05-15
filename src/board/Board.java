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
    
	private static final int BOARD_LEN = 3;
	
    private Square[][] board;
    
    private ArrayList<Square> remainingSpots;
    
    private Square[] log;

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
    
    public Square getRandomRemainingSpot() {
    	int index = (int)(Math.random() * remainingSpots.size());
    	return remainingSpots.get(index);
    }
    
    
    public Square getSquare(int i, int j) {
    	return board[i][j];
    }
    
    public Square getPastPlay(int turn) {
    	return log[turn - 1];
    }
    
    public void addToLog(int turn, int i, int j) {
    	log[turn - 1] = board[i][j];
    }
    
    public boolean isOpen(int i, int j) {
    	return board[i][j].getSymbol() == 0;
    }
    
    public void place(int i, int j, char symbol) {
    	if(!isOpen(i, j)) {
    		throw new IllegalArgumentException("Spot already has already been placed in");
    	}
    	
    	board[i][j].setSymbol(symbol);
    	remainingSpots.remove(getSquare(i, j));
    }
    
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
    
    public char getPosition(int i, int j) {
    	return board[i][j].getSymbol();
    }
    
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
    
    private boolean isWinnable(int symbolCount, int spaceCount) {
    	return symbolCount == 2 && spaceCount == 1;
    }
    
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

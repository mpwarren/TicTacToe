package board;

/**
 * 
 *  Board Coordinates:
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
 *   1 | 2 | BOARD_LEN  
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
	
    private char[][] board;

    public Board(){
        board = new char[BOARD_LEN][BOARD_LEN];
    }
    
    public boolean isOpen(int i, int j) {
    	return board[i][j] == 0;
    }
    
    public void place(int i, int j, char symbol) {
    	if(!isOpen(i, j)) {
    		throw new IllegalArgumentException("Spot already has already been placed in");
    	}
    	
    	board[i][j] = symbol;
    }
    
    public void printBoard(){
        for(int i = 0; i < BOARD_LEN; i++) {
        	for(int j = 0; j < BOARD_LEN; j++) {
        		System.out.print(" " + (board[i][j] == 0 ? " " : board[i][j]));
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
    		char symbol = board[i][0];
    		if(symbol != 0 && board[i][1] == symbol && board[i][2] == symbol) {
    			return symbol;
    		}
    	}
    	
    	//Check cols
    	for(int i = 0; i < BOARD_LEN; i++) {
    		char symbol = board[0][i];
    		if(symbol != 0 && board[1][i] == symbol && board[2][i] == symbol) {
    			return symbol;
    		}
    	}
    	
    	//check top left to bottom right
    	if(board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
    		return board[0][0];
    	}
    	
    	//check top right to bottom left
    	if(board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
    		return board[0][2];
    	}
    	
    	return 0;
    }
    
    public char getPosition(int i, int j) {
    	return board[i][j];
    }
    
    public int[] canWin(char symbol) {
    	int symbolCount = 0;
    	int spaceCount = 0;
    	int[] spacePos = new int[2];
    	
    	//check rows
    	for(int i = 0; i < BOARD_LEN; i++) {
    		for(int j = 0; j < BOARD_LEN; j++) {
    			if(board[i][j] == symbol) {
    				symbolCount++;
    			}
    			else if(board[i][j] == 0) {
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
    			if(board[j][i] == symbol) {
    				symbolCount++;
    			}
    			else if(board[j][i] == 0) {
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
			if(board[i][i] == symbol) {
				symbolCount++;
			}
			else if(board[i][i] == 0) {
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
			if(board[j][i] == symbol) {
				symbolCount++;
			}
			else if(board[j][i] == 0) {
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
    
}

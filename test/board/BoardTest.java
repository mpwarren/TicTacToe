package board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BoardTest {
	
	@Test
	void testPlaceAndIsOpen() {
		Board board = new Board();
		board.place(0, 0, 'X');
		board.place(0, 1, 'O');
		board.place(0, 2, 'X');
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(i == 0) {
					assertFalse(board.isOpen(i, j));
				}
				else {
					assertTrue(board.isOpen(i, j));
				}
			}
		}
	}

	@Test
	void testCheckWinner() {
		Board board = new Board();
		assertEquals(0, board.checkWinner());
		
		board.place(0, 0, 'X');
		board.place(0, 1, 'X');
		board.place(0, 2, 'X');
		
		assertEquals('X', board.checkWinner());
		
		//check cols
		board = new Board();
		board.place(1, 0, 'O');
		board.place(1, 1, 'O');
		board.place(1, 2, 'O');
		
		assertEquals('O', board.checkWinner());
		
		board = new Board();
		board.place(0, 0, 'O');
		board.place(1, 0, 'O');
		board.place(2, 0, 'O');
		board.place(0, 1, 'X');
		board.place(0, 2, 'X');
		
		assertEquals('O', board.checkWinner());
		
		//check diag
		
		board = new Board();
		board.place(0, 0, 'X');
		board.place(1, 1, 'X');
		board.place(2, 2, 'X');
		
		assertEquals('X', board.checkWinner());

		board = new Board();
		board.place(0, 2, 'X');
		board.place(1, 1, 'X');
		board.place(2, 0, 'X');
		
		assertEquals('X', board.checkWinner());
		
	}

	@Test
	void testGetPosition() {
		Board board = new Board();
		board.place(1,1,'X');
		assertEquals('X', board.getPosition(1,1));
	}

	@Test
	void testCanWin() {
		Board board = new Board();
		assertNull(board.canWin('X'));
		
		//row
		board = new Board();
		board.place(0, 0, 'X');
		board.place(0, 2, 'X');
		int[] pos = board.canWin('X');
		assertEquals(0, pos[0]);
		assertEquals(1, pos[1]);
		
		//col
		board = new Board();
		board.place(0, 1, 'X');
		board.place(1, 1, 'X');
		pos = board.canWin('X');
		assertEquals(2, pos[0]);
		assertEquals(1, pos[1]);
		
		//diag (top left to bot right)
		board = new Board();
		board.place(0, 0, 'X');
		board.place(1, 1, 'X');
		pos = board.canWin('X');
		assertEquals(2, pos[0]);
		assertEquals(2, pos[1]);
		
		//diag (top right to bot left)
		board = new Board();
		board.place(0, 2, 'X');
		board.place(2, 0, 'X');
		pos = board.canWin('X');
		assertEquals(1, pos[0]);
		assertEquals(1, pos[1]);
		
		//mismatch
		board = new Board();
		board.place(0, 0, 'X');
		board.place(1, 1, 'O');
		assertNull(board.canWin('X'));
	}

}

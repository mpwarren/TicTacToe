package board;

import java.util.Objects;

public class Square {
	private char symbol;
	
	private boolean isCorner;
	
	private int iPos;
	
	private int jPos;
	
	public Square (int iPos, int jPos, boolean isCorner) {
		setSymbol((char)0);
		this.iPos = iPos;
		this.jPos = jPos;
		this.isCorner = isCorner;
	}
	
	public boolean isCorner() {
		return isCorner;
	}
	
	public int getIPos() {
		return iPos;
	}
	
	public int getJPos() {
		return jPos;
	}
	
	public char getSymbol() {
		return symbol;
	}
	
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(symbol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Square other = (Square) obj;
		return symbol == other.symbol;
	}
}

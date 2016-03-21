package chess;

import java.awt.Panel;

public class Move {
	
	public static int fromRow, fromColumn, toRow, toColumn;
	
	public Move() {
		
	}

	public Move(int fromRow, int fromColumn, int toRow, int toColumn) {
		this.fromRow = fromRow;
		this.fromColumn = fromColumn;
		this.toRow = toRow;
		this.toColumn = toColumn;
	}
}

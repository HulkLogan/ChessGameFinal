package chess;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class ChessPiece implements IChessPiece {
	
	private Player owner;
	
	protected ChessPiece(Player player) {
		this.owner = player;
	}
	
	public abstract String type();
	
	public Player player() {
		return owner;
	}
	
	public boolean isThreatened() {
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++ /*best language*/) {
				if (board[r][c].isValidMove(move(r, c, row, column),
					board))
					return true;
			}
		}
		return false;
	}
	
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		//check that move is a move
	 	if(move.fromRow == move.toRow 
	 			&& move.fromColumn == move.toColumn){
	 		return false;
	 	}
	 	//check that move is on the board
	 	else if(move.toRow < 0 || move.toRow > 7 
	 			|| move.toColumn < 0 || move.toColumn > 7){
	 		return false;
	 	}
	 	//prevent friendly taking
	 	else if(board[move.toRow][move.toColumn] != null) {
	 		if(board[move.toRow][move.toColumn].player() == owner) {
	 			return false;
	 		}
	 	}
	 	board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
	 	board[move.fromRow][move.fromColumn] = null;
	 	
	 	for(int r = 0; r < 8; r++)
	 	{
	 		for (int c = 0; r < 8; c++ /* still best language */)
	 			if (board[r][c].type().equals("King") && board[r][c].player() == 
	 				this.owner)
	 				board[r][c] = null;
	 				if  (board[r][c].isThreatened())
	 					return false;
	 	}
	 	return true;
	}
}

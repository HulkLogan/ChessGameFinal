package chess;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
/******************************************************************
 * The specific variations to ChessPiece that make a Pawn 
 * @author Logan R. Crowe, Jake Young, Henry McDonough
 *****************************************************************/
public class Pawn extends ChessPiece {
	
	private Player owner;
	public boolean hasMoved = false;

	protected Pawn(Player player) {
		super(player);
		this.owner = player;
	}
	
	public String type(){
		return "Pawn";
	}
	
	public Player player() {
		return owner;
	}
	
	/*****************************************************************
	 * Returns whether a move is allowed for a Pawn
	 *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		if(super.isValidMove(move, board)) {
			if(board[move.fromRow][move.fromColumn].player() == Player.BLACK) {
				//check if blocked
				if(board[move.fromRow+1][move.fromColumn] != null) {
					//check for attack
					if(move.toRow - move.fromRow == 1 && Math.abs(move.fromColumn - move.toColumn) == 1) {
						//check for enemy piece
						if(board[move.toRow][move.toColumn] != null && 
								board[move.toRow][move.toColumn].player() == Player.WHITE) {
							return true;
						}
						return false;
					}
					return false;
				}
				else if(hasMoved == true) {
					//black normal move
					if((move.toRow - move.fromRow == 1)
							&& move.fromColumn == move.toColumn) {
						return true;
					}
					//black attack after hasMoved
					else if(move.toRow - move.fromRow == 1 && Math.abs(move.toColumn - move.fromColumn) == 1) {
						if(board[move.toRow][move.toColumn] != null && 
								board[move.toRow][move.toColumn].player() == Player.WHITE) {
							return true;
						}
						return false;
					}
					return false;
				}
				else {
					//black first move
					if((move.toRow - move.fromRow < 3 ) && move.toColumn == move.fromColumn) {
						//hasMoved = true;
						return true;
					}
					//black attack first move
					else if(move.toRow - move.fromRow == 1 && Math.abs(move.toColumn - move.fromColumn) == 1) {
						if(board[move.toRow][move.toColumn] != null && 
								board[move.toRow][move.toColumn].player() == Player.WHITE) {
							//hasMoved = true;
							return true;
						}
						return false;
					}
					return false;
				}
			}
			else {
				if(move.fromRow > 0)
				//check if blocked
				if(board[move.fromRow-1][move.fromColumn] != null) {
					//check for attack
					if(move.fromRow - move.toRow == 1 && Math.abs(move.fromColumn - move.toColumn) == 1) {
						//check for enemy piece
						if(board[move.toRow][move.toColumn] != null && 
								board[move.toRow][move.toColumn].player() == Player.BLACK) {
							return true;
						}
						return false;
					}
					return false;
				}
				else if(hasMoved == true) {
					//white normal move
					if((move.fromRow - move.toRow == 1)
							&& move.fromColumn == move.toColumn) {
						return true;
					}
					//white attack
					else if(move.fromRow - move.toRow == 1 && Math.abs(move.fromColumn - move.toColumn) == 1) {
						if(board[move.toRow][move.toColumn] != null && 
								board[move.toRow][move.toColumn].player() == Player.BLACK) {
							return true;
						}
						return false;
					}
					return false;
				}
				else {
					//white first move
					if(move.fromRow - move.toRow < 3 && move.toColumn == move.fromColumn) {
						hasMoved = true;
						return true;
					}
					//white attack first move
					else if(move.fromRow - move.toRow == 1 && Math.abs(move.fromColumn - move.toColumn) == 1) {
						if(board[move.toRow][move.toColumn] != null && 
								board[move.toRow][move.toColumn].player() == Player.BLACK) {
							hasMoved = true;
							return true;
						}
						return false;
					}
					return false;
				}
			}
		}
		return false;
	}
	
	public boolean getHasMoved() {
		return hasMoved;
	}

	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
}

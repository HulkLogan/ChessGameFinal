package chess;
/******************************************************************
 * The specific variations to ChessPiece that make a Bishop.  
 * @author Logan R. Crowe, Jake Young, Henry McDonough
 *****************************************************************/
public class Bishop extends ChessPiece {
	
	private Player owner;
	
	protected Bishop(Player player) {
		super(player);
		this.owner = player;
	}
	
	public String type(){
		return "Bishop";
	}
	
	public Player player() {
		return owner;
	}
	
	/*****************************************************************
	 * Returns whether a move is allowed for a Bishop
	 *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		if(super.isValidMove(move, board) == false) {
			return false;
		}
		if (!(Math.abs(move.fromRow - move.toRow) == Math.abs(move.fromColumn - move.toColumn))) {
			return false;
		} 
		if(move.toRow > move.fromRow) {
			//top right
			if(move.toColumn > move.fromColumn) {
				for(int j = move.fromRow + 1, i = move.fromColumn + 1; j != move.toRow && i != move.toColumn; j++, i++) {
					if(board[j][i] != null) {
						return false;
					}
				}
			}
			//bottom right
			if(move.toColumn < move.fromColumn) {
				for(int j = move.fromRow + 1, i = move.fromColumn - 1; j != move.toRow && i != move.toColumn; j++, i--) {
					if(board[j][i] != null) {
						return false;
					}
				}
			}
		}
		if(move.toRow < move.fromRow) {
			//top left
			if(move.toColumn > move.fromColumn) {
				for(int j = move.fromRow - 1, i = move.fromColumn + 1; j != move.toRow && i != move.toColumn; j--, i++) {
					if(board[j][i] != null) {
						return false;
					}
				}
			}
			//bottom left
			if(move.toColumn < move.fromColumn) {
				for(int j = move.fromRow - 1, i = move.fromColumn - 1; j != move.toRow && i != move.toColumn; j--, i--) {
					if(board[j][i] != null) {
						return false;
					}
				}
			}
		}
		return true;
	}
}

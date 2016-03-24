package chess;
/******************************************************************
 * The specific variations to ChessPiece that make a Queen.  
 * @author Logan R. Crowe, Jake Young, Henry McDonough
 *****************************************************************/
public class Queen extends ChessPiece {
	
	private Player owner;

	protected Queen(Player player) {
		super(player);
		this.owner = player;
	}

	public String type(){
		return "Queen";
	}
	
	public Player player() {
		return owner;
	}
	
	/*****************************************************************
	 * Returns whether a move is allowed for a Queen
	 *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		if(super.isValidMove(move, board) == false) {
			return false;
		}
		if(!(Math.abs(move.fromRow - move.toRow) 
				== Math.abs(move.fromColumn - move.toColumn))) {
			if(!(move.fromRow == move.toRow 
					|| move.fromColumn == move.toColumn)) {
				return false;
			}
		}
		if(move.toRow > move.fromRow) {
			//southeast
			if(move.toColumn > move.fromColumn) {
				for(int j = move.fromRow + 1, i = move.fromColumn + 1; j != move.toRow && i != move.toColumn; j++, i++) {
					if(board[j][i] != null) {
						return false;
					}
				}
			}
			//south
			if(move.toColumn==move.fromColumn) {
				for(int i = move.fromRow + 1; i != move.toRow; i++) {
					if(board[i][move.fromColumn] != null) {
						return false;
					}
				}
			}
			//southwest
			if(move.toColumn < move.fromColumn) {
				for(int j = move.fromRow + 1, i = move.fromColumn - 1; j != move.toRow && i != move.toColumn; j++, i--) {
					if(board[j][i] != null) {
						return false;
					}
				}
			}
		}
		if(move.toRow < move.fromRow) {
			//northeast
			if(move.toColumn > move.fromColumn) {
				for(int j = move.fromRow - 1, i = move.fromColumn + 1; j != move.toRow && i != move.toColumn; j--, i++) {
					if(board[j][i] != null) {
						return false;
					}
				}
			}
			//north
			if(move.toColumn == move.fromColumn) {
				for(int i = move.toRow+1; i!=move.fromRow; i++) {
					if(board[i][move.fromColumn] != null) {
						return false;
					}
				}
			}
			//northwest
			if(move.toColumn < move.fromColumn) {
				for(int j = move.fromRow - 1, i = move.fromColumn - 1; j != move.toRow && i != move.toColumn; j--, i--) {
					if(board[j][i] != null) {
						return false;
					}
				}
			}
		}
		if(move.toRow == move.fromRow) {
			//east
			if(move.toColumn > move.fromColumn) {
				for(int i = move.fromColumn + 1; i != move.toColumn; i++) {
					if(board[move.fromRow][i] != null) {
						return false;
					}
				}
			}
			//west
			if(move.toColumn < move.fromColumn) {
				for(int i = move.toColumn + 1; i != move.fromColumn; i++) {
					if(board[move.fromRow][i] != null) {
						return false;
					}
				}
			}
		}
		return true;
	}

}

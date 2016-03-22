package chess;
/******************************************************************
 * The specific variations to ChessPiece that make a Knight.  
 * @author Logan R. Crowe, Jake Young, Henry McDonough
 *****************************************************************/
public class Knight extends ChessPiece {

	protected Knight(Player player) {
		super(player);
		this.owner = player;
	}

	private Player owner;
	
	
	
	public String type(){
		return "Knight";
	}
	
	public Player player() {
		return owner;
	}
	/*****************************************************************
	 * Returns whether a move is allowed for a Knight
	 *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		if(super.isValidMove(move, board)) {
			//checks that the move is either two(2) rows and one(1) column or 
			//two(2) columns and one(1) row
			if( ((Math.abs(move.fromRow - move.toRow) == 2) 
					&& (Math.abs(move.fromColumn - move.toColumn) == 1)) 
					|| ((Math.abs(move.fromColumn - move.toColumn) == 2) 
							&& (Math.abs(move.fromRow - move.toRow) == 1))){
				return true;
			}
			
		}
		else {
			return false;
		}
		return false;
	}
}

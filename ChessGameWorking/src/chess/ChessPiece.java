package chess;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/******************************************************************
 * The abstract ChessPiece allows for extensions into the various 
 * needed pieces, King, Queen, Pawn, etc..  
 * @author Logan R. Crowe, Jake Young, Henry McDonough
 *****************************************************************/
public abstract class ChessPiece implements IChessPiece {
	
	/*Player variable that holds this pieces owner*/
	private Player owner;
	
	/*holds whether a piece has move or not*/
	protected boolean hasMoved;
	
	/*sets the owner variable for this piece*/
	protected ChessPiece(Player player) {
		this.owner = player;
	}
	
	/*holds the String listing the type of piece*/
	public abstract String type();
	
	/*returns the owner of the current piece*/
	public Player player() {
		return owner;
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
	 	return true;
	}
}


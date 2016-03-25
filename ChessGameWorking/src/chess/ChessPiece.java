package chess;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/******************************************************************
 * The abstract ChessPiece allows for extensions into the various 
 * needed pieces, King, Queen, Pawn, etc..  
 * @author Logan R. Crowe, Jake Young, Henry McDonough
 *****************************************************************/
public abstract class ChessPiece implements IChessPiece {
	
	private int myRow;
	private int myCol;
	
	/*Player variable that holds this pieces owner*/
	private Player owner;
	
	/*holds whether a piece has move or not*/
	protected boolean hasMoved;
	
	/*sets the owner variable for this piece*/
	protected ChessPiece(Player player, int row, int col) {
		this.owner = player;
		myRow = row;
		myCol = col;
		hasMoved = false;
	}
	
	/*sets hasMoved to true*/
	public void isNowMoved() {
		hasMoved = true;
	}
	
	/*holds the String listing the type of piece*/
	public abstract String type();
	
	/*returns the owner of the current piece*/
	public Player player() {
		return owner;
	}
	
	/*******************************************************************
	 * Checks if the piece is threatened.
	 * @return false - the piece is not threatened
	 * @return true - the piece is threatened
	 ******************************************************************/
	public boolean isThreatened(IChessPiece[][] board) {
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (board[r][c].isValidMove(new Move(r, c, myRow, myCol),
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
	 	
	 	/* Commenting this out: the isValidMove() in ChessModel takes care of actually
	 	 * governing whether or not a particular piece can make a particular move.
	 	 * For the purposes of the squareIsThreatened() method there, the isValidMove()
	 	 * method in this and other subclasses of ChessPiece disregards check.
	 	 
	 	board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
	 	board[move.fromRow][move.fromColumn] = null;
	 	 
	 	//remove own king from board parameter, preventing recursion if it's
	 	//necessary to check a spot attacked by both kings
	 	for(int r = 0; r < 8; r++){
	 		for (int c = 0; r < 8; c++)
	 			if (board[r][c].type().equals("King") && board[r][c].player() == 
	 				this.owner)
	 				board[r][c] = null;
	 				if  ((ChessPiece) board[r][c].isThreatened())
	 					return false;
	 	}
	 	return true;
	 	*/
	}
}

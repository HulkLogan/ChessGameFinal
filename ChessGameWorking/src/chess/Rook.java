package chess;

/******************************************************************
 * The specific variations to ChessPiece that make a Rook.  
 * @author Logan R. Crowe, Jake Young, Henry McDonough
 *****************************************************************/
public class Rook extends ChessPiece {
	
	protected int myRow;
	protected int myCol;

	protected Rook(Player player, row, col) {
		super(player);
		this.owner = player;
		
		myRow = row;
		myCol = col;
	}

	private Player owner;

	public String type() {
		return "Rook";
	}

	public Player player() {
		return owner;
	}
	/*****************************************************************
	 * Returns whether a move is allowed for a Rook
	 *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		/*holds a step value*/
		int stepValue = 0;	
		//check the super 
		if (super.isValidMove(move, board)) {
			//check that its a valid Rook move
			if (!(move.fromRow == move.toRow || move.fromColumn == move.toColumn)) {
				return false;
			} 
			else {
				//check that to move is along a row
				if (move.toRow - move.fromRow != 0) {
					stepValue = (-(move.fromRow - move.toRow)/ Math.abs(move.fromRow - move.toRow));
					for (int i = (move.fromRow + stepValue); i != move.toRow; i = i + stepValue) {
						if (board[i][move.fromColumn] != null) {
							return false;
						}
					}
					return true;
				}
				else{
					stepValue = (-(move.fromColumn - move.toColumn)/ Math.abs(move.fromColumn - move.toColumn));
					for (int i = (move.fromColumn + stepValue); i != move.toColumn; i = i + stepValue) {
						if (board[move.fromRow][i] != null) {
							return false;
						}
					}
					return true;
				}
			}
		} 
		else {
			return false;
		}
	}
}

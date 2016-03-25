package chess;
/******************************************************************
 * The specific variations to ChessPiece that make a Queen.  
 * @author Logan R. Crowe, Jake Young, Henry McDonough
 *****************************************************************/
public class Queen extends ChessPiece {
	
	private Player owner;
	
	protected int myRow;
	protected int myCol;

	protected Queen(Player player, int row, int col) {
		super(player, row, col);
		this.owner = player;
		
		myRow = row;
		myCol = col;
		hasMoved = false;
	}
	
	public boolean hasMoved() {
		return super.hasMoved();
	}

	public String type(){
		return "Queen";
	}
	
	protected boolean hasMoved;
	
	public void isNowMoved() {
		hasMoved = true;
	}
	
	public boolean hasMoved() {
		return hasMoved;
	}
	
	/*****************************************************************
	 * Returns whether a move is allowed for a Queen
	 *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		if(super.isValidMove(move, board)) {
			if(!(Math.abs(move.fromRow - move.toRow) 
					== Math.abs(move.fromColumn - move.toColumn))) {
				if(!(move.fromRow == move.toRow 
						|| move.fromColumn == move.toColumn)) {
					return false;
				}
			}
		}
		return true;
	}

}

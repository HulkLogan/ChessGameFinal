package chess;

/******************************************************************
 * The specific variations to ChessPiece that make a King.  
 * @author Logan R. Crowe, Jake Young, Henry McDonough
 *****************************************************************/
public class King extends ChessPiece{
  
	private Player owner;
	
	protected int myRow;
	protected int myCol;
	
	protected King(Player player, int row, int col) {
		super(player);
		this.owner = player;
		
		myRow = row;
		myCol = col;
	}
	
	public String type(){
		return "King";
	}
	
	public Player player() {
		return owner;
	}
	
	/*****************************************************************
	 * Returns whether a move is allowed for a King
	 *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		int firstRank = (this.owner == Player.WHITE) ? 0 : 7;
		if(super.isValidMove(move, board)) {
			if (!this.hasMoved && Math.abs(move.toColumn - move.fromColumn) == 2 &&
				move.toRow == firstRank) {
				int rookCol;
				rookCol = move.toColumn > move.fromColumn ? 7 : 0;
				if (!board[firstRank][0].hasMoved()) {
					for (int n = 4; n != rookCol; n += (rookCol / 3) - 1)
					/* 7 / 3 == 2
					 * 2 - 1 == 1
					 *
					 * 0 / 0 == 0
					 * 0 - 1 == -1 */
					{
						if (board[firstRank][n] != null || (ChessPiece)
							|| (board[firstRank][n].isThreatened(board) &&
							Math.abs(n-4) <= 2))
							return false;
					}
				}
			}

			if (Math.abs(move.toRow - move.fromRow) > 1 || 
					Math.abs(move.toColumn - move.fromColumn) > 1) {
				return false;
			}
			else {
				return true;
			}
		}
		return true;
	}
}

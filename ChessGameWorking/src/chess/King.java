package chess;

public class King extends ChessPiece{
  
private Player owner;
	
	protected King(Player player) {
		super(player);
		this.owner = player;
	}
	
	public String type(){
		return "King";
	}
	
	public Player player() {
		return owner;
	}
	
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		int firstRank = (this.owner == Player.WHITE) ? 0 : 7;
		if(super.isValidMove(move, board)) {
			if (!this.hasMoved && Math.abs(move.toColumn - move.fromColumn) == 2 &&
				move.toRow == firstRank)
				boolean kingside == move.toColumn > 4;
				if (!(ChessPiece) board[firstRank][0].hasMoved()) {
					for (int n = 4; n != kingside ? 8 : -1; n += kingside ? 1 : -1)
					{
						if (board[firstRank][n] != null || (ChessPiece)
							|| (board[firstRank][n].isThreatened() &&
							Math.abs(n-4) <= 2))
							return false;
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

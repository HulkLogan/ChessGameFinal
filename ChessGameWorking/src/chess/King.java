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
		if(super.isValidMove(move, board)) {
			if (Math.abs(move.toRow - move.fromRow) > 1 || 
					Math.abs(move.toColumn - move.fromColumn) > 1) {
				return false;
			}
			else {
				return true;
			}
		}
		return false;
	}
}

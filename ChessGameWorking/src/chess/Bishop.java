package chess;

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
	
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		int stepValue = 0;
		if(super.isValidMove(move, board)) {
			//check that the move is diagonal from the current spot
			if(!(Math.abs(move.fromRow - move.toRow) 
					== Math.abs(move.fromColumn - move.toColumn))){
				return false;
			}
			else {
				//check that to move is along a row
				if (move.toRow - move.fromRow != 0) {
					stepValue = (-(move.fromRow - move.toRow)/ Math.abs(move.fromRow - move.toRow));
					for (int i = (move.fromRow + stepValue); i != move.toRow; i = i + stepValue) {
						if (board[i][i] != null) {
							return false;
						}
					}
					return true;
				}
				else{
					stepValue = (-(move.fromColumn - move.toColumn)/ Math.abs(move.fromColumn - move.toColumn));
					for (int i = (move.fromColumn + stepValue); i != move.toColumn; i = i + stepValue) {
						if (board[i][i] != null) {
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

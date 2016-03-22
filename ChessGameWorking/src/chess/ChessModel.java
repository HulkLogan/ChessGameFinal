package chess;
/******************************************************************
 * ChessModel() does the bulk of the work for ChessGame. The board
 * and all of the pieces are created and held here, and most of the
 * more complicated game elements are located here. 
 * @author Logan R. Crowe, Jake Young, Henry McDonough
 *****************************************************************/
import java.awt.Panel;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ChessModel implements IChessModel {
	
	/**array to hold the board pieces*/
	private IChessPiece[][] board;
	
	/**the current Player**/
	private Player player;
	
	/**the current piece*/
	public IChessPiece currentPiece;
	
	/**holds the number of pieces taken during the game*/
	public int takenBlackKnight = 0;
	public int takenWhiteKnight = 0;
	public int takenBlackBishop = 0;
	public int takenWhiteBishop = 0;
	public int takenBlackRook = 0;
	public int takenWhiteRook = 0;
	public int takenBlackPawn = 0;
	public int takenWhitePawn = 0;
	public int takenBlackQueen = 0;
	public int takenWhiteQueen = 0;

	/*****************************************************************
	 * The main method sets up the board and all of the pieces
	 *****************************************************************/
	public ChessModel() {
		board = new IChessPiece[8][8];
		player = Player.WHITE;
		
		//-----back row for Black---------//
		board[0][0] = new Rook(Player.BLACK);
		board[0][1] = new Knight(Player.BLACK);
		board[0][2] = new Bishop(Player.BLACK);
		board[0][3] = new Queen(Player.BLACK);
		board[0][4] = new King(Player.BLACK);
		board[0][5] = new Bishop(Player.BLACK);
		board[0][6] = new Knight(Player.BLACK);
		board[0][7] = new Rook(Player.BLACK);
		//---------Black pawns------------//
		board[1][0] = new Pawn(Player.BLACK);
		board[1][1] = new Pawn(Player.BLACK);
		board[1][2] = new Pawn(Player.BLACK);
		board[1][3] = new Pawn(Player.BLACK);
		board[1][4] = new Pawn(Player.BLACK);
		board[1][5] = new Pawn(Player.BLACK);
		board[1][6] = new Pawn(Player.BLACK);
		board[1][7] = new Pawn(Player.BLACK);
		
		//-----back row for White---------//
		board[7][0] = new Rook(Player.WHITE);
		board[7][1] = new Knight(Player.WHITE);
		board[7][2] = new Bishop(Player.WHITE);
		board[7][3] = new Queen(Player.WHITE);
		board[7][4] = new King(Player.WHITE);
		board[7][5] = new Bishop(Player.WHITE);
		board[7][6] = new Knight(Player.WHITE);
		board[7][7] = new Rook(Player.WHITE);
		//---------White pawns------------//
		board[6][0] = new Pawn(Player.WHITE);
		board[6][1] = new Pawn(Player.WHITE);
		board[6][2] = new Pawn(Player.WHITE);
		board[6][3] = new Pawn(Player.WHITE);
		board[6][4] = new Pawn(Player.WHITE);
		board[6][5] = new Pawn(Player.WHITE);
		board[6][6] = new Pawn(Player.WHITE);
		board[6][7] = new Pawn(Player.WHITE);
		
		//finish
	}
	
	/*****************************************************************
	 * For the game to be over, one player must have a king in check,
	 * be unable to move it out of check, and unable to remove the 
	 * threat. 
	 * @return false - game is not complete
	 * @return true - game is complete
	 *****************************************************************/
	@Override
	public boolean isComplete() {
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (pieceAt(r, c).type() == "King" 
						&& pieceAt(r,c).player() == currentPlayer()
						&& squareIsThreatened(r,c)
						&& !kingCanMove(r,c)
						&& !canRemoveThreat(r,c)){
					return true;
				}
				else {
					return false;
				}
			}
		}
		return false;
	}
	
	/****************************************************************
	 * Asks the piece if it's a valid move.
	 * @param move - a Move that need to be verified for validity
	 * @return false - move is not a valid Move
	 * @return true - move is a valid Move
	 ****************************************************************/
	@Override
	public boolean isValidMove(Move move) {
		if(pieceAt(move.fromRow, move.fromColumn).isValidMove(move, board)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/****************************************************************
	 * Moves the piece. 
	 * @param move - the Move that needs to be performed
	 ****************************************************************/
	@Override
	public void move(Move move) {
		if(isValidMove(move)) {
			// Castle
			if (pieceAt(move.fromRow, move.fromColumn).type() == "King" && 
				Math.abs(move.fromColumn - move.toColumn) == 2) {
				board[move.toRow][move.toColumn > 4 ? move.fromColumn + 1 : move.fromColumn - 1]
					= board[move.toRow][move.toColumn > 4 ? 7 : 0];
				board[move.toRow][move.toColumn > 4 ? 7 : 0] = null;
			}
			if(pieceAt(move.toRow, move.toColumn) != null) {
				board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
				setNextPlayer();
			}
			board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
			board[move.fromRow][move.fromColumn] = null;
			(ChessPiece) board[move.toRow][move.toColumn].isNowMoved(); 
			setNextPlayer();
		}
		else {
			JOptionPane.showMessageDialog(null, "Not a valid move.");
		}
		currentPiece = null;
	}
	
	/****************************************************************
	 * Verifies if player p's King is in check
	 * @param p - the player that need to verifiy if they are checked
	 * @return false - if the player is not in check
	 * @return true - if the player is in check
	 ****************************************************************/
	@Override
	public boolean inCheck(Player p){
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (pieceAt(r, c).type() == "King" 
					&& pieceAt(r,c).player() == p
					&& squareIsThreatened(r,c)){
					return true;
				}
			}
		}
		return false;
	}
	
	/****************************************************************
	 * Check if a certain square is threatened.
	 * @param Row - int that is the squares row location
	 * @param Col - int that is the squares column location
	 * @return false - the square is not threatened
	 * @return true - the square is threatened
	 ****************************************************************/
	public boolean squareIsThreatened(int Row, int Col) {
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				Move temp = new Move(r, c, Row, Col);
				if (pieceAt(r, c).isValidMove(temp,
						board))
					return true;
			}
		}
		return false;
	}
	
	/****************************************************************
	 * Checks to see if the King can move.
	 * @param kingRow - int for the King's row location
	 * @param kingCol - int for the King's column location
	 * @return false - the King can not move
	 * @return true - the King can move
	 ****************************************************************/
	public boolean kingCanMove(int kingRow, int kingCol){
		boolean canMove = false;
		for(int a = kingRow-1; a <= kingRow+1; a++) {
			for(int b = kingCol-1; b <= kingCol+1; b++) {
				if(!squareIsThreatened(a,b) 
					&& (a != kingRow) 
					&& (b != kingCol)){
						canMove = true;
				}
			}
		}
		return canMove;
	}
	
	/****************************************************************
	 * Checks to see if a piece that is threatening a King can be 
	 * removed and if that was the only threat.
	 * @param kingRow - int for the King's row
	 * @param kingCol - int for the King's column
	 * @return false - removing a threatening piece does not remove 
	 * the threat
	 * @return true - removing a threatening piece does remove the
	 * threat
	 ****************************************************************/
	public boolean canRemoveThreat(int kingRow, int kingCol){
		IChessPiece[][] temp = board;
		boolean canRemove = false;
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				Move x = new Move(r, c, kingRow, kingCol);
				if (pieceAt(r, c).isValidMove(x, board)){
					temp[r][c] = null;
					if(!squareIsThreatened(kingRow,kingCol)){
						canRemove = true;
					}
				}
			}
		}
		return canRemove;
	}

	/****************************************************************
	 * The current Player
	 * @return currentPlayer() - current player
	 ****************************************************************/
	@Override
	public Player currentPlayer() {
		return player;
	}
	
	/****************************************************************
	 * Stores the number of pieces that are removed.
	 * @param x - IChessPiece that is to be removed
	 ****************************************************************/
	public void removePiece(IChessPiece x) {
		if (x.player() == Player.WHITE) {
			switch (x.type())
				case "Knight":	takenWhiteKnight++;
				break;
				case "Rook":	takenWhiteRook++;
				break;
				case "Bishop":	takenWhiteBishop++;
				break;
				case "Pawn":	takenWhitePawn++;
				break;
				case "Queen":	takenWhiteQueen++;
				break;
			
		} else if (x.player() == Player.BLACK) {
			switch (x.type())
				case "Knight":	takenBlackKnight++;
				break;
				case "Rook":	takenBlackRook++;
				break;
				case "Bishop":	takenBlackBishop++;
				break;
				case "Pawn":	takenBlackPawn++;
				break;
				case "Queen":	takenBlackQueen++;
				break;
		}
	}
	
	/**number of rows*/
	public int numRows() {
		return 8;
	}
	/**number of columns*/
	public int numColumns() {
		return 8;
	}
	
	public IChessPiece pieceAt(int row, int column) {
		return board[row][column];
	}
	
	public void setNextPlayer() {
		player = player.next();
	}
	
	public void setCurrentPiece(IChessPiece p) {
		currentPiece = p;
	}
	
	public IChessPiece getCurrentPiece() {
		return currentPiece;
	}

	public int getTakenBlackKnight() {
		return takenBlackKnight;
	}

	public int getTakenWhiteKnight() {
		return takenWhiteKnight;
	}

	public int getTakenBlackBishop() {
		return takenBlackBishop;
	}

	public int getTakenWhiteBishop() {
		return takenWhiteBishop;
	}

	public int getTakenBlackRook() {
		return takenBlackRook;
	}

	public int getTakenWhiteRook() {
		return takenWhiteRook;
	}
	
	public int getTakenBlackQueen() {
		return takenBlackQueen;
	}
	
	public int getTakenWhiteQueen() {
		return takenWhiteQueen;
	}

}

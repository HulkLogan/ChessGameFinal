package chess;

import java.awt.Panel;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ChessModel implements IChessModel {
	
	private IChessPiece[][] board;
	private Player player;
	public IChessPiece currentPiece;
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
	//declare other instance variables as needed
	
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

	@Override
	public boolean isComplete() {	
		for (int r = 0; r < 8; r++) 
			for (int c = 0; c < 8; c++) 
				if(pieceAt(r, c) != null) 
					if(pieceAt(r, c).type() == "King") 
						if(pieceAt(r, c).player() == currentPlayer()) 
							if(canKingMove(r, c, currentPlayer()) == false)
								if(canRemoveThreat(r, c, currentPlayer()) == false)
									return true;
		return false;
								
	}

	@Override
	public boolean isValidMove(Move move) {
		try {
			if(pieceAt(move.fromRow, move.fromColumn).isValidMove(move, board)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(NullPointerException e) {
			return false;
		}
	}

	@Override
	public void move(Move move) {
		if(isValidMove(move)) {
			if(pieceAt(move.toRow, move.toColumn) != null) {
				removePiece(pieceAt(move.toRow, move.toColumn));
				board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
			}
			board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
			board[move.fromRow][move.fromColumn] = null;
			promotion();
			setNextPlayer();
		}
		else {
			JOptionPane.showMessageDialog(null, "Not a valid move.");
		}
		currentPiece = null;
	}
	
	@Override
	public boolean inCheck(Player p){
		Move temp = new Move();
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if(board[r][c] != null) {
					if(pieceAt(r, c).type() == "King" && pieceAt(r, c).player() == p) {
						temp.toRow = r;
						temp.toColumn = c;
					}
				}
			}
		}
		for(int a = 0; a < 8; a++) {
			for(int b = 0; b < 8; b++) {
				if(board[a][b] != null) {
					if(pieceAt(a, b).player() != p) {
						temp.fromRow = a;
						temp.fromColumn = b;
						if(pieceAt(a, b).isValidMove(temp, board)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}


	public boolean squareIsThreatened(int Row, int Col, Player p) {
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				if(pieceAt(a, b) != null) {
					if(pieceAt(a, b).player() != p) {
						Move temp = new Move(a, b, Row, Col);
						if (pieceAt(a, b).isValidMove(temp, board))
							return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean canKingMove(int row, int col, Player p) {
		for(int r = row-1; r <= row+1; r++) {
			for(int c = col-1; c <= col+1; c++) {
				if(r < 8 && r > -1 && c < 8 && c > -1) {
					if(pieceAt(r, c) != null) {
						if(pieceAt(r, c).player() != currentPlayer()) {
							if(squareIsThreatened(r, c, p) == false) {
								return true;
							}
							return false;
						}
					}
					else {
						if(squareIsThreatened(r, c, p) == false) {
							return true;
						}
						return false;
					}
				}
			}
		}
		return false;
	}

	public boolean canRemoveThreat(int row, int col, Player p) {
//		//finds piece attacking king
//		for(int r = 0; r < 8; r++) {
//			for(int c = 0; c < 8; c++) {
//				if(pieceAt(r, c) != null && pieceAt(r, c).player() != p) {
//					Move temp = new Move(r, c, row, col);
//					if(pieceAt(r, c).isValidMove(temp, board)) {
//						//looks for friendly piece
//						for(int a = 0; a < 8; a++) {
//							for(int b = 0; b < 8; b++) {
//								if(pieceAt(a, b) != null && pieceAt(a, b).player() == p) {
//									IChessPiece savior = pieceAt(a, b);
//									//checks for block/capture
//									for(int x = 0; x < 8; x++) {
//										for(int y = 0; y < 8; y++) {
//											Move j = new Move(a, b, x, y);
//											if(savior.isValidMove(j, board) && !inCheck(p)) {
//												return true;
//											}
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//		return false;
		return false;
	}
	
	public void promotion() {
		for(int a = 0; a < 8; a++) {
			if(currentPlayer() == Player.WHITE) {
				//checks if correct piece
				if(pieceAt(0, a) != null && pieceAt(0, a).type() == "Pawn"
						&& pieceAt(0, a).player() == currentPlayer()) {
					IChessPiece temp = pieceAt(0, a);
					Object[] options = { "Rook", "Bishop", "Knight" };
					int n = JOptionPane.showOptionDialog(null, "What piece would you like?", "Promotion",
							JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
							options, options[0]);
					if(n == 0) {
						if(getTakenBlackRook() > 0) {
							temp = null;
							board[0][a] = new Rook(Player.WHITE);
						}
						else {
							JOptionPane.showMessageDialog(null, "You do not have any Rooks");
						}
					}
					if(n == 1) {
						if(getTakenBlackBishop() > 0) {
							temp = null;
							board[0][a] = new Bishop(Player.WHITE);
						}
						else {
							JOptionPane.showMessageDialog(null, "You do not have any Bishops");
						}
					}
					if(n == 2) {
						if(getTakenBlackKnight() > 0) {
							temp = null;
							board[0][a] = new Knight(Player.WHITE);
						}
						else {
							JOptionPane.showMessageDialog(null, "You do not have any Knights");
						}
					}
				}
			}
			if(currentPlayer() == Player.BLACK) {
				if(pieceAt(7, a) != null && pieceAt(7, a).type() == "Pawn"
						&& pieceAt(7, a).player() == currentPlayer()) {
					IChessPiece temp = pieceAt(7, a);
					Object[] options = { "Rook", "Bishop", "Knight" };
					int n = JOptionPane.showOptionDialog(null, "What piece would you like?", "Promotion",
							JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
							options, options[0]);
					if(n == 0) {
						if(getTakenWhiteRook() > 0) {
							temp = null;
							board[7][a] = new Rook(Player.BLACK);
						}
						else {
							JOptionPane.showMessageDialog(null, "You do not have any Rooks");
						}
					}
					if(n == 1) {
						if(getTakenWhiteBishop() > 0) {
							temp = null;
							board[7][a] = new Bishop(Player.BLACK);
						}
						else {
							JOptionPane.showMessageDialog(null, "You do not have any Bishops");
						}
					}
					if(n == 2) {
						if(getTakenWhiteKnight() > 0) {
							temp = null;
							board[7][a] = new Knight(Player.BLACK);
						}
						else {
							JOptionPane.showMessageDialog(null, "You do not have any Knights");
						}
					}
				}
			}
		}
	}
//	public void promotion(Player p) {
//		for(int a = 0; a < 8; a++) {
//			if(p == Player.WHITE) {
//				if(pieceAt(0, a) != null) {
//					if(pieceAt(0, a).type() == "Pawn") {
//						Object[] options = { "Rook", "Bishop", "Knight" };
//						int n = JOptionPane.showOptionDialog(null, "What piece would you like?", "Promotion",
//								JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
//								options, options[0]);
//						if(n == 0) {
//							if(getTakenBlackRook() > 0) {
//								x = new Rook(Player.WHITE);
//							}
//							else {
//								JOptionPane.showMessageDialog(null, "You do not have any Rooks");
//							}
//						}
//						if(n == 1) {
//							if(getTakenBlackBishop() > 0) {
//								x = new Bishop(Player.WHITE);
//							}
//							else {
//								JOptionPane.showMessageDialog(null, "You do not have any Bishops");
//							}
//						}
//						if(n == 2) {
//							if(getTakenBlackKnight() > 0) {
//								x = new Knight(Player.WHITE);
//							}
//							else {
//								JOptionPane.showMessageDialog(null, "You do not have any Knights");
//							}
//						}
//					}
//				}
//			}
//			if(p == Player.BLACK) {
//				if(x == board[7][a]) {
//					Object[] options = { "Rook", "Bishop", "Knight" };
//					int n = JOptionPane.showOptionDialog(null, "What piece would you like?", "Promotion",
//				    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
//				    options, options[0]);
//					if(n == 0) {
//						if(getTakenWhiteRook() > 0) {
//							x = new Rook(Player.BLACK);
//						}
//						else {
//							JOptionPane.showMessageDialog(null, "You do not have any Rooks");
//						}
//					}
//					if(n == 1) {
//						if(getTakenWhiteBishop() > 0) {
//								x = new Bishop(Player.BLACK);
//							}
//							else {
//								JOptionPane.showMessageDialog(null, "You do not have any Bishops");
//							}
//						}
//						if(n == 2) {
//							if(getTakenWhiteKnight() > 0) {
//								x = new Knight(Player.BLACK);
//							}
//							else {
//								JOptionPane.showMessageDialog(null, "You do not have any Knights");
//							}
//						}
//					}
//				}
//			}
//		}
//	}
	
	@Override
	public Player currentPlayer() {
		return player;
	}
	
	public void removePiece(IChessPiece x) {
		if (x.player() == Player.WHITE) {
			if (x.type() == "Knight") {
				takenWhiteKnight++;
			} 
			else if (x.type() == "Rook") {
				takenWhiteRook++;
			} 
			else if (x.type() == "Bishop") {
				takenWhiteBishop++;
			}
			else if(x.type() == "Pawn") {
				takenWhitePawn++;
			}
			else if(x.type() == "Queen") {
				takenWhiteQueen++;
			}
		} else if (x.player() == Player.BLACK) {
			if (x.type() == "Knight") {
				takenBlackKnight++;
			} 
			else if (x.type() == "Rook") {
				takenBlackRook++;
			} 
			else if (x.type() == "Bishop") {
				takenBlackBishop++;
			}
			else if(x.type() == "Pawn") {
				takenBlackPawn++;
			}
			else if(x.type() == "Queen") {
				takenBlackQueen++;
			}
		}
	}
	
	public int numRows() {
		return 8;
	}
	
	public int numColumns() {
		return 8;
	}
	
	public IChessPiece pieceAt(int row, int column) {
		return board[row][column];
	}
	
	public void setNextPlayer() {
		player = player.next();
	}
	
	public void setPlayer(Player p) {
		player = p;
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
	
	
	//add other public or helper methods as needed

}

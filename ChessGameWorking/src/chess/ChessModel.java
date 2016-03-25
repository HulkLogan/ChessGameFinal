package chess;

import java.awt.Component;
import java.awt.Image;
import java.awt.Panel;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/******************************************************************
 * ChessModel() does the bulk of the work for ChessGame. The board
 * and all of the pieces are created and held here, and most of the
 * more complicated game elements are located here. 
 * @author Logan R. Crowe, Jake Young, Henry McDonough
 *****************************************************************/

public class ChessModel implements IChessModel {
	
	/**array to hold the board pieces*/
	private IChessPiece[][] board;
	
	private IChessPiece[][] tempBoard;
	
	/**the current Player**/
	private Player player;
	
	/**the current piece*/
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
	public IChessPiece savior;
	private Image bPawn;
	private ImageIcon blackPawn;
	//declare other instance variables as needed
	
	public ChessModel() {
		board = new IChessPiece[8][8];
		player = Player.WHITE;
		try {
			bPawn = ImageIO.read(getClass().getResource("/Resources/blackPawn.png"));
		} catch (IOException e) {
			System.out.println(5);
		}
		blackPawn = new ImageIcon(bPawn);
		
		
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
		for (int r = 0; r < 8; r++){ 
			for (int c = 0; c < 8; c++){
				for (int x = 0; x < 8; x++){ 
					for (int y = 0; y < 8; y++){
						savior = pieceAt(r, c);
						if(pieceAt(r, c) != null && pieceAt(r, c).player() == currentPlayer()){
							Move test = new Move(r,c,x,y);
							if(isValidMove(test)){
								return !testMove(test);
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	public boolean testMove(Move move){
		duplicateBoard(board);
		IChessPiece tempPiece = tempBoard[move.toRow][move.toColumn];
		tempBoard[move.toRow][move.toColumn] = tempBoard[move.fromRow][move.fromColumn];
		tempBoard[move.fromRow][move.fromColumn] = null;
		if(!inCheck(player)){
			tempBoard[move.fromRow][move.fromColumn] = tempBoard[move.toRow][move.toColumn];
			tempBoard[move.toRow][move.toColumn] = tempPiece;
			return false;
		}
			tempBoard[move.fromRow][move.fromColumn] = tempBoard[move.toRow][move.toColumn];
			tempBoard[move.toRow][move.toColumn] = tempPiece;
			return true;
	}
	
	/****************************************************************
	 * Asks the piece if it's a valid move.
	 * @param move - a Move that need to be verified for validity
	 * @return false - move is not a valid Move
	 * @return true - move is a valid Move
	 ****************************************************************/
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
			JOptionPane.showMessageDialog(null, "Not a valid Move");
			return false;
		}
	}

	/****************************************************************
	 * Moves the piece. 
	 * @param move - the Move that needs to be performed
	 ****************************************************************/
	@Override
	public void move(Move move) {
		if(pieceAt(move.fromRow, move.fromColumn) != null) {
			if(isValidMove(move)) {
				if(currentPiece != null) {
					if(currentPiece.type() == "Pawn") {
						((Pawn) currentPiece).hasMoved = true;
						currentPiece = null;
					}
					else if(currentPiece.type() == "Rook") {
						((Rook) currentPiece).hasMoved = true;
						currentPiece = null;
					}
					else if(currentPiece.type() == "King") {
						((King) currentPiece).hasMoved = true;
						currentPiece = null;
					}
				}
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
		}
	}
	
	/****************************************************************
	 * Verifies if player p's King is in check
	 * @param p - the player that need to verifiy if they are checked
	 * @return false - if the player is not in check
	 * @return true - if the player is in check
	 ****************************************************************/
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
							//attacker = pieceAt(a, b);
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
	
//	public boolean canKingMove(int row, int col, Player p) {
//		for(int r = row-1; r <= row+1; r++) {
//			for(int c = col-1; c <= col+1; c++) {
//				if(r < 8 && r > -1 && c < 8 && c > -1) {
//					if(pieceAt(r, c) != null) {
//						if(pieceAt(r, c).player() != currentPlayer()) {
//							if(squareIsThreatened(r, c, p) == false) {
//								return true;
//							}
//							return false;
//						}
//					}
//					else {
//						if(squareIsThreatened(r, c, p) == false) {
//							return true;
//						}
//						return false;
//					}
//				}
//			}
//		}
//		return false;
//	}
//
//	public boolean canRemoveThreat(int row, int col, Player p) {
//		IChessPiece fromSpot; 
//		IChessPiece toSpot; 
//		for(int r = 0; r < 8; r++) {
//			for(int c = 0; c < 8; c++) {
//				if(board[r][c] != null) {
//					if(board[r][c].player() == p) {
//						for(int x = 0; x < 8; x++) {
//							for(int y = 0; y < 8; y++) {
//								Move threat = new Move(r, c, x, y);
//								if(board[r][c].isValidMove(threat, board)) {
//									if(testMove(board, threat, p)){
//										return true;
//									}
//									else {
//										return false;
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//		return false;
//	}
	
//	public boolean testMove(IChessPiece[][] board, Move move, Player p){
//		
//		IChessPiece fromPiece = board[move.fromRow][move.fromColumn];
//		IChessPiece toPiece = board[move.toRow][move.toColumn];
//		
//		board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
//		board[move.fromRow][move.fromColumn] = null;
//		if(!inCheck(p)){
//			board[move.fromRow][move.fromColumn] = fromPiece;
//			board[move.toRow][move.toColumn] = toPiece;
//			return true;
//		}
//		else{
//			board[move.fromRow][move.fromColumn] = fromPiece;
//			board[move.toRow][move.toColumn] = toPiece;
//			return false;
//		}
//	}
	
	public void promotion() {
		for(int a = 0; a < 8; a++) {
			if(currentPlayer() == Player.WHITE) {
				if(pieceAt(0, a) != null && pieceAt(0, a).type() == "Pawn" 
						&& pieceAt(0, a).player() == currentPlayer()) {
					if(takenWhiteBishop == 0 && takenWhiteKnight == 0 &&
							takenWhiteRook == 0) {
						JOptionPane.showMessageDialog(null, "You have not lost any pieces \n"
								+ "You will be given a Queen");
						board[0][a] = new Queen(Player.WHITE);
					}
					if(takenWhiteBishop != 0) {
						if(takenWhiteKnight != 0) {
							if(takenWhiteRook != 0) {
								Object[] options = {"Bishop", "Knight", "Rook"};
								int n = JOptionPane.showOptionDialog(null, "Please select a piece.", "Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, blackPawn, options, options[0]);
								if(n == 0) {
									board[0][a] = new Bishop(Player.WHITE);
									takenWhiteBishop--;
								}
								if(n == 1) {
									board[0][a] = new Knight(Player.WHITE);
									takenWhiteKnight--;
								}
								if(n == 2) {
									board[0][a] = new Rook(Player.WHITE);
									takenWhiteRook--;
								}
							}
							else {
								Object[] options = {"Bishop", "Knight"};
								int n = JOptionPane.showOptionDialog(null, "Please select a piece.", "Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, blackPawn, options, options[0]);
								if(n == 0) {
									board[0][a] = new Bishop(Player.WHITE);
									takenWhiteBishop--;
								}
								if(n == 1) {
									board[0][a] = new Knight(Player.WHITE);
									takenWhiteKnight--;
								}
							}
						}
						else{
							if(takenWhiteRook != 0) {
								Object[] options = {"Bishop", "Rook"};
								int n = JOptionPane.showOptionDialog(null, "Please select a piece.", "Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, blackPawn, options, options[0]);
								if(n == 0) {
									board[0][a] = new Bishop(Player.WHITE);
									takenWhiteBishop--;
								}
								if(n == 1) {
									board[0][a] = new Rook(Player.WHITE);
									takenWhiteRook--;
								}
							}
							else {
								Object[] options = {"Bishop"};
								int n = JOptionPane.showOptionDialog(null, "Please select a piece.", "Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, blackPawn, options, options[0]);
								if(n == 0) {
									board[0][a] = new Bishop(Player.WHITE);
									takenWhiteBishop--;
								}
							}
						}
					}
					else {
						if(takenWhiteKnight != 0) {
							if(takenWhiteRook != 0) {
								Object[] options = {"Knight", "Rook"};
								int n = JOptionPane.showOptionDialog(null, "Please select a piece.", "Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, blackPawn, options, options[0]);
								if(n == 0) {
									board[0][a] = new Knight(Player.WHITE);
									takenWhiteKnight--;
								}
								if(n == 1) {
									board[0][a] = new Rook(Player.WHITE);
									takenWhiteRook--;
								}
							}
							else {
								Object[] options = {"Knight"};
								int n = JOptionPane.showOptionDialog(null, "Please select a piece.", "Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, blackPawn, options, options[0]);
								if(n == 0) {
									board[0][a] = new Knight(Player.WHITE);
									takenWhiteKnight--;
								}
							}
						}
						else {
							if(takenWhiteRook != 0) {
							Object[] options = {"Rook"};
							int n = JOptionPane.showOptionDialog(null, "Please select a piece.", "Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, blackPawn, options, options[0]);
							if(n == 0) {
									board[0][a] = new Rook(Player.WHITE);
									takenWhiteRook--;
							}
							}
						}
					}
				}
			}
			if(currentPlayer() == Player.BLACK) {
				if(pieceAt(7, a) != null && pieceAt(7, a).type() == "Pawn" 
						&& pieceAt(7, a).player() == currentPlayer()) {
					if(takenBlackBishop == 0 && takenBlackKnight == 0 &&
							takenBlackRook == 0) {
						JOptionPane.showMessageDialog(null, "You have not lost any pieces \n"
								+ "You will be given a Queen");
						board[7][a] = new Queen(Player.BLACK);
					}
					if(takenBlackBishop != 0) {
						if(takenBlackKnight != 0) {
							if(takenBlackRook != 0) {
								Object[] options = {"Bishop", "Knight", "Rook"};
								int n = JOptionPane.showOptionDialog(null, "Please select a piece.", "Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, blackPawn, options, options[0]);
								if(n == 0) {
									board[7][a] = new Bishop(Player.BLACK);
									takenBlackBishop--;
								}
								if(n == 1) {
									board[7][a] = new Knight(Player.BLACK);
									takenBlackKnight--;
								}
								if(n == 2) {
									board[7][a] = new Rook(Player.BLACK);
									takenBlackRook--;
								}
							}
							else {
								Object[] options = {"Bishop", "Knight"};
								int n = JOptionPane.showOptionDialog(null, "Please select a piece.", "Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, blackPawn, options, options[0]);
								if(n == 0) {
									board[7][a] = new Bishop(Player.BLACK);
									takenBlackBishop--;
								}
								if(n == 1) {
									board[7][a] = new Knight(Player.BLACK);
									takenBlackKnight--;
								}
							}
						}
						else{
							if(takenBlackRook != 0) {
								Object[] options = {"Bishop", "Rook"};
								int n = JOptionPane.showOptionDialog(null, "Please select a piece.", "Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, blackPawn, options, options[0]);
								if(n == 0) {
									board[7][a] = new Bishop(Player.BLACK);
									takenBlackBishop--;
								}
								if(n == 1) {
									board[7][a] = new Rook(Player.BLACK);
									takenBlackRook--;
								}
							}
							else {
								Object[] options = {"Bishop"};
								int n = JOptionPane.showOptionDialog(null, "Please select a piece.", "Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, blackPawn, options, options[0]);
								if(n == 0) {
									board[7][a] = new Bishop(Player.BLACK);
									takenBlackBishop--;
								}
							}
						}
					}
					else {
						if(takenBlackKnight != 0) {
							if(takenBlackRook != 0) {
								Object[] options = {"Knight", "Rook"};
								int n = JOptionPane.showOptionDialog(null, "Please select a piece.", "Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, blackPawn, options, options[0]);
								if(n == 0) {
									board[7][a] = new Knight(Player.BLACK);
									takenBlackKnight--;
								}
								if(n == 1) {
									board[7][a] = new Rook(Player.BLACK);
									takenBlackRook--;
								}
							}
							else {
								Object[] options = {"Knight"};
								int n = JOptionPane.showOptionDialog(null, "Please select a piece.", "Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, blackPawn, options, options[0]);
								if(n == 0) {
									board[7][a] = new Knight(Player.BLACK);
									takenBlackKnight--;
								}
							}
						}
						else {
							if(takenBlackRook != 0) {
							Object[] options = {"Rook"};
							int n = JOptionPane.showOptionDialog(null, "Please select a piece.", "Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, blackPawn, options, options[0]);
							if(n == 0) {
									board[7][a] = new Rook(Player.BLACK);
									takenBlackRook--;
							}
							}
						}
					}
				}
			}
		}
	}
	
	public void castling(String x) {
		if(currentPlayer() == Player.WHITE) {
			if(x.equals("Right")) {
				if(board[7][4].type() == "King") {
					IChessPiece tempKing = board[7][4];
					if(((King) tempKing).hasMoved == false) {
						if(board[7][7].type() == "Rook") {
							IChessPiece tempRook = board[7][7];
							if(((Rook) tempRook).hasMoved == false) {
								if(board[7][6] == null && board[7][5] == null) {
									board[7][6] = board[7][4];
									board[7][4] = null;
									board[7][5] = board[7][7];
									board[7][7] = null;
									setNextPlayer();
								}
							}
							else {
								JOptionPane.showMessageDialog(null, "You cannot castle at this time.");
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "You cannot castle at this time.");
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "You cannot castle at this time.");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "You cannot castle at this time.");
				}
			}
			if(x.equals("Left")) {
				if(board[7][4].type() == "King") {
					IChessPiece tempKing = board[7][4];
					if(((King) tempKing).hasMoved == false) {
						if(board[7][0].type() == "Rook") {
							IChessPiece tempRook = board[7][0];
							if(((Rook) tempRook).hasMoved == false) {
								if(board[7][2] == null && board[7][3] == null) {
									board[7][2] = board[7][4];
									board[7][4] = null;
									board[7][3] = board[7][0];
									board[7][0] = null;
									setNextPlayer();
								}
							}
							else {
								JOptionPane.showMessageDialog(null, "You cannot castle at this time.");
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "You cannot castle at this time.");
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "You cannot castle at this time.");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "You cannot castle at this time.");
				}
			}
		}
		if(currentPlayer() == Player.BLACK) {
			if(x.equals("Right")) {
				if(board[0][4].type() == "King") {
					IChessPiece tempKing = board[0][4];
					if(((King) tempKing).hasMoved == false) {
						if(board[0][0].type() == "Rook") {
							IChessPiece tempRook = board[0][0];
							if(((Rook) tempRook).hasMoved == false) {
								if(board[0][2] == null && board[0][3] == null) {
									board[0][2] = board[0][4];
									board[0][4] = null;
									board[0][3] = board[0][0];
									board[0][0] = null;
									setNextPlayer();
								}
							}
							else {
								JOptionPane.showMessageDialog(null, "You cannot castle at this time.");
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "You cannot castle at this time.");
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "You cannot castle at this time.");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "You cannot castle at this time.");
				}
			}
			if(x.equals("Left")) {
				if(board[0][4].type() == "King") {
					IChessPiece tempKing = board[0][4];
					if(((King) tempKing).hasMoved == false) {
						if(board[0][7].type() == "Rook") {
							IChessPiece tempRook = board[0][7];
							if(((Rook) tempRook).hasMoved == false) {
								if(board[0][6] == null && board[0][5] == null) {
									board[0][6] = board[0][4];
									board[0][4] = null;
									board[0][5] = board[0][7];
									board[0][7] = null; 
									setNextPlayer();
								}
							}
							else {
								JOptionPane.showMessageDialog(null, "You cannot castle at this time.");
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "You cannot castle at this time.");
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "You cannot castle at this time.");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "You cannot castle at this time.");
				}
			}
		}
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
	
	public void duplicateBoard(IChessPiece[][] masterBoard){

		tempBoard = new IChessPiece[8][8];

		for (int r = 0; r < 8; r++){
			for (int c = 0; c < 8; c++) {
				if (board[r][c] != null) {
					if(board[r][c].player() == Player.BLACK){
						if(board[r][c].type() == "King"){
						}
						else if(board[r][c].type() == "Queen"){
							tempBoard[r][c] = new Queen(Player.BLACK);
						}
						else if(board[r][c].type() == "Rook"){
							tempBoard[r][c] = new Rook(Player.BLACK);
						}
						else if(board[r][c].type() == "Bishop"){
							tempBoard[r][c] = new Bishop(Player.BLACK);
						}
						else if(board[r][c].type() == "Knight"){
							tempBoard[r][c] = new Knight(Player.BLACK);
						}
						else if(board[r][c].type() == "Pawn"){
							tempBoard[r][c] = new Pawn(Player.BLACK);
						}
						else if(board[r][c] == null){
							tempBoard[r][c] = null;
						}
					
					}
					else if(board[r][c].player() == Player.WHITE){
						if(board[r][c].type() == "King"){
							tempBoard[r][c] = new King(Player.WHITE);
						}
						else if(board[r][c].type() == "Queen"){
							tempBoard[r][c] = new Queen(Player.WHITE);
						}
						else if(board[r][c].type() == "Rook"){
							tempBoard[r][c] = new Rook(Player.WHITE);
						}
						else if(board[r][c].type() == "Bishop"){
							tempBoard[r][c] = new Bishop(Player.WHITE);
						}
						else if(board[r][c].type() == "Knight"){
							tempBoard[r][c] = new Knight(Player.WHITE);
						}
						else if(board[r][c].type() == "Pawn"){
							tempBoard[r][c] = new Pawn(Player.WHITE);
						}
						else if(board[r][c] == null){
							tempBoard[r][c] = null;
						}
					}
				}
			}
		}
	}
	
	//add other public or helper methods as needed

}

package chess;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ChessGUI {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Chess Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ChessPanel panel = new ChessPanel();
		frame.getContentPane().add(panel, BorderLayout.EAST);
		frame.pack();
		frame.setVisible(true);
	}
	
}

package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame("Tetris Game");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		GamePanel gp = new GamePanel();
		window.add(gp);
		// pack() を呼び出すと、ボタンなどのサイズに合わせてウィンドウが自動調整される。;
		window.pack();
		// window表示を可能にする
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		// スレッドを開始させる！！
		gp.launchGame();
	}
}

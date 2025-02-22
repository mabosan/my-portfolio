package main;

import javax.swing.JFrame;

public class main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		
		// ウィンドウを適切に閉じれるようにする
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ウィンドウのサイズを固定する
		window.setResizable(false);
		// ゲームタイトルの設定
		window.setTitle("2D Monster Adventure");
		// gamePanelの実装
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack();
		// ウィンドウがスクリーンの中央に位置する
		window.setLocationRelativeTo(null);
		// ウィンドウの表示
		window.setVisible(true);
		// ゲーム開始前の設定
		gamePanel.setUpGame();
		// runメソッド呼び出し
		gamePanel.startGameThread();
	}

}

package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import Mino.Block;
import Mino.Mino;
import MinoBlock.Mino1;

public class PlayManager {

	// プレイするエリアの設定
	final int playWIDTH = 360;
	final int playHEIGHT = 600;
	public static int left_x;
	public static int right_x;
	public static int top_y;
	public static int bottom_y;

	// Minoの設定
	Mino currentMino;
	final int MINO_START_X;
	final int MINO_START_Y;
	
	// Drop機能のインターバル,1秒後または60フレーム毎に落下する
	public static int dropInterval = 60;

	public PlayManager() {

		// メインとなるプレイエリアのフレーム設定
		left_x = (GamePanel.WIDTH / 2) - (playWIDTH / 2);
		right_x = left_x + playWIDTH;
		top_y = 50;
		bottom_y = top_y + playHEIGHT;
		
		MINO_START_X = left_x + (playWIDTH/2) - Block.SIZE;
		MINO_START_Y = top_y + Block.SIZE;
		
		// 開始位置のMINOの設定
		currentMino = new Mino1();
		currentMino.setXY(MINO_START_X, MINO_START_Y);
	}

	public void update() {

		currentMino.update();
	}

	public void draw(Graphics2D g2) {

		// play areaの枠サイズ 
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(4f));
		g2.drawRect(left_x - 4, top_y - 4, playWIDTH + 8, playHEIGHT + 8);

		// 次降りてくるもののareaの枠サイズ
		int x = right_x + 100;
		int y = bottom_y - 200;
		g2.drawRect(x, y, 200, 200);
		// 文字の設定
		g2.setFont(new Font("Arial", Font.PLAIN, 30));
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.drawString("NEXT", x + 60, y + 60);
		
		// 現在地のMinoを描画する
		if(currentMino != null) {
			currentMino.draw(g2);
			
		}
	}
}

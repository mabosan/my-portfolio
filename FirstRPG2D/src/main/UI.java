package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.Key;

public class UI {

	GamePanel gamePanel;
	Font arial_40, arial_80B;
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	// メッセージのカウント用
	int messageCounter = 0;
	public boolean gameFinished = false;
	// RTA表示用
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.000");

	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;

		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);

		Key key = new Key();
		keyImage = key.image;
	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
	}

	public void draw(Graphics2D graphics2d) {

		//		// これはdrawメソッド内の処理つまりは1秒間に６０回インスタンスを作成することになり、時間とリソースを無駄に消費する
		//		// 悪い例
		//		graphics2d.setFont(new Font("Arial", Font.PLAIN, 40));

		if (gameFinished == true) {

			graphics2d.setFont(arial_40);
			graphics2d.setColor(Color.white);

			String text;
			int textLength;
			int x;
			int y;
			
			text = "Are you a hero? No,You're God!";
			textLength = (int) graphics2d.getFontMetrics().getStringBounds(text, graphics2d).getWidth();
			
			x = gamePanel.screenWidth/2 - textLength/2;
			y = gamePanel.screenHeight/2 - (gamePanel.tileSize * 3);
			graphics2d.drawString(text, x, y);
			
			text = "Your Time is :" + dFormat.format(playTime) + "!";
			textLength = (int) graphics2d.getFontMetrics().getStringBounds(text, graphics2d).getWidth();
			
			x = gamePanel.screenWidth/2 - textLength/2;
			y = gamePanel.screenHeight/2 + (gamePanel.tileSize * 4);
			graphics2d.drawString(text, x, y);

			graphics2d.setFont(arial_80B);
			graphics2d.setColor(Color.yellow);

			text = "Congratulations!!";
			textLength = (int) graphics2d.getFontMetrics().getStringBounds(text, graphics2d).getWidth();
			
			x = gamePanel.screenWidth/2 - textLength/2;
			y = gamePanel.screenHeight/2 + (gamePanel.tileSize * 2);
			graphics2d.drawString(text, x, y);

			// gameを終了させる
			gamePanel.gameThread = null;
		} else {

			graphics2d.setFont(arial_40);
			graphics2d.setColor(Color.white);
			graphics2d.drawImage(keyImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize,
					gamePanel.tileSize, null);
			graphics2d.drawString("× " + gamePanel.player.hasKey, 74, 65);

			// タイムを表示,drawメソッドが１秒間に６０回呼び出されるので1/60秒する
			playTime +=(double)1/60; 
			graphics2d.drawString("Time:" + dFormat.format(playTime), gamePanel.tileSize * 11, 65);
			
			// メッセージUIの描画
			if (messageOn == true) {

				graphics2d.setFont(graphics2d.getFont().deriveFont(30F));
				graphics2d.drawString(message, gamePanel.tileSize / 2, gamePanel.tileSize * 3);

				messageCounter++;

				if (messageCounter > 120) {
					messageCounter = 0;
					messageOn = false;
				}
			}
		}
	}
}

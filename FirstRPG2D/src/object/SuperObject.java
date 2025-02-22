package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class SuperObject {

	public BufferedImage image;
	public String objectName;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;

	public void draw(Graphics2D graphics2d, GamePanel gamePanel) {

		// 画面に映るタイルの座標
		// スポーン地点のplayer分を引きスクリーン(4分割の左上)サイズ分を足す
		int screenX = worldX - gamePanel.player.characterX + gamePanel.player.screenX;
		int screenY = worldY - gamePanel.player.characterY + gamePanel.player.screenY;

		// 画面に見えないタイルは描画されないようにし処理の負荷を軽減する
		if (worldX + gamePanel.tileSize > gamePanel.player.characterX - gamePanel.player.screenX &&
				worldX - gamePanel.tileSize < gamePanel.player.characterX + gamePanel.player.screenX &&
				worldY + gamePanel.tileSize > gamePanel.player.characterY - gamePanel.player.screenY &&
				worldY - gamePanel.tileSize < gamePanel.player.characterY + gamePanel.player.screenY) {

			graphics2d.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize,
					null);

		}

	}
}

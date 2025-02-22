package main;

import object.Boots;
import object.Chest;
import object.Door;
import object.Key;

public class AssetSetter {

	GamePanel gamePanel;

	public AssetSetter(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public void setObject() {

		gamePanel.obj[0] = new Key();
		gamePanel.obj[0].worldX = 6 * gamePanel.tileSize;
		gamePanel.obj[0].worldY = 20 * gamePanel.tileSize;

		gamePanel.obj[1] = new Key();
		gamePanel.obj[1].worldX = 43 * gamePanel.tileSize;
		gamePanel.obj[1].worldY = 22 * gamePanel.tileSize;

		gamePanel.obj[2] = new Key();
		gamePanel.obj[2].worldX = 26 * gamePanel.tileSize;
		gamePanel.obj[2].worldY = 5 * gamePanel.tileSize;

		gamePanel.obj[3] = new Door();
		gamePanel.obj[3].worldX = 72 * gamePanel.tileSize;
		gamePanel.obj[3].worldY = 21 * gamePanel.tileSize;

		gamePanel.obj[4] = new Door();
		gamePanel.obj[4].worldX = 63 * gamePanel.tileSize;
		gamePanel.obj[4].worldY = 16 * gamePanel.tileSize;

		gamePanel.obj[5] = new Door();
		gamePanel.obj[5].worldX = 67 * gamePanel.tileSize;
		gamePanel.obj[5].worldY = 7 * gamePanel.tileSize;

		gamePanel.obj[6] = new Chest();
		gamePanel.obj[6].worldX = 67 * gamePanel.tileSize;
		gamePanel.obj[6].worldY = 3 * gamePanel.tileSize;

		gamePanel.obj[7] = new Boots();
		gamePanel.obj[7].worldX = 15 * gamePanel.tileSize;
		gamePanel.obj[7].worldY = 9 * gamePanel.tileSize;

	}
}

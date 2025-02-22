package main;

import entity.Entity;

public class CollisionChecker {

	GamePanel gamePanel;
	
	public CollisionChecker(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void checkTile(Entity entity) {
		
		int entityLeftWorldX = entity.characterX + entity.solidArea.x;
		int entityRightWorldX = entity.characterX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.characterY + entity.solidArea.y;
		int entityBottomWorldY = entity.characterY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gamePanel.tileSize;
		int entityRightCol = entityRightWorldX/gamePanel.tileSize;
		int entityTopRow = entityTopWorldY/gamePanel.tileSize;
		int entityBottomRow = entityBottomWorldY/gamePanel.tileSize;
		
		int tileNum1, tileNum2;
		
		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.characterSpeed)/gamePanel.tileSize;
			// 衝突エリアの左上角のチェック
			tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
			// 衝突エリアの右上角のチェック
			tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
			if(gamePanel.tileManager.tile[tileNum1].collision == true || gamePanel.tileManager.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY - entity.characterSpeed)/gamePanel.tileSize;
			// 衝突エリアの左下角のチェック
			tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
			// 衝突エリアの右下角のチェック
			tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
			if(gamePanel.tileManager.tile[tileNum1].collision == true || gamePanel.tileManager.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.characterSpeed)/gamePanel.tileSize;
			// 衝突エリアの左上角のチェック
			tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
			// 衝突エリアの左下角のチェック
			tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
			if(gamePanel.tileManager.tile[tileNum1].collision == true || gamePanel.tileManager.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX - entity.characterSpeed)/gamePanel.tileSize;
			// 衝突エリアの右上角のチェック
			tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
			// 衝突エリアの右下角のチェック
			tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
			if(gamePanel.tileManager.tile[tileNum1].collision == true || gamePanel.tileManager.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		}
	}
	
	public int checkObject(Entity entity, boolean player) {
		
		int index = 999;
		
		for(int i = 0; i < gamePanel.obj.length; i++) {
			
			if(gamePanel.obj[i] != null) {
				
				// entityの物理判定領域を取得
				entity.solidArea.x = entity.characterX + entity.solidArea.x;
				entity.solidArea.y = entity.characterY + entity.solidArea.y;
				// objectの物理判定領域を取得
				gamePanel.obj[i].solidArea.x = gamePanel.obj[i].worldX + gamePanel.obj[i].solidArea.x;
				gamePanel.obj[i].solidArea.y = gamePanel.obj[i].worldY + gamePanel.obj[i].solidArea.y;

				
				switch (entity.direction) {
				case "up":
					entity.solidArea.y -= entity.characterSpeed;
					// intersectsは二つの長方形（物理判定領域）が交差（衝突）しているかのチェックする
					if(entity.solidArea.intersects(gamePanel.obj[i].solidArea)) {
						if(gamePanel.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if(player == true) {
							index = i;
						}
					}
					break;
				case "down":
					entity.solidArea.y += entity.characterSpeed;
					// intersectsは二つの長方形（物理判定領域）が交差（衝突）しているかのチェックする
					if(entity.solidArea.intersects(gamePanel.obj[i].solidArea)) {
						if(gamePanel.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if(player == true) {
							index = i;
						}
					}
					break;
				case "left":
					entity.solidArea.x -= entity.characterSpeed;
					// intersectsは二つの長方形（物理判定領域）が交差（衝突）しているかのチェックする
					if(entity.solidArea.intersects(gamePanel.obj[i].solidArea)) {
						if(gamePanel.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if(player == true) {
							index = i;
						}
					}
					break;
				case "right":
					entity.solidArea.x += entity.characterSpeed;
					// intersectsは二つの長方形（物理判定領域）が交差（衝突）しているかのチェックする
					if(entity.solidArea.intersects(gamePanel.obj[i].solidArea)) {
						if(gamePanel.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if(player == true) {
							index = i;
						}
					}
					break;
				}
				// 保存していたデフォルト値を使用してリセットする
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gamePanel.obj[i].solidArea.x = gamePanel.obj[i].solidAreaDefaultX;
				gamePanel.obj[i].solidArea.y = gamePanel.obj[i].solidAreaDefaultY;

			}
		}
		return index;
	}
}

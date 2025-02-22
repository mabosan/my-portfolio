package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

	GamePanel gamePanel;
	KeyHandler keyH;

	public final int screenX;
	public final int screenY;
	// 所持している鍵の数
	public int hasKey = 0;
	int standCounter = 0;

	public Player(GamePanel gamePanel, KeyHandler keyH) {

		this.gamePanel = gamePanel;
		this.keyH = keyH;

		// 画面を４分割した際の左上側のスクリーンを指す
		// playerのtilesizeで左上の角がポインターとなってしまい
		// 正常に中央にplayerが位置しないために、tilesize半個分引き
		// 中央にポインターが位置するようにする
		screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
		screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

		// 衝突検知範囲の作成
		solidArea = new Rectangle();
		solidArea.x = gamePanel.originalTileSize / gamePanel.collisionRate;
		solidArea.y = gamePanel.originalTileSize;
		// 後に、上のx,yを変更するので、デフォルト値を保存しておくため
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = gamePanel.originalTileSize * gamePanel.collisionRate;
		solidArea.height = gamePanel.originalTileSize * gamePanel.collisionRate;

		setDefaltValues();
		getPlayerImage();
	}

	// characterの初期値の設定
	public void setDefaltValues() {

		// スポーン地点
		characterX = gamePanel.tileSize * 5;
		characterY = gamePanel.tileSize * 69;
		characterSpeed = 4;
		// 方向の設定
		direction = "down";
	}

	// playerの画像読み込み処理
	public void getPlayerImage() {
		
		try {

			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 1秒間に60回呼ばれる
	public void update() {

		// キーボードが押されているときのみspriteCounterが増加するようにすることで
		// 停止時にplayerが動いているように見せないようにする
		// つまり画像切り替えをplayerを動かしているのみ行わせるということ
		if (keyH.upPressed == true || keyH.downPressed == true ||
				keyH.leftPressed == true || keyH.rightPressed == true) {

			// javaの座標は左角がx:0,y:0なので、現座標から足すか引くかでパネルを更新し
			// 動いてるように見せる
			if (keyH.upPressed == true) {
				direction = "up";
			} else if (keyH.downPressed == true) {
				direction = "down";
			} else if (keyH.leftPressed == true) {
				direction = "left";
			} else if (keyH.rightPressed == true) {
				direction = "right";
			}

			// タイル衝突チェック
			collisionOn = false;
			gamePanel.cChecker.checkTile(this);
			
			// オブジェクト衝突チェック
			// このクラスはplayerなのでtrue
			int objIndex = gamePanel.cChecker.checkObject(this, true);
			pickUpObject(objIndex);

			// falseの場合はplayerは移動できる
			if (collisionOn == false) {

				switch (direction) {
				case "up":
					characterY -= characterSpeed;
					break;
				case "down":
					characterY += characterSpeed;
					break;
				case "left":
					characterX -= characterSpeed;
					break;
				case "right":
					characterX += characterSpeed;
					break;
				}
			}

			// シンプルな画像システム導入
			// spriteCounterは1秒間に1ずつ加算される(1フレーム)
			spriteCounter++;
			// 12フレーム毎に画像が切り替わるということ
			if (spriteCounter > 12) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		else {
			standCounter++;
			
			if(standCounter == 20) {
				
				spriteNum = 1;
				standCounter = 0;
			}
		}
	}
	public void pickUpObject(int index) {
		
		if(index != 999) {
			
			String objectName = gamePanel.obj[index].objectName;
			
			switch(objectName) {
			case "Key":
				gamePanel.playSE(1);
				hasKey++;
				gamePanel.obj[index] = null;
				gamePanel.ui.showMessage("You got a Key!");
				break;
			case "Door":
				if(hasKey > 0) {
					gamePanel.playSE(3);
					gamePanel.obj[index] = null;
					hasKey--;
					gamePanel.ui.showMessage("Nice!! You opened the door!");
				}
				else {
					gamePanel.ui.showMessage("Oh No! You need a key!");
				}
				break;
			case "Boots":
				gamePanel.playSE(2);
				characterSpeed += 2;
				gamePanel.obj[index] = null;
				gamePanel.ui.showMessage("Your speed is God");
				break;
			case "Chest":
				gamePanel.ui.gameFinished = true;
				gamePanel.stopMusic();
				gamePanel.playSE(4);
				break;
			}
		}
	}

	public void draw(Graphics2D graphics2) {

		//		graphics2.setColor(Color.white);
		//		// fillRectは四角形の実装(ゲームキャラクターの実装)
		//		graphics2.fillRect(characterX, characterY, gamePanel.tileSize, gamePanel.tileSize);

		BufferedImage image = null;

		switch (direction) {
		case "up":
			if (spriteNum == 1) {
				image = up1;
			}
			if (spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if (spriteNum == 1) {
				image = down1;
			}
			if (spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if (spriteNum == 1) {
				image = left1;
			}
			if (spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if (spriteNum == 1) {
				image = right1;
			}
			if (spriteNum == 2) {
				image = right2;
			}
			break;

		}
		// playerを中央で固定する
		graphics2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
	}
}

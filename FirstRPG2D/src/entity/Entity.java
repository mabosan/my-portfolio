package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

// プレイヤーやその他のクラスの親クラスとなるもの
public class Entity {

	public int characterX,characterY;
	public int characterSpeed;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	// 抽象的な四角形を実装
	public Rectangle solidArea;
	public int solidAreaDefaultX,solidAreaDefaultY;
	
	public boolean collisionOn = false;
}

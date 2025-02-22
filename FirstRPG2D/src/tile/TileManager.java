package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

	GamePanel gamePanel;
	public Tile[] tile;
	// マップファイル読み込みで使用
	public int mapTileNum[][];

	public TileManager(GamePanel gamePanel) {

		this.gamePanel = gamePanel;
		// 10種類のタイルを収納できるということ
		tile = new Tile[10];
		// マップファイルの中身の数字をすべて格納する
		mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

		getTileImage();
		loadMap("/maps/world01.txt");
	}

	public void getTileImage() {

		try {

			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass00.png"));

			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[1].collision = true; // 物理判定実装

			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water00.png"));
			tile[2].collision = true; // 物理判定実装

			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));

			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[4].collision = true; // 物理判定実装

			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road00.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void loadMap(String mapFilePath) {

		try {
			InputStream iStream = getClass().getResourceAsStream(mapFilePath);
			// テキストファイルの中身を読むためにBufferedReaderを使用する
			BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

			int col = 0;
			int row = 0;

			while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {

				// readlineは1行だけのテキストを読む
				String line = br.readLine();

				while (col < gamePanel.maxWorldCol) {
					// 1行の文字列をsplitで分割する
					String mapNumbers[] = line.split(" ");
					// 数字で扱えるようにする
					int mapNumber = Integer.parseInt(mapNumbers[col]);

					mapTileNum[col][row] = mapNumber;
					col++;
				}
				// 次の段の読み込みへ
				if (col == gamePanel.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void draw(Graphics2D graphics2d) {

		int worldCol = 0;
		int worldRow = 0;

		while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {

			// Tile[] tileのインデックスとして使用
			int tileNum = mapTileNum[worldCol][worldRow];

			// 設定したワールドのタイル座標
			int worldX = worldCol * gamePanel.tileSize;
			int worldY = worldRow * gamePanel.tileSize;
			// 画面に映るタイルの座標
			// スポーン地点のplayer分を引きスクリーン(4分割の左上)サイズ分を足す
			int screenX = worldX - gamePanel.player.characterX + gamePanel.player.screenX;
			int screenY = worldY - gamePanel.player.characterY + gamePanel.player.screenY;

			// 画面に見えないタイルは描画されないようにし処理の負荷を軽減する
			if (worldX + gamePanel.tileSize > gamePanel.player.characterX - gamePanel.player.screenX &&
				worldX - gamePanel.tileSize < gamePanel.player.characterX + gamePanel.player.screenX &&
				worldY + gamePanel.tileSize > gamePanel.player.characterY - gamePanel.player.screenY &&
				worldY - gamePanel.tileSize < gamePanel.player.characterY + gamePanel.player.screenY) {

				graphics2d.drawImage(tile[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize,
						null);

			}

			worldCol++;

			// 次の段落のタイル描画に移る
			if (worldCol == gamePanel.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
}

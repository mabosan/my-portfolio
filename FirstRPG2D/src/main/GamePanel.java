package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

// 画面設定をするクラス
public class GamePanel extends JPanel implements Runnable {

	// 16 * 16のタイル設定(player,NPC,gameBackGroundのサイズ)
	public final int originalTileSize = 16;
	// 16 * 16のタイル設定を３倍にするためスケーリング設定をする
	final int scale = 3;
	// キャラクター物理判定に使用する
	public final int collisionRate = 2;
	// 48 * 48のタイルサイズ
	public final int tileSize = originalTileSize * scale;
	// 横に16マス
	public final int maxScreenCol = 16;
	// 縦に12マス
	public final int maxScreenRow = 12;
	// 768 pixels
	public final int screenWidth = tileSize * maxScreenCol;
	// 576 pixels
	public final int screenHeight = tileSize * maxScreenRow;
	// worldMapの設定
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	// FPS
	int FPS = 60;
	// キャラクター移動の定義クラスを実装
	KeyHandler keyH = new KeyHandler();
	// 音楽と効果音は同時に処理されないため分けます
	Sound music = new Sound();
	Sound se = new Sound();
	// このGamePanelクラスとKeyHandlerを渡す
	public Player player = new Player(this, keyH);
	// TileManagerにこのGamePanelを渡す
	TileManager tileManager = new TileManager(this);
	// 衝突チェックのためこのGamePanelの情報を渡す
	public CollisionChecker cChecker = new CollisionChecker(this);
	// WORLDにオブジェクトを設定する
	public AssetSetter aSetter = new AssetSetter(this);
	// WORLDに最大いくつまでオブジェクト設定できるかの上限値設定
	public SuperObject obj[] = new SuperObject[10];
	
	public UI ui = new UI(this);
	// ゲーム内に時間を実装する
	Thread gameThread;

	// コンストラクタ
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		// ダブルバッファリングを有効化
		this.setDoubleBuffered(true);
		// keyの入力を感知できるようにする
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	// object配置
	public void setUpGame() {
		
		aSetter.setObject();
		
		playMusic(0);
	}

	public void startGameThread() {
		// thisはこのGamePanelクラスのこと
		gameThread = new Thread(this);
		gameThread.start();

	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			// 1ループ毎に、経過時間（()の中の処理）をdrawIntervalで割る
			delta += (currentTime - lastTime) / drawInterval;
			
			lastTime = currentTime;
			
			// sleepさせずにdeltaをリセットすることで黒いちらつきの発生防止
			if(delta >= 1) {
				updatePanel();
				repaint();
				delta--;
			}
		}
//		// 1000000000ナノ秒は１秒なので、0.01666秒
//		double drawInterval = 1000000000 / FPS;
//		// ゲームループの処理時間の設定
//		double nextDrawTime = System.nanoTime() + drawInterval;
//
//		// ゲームの心臓部となるゲームループを実装
//		// デルタ、累積型方式のゲームループです
//		while (gameThread != null) {
//
//			// キャラクターのポジションを更新するため
//			updatePanel();
//
//			// updateされた情報から再描画するため
//			repaint();
//
//			try {
//				
//				// 次の描画時間までに残された時間がわかる
//				double remainingTime = nextDrawTime - System.nanoTime();
//				// ナノ秒からミリ秒に変換
//				remainingTime = remainingTime / 1000000;
//				// drawInterval以上の時間がかかった場合はsleepさせる必要ないので
//				// 0を設定する
//				if (remainingTime > 0) {
//					// sleep時はいかなる処理も実行されない
//					Thread.sleep((long) remainingTime);
//				}
//				// 次の描画時間までの設定
//				nextDrawTime += drawInterval;
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}

	public void updatePanel() {
		// update処理を実装
		player.update();
	}

	public void paintComponent(Graphics graphics) {
		// Graphicsは鉛筆のようなもの
		// paintComponenntを機能させるための決まったフォーマット
		super.paintComponent(graphics);

		Graphics2D graphics2 = (Graphics2D) graphics;

		// アンチエイリアスを適用（見た目を滑らかに）
		graphics2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
				java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

		// タイルを先に描画する
		// 先に描画しないとタイルでプレイヤーが隠れてしまう
		tileManager.draw(graphics2);
		
		// オブジェクトは消えることもあるのでマップタイルより後で描画
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(graphics2, this);
			}
		}

		// ゲームキャラクターの実装
		player.draw(graphics2);
		
		// 基本UIというのはユーザに見られるものなので、プレイヤーより後で描画
		ui.draw(graphics2);
		
		// リソースの解放するため必ず行う
		// 次回ゲーム起動時やマップ移動などの更新の際、不要なものが溜まっていると、
		// パフォーマンス低下につながるので、リソースを破棄
		graphics2.dispose();
	}
	
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		
		music.stop();
	}
	
	public void playSE(int i) {
		
		se.setFile(i);
		se.play();
	}
}

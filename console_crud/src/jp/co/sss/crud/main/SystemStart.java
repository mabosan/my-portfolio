package jp.co.sss.crud.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import jp.co.sss.crud.db.DBController;
import jp.co.sss.crud.db.DB_Delete;
import jp.co.sss.crud.db.DB_DeptSelect;
import jp.co.sss.crud.db.DB_EmpSelect;
import jp.co.sss.crud.db.DB_Update;

/**
 * 社員管理システム実行用クラス
 */
public class SystemStart {
	/**
	 * メイン処理
	 *
	 * @param args
	 *            コマンドライン引数
	 */
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int menuNo = 0; // 無効な値で初期化しておく
		try {
			do {
				// メニューの表示
				System.out.println("=== 社員管理システム ===");
				System.out.println("1. 全件表示");
				System.out.println("2. 社員名検索");
				System.out.println("3. 部署ID検索");
				System.out.println("4. 登録");
				System.out.println("5. 更新");
				System.out.println("6. 削除");
				System.out.println("7. 終了");
				System.out.print("メニュー番号を入力してください:");
				
				while(true) {
				// メニュー番号の入力
				String menuNoStr = br.readLine();
				menuNo = Integer.parseInt(menuNoStr);
				if (menuNo >= 1 && menuNo <= 7) {
	            	break;
	            	// 範囲内なら次の処理へ
	            } else {
	            	System.out.println("1以上7以下の整数を入力してください：");
	            	//もう一度入力。１から７が入力されるまで繰り返す。           	
	            }    
			}
				// 機能の呼出
				switch (menuNo) {
				case 1:
					// 全件表示機能の呼出
					DBController.findAll();
					break;
				case 2:
					//社員名検索機能の呼出					
					DB_EmpSelect.empselect();
					break;
				case 3:
					//部署ID検索機能の呼出	
					DB_DeptSelect.deptselect();
					break;
				case 4:
					// 登録する値を入力
					System.out.print("社員名:");
				    String empName = null; // ここで empName を宣言しておく
				    	
				    while(true) {
				    	empName = br.readLine();
				    	// 社員名が1～30の範囲かをチェック
				    	if (empName.length() >= 1 && empName.length() <= 30) {
				    		break;
				        // 範囲内なら次の処理へ
				    } else {
				        System.out.println("1文字以上30文字以下の文字列を入力してください：");
				        //もう一度入力。１から３０が入力されるまで繰り返す。           	
				    }    
				    }
					System.out.print("性別(1:男性, 2:女性):");
					String gender = br.readLine();
					System.out.print("生年月日(西暦年/月/日):");
					String birthday = br.readLine();
					System.out.print("部署ID(1:営業部、2:経理部、3:総務部):");
					String deptId = br.readLine();
					// 登録機能の呼出
					DBController.insert(empName, gender, birthday, deptId);
					break;
				case 5:
					// 更新機能の呼出
					DB_Update.update();
					break;
				case 6:
					// 削除機能の呼出
					DB_Delete.delete();
					break;
				}
			} while (menuNo != 7);
		} catch (Exception e) {
			System.out.println("システムエラーが発生しました");
			e.printStackTrace();
		}
		System.out.println("システムを終了します。");
	}

}

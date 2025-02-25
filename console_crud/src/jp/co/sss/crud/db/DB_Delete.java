package jp.co.sss.crud.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jp.co.sss.crud.util.ConstantSQL;

/**
 * 社員管理システム削除用クラス
 */
public class DB_Delete {
	public static void delete() throws ClassNotFoundException, SQLException, IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		System.out.println("削除する社員の社員IDを入力してください:");
		int empNo;
		
		while(true) {
				String str = br.readLine();
				empNo = Integer.parseInt(str);
			// 社員IDが1～9999の範囲かをチェック
            if (empNo >= 1 && empNo <= 9999) {
            	break;
            	// 範囲内なら次の処理へ
            } else {
            	System.out.println("1以上9999以下の整数を入力してください：");
            	//もう一度入力。１から９９９９が入力されるまで繰り返す。           	
            }   
		}
		
		try {
			// DBに接続
			connection = DBManager.getConnection();

			// ステートメントを作成
			preparedStatement = connection.prepareStatement(ConstantSQL.SQL_DELETE);

			// 入力値をバインド
			preparedStatement.setInt(1, empNo);
			
			// SQL文を実行
			preparedStatement.executeUpdate();

			// 登録完了メッセージを出力
			System.out.println("社員情報を削除しました");
		} catch (Exception e) { 
			e.printStackTrace();
		}finally {
			DBManager.close(preparedStatement);
			DBManager.close(connection);
		}
	}
}

package jp.co.sss.crud.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sss.crud.util.ConstantSQL;

/**
 * 社員管理システム社員名検索用クラス
 */
public class DB_EmpSelect {
	
	public static void empselect() throws SQLException, IOException{
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	System.out.println("社員名を入力してください。");
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
	
	try {
		//DBに接続
		connection = DBManager.getConnection();
		
		// ステートメントを作成
		preparedStatement = connection.prepareStatement(ConstantSQL.SQL_EMPSELECT);
		
		// 入力値をバインド
		preparedStatement.setString(1,"%"+ empName + "%");
		
		// SQL文を実行
		resultSet = preparedStatement.executeQuery();
		
		// レコードの行数を数えるための変数を用意
		int rowCount = 0;
		
		while (resultSet.next()) {
			System.out.println("emp_id\temp_name\tgender\tbirthday\tdept_name");
			System.out.print(resultSet.getString("emp_id") + "\t");
			System.out.print(resultSet.getString("emp_name") + "\t");
			
			int gender = Integer.parseInt(resultSet.getString("gender"));
			if (gender == 1) {
				System.out.print("男性\t");
			} else if (gender == 2) {
				System.out.print("女性\t");
			}
			
			System.out.print(resultSet.getString("birthday") + "\t");
			System.out.println(resultSet.getString("dept_name"));
			
			rowCount++;
		}
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		DBManager.close(resultSet);
		DBManager.close(preparedStatement);
		DBManager.close(connection);
	}
	
 
	}

}

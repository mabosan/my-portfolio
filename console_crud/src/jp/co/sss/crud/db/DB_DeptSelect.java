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
 * 社員管理システム部署ID検索用クラス
 */
public class DB_DeptSelect {
	public static void deptselect() throws SQLException, IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		System.out.println("部署ID(1:営業部、2:経理部、3:総務部)を入力してください:");
		int dept_id = -1; // 無効な値で初期化しておく
	
		while(true) {
			String str = br.readLine();
		try {
			 dept_id = Integer.parseInt(str);
			// 部署IDが1～3の範囲かをチェック
            if (dept_id >= 1 && dept_id <= 3) {
            	break;
            	// 範囲内なら次の処理へ
            } else {
            	System.out.println("1以上3以下の整数を入力してください：");
            	//もう一度入力。１から３が入力されるまで繰り返す。           	
            }    
		}catch (NumberFormatException e) {
            System.out.println("1以上3以下の整数を入力してください：");
        }    
		
		}
            
		try {	
			//DBに接続
			connection = DBManager.getConnection();
			
			// ステートメントを作成
			preparedStatement = connection.prepareStatement(ConstantSQL.SQL_DEPTSELECT);
			
			// 入力値をバインド
			preparedStatement.setInt(1,dept_id);
			
			// SQL文を実行
			resultSet = preparedStatement.executeQuery();
			
			// レコードの行数を数えるための変数を用意
			int rowCount = 0;
					
			boolean employeeFound = false; // フラグを用意

			while (resultSet.next()) {
			    if (!employeeFound) {
			        System.out.println("emp_id\temp_name\tgender\tbirthday\tdept_name");
			    }
			    employeeFound = true; // 社員が見つかったのでフラグを true にする

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

			if (!employeeFound) {
			    System.out.println("該当する社員は存在しません。");
			}
			//falseなら社員の情報を出力せず返します。			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(resultSet);
			DBManager.close(preparedStatement);
			DBManager.close(connection);
		}
		
	 
		}
		
		
}

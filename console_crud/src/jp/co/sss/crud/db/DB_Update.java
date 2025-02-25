package jp.co.sss.crud.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sss.crud.util.ConstantSQL;

/**
 * 社員管理システム更新用クラス
 */
public class DB_Update {
    public static void update() throws ClassNotFoundException, SQLException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        System.out.println("更新する社員の社員IDを入力してください:");
        int empId;
        
        while(true) {
            	String str = br.readLine();
            	empId = Integer.parseInt(str);
            	// 社員IDが1～9999の範囲かをチェック
            if (empId >= 1 && empId <= 9999) {
            	break;
            	// 範囲内なら次の処理へ
            } else {
            	System.out.println("1以上9999以下の整数を入力してください：");
            	//もう一度入力。１から９９９９が入力されるまで繰り返す。           	
            }   
        	}
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
        
        try {
             System.out.print("性別(1:男性, 2:女性):");
             String gender = br.readLine();
             System.out.print("生年月日(西暦年/月/日):");
             String birthday = br.readLine();
             System.out.print("部署ID(1:営業部、2:経理部、3:総務部):");
             String deptId = br.readLine();
            // DBに接続
            connection = DBManager.getConnection();
            
            // SQLクエリの動的生成
            List<String> columnsToUpdate = new ArrayList<>();
            List<Object> values = new ArrayList<>();

            if (empName != null && empName.length() > 0) {
                columnsToUpdate.add("emp_name = ?");
                values.add(empName);
            }
            if (gender != null && gender.length() > 0) {
                columnsToUpdate.add("gender = ?");
                values.add(gender);
            }
            if (birthday != null && birthday.length() > 0) {
                columnsToUpdate.add("birthday = ?");
                values.add(birthday);
            }
            if (deptId != null && deptId.length() > 0) {
                columnsToUpdate.add("dept_id = ?");
                values.add(deptId);
            }

            if (columnsToUpdate.size() == 0) {
                System.out.println("更新するデータがありません。");
                return;
            }

            // SQLクエリを構築
            String sql = ConstantSQL.SQL_UPDATE;

            // カラムの設定部分を追加
            for (int i = 0; i < columnsToUpdate.size(); i++) {
                sql += columnsToUpdate.get(i);
                if (i < columnsToUpdate.size() - 1) {
                    sql += ",";
                }
            }

            // 最後の余分なコンマを削除する
            if (sql.endsWith(",")) {
                sql = sql.substring(0, sql.length() - 1);
            }

            // WHERE句を追加
            sql += " WHERE emp_id = ?";

            // ステートメントを作成
            preparedStatement = connection.prepareStatement(sql);

            // プレースホルダに値をバインド
            for (int i = 0; i < values.size(); i++) {
                preparedStatement.setObject(i + 1, values.get(i));
            }
            preparedStatement.setInt(values.size() + 1, empId);

            // SQL文を実行
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("社員情報を更新しました。");
            } else {
                System.out.println("社員ID " + empId + " の情報を更新できませんでした。");
            }
        } catch (Exception e) { 
            e.printStackTrace();
        } finally {
            DBManager.close(preparedStatement);
            DBManager.close(connection);
        }
    }
}

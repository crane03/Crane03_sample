package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateDB {
	//メインメソッド
	public static void main(String[] args) {
		try {
			//ファイル読み込み
			FileInputStream fis = new FileInputStream("assets/ejdic-hand-utf8.txt");
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(isr);

			//データソースを利用せずダイレクトに接続する。
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/dictionary?useUnicode=true&characterEncoding=utf8";
			String user = "root";
			String pass = "dolphin03";//パスワード設定を指定ない人は""と空文字にする
			Connection db = DriverManager.getConnection(url, user, pass);

			//トランザクション処理開始
			db.setAutoCommit(false);
			PreparedStatement ps = db.prepareStatement("INSERT INTO words(title,body) VALUES(?,?)");
			String line;
			while ((line = br.readLine()) != null) {
				//1行毎にデータを登録していく
				String[] vals = line.split("\t");
				ps.setString(1, vals[0]);
				ps.setString(2, vals[1]);
				ps.executeUpdate();
			}
			//例外が発生しなければcommit(確定)
			db.commit();
			db.close();
			br.close();
			//時間がかかる処理なのでコンソールで終了を伝える。
			System.out.println("done!");
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

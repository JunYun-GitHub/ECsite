package com.internousdev.ecsite.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	/**
	 * JDBC ドライバー名
	 */
	private static String driverName = "com.mysql.jdbc.Driver";
	/**
	 * データベース接続URL
	 */
	private static String url = "jdbc:mysql://localhost/ecsite";
	/**
	 * データベース接続ユーザー名
	 */
	private static String user = "root";
	/**
	 * データベース接続パスワード
	 */
	private static String password = "mysql";

	public Connection getConnection() {
		//一度状態を初期化
		Connection con = null;  //ここまでの流れは、接続をするクラスの公式として覚えておくと良い。

		// 「try~catch 構文」 :Java の例外処理の為の構文
		// 「try」の中にはエラーが発生しそうな処理を書く
		try {
			//ドライバーがロードされ使えるような状態にする
			Class.forName(driverName);
			con = DriverManager.getConnection(url,user,password);
			//「try」の中でエラーが発生した場合に、「catch」が受け取り、「printStackTrace」でエラーに至る履歴を表示する。(今回の場合はエラーが2つ表示される)
		} catch (ClassNotFoundException e) {
			e.printStackTrace() ;
		} catch (SQLException e) {
			e.printStackTrace() ;
		}
		return con;
	}
}


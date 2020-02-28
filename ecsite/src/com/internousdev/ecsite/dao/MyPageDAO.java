package com.internousdev.ecsite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.internousdev.ecsite.dto.MyPageDTO;
import com.internousdev.ecsite.util.DBConnector;


public class MyPageDAO {

	public ArrayList<MyPageDTO> getMyPageUserInfo(String item_transaction_id, String user_master_id) throws SQLException {

		//JDBCの接続に使用するオブジェクトを宣言
		DBConnector db = new DBConnector();		// Connection (DB接続情報) 格納用変数
		Connection con = db.getConnection();	// PreparedStatement (SQL発行用オブジェクト)　格納用変数
		ArrayList<MyPageDTO> myPageDTO = new ArrayList<MyPageDTO>();

		//テーブル結合(参考:MYSQLコマンドプロンプト)
		// iit: item_info_transaction テーブルを再定義したもの
		// ubit:　buy_item_transaction テーブルを再定義したもの
		String sql =
				   	  "SELECT"
				+ "			 ubit.id, iit.item_name, ubit.total_price, ubit.total_count,"
				+ "			 ubit.pay, ubit.insert_date"
				+ "   FROM "
				+ "			 user_buy_item_transaction ubit "
				+ "	  LEFT JOIN "
				+ "			 item_info_transaction iit "
				+ "	  ON "
				+ "			 ubit.item_transaction_id = iit.id "
				+ "	  WHERE "
				+ "			 ubit.item_transaction_id = ? AND ubit.user_master_id = ? "//この ? はプレースホルダ
				+ "   ORDER  BY "													   //PreparedStatementにはプレースホルダ
				+ "			 ubit.insert_date DESC";								   //を扱えるという特徴があり、SQL文中に ? を
																					   //つけることで後から好きな値を発行できる。
		try {
			//PreparedStatementを生成&発行するSQLを取得
			PreparedStatement ps = con.prepareStatement(sql);
			 //"1"番目の? に値を代入
			ps.setString(1, item_transaction_id);
			ps.setString(2, user_master_id);

			ResultSet rs = ps.executeQuery();		// ResultSetオブジェクト  Statement系クラスが持つexecuteQueryの戻り値
													//					 として受け取ることのできるオブジェクト。
													//					 送信したSELECT文の抽出結果のデータが格納されている。

			while(rs.next()) {
				//nextメソッド "カーソル"を1行下に移動させる。※カーソルの初期位置は0行目
				//カーソル移動後の行にデータが存在すればtrue、
				//存在しなければfalseを返す。
				MyPageDTO dto = new MyPageDTO(); //インスタンス化
				dto.setId(rs.getString("id"));
						   //getStringメソッド　現在のカーソルが指している。
						   //				"レコード"で引数と合致する"カラム"のデータを取得する

				//DTOは複数の型のデータをまとめられる。


				dto.setItemName(rs.getString("item_name"));
								//引数と一致したデータを取り出す
				dto.setTotalPrice(rs.getString("total_price"));
				dto.setTotalCount(rs.getString("total_count"));
				dto.setPayment(rs.getString("pay"));
				dto.setInsert_date(rs.getString("insert_date"));
				myPageDTO.add(dto);// → MyPageAction 28行目へ
			}

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
				con.close();
		}
		return myPageDTO;
	}
	public int buyItemHistoryDelete
		(String item_transaction_id, String user_master_id) throws SQLException {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		String sql =
				  "DELETE FROM "
				+ 	"user_buy_item_transaction"
				+ " WHERE "
				+ 	"item_transaction_id = ? AND user_master_id = ?";
		PreparedStatement ps;
		int result = 0;								//

		try {
			ps = con.prepareStatement(sql);			// PreparedStatementオブジェクトを生成するためのメソッド
			ps.setString(1, item_transaction_id);
			ps.setString(2, user_master_id);
			result = ps.executeUpdate();			//executeUpdate() INSERT文，UPDATE文，およびDELETE文を実行した場合は，
													//更新行数(int型)が返却される。これら以外のSQL文を実行した場合は，0が返却される。
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return result;
	}

}

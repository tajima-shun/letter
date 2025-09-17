package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**----------------------------------------------------------------------*
 *■■■UserDaoクラス■■■
 *概要：DAO（「users」テーブル）
 *----------------------------------------------------------------------**/
public class UserDao {
	//-------------------------------------------
	//データベースへの接続情報
	//-------------------------------------------

	//JDBCドライバの相対パス
	String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

	//接続先のデータベース
	String JDBC_URL    = "jdbc:mysql://localhost/test_db?characterEncoding=UTF-8&serverTimezone=JST&useSSL=false";

	//接続するユーザー名
	String USER_ID     = "test_user";

	//接続するユーザーのパスワード
	String USER_PASS   = "test_pass";


	//----------------------------------------------------------------
	//メソッド
	//----------------------------------------------------------------

	/**----------------------------------------------------------------------*
	 *■doSelectメソッド
	 *概要　：引数のユーザー情報に紐づくユーザーデータを「users」テーブルから抽出する
	 *引数①：ユーザー名（ユーザー入力）
	 *引数②：ユーザーパスワード（ユーザー入力）
	 *戻り値：「users」テーブルから抽出したユーザーデータ（UserDto型）
	 *----------------------------------------------------------------------**/
	public UserDto doSelect(String inputUserName, String inputPassWord) {

		//-------------------------------------------
		//JDBCドライバのロード
		//-------------------------------------------
		try {
			Class.forName(DRIVER_NAME);       //JDBCドライバをロード＆接続先として指定
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		//-------------------------------------------
		//SQL発行
		//-------------------------------------------

		//JDBCの接続に使用するオブジェクトを宣言
		Connection        con = null ;
		PreparedStatement ps  = null ;
		ResultSet         rs  = null ;

		//抽出データ（UserDto型）格納用変数
		UserDto dto = new UserDto();

		try {

			//-------------------------------------------
			//接続の確立（Connectionオブジェクトの取得）
			//-------------------------------------------
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);

			//-------------------------------------------
			//SQL文の送信 ＆ 結果の取得
			//-------------------------------------------

			//発行するSQL文の生成（SELECT）
			StringBuffer buf = new StringBuffer();
			buf.append(" SELECT             ");
			buf.append("   user_id,       ");
			buf.append("   user_name,       ");
			buf.append("   password         ");
			buf.append(" FROM               ");
			buf.append("   users        ");
			buf.append(" WHERE              ");
			buf.append("   user_name  = ? AND ");
			buf.append("   password = ?     ");

			ps = con.prepareStatement(buf.toString());

			//パラメータをセット
			ps.setString( 1, inputUserName   );
			ps.setString( 2, inputPassWord );

			rs = ps.executeQuery();

			//--------------------------------------------------------------------------------
			//ResultSetオブジェクトからユーザーデータを抽出
			//--------------------------------------------------------------------------------
			if (rs.next()) {
				//ResultSetから1行分のレコード情報をDTOへ登録
				dto.setUserId(   rs.getInt("user_id")   );
				dto.setUserName( rs.getString("user_name") );
				dto.setPassword( rs.getString("password")  );
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			//-------------------------------------------
			//接続の解除
			//-------------------------------------------

			if (rs != null) {    //接続が確認できている場合のみ実施
				try {
					rs.close();  //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (ps != null) {    //接続が確認できている場合のみ実施
				try {
					ps.close();  //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (con != null) {    //接続が確認できている場合のみ実施
				try {
					con.close();  //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		//抽出したユーザーデータを戻す
		return dto;
	}

	/**----------------------------------------------------------------------*
	 *■selectAllUsersメソッド
	 *概要　：「users」テーブルのデータを全件抽出する（指定したユーザー以外）
	 *引数　：除外するユーザーのID
	 *戻り値：抽出結果（DTOリスト）
	 *----------------------------------------------------------------------**/
	public List<UserDto> selectAllUsers(int excludeUserId) {
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<UserDto> dtoList = new ArrayList<UserDto>();

		try {
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);

			String sql = "SELECT user_id, user_name FROM users WHERE user_id != ? ORDER BY user_id";
			ps = con.prepareStatement(sql);
			ps.setInt(1, excludeUserId);
			rs = ps.executeQuery();

			while (rs.next()) {
				UserDto dto = new UserDto();
				dto.setUserId(rs.getInt("user_id"));
				dto.setUserName(rs.getString("user_name"));
				dtoList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if (ps != null) { try { ps.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if (con != null) { try { con.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}

		return dtoList;
	}
}

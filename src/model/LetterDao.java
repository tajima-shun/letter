package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**----------------------------------------------------------------------*
 *■■■LetterDaoクラス■■■
 *概要：DAO（「letters」テーブル）
 *----------------------------------------------------------------------**/
public class LetterDao {
	//-------------------------------------------
	//データベースへの接続情報
	//-------------------------------------------

	String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	String JDBC_URL    = "jdbc:mysql://localhost/test_db?characterEncoding=UTF-8&serverTimezone=JST&useSSL=false";
	String USER_ID     = "test_user";
	String USER_PASS   = "test_pass";

	/**----------------------------------------------------------------------*
	 *■doSelectメソッド
	 *概要　：「letters」テーブルから指定したユーザーが送受信したデータを抽出する
	 *引数　：ログインユーザーのID
	 *戻り値：抽出結果（DTOリスト）
	 *----------------------------------------------------------------------**/
	public List<LetterDto> doSelect(int loginUserId) {

		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection        con = null ;
		PreparedStatement ps  = null ;
		ResultSet         rs  = null ;

		List<LetterDto> dtoList = new ArrayList<LetterDto>();

		try {

			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);

			StringBuffer buf = new StringBuffer();
			buf.append("SELECT ");
			buf.append("    l.*, ");
			buf.append("    sender.user_name AS sender_name, ");
			buf.append("    recipient.user_name AS recipient_name ");
			buf.append("FROM ");
			buf.append("    letters l ");
			buf.append("JOIN ");
			buf.append("    users sender ON l.sender_id = sender.user_id ");
			buf.append("JOIN ");
			buf.append("    users recipient ON l.recipient_id = recipient.user_id ");
			buf.append("WHERE ");
			buf.append("    l.sender_id = ? OR l.recipient_id = ? ");
			buf.append("ORDER BY ");
			buf.append("    l.created_at DESC");

			ps = con.prepareStatement(buf.toString());
			ps.setInt(1, loginUserId);
			ps.setInt(2, loginUserId);
			rs = ps.executeQuery();

			while (rs.next()) {
				LetterDto dto = new LetterDto();
				dto.setLetterId(rs.getInt("letter_id"));
				dto.setSenderId(rs.getInt("sender_id"));
				dto.setRecipientId(rs.getInt("recipient_id"));
				dto.setMessage(rs.getString("message"));
				dto.setCreatedAt(rs.getTimestamp("created_at"));
				dto.setSenderName(rs.getString("sender_name"));
				dto.setRecipientName(rs.getString("recipient_name"));
				dto.setImageFilename(rs.getString("image_filename"));
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

	/**----------------------------------------------------------------------*
	 *■getReceivedLettersメソッド
	 *概要　：「letters」テーブルから指定したユーザーが受信したデータを抽出する
	 *引数　：ログインユーザーのID
	 *戻り値：抽出結果（DTOリスト）
	 *----------------------------------------------------------------------**/
	public List<LetterDto> getReceivedLetters(int loginUserId) {

		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection        con = null ;
		PreparedStatement ps  = null ;
		ResultSet         rs  = null ;

		List<LetterDto> dtoList = new ArrayList<LetterDto>();

		try {

			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);

			StringBuffer buf = new StringBuffer();
			buf.append("SELECT ");
			buf.append("    l.*, ");
			buf.append("    sender.user_name AS sender_name, ");
			buf.append("    recipient.user_name AS recipient_name ");
			buf.append("FROM ");
			buf.append("    letters l ");
			buf.append("JOIN ");
			buf.append("    users sender ON l.sender_id = sender.user_id ");
			buf.append("JOIN ");
			buf.append("    users recipient ON l.recipient_id = recipient.user_id ");
			buf.append("WHERE ");
			buf.append("    l.recipient_id = ? ");
			buf.append("ORDER BY ");
			buf.append("    l.created_at DESC");

			ps = con.prepareStatement(buf.toString());
			ps.setInt(1, loginUserId);
			rs = ps.executeQuery();

			while (rs.next()) {
				LetterDto dto = new LetterDto();
				dto.setLetterId(rs.getInt("letter_id"));
				dto.setSenderId(rs.getInt("sender_id"));
				dto.setRecipientId(rs.getInt("recipient_id"));
				dto.setMessage(rs.getString("message"));
				dto.setCreatedAt(rs.getTimestamp("created_at"));
				dto.setSenderName(rs.getString("sender_name"));
				dto.setRecipientName(rs.getString("recipient_name"));
				dto.setImageFilename(rs.getString("image_filename"));
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

	/**----------------------------------------------------------------------*
	 *■getSentLettersメソッド
	 *概要　：「letters」テーブルから指定したユーザーが送信したデータを抽出する
	 *引数　：ログインユーザーのID
	 *戻り値：抽出結果（DTOリスト）
	 *----------------------------------------------------------------------**/
	public List<LetterDto> getSentLetters(int loginUserId) {

		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection        con = null ;
		PreparedStatement ps  = null ;
		ResultSet         rs  = null ;

		List<LetterDto> dtoList = new ArrayList<LetterDto>();

		try {

			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);

			StringBuffer buf = new StringBuffer();
			buf.append("SELECT ");
			buf.append("    l.*, ");
			buf.append("    sender.user_name AS sender_name, ");
			buf.append("    recipient.user_name AS recipient_name ");
			buf.append("FROM ");
			buf.append("    letters l ");
			buf.append("JOIN ");
			buf.append("    users sender ON l.sender_id = sender.user_id ");
			buf.append("JOIN ");
			buf.append("    users recipient ON l.recipient_id = recipient.user_id ");
			buf.append("WHERE ");
			buf.append("    l.sender_id = ? ");
			buf.append("ORDER BY ");
			buf.append("    l.created_at DESC");

			ps = con.prepareStatement(buf.toString());
			ps.setInt(1, loginUserId);
			rs = ps.executeQuery();

			while (rs.next()) {
				LetterDto dto = new LetterDto();
				dto.setLetterId(rs.getInt("letter_id"));
				dto.setSenderId(rs.getInt("sender_id"));
				dto.setRecipientId(rs.getInt("recipient_id"));
				dto.setMessage(rs.getString("message"));
				dto.setCreatedAt(rs.getTimestamp("created_at"));
				dto.setSenderName(rs.getString("sender_name"));
				dto.setRecipientName(rs.getString("recipient_name"));
				dto.setImageFilename(rs.getString("image_filename"));
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

	/**----------------------------------------------------------------------*
	 *■doInsertメソッド
	 *概要　：「letters」テーブルに対象のデータを挿入する
	 *引数　：対象のデータ（LetterDto型）
	 *戻り値：実行結果（真：成功、偽：例外発生）
	 *----------------------------------------------------------------------**/
	public boolean doInsert(LetterDto dto) {

		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection        con = null ;
		PreparedStatement ps  = null ;
		boolean isSuccess = true ;

		try {

			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);
			con.setAutoCommit(false);

			StringBuffer buf = new StringBuffer();
			buf.append("INSERT INTO letters (sender_id, recipient_id, message, image_filename) VALUES (?, ?, ?, ?)");

			ps = con.prepareStatement(buf.toString());

			ps.setInt(1, dto.getSenderId());
			ps.setInt(2, dto.getRecipientId());
			ps.setString(3, dto.getMessage());
			ps.setString(4, dto.getImageFilename());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			isSuccess = false ;
		} finally {
			if(isSuccess){
				try { con.commit(); } catch (SQLException e) { e.printStackTrace(); }
			}else{
				try { con.rollback(); } catch (SQLException e) { e.printStackTrace(); }
			}
			if (ps != null) { try { ps.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if (con != null) { try { con.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}

		return isSuccess;
	}

	/**----------------------------------------------------------------------*
	 *■doDeleteSentメソッド
	 *概要　：「letters」テーブルから対象のデータを削除する（送信者本人による削除）
	 *引数①：削除対象のメッセージID
	 *引数②：削除を実行するユーザーのID（送信者であることの確認用）
	 *戻り値：実行結果（真：成功、偽：例外発生）
	 *----------------------------------------------------------------------**/
	public boolean doDeleteSent(int letterId, int senderId) {

		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection con = null;
		PreparedStatement ps = null;
		boolean isSuccess = true;

		try {
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);
			con.setAutoCommit(false);

			String sql = "DELETE FROM letters WHERE letter_id = ? AND sender_id = ?";

			ps = con.prepareStatement(sql);
			ps.setInt(1, letterId);
			ps.setInt(2, senderId);

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			isSuccess = false;
		} finally {
			if (isSuccess) {
				try { con.commit(); } catch (SQLException e) { e.printStackTrace(); }
			} else {
				try { con.rollback(); } catch (SQLException e) { e.printStackTrace(); }
			}
			if (ps != null) { try { ps.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if (con != null) { try { con.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}

		return isSuccess;
	}

	/**----------------------------------------------------------------------*
	 *■selectByLetterIdメソッド
	 *概要　：「letters」テーブルから特定のデータを1件抽出する
	 *引数①：対象のメッセージID
	 *引数②：編集を実行するユーザーのID（送信者であることの確認用）
	 *戻り値：抽出結果（DTO）
	 *----------------------------------------------------------------------**/
	public LetterDto selectByLetterId(int letterId, int senderId) {
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		LetterDto dto = null;

		try {
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);
			String sql = "SELECT * FROM letters WHERE letter_id = ? AND sender_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, letterId);
			ps.setInt(2, senderId);
			rs = ps.executeQuery();

			if (rs.next()) {
				dto = new LetterDto();
				dto.setLetterId(rs.getInt("letter_id"));
				dto.setSenderId(rs.getInt("sender_id"));
				dto.setRecipientId(rs.getInt("recipient_id"));
				dto.setMessage(rs.getString("message"));
				dto.setCreatedAt(rs.getTimestamp("created_at"));
				dto.setImageFilename(rs.getString("image_filename")); // ★この行を追加
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if (ps != null) { try { ps.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if (con != null) { try { con.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
		return dto;
	}

	/**----------------------------------------------------------------------*
	 *■doUpdateメソッド
	 *概要　：「letters」テーブルのデータを更新する
	 *引数①：対象のメッセージID
	 *引数②：新しいメッセージ内容
	 *引数③：新しい画像ファイル名
	 *引数④：編集を実行するユーザーのID（送信者であることの確認用）
	 *戻り値：実行結果（真：成功、偽：例外発生）
	 *----------------------------------------------------------------------**/
	public boolean doUpdate(int letterId, String message, String imageFilename, int senderId) {
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection con = null;
		PreparedStatement ps = null;
		boolean isSuccess = true;

		try {
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);
			con.setAutoCommit(false);

			String sql = "UPDATE letters SET message = ?, image_filename = ? WHERE letter_id = ? AND sender_id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, message);
			ps.setString(2, imageFilename);
			ps.setInt(3, letterId);
			ps.setInt(4, senderId);

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			isSuccess = false;
		} finally {
			if (isSuccess) {
				try { con.commit(); } catch (SQLException e) { e.printStackTrace(); }
			} else {
				try { con.rollback(); } catch (SQLException e) { e.printStackTrace(); }
			}
			if (ps != null) { try { ps.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if (con != null) { try { con.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
		return isSuccess;
	}
}

package model;

/**----------------------------------------------------------------------*
 *■■■UserDtoクラス■■■
 *概要：DTO（「users」テーブル）
 *----------------------------------------------------------------------**/
public class UserDto {

	//----------------------------------------------------------------
	//フィールド
	//----------------------------------------------------------------
	private int userId;         //ユーザーID
	private String userName;       //ユーザー名
	private String password;       //ユーザーパスワード


	//----------------------------------------------------------------
	//getter/setter
	//----------------------------------------------------------------

	//getter/setter（対象フィールド：userId）
	public int getUserId() { return userId; }
	public void setUserId(int userId) { this.userId = userId; }

	//getter/setter（対象フィールド：userName）
	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }

	//getter/setter（対象フィールド：password）
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

}

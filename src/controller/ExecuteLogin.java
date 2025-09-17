package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserDao;
import model.UserDto;


/**----------------------------------------------------------------------*
 *■■■ExecuteLoginクラス■■■
 *概要：サーブレット
 *詳細：「users」テーブルからユーザー入力値と合致するユーザーデータを抽出し、リダイレクトする。
 *　　　＜リダイレクト先＞合致データあり（ログインOK）：LetterMainサーブレット
 *　　　　　　　　　　　　合致データなし（ログインNG）：Loginサーブレット（前画面に戻る）
 *----------------------------------------------------------------------**/
public class ExecuteLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteLogin() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//レスポンス（出力データ）の文字コードを設定
		response.setContentType("text/html;charset=UTF-8");
		//リクエスト（受信データ）の文字コードを設定
		request.setCharacterEncoding("UTF-8");

		//セッションからユーザーデータを取得
		HttpSession session           = request.getSession();
		UserDto userInfoOnSession = (UserDto)session.getAttribute("LOGIN_INFO");

		//ログイン状態によって表示画面を振り分ける
		if (userInfoOnSession != null) {

			//ログイン済：メイン画面に転送
			response.sendRedirect("LetterMain");

		} else {

			//未ログイン：ログイン処理を実施

			boolean succesFlg = true;  //成功フラグ（true:成功/false:失敗）

			//パラメータのバリデーションチェック
			if( !(validatePrmUserName(request.getParameter("USER_NAME"))        &&
					validatePrmPassword(request.getParameter("PASSWORD"))     ) ) {

				//バリデーションNGの場合
				succesFlg = false ;

			}else {

				//バリデーションOKの場合

				//リクエストパラメータからユーザー入力値を取得
				String userName   = request.getParameter("USER_NAME");
				String passWord = request.getParameter("PASSWORD");

				//「users」テーブルからユーザー入力値と合致するユーザーデータ（UserDto型）を抽出
				UserDao dao   = new UserDao();
				UserDto    dto   = dao.doSelect(userName, passWord);

				if (dto.getUserName() == null) {

					//データの取得に失敗した場合
					succesFlg = false ;

				}else {
					//データの取得に成功した場合、DBから抽出したユーザデータをセッションにセット
					session.setAttribute("LOGIN_INFO", dto);

				}
			}


			//成功/失敗に応じて表示させる画面を振り分ける
			if (succesFlg) {
				//成功：ログインOKとしてメイン画面へ
				response.sendRedirect("LetterMain");

			} else {
				//失敗：ログインNGとしてログイン画面へ転送
				response.sendRedirect("Login");

			}
		}
	}

	/**----------------------------------------------------------------------*
	 *■■■validatePrmUserNameクラス■■■
	 *概要：バリデーションチェック
	 *詳細：入力値（ユーザー名）の検証を行う
	 *----------------------------------------------------------------------**/
	private boolean validatePrmUserName( String pr) {

		boolean validateResult = true ;

		//入力値がnullまたは空白の場合はエラーとする
			if( pr == null || pr.equals("") ) {
			validateResult = false ;
		}

		return validateResult ;
	}

	/**----------------------------------------------------------------------*
	 *■■■validatePrmPasswordクラス■■■
	 *概要：バリデーションチェック
	 *詳細：入力値（パスワード）の検証を行う
	 *----------------------------------------------------------------------**/
	private boolean validatePrmPassword( String pr) {

		boolean validateResult = true ;

		//入力値がnullまたは空白の場合はエラーとする
			if( pr == null || pr.equals("") ) {
			validateResult = false ;
		}

		return validateResult ;
	}

}

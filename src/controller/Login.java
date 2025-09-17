package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserDto;

/**----------------------------------------------------------------------*
 *■■■Loginクラス■■■
 *概要：サーブレット
 *詳細：HTML文書（ログイン画面）を出力する。
 *----------------------------------------------------------------------**/
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	 throws ServletException, IOException {

		//レスポンス（出力データ）の文字コードを設定
		response.setContentType("text/html;charset=UTF-8");  //文字コードをUTF-8で設定


		// セッションからログイン情報を取得
		HttpSession session = request.getSession();
		UserDto userInfoOnSession = (UserDto) session.getAttribute("LOGIN_INFO");

		//ログイン状態によって表示画面を振り分ける
		if (userInfoOnSession != null) {
			//ログイン済：メイン画面へ転送
			response.sendRedirect("LetterMain");

		} else {
			//未ログイン：HTML文書（ログイン画面）の出力
			//Viewにフォワード
			RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/login.jsp");
			dispatch.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	 throws ServletException, IOException {
		doGet(request, response);
	}

}

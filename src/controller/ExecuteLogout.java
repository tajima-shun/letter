package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**----------------------------------------------------------------------*
 *■■■ExecuteLogoutクラス■■■
 *概要：サーブレット
 *詳細：ログアウト処理を行い、ログイン画面にリダイレクトする。
 *----------------------------------------------------------------------**/
public class ExecuteLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteLogout() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// セッションを取得し、存在すれば無効化する
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		// ログイン画面にリダイレクト
		response.sendRedirect("Login");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

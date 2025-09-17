package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.LetterDao;
import model.LetterDto;
import model.UserDao;
import model.UserDto;

/**----------------------------------------------------------------------*
 *■■■LetterMainクラス■■■
 *概要：サーブレット
 *詳細：ログイン後のメイン画面を表示する。
 *----------------------------------------------------------------------**/
public class LetterMain extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LetterMain() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//レスポンス（出力データ）の文字コードを設定
		response.setContentType("text/html;charset=UTF-8");

		// セッションからログイン情報を取得
		HttpSession session = request.getSession();
		UserDto userInfoOnSession = (UserDto) session.getAttribute("LOGIN_INFO");

		//ログイン状態によって表示画面を振り分ける
		if (userInfoOnSession != null) {
			//ログイン済

			//DAOを生成し、自分宛のメッセージを取得
			LetterDao letterDao = new LetterDao();
			List<LetterDto> letterList = letterDao.getReceivedLetters(userInfoOnSession.getUserId());

			//リクエストスコープにメッセージリストを保存
			request.setAttribute("letterList", letterList);

			// Viewにフォワード
			RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/main.jsp");
			dispatch.forward(request, response);

		} else {
			//未ログイン：ログイン画面にリダイレクト
			response.sendRedirect("Login");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

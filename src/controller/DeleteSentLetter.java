package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.LetterDao;
import model.UserDto;

/**----------------------------------------------------------------------*
 *■■■DeleteSentLetterクラス■■■
 *概要：サーブレット
 *詳細：送信済みメッセージの削除処理を行う。
 *----------------------------------------------------------------------**/
public class DeleteSentLetter extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DeleteSentLetter() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッションからログイン情報を取得
        HttpSession session = request.getSession();
        UserDto loginUser = (UserDto) session.getAttribute("LOGIN_INFO");

        if (loginUser != null) {
            // ログイン済

            try {
                // フォームから送信されたパラメータを取得
                int letterId = Integer.parseInt(request.getParameter("LETTER_ID"));

                // DAOを生成し、削除処理を実行
                LetterDao dao = new LetterDao();
                dao.doDeleteSent(letterId, loginUser.getUserId());

            } catch (NumberFormatException e) {
                // letter_idが不正な場合は何もしない
                e.printStackTrace();
            }

            // 処理の成否にかかわらず、メイン画面にリダイレクト
            response.sendRedirect("LetterMain");

        } else {
            // 未ログイン：ログイン画面にリダイレクト
            response.sendRedirect("Login");
        }
    }
}

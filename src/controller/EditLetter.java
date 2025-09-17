package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.LetterDao;
import model.LetterDto;
import model.UserDto;

/**----------------------------------------------------------------------*
 *■■■EditLetterクラス■■■
 *概要：サーブレット
 *詳細：メッセージ編集画面を表示する。
 *----------------------------------------------------------------------**/
public class EditLetter extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserDto loginUser = (UserDto) session.getAttribute("LOGIN_INFO");

        if (loginUser != null) {
            try {
                int letterId = Integer.parseInt(request.getParameter("letter_id"));

                LetterDao dao = new LetterDao();
                LetterDto letter = dao.selectByLetterId(letterId, loginUser.getUserId());

                request.setAttribute("letter", letter);

            } catch (NumberFormatException e) {
                request.setAttribute("letter", null);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/edit_letter.jsp");
            dispatcher.forward(request, response);

        } else {
            response.sendRedirect("Login");
        }
    }
}

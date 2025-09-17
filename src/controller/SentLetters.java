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
import model.UserDto;

public class SentLetters extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SentLetters() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession();
		UserDto userInfoOnSession = (UserDto) session.getAttribute("LOGIN_INFO");

		if (userInfoOnSession != null) {
			LetterDao letterDao = new LetterDao();
			List<LetterDto> letterList = letterDao.getSentLetters(userInfoOnSession.getUserId());

			request.setAttribute("letterList", letterList);

			RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/sent.jsp");
			dispatch.forward(request, response);

		} else {
			response.sendRedirect("Login");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

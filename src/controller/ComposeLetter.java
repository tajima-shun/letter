package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserDao;
import model.UserDto;

public class ComposeLetter extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ComposeLetter() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession();
		UserDto userInfoOnSession = (UserDto) session.getAttribute("LOGIN_INFO");

		if (userInfoOnSession == null) {
			response.sendRedirect("Login");
			return;
		}

		String theme = request.getParameter("theme");

		if (theme == null || theme.isEmpty()) {
			// テーマが選択されていない場合は選択画面にフォワード
			RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/letter_selection.jsp");
			dispatch.forward(request, response);
		} else {
			// テーマが選択されている場合は送信画面にフォワード
			UserDao userDao = new UserDao();
			List<UserDto> userList = userDao.selectAllUsers(userInfoOnSession.getUserId());

			request.setAttribute("userList", userList);
			request.setAttribute("theme", theme);

			RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/send_letter.jsp");
			dispatch.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

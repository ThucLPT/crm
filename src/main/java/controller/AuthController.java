package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import model.User;

/**
 * Servlet implementation class LoginController
 */
@WebServlet(urlPatterns = { "/login", "/logout", "/403" })
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthController() {
		super();
		// TODO Auto-generated constructor stub
		userDao = new UserDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
		if (path.equals("/login") && request.getSession().getAttribute("user") == null)
			request.getRequestDispatcher("login.jsp").forward(request, response);
		if (path.equals("/logout")) {
			request.getSession().invalidate();
			response.sendRedirect("login");
		}
		if (path.equals("/403"))
			request.getRequestDispatcher("403.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User user = userDao.login(email, password);
		request.getSession().setAttribute("user", user);
		String roleName = user.getRole().getName();
		if (roleName.equals("ROLE_ADMIN"))
			response.sendRedirect("user-table");
		else if (roleName.equals("ROLE_LEADER"))
			response.sendRedirect("groupwork");
		else if (roleName.equals("ROLE_MEMBER"))
			response.sendRedirect("profile");
	}
}

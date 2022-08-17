package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RoleDao;
import dao.UserDao;
import model.User;

/**
 * Servlet implementation class AdminController
 */
@WebServlet(urlPatterns = { "/user-add", "/user-table" })
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private RoleDao roleDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminController() {
		super();
		// TODO Auto-generated constructor stub
		userDao = new UserDao();
		roleDao = new RoleDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
		if (path.contains("user-add")) {
			request.setAttribute("roles", roleDao.findAll());
			request.getRequestDispatcher("user-add.jsp").forward(request, response);
		}
		if (path.contains("user-table")) {
			request.setAttribute("users", userDao.findAll());
			request.getRequestDispatcher("user-table.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = new User();
		user.setEmail(request.getParameter("email"));
		user.setPassword(request.getParameter("password"));
		user.setFullname(request.getParameter("fullname"));
		user.setRole(roleDao.findRoleById(Integer.valueOf(request.getParameter("role"))));
		userDao.save(user);
		response.sendRedirect("user-table");
	}
}

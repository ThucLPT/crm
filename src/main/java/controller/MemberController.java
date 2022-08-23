package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TaskDao;
import model.User;

/**
 * Servlet implementation class MemberController
 */
@WebServlet(urlPatterns = { "/profile", "/profile-edit" })
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TaskDao taskDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberController() {
		super();
		// TODO Auto-generated constructor stub
		taskDao = new TaskDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
		if (path.equals("/profile")) {
			request.setAttribute("tasks",
					taskDao.findByUserId(((User) request.getSession().getAttribute("user")).getId()));
			request.getRequestDispatcher("profile.jsp").forward(request, response);
		}
		if (path.equals("/profile-edit")) {
			request.setAttribute("task", taskDao.findById(Integer.parseInt(request.getParameter("id"))));
			request.getRequestDispatcher("profile-edit.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}

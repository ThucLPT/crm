package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StatusDao;
import dao.TaskDao;
import model.Task;
import model.User;

/**
 * Servlet implementation class MemberController
 */
@WebServlet(urlPatterns = { "/profile", "/profile-edit" })
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TaskDao taskDao;
	private StatusDao statusDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberController() {
		super();
		// TODO Auto-generated constructor stub
		taskDao = new TaskDao();
		statusDao = new StatusDao();
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
			List<Task> tasks = taskDao.findByUserId(((User) request.getSession().getAttribute("user")).getId());
			long count = tasks.stream().filter(t -> t.getId() == Integer.parseInt(request.getParameter("id"))).count();
			if (count > 0) {
				request.setAttribute("task", taskDao.findById(Integer.parseInt(request.getParameter("id"))));
				request.setAttribute("statuses", statusDao.findAll());
				request.getRequestDispatcher("profile-edit.jsp").forward(request, response);
			} else
				response.sendRedirect("403");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Task> tasks = taskDao.findByUserId(((User) request.getSession().getAttribute("user")).getId());
		long count = tasks.stream().filter(t -> t.getId() == Integer.parseInt(request.getParameter("id"))).count();
		if (count > 0) {
			Task task = taskDao.findById(Integer.parseInt(request.getParameter("id")));
			task.setStatus(statusDao.findById(Integer.parseInt(request.getParameter("status"))));
			taskDao.updateStatus(task);
			response.sendRedirect("profile");
		} else
			response.sendRedirect("403");
	}
}

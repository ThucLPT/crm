package controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.JobDao;
import dao.TaskDao;
import model.Job;
import model.User;

/**
 * Servlet implementation class LeaderController
 */
@WebServlet(urlPatterns = { "/groupwork", "/groupwork-add", "/task", "/task-add" })
public class LeaderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JobDao jobDao;
	private TaskDao taskDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LeaderController() {
		super();
		// TODO Auto-generated constructor stub
		jobDao = new JobDao();
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
		if (path.equals("/groupwork")) {
			request.setAttribute("jobs",
					jobDao.findByUserId(((User) request.getSession().getAttribute("user")).getId()));
			request.getRequestDispatcher("groupwork.jsp").forward(request, response);
		}
		if (path.equals("/groupwork-add"))
			request.getRequestDispatcher("groupwork-add.jsp").forward(request, response);
		if (path.equals("/task")) {
			request.setAttribute("tasks", taskDao.findByJobId(Integer.parseInt(request.getParameter("id"))));
			request.getRequestDispatcher("task.jsp").forward(request, response);
		}
		if (path.equals("/task-add")) {
			request.getRequestDispatcher("task-add.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
		if (path.equals("/groupwork-add")) {
			Job job = new Job();
			job.setName(request.getParameter("name"));
			job.setStartDate(LocalDate.parse(request.getParameter("startdate")));
			job.setEndDate(LocalDate.parse(request.getParameter("enddate")));
			job.setLeader((User) request.getSession().getAttribute("user"));
			jobDao.save(job);
			response.sendRedirect("groupwork");
		}
	}
}

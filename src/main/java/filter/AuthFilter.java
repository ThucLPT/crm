package filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

/**
 * Servlet Filter implementation class AuthFilter
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

	/**
	 * @see HttpFilter#HttpFilter()
	 */
	public AuthFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String path = req.getServletPath();
		if (path.equals("/login")) {
			chain.doFilter(request, response);
			return;
		}
		User user = (User) req.getSession().getAttribute("user");
		if (user != null) {
			String roleName = user.getRole().getName();
			List<String> adminUrls = Arrays.asList("/user-add", "/user-table");
			List<String> leaderUrls = Arrays.asList("/groupwork", "/groupwork-add", "/task", "/task-add");
			List<String> memberUrls = Arrays.asList("/profile", "/profile-edit");
			List<String> resourceUrls = Arrays.asList("/bootstrap/", "/css/", "/js/", "/less/", "/plugins/");
			boolean isAuthorized = (roleName.equals("ROLE_ADMIN") && adminUrls.contains(path))
					|| (roleName.equals("ROLE_LEADER") && leaderUrls.contains(path))
					|| (roleName.equals("ROLE_MEMBER") && memberUrls.contains(path));
			if (isAuthorized || path.equals("/logout") || path.equals("/403")
					|| resourceUrls.stream().anyMatch(req.getRequestURI()::contains))
				chain.doFilter(request, response);
			else
				res.sendRedirect("403");
		} else
			res.sendRedirect("login");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
}

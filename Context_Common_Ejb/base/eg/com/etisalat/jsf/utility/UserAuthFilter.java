package eg.com.etisalat.jsf.utility;

import java.io.IOException;
import java.security.Principal;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import eg.com.etisalat.contest.dao.UserDAO;
import eg.com.etisalat.contest.domain.User;

@WebFilter(filterName = "UserAuthFilter", urlPatterns = { "/*" })
public class UserAuthFilter implements Filter {

	@Inject
	private UserDAO userDAO;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse arg1, FilterChain filterChain) throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
		User user = (User) session.getAttribute("LOGGEDINUSER");
		if (user == null) {
			Principal userPrincibal = ((HttpServletRequest) servletRequest).getUserPrincipal();
			if (userPrincibal != null) {
				user = userDAO.getUserByEmail(userPrincibal.getName());
				session.setAttribute("LOGGEDINUSER", user);
			}
		}
		filterChain.doFilter(servletRequest, arg1);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}

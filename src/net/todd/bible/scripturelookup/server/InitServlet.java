package net.todd.bible.scripturelookup.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 5494752422477925172L;

	@Override
	public void init() throws ServletException {
		super.init();
		new ServletContextProvider().setServletContext(getServletContext());
	}
}

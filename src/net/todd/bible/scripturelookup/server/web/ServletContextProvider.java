package net.todd.bible.scripturelookup.server.web;

import javax.servlet.ServletContext;

public class ServletContextProvider implements IServletContextProvider {
	private static ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		ServletContextProvider.servletContext = servletContext;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}
}

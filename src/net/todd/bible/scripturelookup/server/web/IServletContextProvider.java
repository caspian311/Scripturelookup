package net.todd.bible.scripturelookup.server.web;

import javax.servlet.ServletContext;

public interface IServletContextProvider {

	void setServletContext(ServletContext servletContext);

	ServletContext getServletContext();

}
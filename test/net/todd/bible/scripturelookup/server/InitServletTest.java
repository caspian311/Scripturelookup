package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;

public class InitServletTest {
	private ServletContext servletContext;

	@Before
	public void setUp() {
		servletContext = mock(ServletContext.class);
		new ServletContextProvider().setServletContext(null);
	}

	@Test
	public void whenInitializedSetServletContextInTheProvider() throws ServletException {
		new InitServlet() {
			private static final long serialVersionUID = 1L;

			@Override
			public ServletContext getServletContext() {
				return servletContext;
			}
		}.init();

		assertSame(servletContext, new ServletContextProvider().getServletContext());
	}
}

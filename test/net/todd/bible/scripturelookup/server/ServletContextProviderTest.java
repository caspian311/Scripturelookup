package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import javax.servlet.ServletContext;

import net.todd.bible.scripturelookup.server.web.ServletContextProvider;

import org.junit.Before;
import org.junit.Test;

public class ServletContextProviderTest {
	private ServletContext servletContext;

	@Before
	public void setUp() {
		servletContext = mock(ServletContext.class);
	}

	@Test
	public void singletonNature() {
		new ServletContextProvider().setServletContext(servletContext);

		assertSame(servletContext, new ServletContextProvider().getServletContext());
	}
}

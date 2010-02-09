package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import javax.servlet.ServletContext;

import org.junit.Before;
import org.junit.Test;

public class BibleSearchFactoryTest {
	private BibleSearchFactory bibleSearchFactory;
	private IServletContextProvider servletContextProvider;
	private ServletContext servletContext;

	@Before
	public void setUp() {
		servletContextProvider = mock(IServletContextProvider.class);
		servletContext = mock(ServletContext.class);
		bibleSearchFactory = new BibleSearchFactory(servletContextProvider);
		
		doReturn(servletContext).when(servletContextProvider).getServletContext();
		doReturn("").when(servletContext).getRealPath("/WEB-INF/index");
	}

	@Test
	public void makeSureToUseTheIndexFoundInTheServletsContext() {
		bibleSearchFactory.getSearchEngine("keyword");

		verify(servletContextProvider).getServletContext();
		verify(servletContext).getRealPath("/WEB-INF/index");
	}

	@Test
	public void whenAskedForAKeywordSearchEngineReturnLuceneSearchEngine() {
		assertTrue(bibleSearchFactory.getSearchEngine("keyword") instanceof LuceneSearchEngine);
	}
	
	@Test
	public void whenAskedForAReferenceSearchEngineReturnReferenceSearchEngine() {
		assertTrue(bibleSearchFactory.getSearchEngine("reference") instanceof ReferenceSearchEngine);
	}
	
	@Test(expected = RuntimeException.class)
	public void whenAskedForAnOddSearchEngineThrowRuntimeException() {
		bibleSearchFactory.getSearchEngine(UUID.randomUUID().toString());
	}
}

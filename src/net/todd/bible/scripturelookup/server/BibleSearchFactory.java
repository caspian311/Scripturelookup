package net.todd.bible.scripturelookup.server;

import org.mortbay.jetty.handler.ContextHandler;

import com.google.apphosting.utils.jetty.AppEngineWebAppContext;

public class BibleSearchFactory implements IBibleSearchFactory {
	@Override
	public ISearchEngine getSearchEngine(String queryType) {
		ISearchEngine searchEngine = null;
		if ("keyword".equals(queryType)) {
			ContextHandler currentWebAppContext = AppEngineWebAppContext.getCurrentWebAppContext();
			String indexLocation = null;
			if (currentWebAppContext != null) {
				indexLocation = currentWebAppContext.getServletContext().getRealPath(
						"/WEB-INF/index");
			}
			searchEngine = new LuceneSearchEngine(indexLocation, new SearchResultToVerseConverter());
		} else if ("reference".equals(queryType)) {
			searchEngine = new ReferenceSearchEngine(new ReferenceParser(), new ReferenceLookup(
					BibleDaoProvider.getBibleDao()));
		} else {
			throw new RuntimeException();
		}
		return searchEngine;
	}
}

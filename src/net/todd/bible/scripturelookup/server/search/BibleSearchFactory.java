package net.todd.bible.scripturelookup.server.search;

import java.util.logging.Logger;

import net.todd.bible.scripturelookup.server.ISearchEngine;
import net.todd.bible.scripturelookup.server.ReferenceLookup;
import net.todd.bible.scripturelookup.server.ReferenceParser;
import net.todd.bible.scripturelookup.server.ReferenceSearchEngine;
import net.todd.bible.scripturelookup.server.SearchResultToVerseConverter;
import net.todd.bible.scripturelookup.server.data.BibleDaoProvider;
import net.todd.bible.scripturelookup.server.web.IServletContextProvider;

public class BibleSearchFactory implements IBibleSearchFactory {
	private static final Logger LOG = Logger.getLogger(BibleSearchFactory.class.getName());
	private final IServletContextProvider contextProvider;

	public BibleSearchFactory(IServletContextProvider contextProvider) {
		this.contextProvider = contextProvider;
	}

	@Override
	public ISearchEngine getSearchEngine(String queryType) {
		ISearchEngine searchEngine = null;
		if ("keyword".equals(queryType)) {
			String indexLocation = contextProvider.getServletContext()
					.getRealPath("/WEB-INF/index");
			searchEngine = new LuceneSearchEngine(indexLocation, new SearchResultToVerseConverter());
		} else if ("reference".equals(queryType)) {
			searchEngine = new ReferenceSearchEngine(new ReferenceParser(), new ReferenceLookup(
					BibleDaoProvider.getBibleDao()));
		} else {
			LOG.severe("Unsupported query type: " + queryType);
			throw new RuntimeException("Unsupported query type: " + queryType);
		}
		return searchEngine;
	}
}

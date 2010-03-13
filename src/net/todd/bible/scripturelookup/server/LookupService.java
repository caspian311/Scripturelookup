package net.todd.bible.scripturelookup.server;

import java.util.List;

import net.todd.bible.scripturelookup.client.service.ILookupService;
import net.todd.bible.scripturelookup.server.data.Verse;
import net.todd.bible.scripturelookup.server.search.BibleSearchFactory;
import net.todd.bible.scripturelookup.server.search.IBibleSearchFactory;
import net.todd.bible.scripturelookup.server.web.IJSONWriter;
import net.todd.bible.scripturelookup.server.web.JSONWriter;
import net.todd.bible.scripturelookup.server.web.ServletContextProvider;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LookupService extends RemoteServiceServlet implements ILookupService {
	private static final long serialVersionUID = -4750186401651003378L;
	private final IJSONWriter jsonWriter;
	private final IBibleSearchFactory bibleSearchFactory;

	public LookupService() {
		jsonWriter = new JSONWriter();
		bibleSearchFactory = new BibleSearchFactory(new ServletContextProvider());
	}
	
	public LookupService(IJSONWriter jsonWriter, IBibleSearchFactory bibleSearchFactory) {
		this.jsonWriter = jsonWriter;
		this.bibleSearchFactory = bibleSearchFactory;
	}

	public String lookup(String queryType, String query) {
		ISearchEngine searchEngine = bibleSearchFactory.getSearchEngine(queryType);
		List<Verse> searchResults = searchEngine.search(query);
		return jsonWriter.writeOut(searchResults);
	}
}
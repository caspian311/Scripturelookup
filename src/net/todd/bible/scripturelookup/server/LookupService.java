package net.todd.bible.scripturelookup.server;

import java.util.List;

import net.todd.bible.scripturelookup.client.ILookupService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LookupService extends RemoteServiceServlet implements ILookupService {
	private static final long serialVersionUID = -4750186401651003378L;
	private final IJSONWriter jsonWriter;
	private final IBibleSearchFactory bibleSearchFactory;

	public LookupService() {
		jsonWriter = new JSONWriter();
		bibleSearchFactory = new BibleSearchFactory();
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
package net.todd.bible.scripturelookup.server;

import java.util.List;

import javax.servlet.ServletException;

import net.todd.bible.scripturelookup.client.ILookupService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LookupService extends RemoteServiceServlet implements ILookupService {
	private static final long serialVersionUID = -4750186401651003378L;
	private final IBibleService bibleService;

	public LookupService() {
		super();
		bibleService = new BibleService();
	}
	
	public LookupService(IBibleService bibleService) {
		super();
		this.bibleService = bibleService;
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		 bibleService.loadDatabase();
		 bibleService.buildIndex();
	}

	public String lookup(String query) {
		StringBuffer response = new StringBuffer();
		response.append("[");

		List<Verse> searchResults = bibleService.search(query);
		for (int i = 0; i < searchResults.size(); i++) {
			addVerse(response, searchResults.get(i));
			if (i < searchResults.size() - 1) {
				response.append(",\n");
			}
		}
		response.append("]");

		return response.toString();
	}

	private void addVerse(StringBuffer response, Verse verse) {
		response.append("	{");
		response.append("		\"book\"").append(" : ").append("\"" + verse.getBook() + "\"").append(
				",");
		response.append("		\"chapter\"").append(" : ").append("\"" + verse.getChapter() + "\"")
				.append(",");
		response.append("		\"verse\"").append(" : ").append("\"" + verse.getVerse() + "\"").append(
				",");
		response.append("		\"text\"").append(" : ").append("\"" + verse.getText() + "\"");
		response.append("	}");
	}
}
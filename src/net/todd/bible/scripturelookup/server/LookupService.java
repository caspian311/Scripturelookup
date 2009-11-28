package net.todd.bible.scripturelookup.server;

import java.util.ArrayList;
import java.util.List;

import net.todd.bible.scripturelookup.client.ILookupService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LookupService extends RemoteServiceServlet implements ILookupService {
	private static final long serialVersionUID = -4750186401651003378L;

	public String lookup(String query) {
		StringBuffer response = new StringBuffer();
		response.append("[");

		List<Verse> searchResults = fetchResults();
		for (int i = 0; i < searchResults.size(); i++) {
			addVerse(response, searchResults.get(i));
			if (i < searchResults.size() - 1) {
				response.append(",\n");
			}
		}
		response.append("]");

		return response.toString();
	}

	private List<Verse> fetchResults() {
		Verse verse1 = new Verse("Genesis", "1", "1", "In the beginning God...");
		Verse verse2 = new Verse("John", "3", "16", "For God so loved the world...");
		Verse verse3 = new Verse("Revelation", "21", "1",
				"Then I saw a new heaven and a new earth...");

		ArrayList<Verse> results = new ArrayList<Verse>();
		results.add(verse1);
		results.add(verse2);
		results.add(verse3);
		return results;
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
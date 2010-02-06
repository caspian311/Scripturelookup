package net.todd.bible.scripturelookup.server;

import java.util.List;

public class JSONWriter implements IJSONWriter {
	public String writeOut(List<Verse> searchResults) {
		StringBuffer response = new StringBuffer();

		response.append("[");

		for (int i = 0; i < searchResults.size(); i++) {
			addVerse(response, searchResults.get(i));
			if (i < searchResults.size() - 1) {
				response.append(",");
			}
		}
		response.append("]");

		return response.toString();
	}

	private void addVerse(StringBuffer response, Verse verse) {
		response.append("{");
		response.append("\"book\"").append(":").append("\"" + verse.getBook() + "\"").append(",");
		response.append("\"chapter\"").append(":").append("\"" + verse.getChapter() + "\"").append(
				",");
		response.append("\"verse\"").append(":").append("\"" + verse.getVerse() + "\"").append(",");
		response.append("\"text\"").append(":").append("\"" + verse.getText() + "\"");
		response.append("}");
	}
}

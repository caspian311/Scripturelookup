package net.todd.bible.scripturelookup.server;

import java.util.List;

public class VerseQueryFactory implements IGQLQueryFactory {
	@Override
	public String createQuery(String book, List<Integer> chapters, List<Integer> verses) {
		String prefix = "SELECT FROM " + Verse.class.getName() + " WHERE book == '" + book + "'";

		String chaptersQuery = "";
		if (chapters.size() > 0) {
			chaptersQuery = " && (";
			for (int i = 0; i < chapters.size(); i++) {
				chaptersQuery += "chapter == " + chapters.get(i);
				if (i + 1 != chapters.size()) {
					chaptersQuery += " || ";
				}
			}
			chaptersQuery += ")";
		}
		
		String versesQuery = "";
		if (verses.size() > 0) {
			versesQuery = " && (";
			for (int i = 0; i < verses.size(); i++) {
				versesQuery += "verse == " + verses.get(i);
				if (i + 1 != verses.size()) {
					versesQuery += " || ";
				}
			}
			versesQuery += ")";
		}
		return prefix + chaptersQuery + versesQuery;
	}
}

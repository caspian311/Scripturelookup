package net.todd.bible.scripturelookup.server;


import java.util.List;


public interface IBibleDao {
	List<Verse> getAllVerses();

	Verse getVerse(String id);

	List<Verse> getAllVersesInBook(String book);

	List<Verse> getAllVersesInChapter(String book, int chapter);

	Verse getVerse(String book, int chapter, int verse);
}

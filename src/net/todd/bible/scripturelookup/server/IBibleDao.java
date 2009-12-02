package net.todd.bible.scripturelookup.server;

import java.io.InputStream;
import java.util.List;

public interface IBibleDao {
	void loadData(InputStream inputStream);
	
	List<Verse> getAllVerses();

	Verse getVerse(String id);
}

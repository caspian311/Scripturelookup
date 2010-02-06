package net.todd.bible.scripturelookup.server;

import java.util.List;

public interface ISearchEngine {
	List<Verse> search(String query);
}

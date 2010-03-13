package net.todd.bible.scripturelookup.server;

import java.util.List;

import net.todd.bible.scripturelookup.server.data.Verse;

public interface ISearchEngine {
	List<Verse> search(String query);
}

package net.todd.bible.scripturelookup.client;

import java.util.List;

public interface ILookupModel {
	void queryServer(String query);

	List<Verse> searchResults();
}

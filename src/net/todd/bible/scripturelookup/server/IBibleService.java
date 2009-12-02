package net.todd.bible.scripturelookup.server;

import java.util.List;

public interface IBibleService {
	List<Verse> search(String query);
}

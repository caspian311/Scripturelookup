package net.todd.bible.scripturelookup.server;

import java.util.List;

import net.todd.bible.scripturelookup.server.data.Verse;

public interface IBibleService {
	List<Verse> search(String query);
}

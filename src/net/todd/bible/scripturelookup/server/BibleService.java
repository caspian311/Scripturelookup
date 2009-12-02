package net.todd.bible.scripturelookup.server;

import java.util.List;

public class BibleService implements IBibleService {
	private final IBibleDao bibleDao;

	public BibleService(IBibleDao bibleDao) {
		this.bibleDao = bibleDao;
	}
	
	@Override
	public List<Verse> search(String searchText) {
		return null;
	}
}

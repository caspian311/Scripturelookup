package net.todd.bible.scripturelookup.server;

import java.util.ArrayList;
import java.util.List;

public class BibleService implements IBibleService {
	private final IBibleDao bibleDao;

	public BibleService(IBibleDao bibleDao) {
		this.bibleDao = bibleDao;
	}
	
	@Override
	public List<Verse> search(String searchText) {
		List<Verse> allVerses = bibleDao.getAllVerses();
		List<Verse> returnedVerses = new ArrayList<Verse>();
		for (Verse verse : allVerses) {
			if (returnedVerses.size() == 10) {
				break;
			}
			returnedVerses.add(verse);
		}
		return returnedVerses;
	}
}

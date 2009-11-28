package net.todd.bible.scripturelookup.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class BibleService implements IBibleService {
	private List<Verse> verses;

	private List<Verse> allVerses() {
		Verse verse1 = new Verse("Genesis", "1", "1", "In the beginning God...");
		Verse verse2 = new Verse("John", "3", "16", "For God so loved the world...");
		Verse verse3 = new Verse("Revelation", "21", "1",
				"Then I saw a new heaven and a new earth...");

		verses = new ArrayList<Verse>();
		verses.add(verse1);
		verses.add(verse2);
		verses.add(verse3);
		return verses;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Verse> search(String searchText) {
		PersistenceManager persistenceManager = PMF.get().getPersistenceManager();
		List<Verse> results;
		try {
			Query query = persistenceManager.newQuery(Verse.class);
			query.setUnique(true);
			results = (List<Verse>) query.execute();
		} finally {
			persistenceManager.close();
		}
		return results;
	}

	@Override
	public void loadDatabase() {
		PersistenceManager persistenceManager = PMF.get().getPersistenceManager();

		try {
			for (Verse verse : allVerses()) {
				persistenceManager.makePersistent(verse);
			}
		} finally {
			persistenceManager.close();
		}
	}
	
	public void initializeIndex() {

	}

	@Override
	public void buildIndex() {
		// TODO Auto-generated method stub

	}
}

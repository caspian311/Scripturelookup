package net.todd.bible.scripturelookup.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

public class BibleDao implements IBibleDao {
	private final PersistenceManagerFactory persistenceManagerFactory;

	public BibleDao(PersistenceManagerFactory persistenceManagerFactory) {
		this.persistenceManagerFactory = persistenceManagerFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Verse> getAllVerses() {
		PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
		List<Verse> results = new ArrayList<Verse>();
		try {
			Query query = persistenceManager.newQuery(Verse.class);
			results.addAll((List<Verse>) query.execute());
			for (Verse verse : results) {
				verse.getText();
			}
		} finally {
			persistenceManager.close();
		}
		return results;
	}

	@Override
	public Verse getVerse(String id) {
		Verse verse = null;
		PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
		try {
			verse = persistenceManager.getObjectById(Verse.class, id);
			verse.getText();
		} finally {
			persistenceManager.close();
		}
		return verse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Verse> getAllVersesInBook(String book) {
		PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
		List<Verse> results = new ArrayList<Verse>();
		try {
			Query query = persistenceManager.newQuery("select from " + Verse.class.getName()
					+ " where book == '" + book + "'");
			results.addAll((List<Verse>) query.execute());
		} finally {
			persistenceManager.close();
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Verse> getAllVersesInChapter(String book, int chapter) {
		PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
		List<Verse> results = new ArrayList<Verse>();
		try {
			Query query = persistenceManager.newQuery("select from " + Verse.class.getName()
					+ " where book == '" + book + "'" + " && chapter == '" + chapter + "'");
			results.addAll((List<Verse>) query.execute());
		} finally {
			persistenceManager.close();
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Verse getVerse(String book, int chapter, int verse) {
		PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
		List<Verse> verses = new ArrayList<Verse>();
		try {
			Query query = persistenceManager.newQuery("select from " + Verse.class.getName()
					+ " where book == '" + book + "'" + " && chapter == '" + chapter
					+ "' && verse == '" + verse + "'");
			verses.addAll((List<Verse>) query.execute());
		} finally {
			persistenceManager.close();
		}
		return verses.get(0);
	}
}

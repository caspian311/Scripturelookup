package net.todd.bible.scripturelookup.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

public class BibleDao implements IBibleDao {
	private static final Logger LOG = Logger.getLogger(BibleDao.class.getName());

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
		} catch (RuntimeException e) {
			LOG.severe(e.getMessage());
			throw e;
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
		} catch (RuntimeException e) {
			LOG.severe(e.getMessage());
			throw e;
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
			query.setOrdering("chapter, verse");
			List<Verse> verses = (List<Verse>) query.execute();
			results.addAll(verses);
		} catch (RuntimeException e) {
			LOG.severe(e.getMessage());
			throw e;
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
					+ " where book == '" + book + "' && chapter == " + chapter);
			query.setOrdering("chapter, verse");
			results.addAll((List<Verse>) query.execute());
		} catch (RuntimeException e) {
			LOG.severe(e.getMessage());
			throw e;
		} finally {
			persistenceManager.close();
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Verse getVerse(String book, int chapter, int verse) {
		PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
		List<Verse> results = new ArrayList<Verse>();
		try {
			Query query = persistenceManager.newQuery("select from " + Verse.class.getName()
					+ " where book == '" + book + "' && chapter == " + chapter + " && verse == "
					+ verse);
			results.addAll((List<Verse>) query.execute());
		} catch (RuntimeException e) {
			LOG.severe(e.getMessage());
			throw e;
		} finally {
			persistenceManager.close();
		}
		Verse foundVerse = null;
		if (results.size() > 0) {
			foundVerse = results.get(0);
		}

		return foundVerse;
	}
}

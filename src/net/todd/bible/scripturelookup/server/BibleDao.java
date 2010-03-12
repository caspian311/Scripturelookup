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

	private final IGQLQueryFactory queryFactory;

	public BibleDao(PersistenceManagerFactory persistenceManagerFactory,
			IGQLQueryFactory queryFactory) {
		this.persistenceManagerFactory = persistenceManagerFactory;
		this.queryFactory = queryFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Verse> getAllVerses() {
		PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
		List<Verse> results = null;
		try {
			Query query = persistenceManager.newQuery(Verse.class);
			results = (List<Verse>) query.execute();
			results = new ArrayList<Verse>(persistenceManager.detachCopyAll(results));
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
	public List<Verse> getAllSpecifiedVerses(String book, List<Integer> chapters,
			List<Integer> verses) {
		String gqlQuery = queryFactory.createQuery(book, chapters, verses);
		PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
		
		List<Verse> results = null;
		
		try {
			Query query = persistenceManager.newQuery(gqlQuery);
			results = (List<Verse>) query.execute();
			results = new ArrayList<Verse>(persistenceManager.detachCopyAll(results));
		} catch (RuntimeException e) {
			LOG.severe(e.getMessage());
			throw e;
		} finally {
			persistenceManager.close();
		}
		
		return results;
	}
}

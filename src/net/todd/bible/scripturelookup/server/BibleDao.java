package net.todd.bible.scripturelookup.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
		List<Verse> results;
		try {
			Query query = persistenceManager.newQuery(Verse.class);
			results = (List<Verse>) query.execute();
		} finally {
			persistenceManager.close();
		}
		return results;
	}

	public void loadData(InputStream inputStream) {
		PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				Verse verse = parse(line);
				persistenceManager.makePersistent(verse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			persistenceManager.close();
			try {
				reader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void deleteData() {
		PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
		try {
			Query query = persistenceManager.newQuery(Verse.class);
			List<Verse> results = (List<Verse>) query.execute();
			for (Verse verse : results) {
				persistenceManager.deletePersistent(verse);
			}
		} finally {
			persistenceManager.close();
		}
	}

	private static Verse parse(String line) {
		String[] splitLine = line.split("|");

		String book = splitLine[0];
		String chapter = splitLine[0];
		String verse = splitLine[0];
		String text = splitLine[0];

		return new Verse(book, chapter, verse, text);
	}

	@Override
	public Verse getVerse(String id) {
		PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
		try {
			return persistenceManager.getObjectById(Verse.class, id);
		} finally {
			persistenceManager.close();
		}
	}
}

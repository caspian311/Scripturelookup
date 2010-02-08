package net.todd.bible.scripturelookup.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

public class DataLoader implements IDataLoader {
	private static final Logger LOG = Logger.getLogger(DataLoader.class.getName());
	
	private final PersistenceManagerFactory persistenceManagerFactory;

	public DataLoader(PersistenceManagerFactory persistenceManagerFactory) {
		this.persistenceManagerFactory = persistenceManagerFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void deleteData() {
		PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
		try {
			Query query = persistenceManager.newQuery(Verse.class);
			List<Verse> results = (List<Verse>) query.execute();
			for (Verse verse : results) {
				persistenceManager.deletePersistent(verse);
			}
		} catch (RuntimeException e) {
			LOG.severe(e.getMessage());
			throw e;
		} finally {
			persistenceManager.close();
		}
	}

	@Override
	public void loadData(InputStream inputStream) {
		PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				Verse verse = parse(line);
				persistenceManager.makePersistent(verse);
			}
		} catch (RuntimeException e) {
			LOG.severe(e.getMessage());
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			persistenceManager.close();
			try {
				inputStream.close();
				reader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	private static Verse parse(String line) {
		String[] splitLine = line.split("\\|");

		String book = splitLine[0];
		String chapter = splitLine[1];
		String verse = splitLine[2];
		String text = splitLine[3];

		return new Verse(book, chapter, verse, text);
	}
}

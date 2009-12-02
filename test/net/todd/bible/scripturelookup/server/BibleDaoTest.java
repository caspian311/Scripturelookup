package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.junit.Test;

public class BibleDaoTest {
	@Test
	public void getSpecificVerse() {
		Verse expectedResult = new Verse("", "", "", "");
		String id = UUID.randomUUID().toString();

		PersistenceManagerFactory persistenceManagerFactory = mock(PersistenceManagerFactory.class);
		PersistenceManager persistenceManager = mock(PersistenceManager.class);

		when(persistenceManagerFactory.getPersistenceManager()).thenReturn(persistenceManager);
		when(persistenceManager.getObjectById(Verse.class, id)).thenReturn(expectedResult);

		IBibleDao bibleDao = new BibleDao(persistenceManagerFactory);
		Verse actualResult = bibleDao.getVerse(id);

		verify(persistenceManager).getObjectById(Verse.class, id);
		verify(persistenceManager).close();

		assertSame(expectedResult, actualResult);
	}

	@Test
	public void fetchAllVerses() {
		List<Verse> expectedResults = new ArrayList<Verse>();
		
		PersistenceManagerFactory persistenceManagerFactory = mock(PersistenceManagerFactory.class);
		PersistenceManager persistenceManager = mock(PersistenceManager.class);
		Query query = mock(Query.class);

		when(persistenceManagerFactory.getPersistenceManager()).thenReturn(persistenceManager);
		when(persistenceManager.newQuery(Verse.class)).thenReturn(query);
		when(query.execute()).thenReturn(expectedResults);
		
		IBibleDao bibleDao = new BibleDao(persistenceManagerFactory);
		List<Verse> actualResults = bibleDao.getAllVerses();
		
		verify(persistenceManager).close();
		
		assertSame(expectedResults, actualResults);
	}

	@Test
	public void loadDataFromTestFile() {
		InputStream inputStream = getClass().getResourceAsStream("/test-data.txt");
		
		PersistenceManagerFactory persistenceManagerFactory = mock(PersistenceManagerFactory.class);
		PersistenceManager persistenceManager = mock(PersistenceManager.class);

		when(persistenceManagerFactory.getPersistenceManager()).thenReturn(persistenceManager);

		IBibleDao bibleDao = new BibleDao(persistenceManagerFactory);
		bibleDao.loadData(inputStream);

		verify(persistenceManager, times(3)).makePersistent(any(Verse.class));
		verify(persistenceManager, times(1)).close();
	}
	
	@Test
	public void deleteAllData() {
		Verse verse1 = new Verse("", "", "", "");
		Verse verse2 = new Verse("", "", "", "");
		Verse verse3 = new Verse("", "", "", "");

		List<Verse> allVerses = new ArrayList<Verse>();
		allVerses.add(verse1);
		allVerses.add(verse2);
		allVerses.add(verse3);
		
		PersistenceManagerFactory persistenceManagerFactory = mock(PersistenceManagerFactory.class);
		PersistenceManager persistenceManager = mock(PersistenceManager.class);
		Query query = mock(Query.class);

		when(persistenceManagerFactory.getPersistenceManager()).thenReturn(persistenceManager);
		when(persistenceManager.newQuery(Verse.class)).thenReturn(query);
		when(query.execute()).thenReturn(allVerses);

		IBibleDao bibleDao = new BibleDao(persistenceManagerFactory);
		bibleDao.deleteData();

		verify(persistenceManager).deletePersistent(verse1);
		verify(persistenceManager).deletePersistent(verse2);
		verify(persistenceManager).deletePersistent(verse3);
		verify(persistenceManager, times(1)).close();
	}
}

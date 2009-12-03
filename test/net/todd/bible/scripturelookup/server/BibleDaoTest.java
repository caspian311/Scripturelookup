package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;
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

import org.junit.Ignore;
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
		expectedResults.add(new Verse("", "", "", ""));
		expectedResults.add(new Verse("", "", "", ""));
		expectedResults.add(new Verse("", "", "", ""));

		PersistenceManagerFactory persistenceManagerFactory = mock(PersistenceManagerFactory.class);
		PersistenceManager persistenceManager = mock(PersistenceManager.class);
		Query query = mock(Query.class);

		when(persistenceManagerFactory.getPersistenceManager()).thenReturn(persistenceManager);
		when(persistenceManager.newQuery(Verse.class)).thenReturn(query);
		when(query.execute()).thenReturn(expectedResults);

		IBibleDao bibleDao = new BibleDao(persistenceManagerFactory);
		List<Verse> actualResults = bibleDao.getAllVerses();

		verify(persistenceManager).close();

		assertEquals(expectedResults.size(), actualResults.size());
		assertSame(expectedResults.get(0), actualResults.get(0));
		assertSame(expectedResults.get(1), actualResults.get(1));
		assertSame(expectedResults.get(2), actualResults.get(2));
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

	@Test
	@Ignore
	public void integrationTest() {
		IBibleDao bibleDao = new BibleDao(PMF.get());
		bibleDao.deleteData();

		bibleDao.loadData(getClass().getResourceAsStream("/test-data.txt"));

		List<Verse> verses = bibleDao.getAllVerses();
		assertEquals(3, verses.size());
		assertEquals("Genesis 1:1 - In the beginning...", verses.get(0).toString());
		assertEquals("John 3:16 - For God so loved the world...", verses.get(1).toString());
		assertEquals("Revelation 21:1 - Then I saw a new heaven and a new earth...", verses.get(2)
				.toString());

		bibleDao.deleteData();
	}
}

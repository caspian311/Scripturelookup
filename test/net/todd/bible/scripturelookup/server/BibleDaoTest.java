package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
		Verse expectedResult = mock(Verse.class);
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
		expectedResults.add(mock(Verse.class));
		expectedResults.add(mock(Verse.class));
		expectedResults.add(mock(Verse.class));

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
}

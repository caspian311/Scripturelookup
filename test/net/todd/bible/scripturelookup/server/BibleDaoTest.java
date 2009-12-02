package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
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
	public void instantiationPullsInData() {
		List<Verse> expectedResults = new ArrayList<Verse>();
		Verse expectedResult = new Verse("", "", "", "");
		String id = UUID.randomUUID().toString();
		
		PersistenceManagerFactory persistenceManagerFactory = mock(PersistenceManagerFactory.class);
		PersistenceManager persistenceManager = mock(PersistenceManager.class);
		Query query = mock(Query.class);

		when(persistenceManagerFactory.getPersistenceManager()).thenReturn(persistenceManager);
		when(persistenceManager.newQuery(Verse.class)).thenReturn(query);
		when(query.execute()).thenReturn(expectedResults);
		when(persistenceManager.getObjectById(Verse.class, id)).thenReturn(expectedResult);
		
		IBibleDao bibleDao = new BibleDao(persistenceManagerFactory);
		List<Verse> actualResults = bibleDao.getAllVerses();
		Verse actualResult = bibleDao.getVerse(id);
		
		verify(persistenceManager, times(3)).makePersistent(any(Verse.class));
		verify(persistenceManager, times(1)).getObjectById(Verse.class, id);
		verify(persistenceManager, times(3)).close();
		
		assertSame(expectedResults, actualResults);
		assertSame(expectedResult, actualResult);
	}
}

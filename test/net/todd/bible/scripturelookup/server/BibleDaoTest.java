package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import net.todd.bible.scripturelookup.server.data.BibleDao;
import net.todd.bible.scripturelookup.server.data.IBibleDao;
import net.todd.bible.scripturelookup.server.data.IVerseQueryFactory;
import net.todd.bible.scripturelookup.server.data.Verse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class BibleDaoTest {
	private PersistenceManagerFactory persistenceManagerFactory;
	private PersistenceManager persistenceManager;
	private IBibleDao bibleDao;
	private IVerseQueryFactory queryFactory;
	private String book;
	private List<Integer> chapters;
	private List<Integer> verses;
	private Query queryFromString;
	private Query queryFromClass;

	@Before
	public void setUp() {
		queryFactory = mock(IVerseQueryFactory.class);
		persistenceManagerFactory = mock(PersistenceManagerFactory.class);
		persistenceManager = mock(PersistenceManager.class);
		doReturn(persistenceManager).when(persistenceManagerFactory).getPersistenceManager();

		queryFromString = mock(Query.class);
		doReturn(queryFromString).when(persistenceManager).newQuery(anyString());
		
		queryFromClass = mock(Query.class);
		doReturn(queryFromClass).when(persistenceManager).newQuery(Verse.class);
		
		book = UUID.randomUUID().toString();
		chapters = Arrays.asList(1, 2, 3);
		verses = Arrays.asList(4, 5, 6);
		
		bibleDao = new BibleDao(persistenceManagerFactory, queryFactory);
	}
	
	@Test
	public void fetchAllVerses() {
		List<Verse> expectedResults = Arrays.asList(mock(Verse.class), mock(Verse.class),
				mock(Verse.class));
		doReturn(expectedResults).when(queryFromClass).execute();
		doReturn(expectedResults).when(persistenceManager).detachCopyAll(expectedResults);

		List<Verse> actualResults = bibleDao.getAllVerses();

		compareResults(expectedResults, actualResults);
	}

	private void compareResults(List<Verse> expectedResults, List<Verse> actualResults) {
		assertEquals(expectedResults.size(), actualResults.size());
		for (int i = 0; i < expectedResults.size(); i++) {
			assertEquals(expectedResults.get(i), actualResults.get(i));
		}
	}

	@Test
	public void closePersistenceManagerAfterExecutingQueryWhenGettingAllVerses() {
		doReturn(Arrays.asList()).when(queryFromClass).execute();
		
		InOrder inOrder = inOrder(persistenceManager, queryFromClass);

		bibleDao.getAllVerses();

		inOrder.verify(queryFromClass).execute();
		inOrder.verify(persistenceManager).close();
	}
	
	@Test(expected = RuntimeException.class)
	public void reThrowExceptionWhenGettingAllVerses() {
		RuntimeException error = new RuntimeException();
		doThrow(error).when(queryFromClass).execute();

		bibleDao.getAllVerses();
	}
	
	@Test
	public void bookChaptersAndVersesAreGivenToQueryFactoryWhenGettingAllSpecifiedVerses() {
		bibleDao.getAllSpecifiedVerses(book, chapters, verses);

		verify(queryFactory).createQuery(book, chapters, verses);
	}
	
	@Test
	public void createQueryFromWhateverFactoryReturnsWhenGettingAllSpecifiedVerses() {
		String gqlQuery = UUID.randomUUID().toString();
		doReturn(gqlQuery).when(queryFactory).createQuery(anyString(), anyListOf(Integer.class),
				anyListOf(Integer.class));

		bibleDao.getAllSpecifiedVerses(book, chapters, verses);

		verify(persistenceManager).newQuery(gqlQuery);
	}
	
	@Test
	public void createdQueryGetsExecutedWhenGettingAllSpecifiedVerses() {
		bibleDao.getAllSpecifiedVerses(book, chapters, verses);
		
		verify(queryFromString).execute();
	}
	
	@Test
	public void returnWhateverResultsFromTheQueryExecutionWhenGettingAllSpecifiedVerses() {
		List<Verse> expectedResults = Arrays.asList(mock(Verse.class));
		doReturn(expectedResults).when(queryFromString).execute();
		doReturn(expectedResults).when(persistenceManager).detachCopyAll(expectedResults);
		
		List<Verse> actualResults = bibleDao.getAllSpecifiedVerses(book, chapters, verses);

		compareResults(expectedResults, actualResults);
	}
	
	@Test
	public void persistenceManagerClosedAfterQueryExecutionWhenGettingAllSpecifiedVerses() {
		InOrder inOrder = inOrder(persistenceManager, queryFromString);

		bibleDao.getAllSpecifiedVerses(book, chapters, verses);

		inOrder.verify(queryFromString).execute();
		inOrder.verify(persistenceManager).close();
	}
	
	@Test(expected = RuntimeException.class)
	public void reThrowExceptionWhenGettingAllSpecifiedVerses() {
		RuntimeException error = new RuntimeException();
		doThrow(error).when(queryFromString).execute();

		bibleDao.getAllSpecifiedVerses(anyString(), anyListOf(Integer.class),
				anyListOf(Integer.class));
	}
}

package net.todd.bible.scripturelookup.server;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import net.todd.bible.scripturelookup.server.data.Verse;

import org.junit.Test;

public class DataLoaderTest {
	@Test
	public void loadDataFromTestFile() {
		InputStream inputStream = getClass().getResourceAsStream("/test-data.txt");

		PersistenceManagerFactory persistenceManagerFactory = mock(PersistenceManagerFactory.class);
		PersistenceManager persistenceManager = mock(PersistenceManager.class);

		when(persistenceManagerFactory.getPersistenceManager()).thenReturn(persistenceManager);

		IDataLoader dataLoader = new DataLoader(persistenceManagerFactory);
		dataLoader.loadData(inputStream);

		verify(persistenceManager, times(3)).makePersistent(any(Verse.class));
		verify(persistenceManager, times(1)).close();
	}

	@Test
	public void deleteAllData() {
		Verse verse1 = mock(Verse.class);
		Verse verse2 = mock(Verse.class);
		Verse verse3 = mock(Verse.class);

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

		IDataLoader dataLoader = new DataLoader(persistenceManagerFactory);
		dataLoader.deleteData();

		verify(persistenceManager).deletePersistent(verse1);
		verify(persistenceManager).deletePersistent(verse2);
		verify(persistenceManager).deletePersistent(verse3);
		verify(persistenceManager, times(1)).close();
	}
}

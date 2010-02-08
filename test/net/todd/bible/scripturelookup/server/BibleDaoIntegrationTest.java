package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.appengine.tools.development.ApiProxyLocalImpl;
import com.google.apphosting.api.ApiProxy;

public class BibleDaoIntegrationTest {
	private static IBibleDao bibleDao;

	@BeforeClass
	public static void setUp() {
		ApiProxy.setEnvironmentForCurrentThread(new TestEnvironment());
		ApiProxy.setDelegate(new ApiProxyLocalImpl(new File("war")) {
		});

		DataLoaderProvider.getDataLoader().deleteData();
		DataLoaderProvider.getDataLoader().loadData(
				BibleDaoIntegrationTest.class
								.getResourceAsStream("/dao-integration-test-data.txt"));
		
		bibleDao = BibleDaoProvider.getBibleDao();
	}
	
	@Test
	public void allVerses() {
		List<Verse> verses = bibleDao.getAllVerses();

		assertEquals(5, verses.size());
	}

	@Test
	public void allVersesInBook() {
		List<Verse> john = bibleDao.getAllVersesInBook("John");
		assertEquals(4, john.size());
		
		List<Verse> gen = bibleDao.getAllVersesInBook("Genesis");
		assertEquals(1, gen.size());
	}

	@Test
	public void allVersesInChapter() {
		List<Verse> john1 = bibleDao.getAllVersesInChapter("John", 1);
		assertEquals(3, john1.size());
		
		List<Verse> john3 = bibleDao.getAllVersesInChapter("John", 3);
		assertEquals(1, john3.size());
	}
	
	@Test
	public void verse() {
		Verse john11 = BibleDaoProvider.getBibleDao().getVerse("John", 1, 1);
		assertEquals("John", john11.getBook());
		assertEquals("1", john11.getChapter());
		assertEquals("1", john11.getVerse());
		assertEquals(
				"In the beginning was the Word, and the Word was with God, and the Word was God.",
				john11.getText());

		Verse john316 = BibleDaoProvider.getBibleDao().getVerse("John", 3, 16);
		assertEquals("John", john316.getBook());
		assertEquals("3", john316.getChapter());
		assertEquals("16", john316.getVerse());
		assertEquals("For God so loved the world...", john316.getText());
	}
	
	private static class TestEnvironment implements ApiProxy.Environment {
		public String getAppId() {
			return "test";
		}

		public String getVersionId() {
			return "1.0";
		}

		public String getEmail() {
			throw new UnsupportedOperationException();
		}

		public boolean isLoggedIn() {
			throw new UnsupportedOperationException();
		}

		public boolean isAdmin() {
			throw new UnsupportedOperationException();
		}

		public String getAuthDomain() {
			throw new UnsupportedOperationException();
		}

		public String getRequestNamespace() {
			return "";
		}

		public Map<String, Object> getAttributes() {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("com.google.appengine.server_url_key", "http://localhost:8080");
			return map;
		}
	}
}

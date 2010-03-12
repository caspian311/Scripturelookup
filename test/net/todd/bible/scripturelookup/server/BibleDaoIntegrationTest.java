package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.appengine.tools.development.ApiProxyLocalImpl;
import com.google.apphosting.api.ApiProxy;

public class BibleDaoIntegrationTest {
	private IBibleDao bibleDao;

	@BeforeClass
	public static void setUpData() {
		ApiProxy.setEnvironmentForCurrentThread(new TestEnvironment());
		ApiProxy.setDelegate(new ApiProxyLocalImpl(new File("war")) {
		});

		DataLoaderProvider.getDataLoader().deleteData();
		DataLoaderProvider.getDataLoader()
				.loadData(
						BibleDaoIntegrationTest.class
								.getResourceAsStream("/dao-integration-test-data.txt"));
	}

	@Before
	public void setUp() {
		bibleDao = BibleDaoProvider.getBibleDao();
	}

	@After
	public void tearDown() {
		DataLoaderProvider.getDataLoader().deleteData();
	}

	@Test
	public void allVersesInJohn1() {
		List<Verse> john1 = bibleDao.getAllSpecifiedVerses("John", Arrays.asList(1), Arrays.asList(
				1, 2, 3));
		assertEquals(3, john1.size());
		assertEquals("John", john1.get(0).getBook());
		assertEquals(1, john1.get(0).getChapter().intValue());
		assertEquals(1, john1.get(0).getVerse().intValue());
		assertEquals(
				"In the beginning was the Word, and the Word was with God, and the Word was God.",
				john1.get(0).getText());
		assertEquals("John", john1.get(1).getBook());
		assertEquals(1, john1.get(1).getChapter().intValue());
		assertEquals(2, john1.get(1).getVerse().intValue());
		assertEquals("He was with God in the beginning", john1.get(1).getText());
		assertEquals("John", john1.get(2).getBook());
		assertEquals(1, john1.get(2).getChapter().intValue());
		assertEquals(3, john1.get(2).getVerse().intValue());
		assertEquals(
				"Through him all things were made; without him nothing was made that has been made.",
				john1.get(2).getText());
	}

	@Test
	public void verseThatDoesntExistReturnEmpty() {
		List<Verse> verses = bibleDao.getAllSpecifiedVerses("Test", Arrays.asList(1), Arrays
				.asList(2));

		assertEquals(0, verses.size());
	}

	static class TestEnvironment implements ApiProxy.Environment {
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

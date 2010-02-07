package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.ApiProxyLocalImpl;
import com.google.apphosting.api.ApiProxy;

public class SearchIntegrationTest {
	private String indexLocation;
	private ISearchResultToVerseConverter converter;

	@Before
	public void setUp() throws IOException {
		converter = new SearchResultToVerseConverter();
		File tempFile = File.createTempFile(getClass().getName(), "");
		indexLocation = tempFile.getAbsolutePath();
		tempFile.delete();
		
		ApiProxy.setEnvironmentForCurrentThread(new TestEnvironment());
		ApiProxy.setDelegate(new ApiProxyLocalImpl(new File("war")) {
		});
		
		DataLoaderProvider.getDataLoader().deleteData();
		DataLoaderProvider.getDataLoader().loadData(
				getClass().getResourceAsStream("/test-data.txt"));
	}

	@After
	public void tearDown() {
		DataLoaderProvider.getDataLoader().deleteData();
		
		ApiProxy.setEnvironmentForCurrentThread(null);
		ApiProxy.setDelegate(null);
	}

	@Test
	public void searchesWorks() {
		new IndexBuilder(BibleDaoProvider.getBibleDao()).createIndex(indexLocation);
		
		List<Verse> results = new LuceneSearchEngine(indexLocation, converter).search("God");

		assertTrue(results.size() > 0);
	}

	class TestEnvironment implements ApiProxy.Environment {
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

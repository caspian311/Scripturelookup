package net.todd.bible.scripturelookup.server.search;


import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import net.todd.bible.scripturelookup.server.DataLoaderProvider;
import net.todd.bible.scripturelookup.server.IDataLoader;
import net.todd.bible.scripturelookup.server.data.BibleDaoProvider;
import net.todd.bible.scripturelookup.server.data.IBibleDao;

import com.google.appengine.tools.development.ApiProxyLocalImpl;
import com.google.apphosting.api.ApiProxy;

public class Indexer {
	public static final String USAGE_ERROR_MESSAGE = "Usage: java net.todd.bible.scripturelookup.server.Indexer <path-to-index-directory>";
	public static final String SUCCESS_MESSAGE = "Index created successfully";
	public static final String ERROR_MESSAGE_PREFIX = "Error: ";
	private final PrintWriter out;
	private final PrintWriter err;
	private final IIndexBuilder indexBuilder;

	public Indexer(OutputStream outStream, OutputStream errStream, IIndexBuilder indexBuilder) {
		this.indexBuilder = indexBuilder;
		out = new PrintWriter(outStream);
		err = new PrintWriter(errStream);
	}

	public void doit(String[] args) {
		try {
			validateNumberOfArgs(args);
			validateIsDirectory(args);

			actuallyBuildIndex(args[0]);

			out.println(SUCCESS_MESSAGE);
		} catch (ValidationException e) {
			err.println(USAGE_ERROR_MESSAGE);
		} catch (Exception e) {
			err.println(ERROR_MESSAGE_PREFIX + e.getMessage());
		} finally {
			out.flush();
			out.close();

			err.flush();
			err.close();
		}
	}

	private void actuallyBuildIndex(String directoryLocation) {
		indexBuilder.createIndex(directoryLocation);
	}

	private void validateIsDirectory(String[] args) {
		if (!new File(args[0]).isDirectory()) {
			throw new ValidationException();
		}
	}

	private void validateNumberOfArgs(String[] args) {
		if (args.length != 1) {
			throw new ValidationException();
		}
	}

	public static void main(String[] args) {
		ApiProxy.setEnvironmentForCurrentThread(new TestEnvironment());
		ApiProxy.setDelegate(new ApiProxyLocalImpl(new File("war")) {
		});

		IDataLoader dataLoader = DataLoaderProvider.getDataLoader();
		dataLoader.deleteData();
		dataLoader.loadData(Indexer.class.getResourceAsStream("/data.txt"));
		IBibleDao bibleDao = BibleDaoProvider.getBibleDao();
		new Indexer(System.out, System.err, new IndexBuilder(bibleDao)).doit(args);
	}

	private static class ValidationException extends RuntimeException {
		private static final long serialVersionUID = -528856349956178698L;
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

package net.todd.bible.scripturelookup.server;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;

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
		new Indexer(System.out, System.err, new IndexBuilder(BibleDaoProvider.getBibleDao()))
				.doit(args);
	}
	
	private static class ValidationException extends RuntimeException {
		private static final long serialVersionUID = -528856349956178698L;
	}
}

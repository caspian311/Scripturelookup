package net.todd.bible.scripturelookup.server;


import java.io.File;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class IndexBuilder implements IIndexBuilder {
	private final IBibleDao bibleDao;

	public IndexBuilder(IBibleDao bibleDao) {
		this.bibleDao = bibleDao;
	}
	
	public void createIndex(String directoryLocation) {
		File indexDirectory = new File(directoryLocation);

		if (!indexDirectory.exists()) {
			indexDirectory.mkdir();
		}

		List<Verse> allVerses = bibleDao.getAllVerses();

		if (!indexDirectory.isDirectory()) {
			throw new RuntimeException("Given index location is not a directory: "
					+ directoryLocation);
		}

		try {
			Directory index = new SimpleFSDirectory(indexDirectory);

			IndexWriter writer = new IndexWriter(index,
					new StandardAnalyzer(Version.LUCENE_CURRENT), true,
					new IndexWriter.MaxFieldLength(1000000));

			for (Verse verse : allVerses) {
				Document doc = convertToDocument(verse);
				writer.addDocument(doc);
			}

			writer.optimize();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private Document convertToDocument(Verse verse) {
		Document doc = new Document();

		doc.add(new Field("book", verse.getBook(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc
				.add(new Field("chapter", "" + verse.getChapter(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
		doc
				.add(new Field("verse", "" + verse.getVerse(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
		doc.add(new Field("text", verse.getText(), Field.Store.YES, Field.Index.ANALYZED));

		return doc;
	}
}

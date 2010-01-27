package net.todd.bible.scripturelookup.server;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class SearchEngine implements ISearchEngine {
	private static final int MAX_RESULTS = 100;
	private final String indexLocation;

	public SearchEngine(String indexLocation) {
		this.indexLocation = indexLocation;
	}

	@Override
	public List<SearchResult> search(String queryString) {
		Query query = generateQuery(queryString);

		List<SearchResult> results = new ArrayList<SearchResult>();
		try {
			Directory index = new SimpleFSDirectory(new File(indexLocation));
			IndexSearcher searcher = new IndexSearcher(index);
			TopDocs topMatchingDocs = searcher.search(query, MAX_RESULTS);

			if (topMatchingDocs != null) {
				for (int i = 0; i < topMatchingDocs.totalHits && i < MAX_RESULTS; i++) {
					Document document = searcher.doc(topMatchingDocs.scoreDocs[i].doc);
					SearchResult searchResult = new SearchResult();

					String book = document.get("book");
					String chapter = document.get("chapter");
					String verse = document.get("verse");
					String text = document.get("text");
					float score = topMatchingDocs.scoreDocs[i].score;

					searchResult.setScore(score);
					searchResult.setBook(book);
					searchResult.setChapter(chapter);
					searchResult.setVerse(verse);
					searchResult.setText(text);

					results.add(searchResult);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		Collections.sort(results);
		Collections.reverse(results);

		return results;
	}

	private Query generateQuery(String queryString) {
		Query query = null;
		try {
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
			QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "text", analyzer);

			query = parser.parse(queryString);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return query;
	}
}

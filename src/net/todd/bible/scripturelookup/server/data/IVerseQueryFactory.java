package net.todd.bible.scripturelookup.server.data;

import java.util.List;

public interface IVerseQueryFactory {

	String createQuery(String book, List<Integer> chapters, List<Integer> verses);

}

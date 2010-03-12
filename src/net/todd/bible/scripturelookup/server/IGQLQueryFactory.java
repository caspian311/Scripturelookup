package net.todd.bible.scripturelookup.server;

import java.util.List;

public interface IGQLQueryFactory {

	String createQuery(String book, List<Integer> chapters, List<Integer> verses);

}

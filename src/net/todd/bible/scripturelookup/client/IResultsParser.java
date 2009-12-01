package net.todd.bible.scripturelookup.client;

import java.util.List;

public interface IResultsParser {
	List<Verse> parse(String response);
}

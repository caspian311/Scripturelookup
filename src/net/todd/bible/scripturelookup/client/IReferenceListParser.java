package net.todd.bible.scripturelookup.client;

import java.util.List;

public interface IReferenceListParser {
	List<Verse> parse(String response);
}

package net.todd.bible.scripturelookup.server;

import java.util.List;

public interface IJSONWriter {
	String writeOut(List<Verse> searchResults);
}
package net.todd.bible.scripturelookup.server.web;

import java.util.List;

import net.todd.bible.scripturelookup.server.data.Verse;

public interface IJSONWriter {
	String writeOut(List<Verse> searchResults);
}
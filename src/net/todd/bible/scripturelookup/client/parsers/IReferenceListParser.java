package net.todd.bible.scripturelookup.client.parsers;

import java.util.List;

import net.todd.bible.scripturelookup.client.Verse;

public interface IReferenceListParser {
	List<Verse> parse(String response);
}

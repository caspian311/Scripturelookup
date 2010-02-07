package net.todd.bible.scripturelookup.server;

import java.util.List;

import net.todd.bible.scripturelookup.server.ReferenceParser.Reference;

public interface IReferenceLookup {
	List<Verse> lookupReference(Reference reference);
}

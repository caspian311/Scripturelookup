package net.todd.bible.scripturelookup.server;

import java.util.List;

import net.todd.bible.scripturelookup.server.ReferenceParser.Reference;
import net.todd.bible.scripturelookup.server.data.Verse;

public interface IReferenceLookup {
	List<Verse> lookupReference(Reference reference);
}

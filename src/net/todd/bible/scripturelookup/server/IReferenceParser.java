package net.todd.bible.scripturelookup.server;

import net.todd.bible.scripturelookup.server.ReferenceParser.Reference;

public interface IReferenceParser {

	Reference parseReference(String query);

}

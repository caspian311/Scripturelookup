package net.todd.bible.scripturelookup.server;

import java.util.List;

import net.todd.bible.scripturelookup.server.ReferenceParser.Reference;

public class ReferenceSearchEngine implements ISearchEngine {
	private final IReferenceParser referenceParser;
	private final IReferenceLookup referenceDao;

	public ReferenceSearchEngine(IReferenceParser referenceParser, IReferenceLookup referenceDao) {
		this.referenceParser = referenceParser;
		this.referenceDao = referenceDao;
	}

	@Override
	public List<Verse> search(String query) {
		Reference reference = referenceParser.parseReference(query);
		return referenceDao.lookupReference(reference);
	}
}

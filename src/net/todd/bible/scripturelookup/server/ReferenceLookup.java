package net.todd.bible.scripturelookup.server;

import java.util.List;

import net.todd.bible.scripturelookup.server.ReferenceParser.Reference;
import net.todd.bible.scripturelookup.server.data.IBibleDao;
import net.todd.bible.scripturelookup.server.data.Verse;

public class ReferenceLookup implements IReferenceLookup {
	private final IBibleDao bibleDao;

	public ReferenceLookup(IBibleDao bibleDao) {
		this.bibleDao = bibleDao;
	}

	@Override
	public List<Verse> lookupReference(Reference reference) {
		List<Verse> verses = bibleDao.getAllSpecifiedVerses(reference.getBook(), reference
				.getChapters(), reference.getVerses());
		return verses;
	}
}

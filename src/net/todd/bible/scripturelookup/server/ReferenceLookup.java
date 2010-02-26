package net.todd.bible.scripturelookup.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.todd.bible.scripturelookup.server.ReferenceParser.Reference;

public class ReferenceLookup implements IReferenceLookup {
	private final IBibleDao bibleDao;

	public ReferenceLookup(IBibleDao bibleDao) {
		this.bibleDao = bibleDao;
	}

	@Override
	public List<Verse> lookupReference(Reference reference) {
		List<Verse> verses = new ArrayList<Verse>();
		if (isReferenceToABook(reference)) {
			verses.addAll(bibleDao.getAllVersesInBook(reference.getBook()));
		} else if (isReferenceToAChapter(reference)) {
			verses.addAll(bibleDao.getAllVersesInChapter(reference.getBook(), reference
					.getChapter()));
		} else {
			Verse verse = bibleDao.getVerse(reference.getBook(), reference.getChapter(),
					reference.getVerse());
			if (verse != null) {
				verses.addAll(Arrays.asList(verse));
			}
		}
		return verses;
	}

	private boolean isReferenceToABook(Reference reference) {
		return reference.getChapter() == 0;
	}

	private boolean isReferenceToAChapter(Reference reference) {
		return reference.getVerse() == 0;
	}
}

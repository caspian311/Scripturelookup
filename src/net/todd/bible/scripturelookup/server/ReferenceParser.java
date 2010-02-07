package net.todd.bible.scripturelookup.server;

public class ReferenceParser implements IReferenceParser {
	@Override
	public Reference parseReference(String query) {
		validateEmptiness(query);
		validateWordCount(query);
		validateChapterAndVerse(query);

		return new Reference(query);
	}

	private void validateChapterAndVerse(String query) {
		String[] tokens = query.split(" ");
		if (tokens.length > 1) {
			String[] chapterAndVerse = tokens[1].split(":");
			if (chapterAndVerse.length == 2) {
				try {
					new Integer(chapterAndVerse[0]);
				} catch (NumberFormatException e) {
					throw new ReferenceParsingException();
				}
				try {
					new Integer(chapterAndVerse[1]);
				} catch (NumberFormatException e) {
					throw new ReferenceParsingException();
				}
			}
		}
	}

	private void validateWordCount(String query) {
		String[] tokens = query.split(" ");
		int wordCount = 0;
		for (String token : tokens) {
			if (token.indexOf(":") < 0) {
				try {
					new Integer(token);
				} catch (NumberFormatException e) {
					wordCount++;
				}
			}
		}
		if (wordCount > 1) {
			throw new ReferenceParsingException();
		}
	}

	private void validateEmptiness(String query) {
		if (query == null || query.length() == 0) {
			throw new ReferenceParsingException();
		}
	}
	
	public static class Reference {
		private final String book;
		private int chapter;
		private int verse;

		Reference(String book) {
			String[] split = book.split(" ");
			this.book = split[0];
			if (split.length > 1) {
				if (split[1].indexOf(":") > 0) {
					String[] chapterAndVerse = split[1].split(":");
					this.chapter = new Integer(chapterAndVerse[0]).intValue();
					this.verse = new Integer(chapterAndVerse[1]).intValue();
				} else {
					this.chapter = new Integer(split[1]).intValue();
				}
			}
		}

		public String getBook() {
			return book;
		}

		public int getChapter() {
			return chapter;
		}

		public int getVerse() {
			return verse;
		}
	}

}

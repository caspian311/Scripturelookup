package net.todd.bible.scripturelookup.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

public class ReferenceParser implements IReferenceParser {
	private Reference reference;
	private int lastPoint = 0;
	
	@Override
	public Reference parseReference(String query) {
		return validateReferenceStr(query);
	}

	private Reference validateReferenceStr(String referenceStr) {
		reference = new Reference();

		if (StringUtils.isEmpty(referenceStr)) {
			throw new ReferenceParsingException();
		}

		extractBook(referenceStr);
		extractChapter(referenceStr);
		extractVerse(referenceStr);

		return reference;
	}

	private void extractVerse(String referenceStr) {
		String ref = referenceStr.substring(lastPoint);
		ref = ref.trim();

		if (StringUtils.isEmpty(ref) == false) {
			if (StringUtils.contains(ref, "-") || StringUtils.contains(ref, ",")) {
				List<Integer> verses = getMixedNumberArray(ref);

				reference.setVerses(verses);
			} else {
				try {
					reference.setVerses(Arrays.asList(Integer.parseInt(ref)));
				} catch (NumberFormatException e) {
					throw new ReferenceParsingException();
				}
			}
		}
	}

	private List<Integer> getMixedNumberArray(String ref) {
		List<Integer> numberList = new ArrayList<Integer>();

		StringTokenizer tokenizer = new StringTokenizer(ref, ",");

		while (tokenizer.hasMoreTokens()) {
			String numberStr = tokenizer.nextToken();

			if (StringUtils.contains(numberStr, "-")) {
				numberList.addAll(getNumberArray(numberStr));
			} else {
				Integer number = Integer.parseInt(numberStr.trim());
				numberList.add(number);
			}
		}

		return numberList;
	}

	private void extractChapter(String referenceStr) {
		String ref = referenceStr.substring(lastPoint);
		ref = ref.trim();

		if (StringUtils.isEmpty(ref) == false) {
			if (StringUtils.isNumeric(ref)) {
				try {
					reference.setChapters(Arrays.asList(Integer.parseInt(ref)));
				} catch (NumberFormatException e) {
					throw new ReferenceParsingException();
				}

				lastPoint = reference.getBook().length() + ref.length() + 1;
			} else if (StringUtils.contains(ref, ":")) {
				StringTokenizer tokenizer = new StringTokenizer(ref, ":");
				String chapterStr = tokenizer.nextToken();

				try {
					reference.setChapters(Arrays.asList(Integer.parseInt(chapterStr)));
				} catch (NumberFormatException e) {
					throw new ReferenceParsingException();
				}

				lastPoint = reference.getBook().length() + chapterStr.length() + 2;
			} else if (StringUtils.contains(ref, "-") || StringUtils.contains(ref, ",")) {
				List<Integer> numbers = getMixedNumberArray(ref);

				reference.setChapters(numbers);
				lastPoint = reference.getBook().length() + ref.length() + 1;
			} else {
				throw new ReferenceParsingException();
			}
		}
	}

	private List<Integer> getNumberArray(String ref) {
		StringTokenizer tokenizer = new StringTokenizer(ref, "-");

		String startStr = tokenizer.nextToken();
		startStr = startStr.trim();
		String endStr = tokenizer.nextToken();
		endStr = endStr.trim();

		Integer startingChapter = Integer.parseInt(startStr);
		Integer endingChapter = Integer.parseInt(endStr);

		List<Integer> numbers = new ArrayList<Integer>();

		for (int i = startingChapter; i <= endingChapter; i++) {
			numbers.add(i);
		}

		return numbers;
	}

	private void extractBook(String referenceStr) {
		StringTokenizer tokenizer = new StringTokenizer(referenceStr);
		if (tokenizer.hasMoreTokens() == false) {
			throw new ReferenceParsingException();
		}

		String book = "";

		boolean firstToken = true;

		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (StringUtils.isNumeric(token) && firstToken == false) {
				break;
			}
			if (StringUtils.contains(token, ":")) {
				break;
			}
			if (StringUtils.contains(token, "-")) {
				break;
			}

			book += " " + token;
			book = book.trim();

			firstToken = false;
		}

		lastPoint = book.length();

		reference.setBook(book);
	}
	
	public static class Reference {
		private String book;
		private List<Integer> chapters = new ArrayList<Integer>();
		private List<Integer> verses = new ArrayList<Integer>();
		
		private void setBook(String book) {
			this.book = book;
		}

		private void setChapters(List<Integer> chapters) {
			this.chapters = chapters;
		}
		
		private void setVerses(List<Integer> verses) {
			this.verses = verses;
		}

		public String getBook() {
			return book;
		}
		
		public Integer getChapter() {
			return chapters.size() == 0 ? 0 : chapters.get(0);
		}
		
		public List<Integer> getChapters() {
			return chapters;
		}

		public Integer getVerse() {
			return verses.size() == 0 ? 0 : verses.get(0);
		}
		
		public List<Integer> getVerses() {
			return verses;
		}
	}

}

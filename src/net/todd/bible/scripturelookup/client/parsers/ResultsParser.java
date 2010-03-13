package net.todd.bible.scripturelookup.client.parsers;

import java.util.List;

import net.todd.bible.scripturelookup.client.Verse;

public class ResultsParser implements IResultsParser {
	private final IReferenceListParser referenceListParser;

	public ResultsParser(IReferenceListParser referenceListParser) {
		this.referenceListParser = referenceListParser;
	}

	@Override
	public ParsedResults parse(String text) {
		List<Verse> referenceList = referenceListParser.parse(text);
		return new ParsedResults(referenceList);
	}
}

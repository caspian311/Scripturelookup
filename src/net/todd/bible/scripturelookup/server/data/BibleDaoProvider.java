package net.todd.bible.scripturelookup.server.data;


public class BibleDaoProvider {
	private static final IBibleDao dao = new BibleDao(PMF.get(), new VerseQueryFactory());

	public static IBibleDao getBibleDao() {
		return dao;
	}
}

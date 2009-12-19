package net.todd.bible.scripturelookup.server;

public class BibleDaoProvider {
	private static IBibleDao dao = new BibleDao(PMF.get());

	public static IBibleDao getBibleDao() {
		return dao;
	}
}

package net.todd.bible.scripturelookup.server;


import java.util.List;


public interface IBibleDao {
	List<Verse> getAllVerses();

	List<Verse> getAllSpecifiedVerses(String book, List<Integer> chapters, List<Integer> verses);
}

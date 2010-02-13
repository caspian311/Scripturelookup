package net.todd.bible.scripturelookup.client;

import java.util.Arrays;
import java.util.List;

public class FileProvider implements IFileProvider {
	private static final List<String> FILES_TO_LOAD = Arrays.asList("/part1.txt", "/part2.txt",
			"/part3.txt", "/part4.txt", "/part5.txt", "/part6.txt", "/part7.txt", "/part8.txt",
			"/part9.txt", "/part10.txt");
	
	@Override
	public List<String> filesToLoad() {
		return FILES_TO_LOAD;
	}
}

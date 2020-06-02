package com.counterapi.rest.service;

import java.util.Arrays;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ParagraphParser {

	/**
	 * Parsing the text paragraph from the below location
	 */
	//TODO:: Move the filename to properties
	private static String fileName = "src/main/resources/data/Paragraph.txt";
	public static Map<String,Long> parseData(){

		Map<String, Long> words = null;
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			words = stream.flatMap(line -> Arrays.stream(line.replaceAll("[^a-zA-Z0-9]", " ").split("\\s+")))
					.map(word -> word.toLowerCase().trim())
					.filter(word ->word.length()>0)
					.map(word -> new SimpleEntry<>(word,1))
					.collect(Collectors.groupingBy(SimpleEntry::getKey,Collectors.counting()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return words;
	}


}

package com.counterapi.rest.service;

import java.util.Arrays;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.counterapi.rest.exception.DataNotFoundException;
import com.counterapi.rest.util.TextSearchConstants;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Service
public class ParagraphParser {

		
	/**
	 * Parsing the text paragraph from the below location
	 */

	@Value("${paragraph.txt.location}")
	private String fileName;
	
	public Map<String,Long> parseData(){

		Map<String, Long> words = null;
		log.info("The file to be parsed "+ fileName);
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			words = stream.flatMap(line -> Arrays.stream(line.replaceAll(TextSearchConstants.REGEX_PATTERN,TextSearchConstants.SPACE)
					.split(TextSearchConstants.WHITESPACES_REGEX)))
					.map(word -> word.toLowerCase().trim())
					.filter(word ->word.length()>0)
					.map(word -> new SimpleEntry<>(word,1))
					.collect(Collectors.groupingBy(SimpleEntry::getKey,Collectors.counting()));
		log.debug("The Map with parsed data " + words);
		} catch (IOException e) {
			log.error("Input Data Not Found " + fileName + e.getMessage());
			throw new DataNotFoundException("Input Data Not Found " + fileName); //Custom Exception
		}
		if(words == null || words.isEmpty()) {
			throw new  DataNotFoundException("Input Data is empty" + fileName);
		}

		return words;
	}


}

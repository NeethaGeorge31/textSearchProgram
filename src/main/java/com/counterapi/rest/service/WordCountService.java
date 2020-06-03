package com.counterapi.rest.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counterapi.rest.exception.ValidationException;
import com.counterapi.rest.model.SearchTextRequest;
import com.counterapi.rest.util.TextSearchConstants;

import lombok.extern.slf4j.Slf4j;

/**
 *Class to provide various services depending on the request
 */

@Slf4j
@Service
public class WordCountService {

	/**
	 * Processing the Top counts of the texts from the Map
	 * @param count
	 * @return String to support the csv format
	 */
	@Autowired
	ParagraphParser parse;
	public String getTopWordCount(int count) {
		Map<String,Long> data = parse.parseData();
		Map<String,Long> sortedData = sortByValue(data);
		log.debug("The sorted map " + sortedData);
		List<String> keyList = new ArrayList<String>(sortedData.keySet());
		if(keyList.size()<count) {
			count = keyList.size(); //If requested count size is more than the words
		}
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<count;i++) {
			String key = keyList.get(i);
			builder.append(key).append(TextSearchConstants.CSV_SEPERATOR).append(sortedData.get(key)).append(TextSearchConstants.NEWLINE);
		}	 
		
		return builder.toString();
	}

	/**
	 * @param Parsed data
	 * @return Map with sorted value
	 */
	static Map<String, Long> sortByValue(Map<String, Long> data) {

		Map<String,Long> sortedData = data.entrySet()
				.stream()
				.sorted((Map.Entry.<String, Long>comparingByValue().reversed()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		return sortedData;
	}


	/**
	 * Retrieving the count of the requested text
	 * @param searchReq List of texts given by the user
	 * @return map of entries in json format
	 */	 
	public Map<String, List<Map<String, Long>>> getSearchText(SearchTextRequest searchReq){

		List<String> req = searchReq.getSearchText();
		if(req==null || req.isEmpty()) {
			log.error("Input Request is Invalid ");
			throw new ValidationException("Input Request is Invalid");//Custom Exception to handle input
		}
		Map<String,Long> data = parse.parseData();
		List<Map<String, Long>> finalReq = req.stream()
				.map(word -> new HashMap<String, Long>(){{
					put(word, data.getOrDefault(word.toLowerCase(),  0L));
				}})
				.collect(Collectors.toList());

		Map<String,List<Map<String, Long>> > searchTextMap = new HashMap<String, List<Map<String, Long>>>();
		searchTextMap.put(TextSearchConstants.RESPONSEKEY, finalReq);
		return searchTextMap;

	}


}

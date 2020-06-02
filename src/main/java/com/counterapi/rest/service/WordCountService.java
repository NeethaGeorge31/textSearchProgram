package com.counterapi.rest.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.counterapi.rest.model.SearchTextRequest;

/**
 *Class to provide various services depending on the request
 */
public class WordCountService {

	/**
	 * Processing the Top counts of the texts from the Map
	 * @param count
	 * @return String to support the csv format
	 */
	public static String getTopWordCount(int count) {
		Map<String,Long> data = ParagraphParser.parseData();
		Map<String,Long> sortedData = sortByValue(data);

		List<String> keyList = new ArrayList<String>(sortedData.keySet());
		if(keyList.size()<count) {
			count = keyList.size(); //If requested count size is more than the words
		}
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<count;i++) {
			String key = keyList.get(i);
			builder.append(key).append("|").append(sortedData.get(key)).append("\n");
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
	public static Map<String, List<Map<String, Long>>> getSearchText(SearchTextRequest searchReq){

		List<String> req = searchReq.getSearchText();
		Map<String,Long> data = ParagraphParser.parseData();
		List<Map<String, Long>> finalReq = req.stream()
				.map(word -> new HashMap<String, Long>(){{
					put(word, data.getOrDefault(word.toLowerCase(),  0L));
				}})
				.collect(Collectors.toList());

		Map<String,List<Map<String, Long>> > searchTextMap = new HashMap<String, List<Map<String, Long>>>();
		searchTextMap.put("counts", finalReq);
		return searchTextMap;

	}


}

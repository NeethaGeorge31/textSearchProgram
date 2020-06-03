package com.counterapi.rest.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.counterapi.rest.model.SearchTextRequest;
import com.counterapi.rest.service.WordCountService;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

/**Handles two REST API controller methods
 * GET and POST
 * @author Neetha George
 * 
 */
@Slf4j
@RestController
public class CounterApiController {

	@Autowired
	WordCountService countServ;
	
	/**Gets the texts with highest counts given the count for the top listing
	 * Path http://localhost:8083/counter-api/top/{count}
	 * @return this top list texts in csv format
	 */
	@GetMapping(path = "/top/{count}", produces="text/csv")
	public String getTopWordCount(@PathVariable("count") int count) {
		log.info("Count provided by user to display the text " + count);
		String result = countServ.getTopWordCount(count);
		log.debug("The "+count+" top texts are " + result);
		return result;
	}

	/**
	 * Search the texts given and returns the count respectively
	 * Path http://localhost:8083/counter-api/search
	 * @param textReq This is the texts requested from the user
	 * @return Returns the occurrence of the text in JSON format
	 */
	@PostMapping(path ="/search",consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<Map<String, Long>>> searchText(@RequestBody SearchTextRequest
			textReq){
		log.debug("The Text String from user "+ textReq.toString());
		Map<String, List<Map<String, Long>>> searchTextResult = countServ.getSearchText(textReq);
		log.debug("The Map of texts with counts " + searchTextResult);
		return searchTextResult;

	}
}

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

/**Handles two REST API controller methods
 * GET and POST
 * @author Neetha George
 * 
 */
@RestController
@RequestMapping(path = "/counter-api")
public class CounterApiController {


	/**Gets the texts with highest counts given the count for the top listing
	 * Path http://localhost:8083/counter-api/top/{count}
	 * @return this top list texts in csv format
	 */
	@GetMapping(path = "/top/{count}", produces = "text/csv")
	public String getTopWordCount(@PathVariable("count") int count) {
		String result = WordCountService.getTopWordCount(count);
		return result;
	}

	/**
	 * Search the texts given and returns the count respectively
	 * Path http://localhost:8083/counter-api/search
	 * @param textReq This is the texts requested from the user
	 * @return Returns the occurance of the text in JSON format
	 */
	@PostMapping(path ="/search",consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<Map<String, Long>>> searchText(@RequestBody SearchTextRequest
			textReq){

		Map<String, List<Map<String, Long>>> searchTextResult = WordCountService.getSearchText(textReq);
		return searchTextResult;

	}


}

package com.counterapi.rest.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SearchTextRequest {

	public List<String> searchText;

	public List<String> getSearchText() {
		return searchText;
	}

	@Override
	public String toString() {
		return "SearchTextRequest [searchText=" + searchText + "]";
	}
	
}

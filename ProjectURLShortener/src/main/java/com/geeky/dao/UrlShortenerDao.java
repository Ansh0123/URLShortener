package com.geeky.dao;

import com.geeky.entity.UrlMapping;

public interface UrlShortenerDao {

	void saveUrlMapping(UrlMapping urlMapping);

	UrlMapping retrieveUrlMapping(String tinnyUrl);
	
	Long maxId();
	
	UrlMapping retrieveUrlMappingById(Long idx);

}

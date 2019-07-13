package com.geeky.dao;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.geeky.entity.UrlMapping;
import com.geeky.repository.UrlMappingRepository;

@Repository
public class UrlShortenerDaoImpl implements UrlShortenerDao {

	@Autowired
	UrlMappingRepository urlRepository;

	@Override
	public void saveUrlMapping(UrlMapping urlMapping) {
		urlRepository.save(urlMapping);
	}

	@Override
	public UrlMapping retrieveUrlMapping(String tinnyUrl) {
		Iterable<UrlMapping> list = urlRepository.findAll();
		Iterator<UrlMapping> it = list.iterator();
		while (it.hasNext()) {
			UrlMapping url = it.next();
			if (url.getShortUrl().equalsIgnoreCase(tinnyUrl)) {
				return url;
			}
		}
		return null;
	}

	@Override
	public Long maxId() {
		return urlRepository.count();
	}

	@Override
	public UrlMapping retrieveUrlMappingById(Long idx) {
		UrlMapping urlMapping = urlRepository.findById(idx).get();
		return urlMapping;
	}

}

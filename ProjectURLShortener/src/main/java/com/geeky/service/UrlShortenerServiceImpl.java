package com.geeky.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geeky.dao.UrlShortenerDao;
import com.geeky.entity.UrlMapping;
import com.geeky.vos.ActualUrlVO;
import com.geeky.vos.ShortenedUrlVO;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

	private static final int BASE_62 = 62;

	@Autowired
	UrlShortenerDao urlShortenerDao;

	@Override
	public ShortenedUrlVO convertToShortUrl(ActualUrlVO actualUrlVO) {
		Long maxId = getMaxId();
		if (maxId == null) {
			maxId = 1L;
		} else {
			maxId = maxId + 1;
		}
		String shortenUrl = base62Encoding(maxId);
		String actualUrl = actualUrlVO.getlongUrl();
		ShortenedUrlVO shortUrl = new ShortenedUrlVO(shortenUrl);
		UrlMapping urlMapping = new UrlMapping();
		urlMapping.setLongUrl(actualUrl);
		urlMapping.setShortUrl(shortenUrl);
		urlMapping.setCreatedAt(String.valueOf(LocalDateTime.now()));
		// Defaulting the expire date to one year later
		// Can be configuration based
		urlMapping.setExpireAt(String.valueOf((LocalDateTime.now().plusYears(1))));
		// Saving the tinny URL mapping
		urlShortenerDao.saveUrlMapping(urlMapping);

		return shortUrl;
	}

	@Override
	public ActualUrlVO getActualUrl(ShortenedUrlVO shortenedUrlVO) {
		/*
		 * Another way UrlMapping url =
		 * urlShortenerDao.retrieveUrlMapping(shortenedUrlVO.getShortUrl());
		 */
		Long idx = base62Decoding(shortenedUrlVO.getShortUrl());
		UrlMapping url = urlShortenerDao.retrieveUrlMappingById(idx);
		ActualUrlVO actualUrlVO = new ActualUrlVO();
		// Can be handled with customized exception 
		// Instead of sending blank response
		if (null == url) {
			return actualUrlVO;
		}
		actualUrlVO.setlongUrl(url.getLongUrl());
		return actualUrlVO;
	}

	/*
	 * This logic can be handled by available distributed systems like zookeeper For
	 * now i am taking the max id and incrementing it to 1
	 */
	private Long getMaxId() {
		return urlShortenerDao.maxId();
	}

	/* Base62 Encoding */
	private String base62Encoding(Long idx) {
		char charactersForEncoding[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		String sortenUrl = "";
		while (idx > 0) {
			sortenUrl += charactersForEncoding[(int) (idx % BASE_62)];
			idx /= BASE_62;
		}
		return sortenUrl;
	}

	/* Base62 Decoding */
	private Long base62Decoding(String shortURL) {
		Long id = 0L;
		for (int i = 0; i < shortURL.length(); i++) {
			if ('a' <= shortURL.charAt(i) && shortURL.charAt(i) <= 'z')
				id = id * BASE_62 + shortURL.charAt(i) - 'a';
			if ('A' <= shortURL.charAt(i) && shortURL.charAt(i) <= 'Z')
				id = id * BASE_62 + shortURL.charAt(i) - 'A' + 26;
			if ('0' <= shortURL.charAt(i) && shortURL.charAt(i) <= '9')
				id = id * BASE_62 + shortURL.charAt(i) - '0' + 52;
		}
		return id;
	}

}

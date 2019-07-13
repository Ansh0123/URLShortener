package com.geeky.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geeky.service.UrlShortenerService;
import com.geeky.vos.ActualUrlVO;
import com.geeky.vos.ShortenedUrlVO;

@RestController
@RequestMapping("/geeky")
public class URLController {
	
	@Autowired
	UrlShortenerService urlShortenerService;

	private static final String GREETING = "Hello Welcome to Geek World !!";
	
	@RequestMapping(method = RequestMethod.GET, value = "/greeting")
	public String greetingUrl() {
		return GREETING;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/shorten/url")
	public ShortenedUrlVO shortenLongUrl(@RequestBody ActualUrlVO urlVO) {
		return urlShortenerService.convertToShortUrl(urlVO);
	}
	
	@RequestMapping(method = RequestMethod.POST,value = "/long/url")
	public ActualUrlVO getLongUrl(@RequestBody ShortenedUrlVO shortenedUrlVO) {
		return urlShortenerService.getActualUrl(shortenedUrlVO);
	}

}

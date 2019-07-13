package com.geeky.service;

import com.geeky.vos.ActualUrlVO;
import com.geeky.vos.ShortenedUrlVO;

public interface UrlShortenerService {

	ShortenedUrlVO convertToShortUrl(ActualUrlVO actualUrlVO);

	ActualUrlVO getActualUrl(ShortenedUrlVO shortenedUrlVO);

}

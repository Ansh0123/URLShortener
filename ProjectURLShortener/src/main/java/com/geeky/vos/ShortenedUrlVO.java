package com.geeky.vos;

public class ShortenedUrlVO {

	private String shortUrl;

	public ShortenedUrlVO() {
		super();
	}

	public ShortenedUrlVO(String shortUrl) {
		super();
		this.shortUrl = shortUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

}

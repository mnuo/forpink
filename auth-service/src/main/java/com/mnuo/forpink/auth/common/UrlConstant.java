package com.mnuo.forpink.auth.common;

public enum UrlConstant {
	LOGIN_URL("/oauth/token"),;
	private String url;

	UrlConstant(String url) {
		this.url = url;

	}

	public String getUrl() {
		return url;
	}
}

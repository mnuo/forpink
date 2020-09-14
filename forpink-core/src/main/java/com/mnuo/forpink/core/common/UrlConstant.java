package com.mnuo.forpink.core.common;

public enum UrlConstant {
	LOGIN_URL("/oauth/token"),
	LOGOUT_URL("/oauth1/revokeToken1"),
	;
	private String url;

	UrlConstant(String url) {
		this.url = url;

	}

	public String getUrl() {
		return url;
	}
}

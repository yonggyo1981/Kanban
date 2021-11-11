package com.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;

public class Request {
	private static HttpServletRequest request;
	
	public static void set(ServletRequest request) {
		if (request instanceof HttpServletRequest) {
			Request.request = (HttpServletRequest)request;
		}
	}
	
	public static HttpServletRequest get() {
		return request;
	}
}

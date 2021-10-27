package com.core;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * 페이지 접속 권한 체크 
 *
 */
public class AccessController {
	
	private static String requestURI;
	
	/** 회원 전용 URI */
	private static String[] memberOnlyURI = {"/member/info"};
	
	/** 비회원 전용 URI */
	private static String[] guestOnlyURI = {"/member/login", "/member/join"};
	
	/**
	 * 페이지별 접속 체크 
	 * @param request
	 */
	public static void init(ServletRequest request, ServletResponse response) throws IOException {
		try {
			if (request instanceof HttpServletRequest) {
				HttpServletRequest req = (HttpServletRequest)request;
				requestURI = req.getRequestURI();
				
				// 비회원 전용 URI 체크 
				checkGuestOnly();
				
			}
			
		} catch (Exception e) {
			Logger.log(e);
			
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.printf("<script>alert('%s');history.back();</script>", e.getMessage());
		}
	}
	
	/**
	 * 비회원 전용 URI 체크 
	 * 
	 * @throws Exception
	 */
	private static void checkGuestOnly() throws Exception {
		
	}
}





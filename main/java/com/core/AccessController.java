package com.core;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import com.models.member.*;

/**
 * 페이지 접속 권한 체크 
 *
 */
public class AccessController {
	
	private static String requestURI;
	private static boolean isLogin;
	
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
				isLogin = MemberDao.isLogin(request);
				
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
		/**
		 * 로그인이 되어 있는 경우 -> 비회원 전용 페이지가 아닌 경우만 접근 가능 
		 */
		if (isLogin) {
			for(String URI : guestOnlyURI) {
				if (requestURI.indexOf(URI) != -1) { // 비회원 전용 페이지 접근한 경우 
					throw new Exception("접근권한이 없습니다.");
				}
			}
		}	
	}
}





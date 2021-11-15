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
	
	private static ServletResponse response;
	private static String requestURI;
	private static String rootURL;
	private static boolean isLogin;
	
	/** 회원 전용 URI */
	private static String[] memberOnlyURI = {"/member/info", "/kanban" };
	
	/** 비회원 전용 URI */
	private static String[] guestOnlyURI = {"/member/join", "/member/findid", "/member/findpw"};
	
	/**
	 * 페이지별 접속 체크 
	 * @param request
	 */
	public static void init(ServletRequest request, ServletResponse res) throws IOException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse response = (HttpServletResponse)res;
	
		try {
			requestURI = req.getRequestURI();
			isLogin = MemberDao.isLogin(request);
			AccessController.response = response; 
			rootURL = (String)request.getAttribute("rootURL");
					
			// 비회원 전용 URI 체크 
			checkGuestOnly();
					
			// 메인페이지 접속 체크 
			checkMainPage();
					
			// 회원 전용 URI 체크
			checkMemberOnly();	
		} catch (Exception e) {
			response.setCharacterEncoding("UTF-8");
			Logger.log(e);
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
				if (requestURI.indexOf(URI) != -1 && requestURI.indexOf("/resources") == -1) { // 비회원 전용 페이지 접근한 경우 
					throw new Exception("접근권한이 없습니다.");
				}
			}
		}	
	}
	
	/**
	 * 메인페이지 접속 권한 체크 
	 * 		- 비회원만 접속 가능(로그인)
	 * 		- 회원 -> 작업 요약 페이지 이동 
	 */
	private static void checkMainPage() throws IOException {
		if (isLogin && (requestURI.indexOf("/index.jsp") != -1 || requestURI.equals(rootURL + "/"))) { // 로그인 한 경우
			PrintWriter out = response.getWriter();
			out.printf("<script>location.replace('%s');</script>", "kanban/work");
		}
	}
	
	/**
	 * 회원 전용 URI 접근 체크 
	 * 
	 * @throws Exception
	 */
	private static void checkMemberOnly() throws Exception {
		/** 로그인 하지 않았을때 접속 하면 X */
		if (!isLogin) {
			for (String URI : memberOnlyURI) {
				if (requestURI.indexOf(URI) != -1 && requestURI.indexOf("/resources") == -1) { // 비회원이 회원전용 URI에 접속 
					throw new Exception("회원 전용 페이지 입니다.");
				}
			}
		}
	}
}





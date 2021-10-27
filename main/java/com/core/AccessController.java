package com.core;

import javax.servlet.*;
import java.io.*;

/**
 * 페이지 접속 권한 체크 
 *
 */
public class AccessController {
	
	/** 회원 전용 URI */
	private static String[] memberOnlyURI = {"/member/info"};
	
	/** 비회원 전용 URI */
	private static String[] guestOnlyURI = {"/member/login", "/member/join"};
	
	/**
	 * 페이지별 접속 체크 
	 * @param request
	 */
	public static void init(ServletRequest request, ServletResponse response) {
		try {
			
		} catch (Exception e) {
			Logger.log(e);
			
		}
	}
}





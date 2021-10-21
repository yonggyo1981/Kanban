package com.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * 공통 필터 - 사이트 전역 적용 
 * 
 * 1. 초기 설정(DB, Logger .... )
 * 2. 헤더 푸터 설정
 */
public class CommonFilter implements Filter {
	public void init(FilterConfig config) throws ServletException {
		
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		
		// 헤더 출력 
		printHeader(request, response);
		
		chain.doFilter(request, response);
		
		// 푸터 출력
		printFooter(request, response);
	}
	
	/** 
	 * 헤더 출력 
	 * 
	 */
	private void printHeader(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher rd = request.getRequestDispatcher("/views/outline/header/main.jsp");
		rd.include(request, response);
	}
	
	/**
	 * 푸터 출력 
	 */
	private void printFooter(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/views/outline/footer/main.jsp");
		rd.include(request, response);
	}	
}




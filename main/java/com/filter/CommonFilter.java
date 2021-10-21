package com.filter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import com.core.*;

/**
 * 공통 필터 - 사이트 전역 적용 
 * 
 * 1. 초기 설정(DB, Logger .... )
 * 2. 헤더 푸터 설정
 */
public class CommonFilter implements Filter {
	/** 
	 * 정적 디렉토리(헤더, 푸터가 적용되지 않는 경로)
	 *    - css, js, image ... 
	 */
	private String[] staticDirs = {"resources"};
	
	public void init(FilterConfig config) throws ServletException {
		
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		/** 사이트 설정 초기화 */
		Config.init(request);
		
		/** rootURL */
		String rootURL = request.getServletContext().getContextPath();
		request.setAttribute("rootURL", rootURL);
		
		// 헤더 출력
		if (isPrintOk(request)) {
			printHeader(request, response);
		}
		
		chain.doFilter(request, response);
		
		// 푸터 출력
		if (isPrintOk(request)) {
			printFooter(request, response);
		}
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
	
	/**
	 * 헤더, 푸터를 출력 할지 결정 
	 * 
	 * @param request
	 * @return
	 */
	private boolean isPrintOk(ServletRequest request) {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest)request;
			
			/** 정적 경로 제외 S */
			String URI = req.getRequestURI();
			for (String dir : staticDirs) {
				if (URI.indexOf("/" + dir) != -1) { // 정적 경로가 포함되어 있으면 false
					return false;
				}
			}
			/** 정적 경로 제외 E */
			
			/** 요청 메서드 GET 방식이 아닌 경우 제외 */
			String method = req.getMethod().toUpperCase();
			if (!method.equals("GET")) {
				return false;
			}
			
			/** 요청 파라미터 중에서 outline = none일때 제외 */
			String outline = request.getParameter("outline");
			if (outline != null && outline.equals("none")) {
				return false;
			}
		}
		
		return true;
	}
}




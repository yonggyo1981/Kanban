package com.core;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import com.models.member.*;

/**
 * 사이트 초기화 
 *
 */
public class BootStrap  {
	public static void init(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		
		/** 사이트 설정 초기화 */
		Config.init(request);
		Config config = Config.getInstance();
		
		/** 로거 초기화 */
		Logger.init();
		
		/** 접속자 정보 로그 */
		Logger.log(request);
		
		/** URI별 추가 CSS */
		request.setAttribute("addCss", config.getCss());

		/** URI별 추가 JS */
		request.setAttribute("addScripts", config.getScripts());
		
		/** 사이트 기본 제목 */
		request.setAttribute("pageTitle", config.get("PageTitle"));
		
		/** Environment - development(개발중), production(서비스 중) */
		String env = ((String)config.get("Environment")).equals("production")?"production":"development";
		request.setAttribute("environment", env);
		
		/** CSS, JS 버전 */
		String cssJsVersion = null;
		if (env.equals("development")) {
			cssJsVersion = "?v=" + String.valueOf(System.currentTimeMillis());
		}
		request.setAttribute("cssJsVersion", cssJsVersion);
		
		/** Body 태그 추가 클래스 */
		request.setAttribute("bodyClass",  config.getBodyClass(request));
		
		/** rootURL */
		String rootURL = request.getServletContext().getContextPath();
		request.setAttribute("rootURL", rootURL);
		
		/** rootPath */
		String rootPath = request.getServletContext().getRealPath(".");
		request.setAttribute("rootPath", rootPath);
		
		/** 요청 메서드 + requestURL, Request Encoding 설정 */
		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest)request;
			
			request.setAttribute("httpMethod", req.getMethod().toUpperCase());
			request.setAttribute("requestURL", req.getRequestURL().toString());
			
			req.setCharacterEncoding("UTF-8");
		}

		/** 로그인 유지 */
		MemberDao.init(request);
		
		AccessController.init(request, response);
		
		Req.set(request);
		Res.set(response);
	}
}

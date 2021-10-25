package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import com.core.Logger;
import com.models.member.*;

/**
 *  /member/* 컨트롤러
 *
 */
public class MemberController extends HttpServlet {
	
	private String httpMethod; // Http 요청 메서드, GET, POST
	private PrintWriter out;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/** 요청 메서드 */
		httpMethod = request.getMethod().toUpperCase();
		if (httpMethod.equals("GET")) {
			response.setContentType("text/html; charset=utf-8");
		} else { // GET이 아닌 경우 -> 유입된 입력 양식 데이터 처
			response.setCharacterEncoding("utf-8");
		}
		
		out = response.getWriter();

		String URI = request.getRequestURI();
		String mode = URI.substring(URI.lastIndexOf("/") + 1);
		switch(mode) {
			case "join" : // 회원가입
				joinController(request, response);
				break;
			case "info" : // 회원정보 수정
				infoController(request, response);
				break;
			case "login" : // 로그인
				loginController(request, response);
				break;
			case "findid" : // 아이디 찾기
				findidController(request, response);
				break;
			case "findpw" : // 비밀번호 찾기
				findpwController(request, response);
				break;
			default : // 없는 페이지 
				RequestDispatcher rd = request.getRequestDispatcher("/views/error/404.jsp");
				rd.forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * 회원 가입 /member/join
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void joinController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (httpMethod.equals("GET")) { // 양식 출력 
			RequestDispatcher rd = request.getRequestDispatcher("/views/member/form.jsp");
			rd.include(request, response);
		} else { // 양식 처리
			MemberDao dao = MemberDao.getInstance();
			try {
				dao.join(request);
			} catch (Exception e) {
				response.setContentType("text/html; charset=utf-8");
				out.printf("<script>alert('%s');</script>", e.getMessage());
				Logger.log(e);
			}
		}
	}
	
	/**
	 * 회원 정보 수정 /member/info
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void infoController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	/**
	 * 로그인 /member/login 
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void loginController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	/**
	 * 아이디 찾기 /member/findid
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void findidController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
	
	/**
	 * 비밀번호 찾기
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void findpwController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
}



package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

/**
 *   /kanban 컨트롤러
 *
 */
public class KanbanController extends HttpServlet {
	
	private String httpMethod; // 요청 메서드
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String URI = request.getRequestURI();
		String mode = URI.substring(URI.lastIndexOf("/") + 1);
		
		httpMethod = request.getMethod().toUpperCase(); // GET, POST, DELETE
		
		if (httpMethod.equals("GET")) {
			response.setContentType("text/html; charset=utf-8");
		} else { 
			request.setCharacterEncoding("UTF-8");
		}
		
		switch(mode) {
			case "work" : // 작업목록
				workController(request, response);
				break;
			case "add" : // 작업 등록  
				addController(request, response);
				break;
			case "edit" : // 작업 수정
				editController(request, response);
				break;
			case "remove" : // 작업 제거
				removeController(request, response);
				break;
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/** 작업 목록 */
	private void workController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/views/kanban/main.jsp");
		rd.include(request, response);
	}
	
	/** 작업 등록 */
	private void addController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (httpMethod.equals("POST")) { // 등록 처리 
			
		} else { // 등록 양식
			RequestDispatcher rd = request.getRequestDispatcher("/views/kanban/form.jsp");
			rd.include(request, response);
		}
	}
	
	/** 작업 수정 */
	private void editController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (httpMethod.equals("POST")) { // 수정 처리
			
		} else { // 수정 양식 
			RequestDispatcher rd = request.getRequestDispatcher("/views/kanban/form.jsp");
			rd.include(request, response);
		}
	}

	/** 작업 삭제 */
	private void removeController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}






package com.controller;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import com.core.*;
import com.models.kanban.*;
import com.models.file.*;

/**
 *   /kanban 컨트롤러
 *
 */
public class KanbanController extends HttpServlet {
	
	private String httpMethod; // 요청 메서드
	private PrintWriter out;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String URI = request.getRequestURI();
		String mode = URI.substring(URI.lastIndexOf("/") + 1);
		
		httpMethod = request.getMethod().toUpperCase(); // GET, POST, DELETE
		
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		if (httpMethod.equals("GET")) {
			response.setContentType("text/html; charset=utf-8");
		}
		
		out = response.getWriter();
		
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
			case "view" : // 작업 상세보기
				viewController(request, response);
				break;
			case "list" : // 작업 구분별 리스트 
				listController(request, response);
				break;
			default : // 없는 페이지 
				RequestDispatcher rd = request.getRequestDispatcher("/views/error/404.jsp");
				rd.forward(request, response);
				
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/** 작업 목록 */
	private void workController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		KanbanDao dao = KanbanDao.getInstance();
		ArrayList<Kanban> list = dao.getList();
		
		request.setAttribute("list", list);
		
		RequestDispatcher rd = request.getRequestDispatcher("/views/kanban/main.jsp");
		rd.include(request, response);
	}
	
	/** 작업 등록 */
	private void addController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (httpMethod.equals("POST")) { // 등록 처리 
			try {
				KanbanDao dao = KanbanDao.getInstance();
				boolean result = dao.add(request);
				if (!result) {
					throw new Exception("작업등록 실패하였습니다.");
				}
				out.print("<script>parent.location.reload();</script>");
			} catch (Exception e) {
				out.printf("<script>alert('%s');</script>", e.getMessage());
			}
			
		} else { // 등록 양식
			request.setAttribute("mode", "add");
			request.setAttribute("gid", System.currentTimeMillis());
			
			RequestDispatcher rd = request.getRequestDispatcher("/views/kanban/form.jsp");
			rd.include(request, response);
		}
	}
	
	/** 작업 수정 */
	private void editController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		KanbanDao dao = KanbanDao.getInstance();
		if (httpMethod.equals("POST")) { // 수정 처리
			try {
				boolean result = dao.edit(request);
				if (!result) {
					throw new Exception("수정에 실패하였습니다.");
				}
				out.print("<script>parent.location.reload();</script>");
			} catch (Exception e) {
				out.printf("<script>alert('%s');</script>", e.getMessage());
			}
		} else { // 수정 양식
			try {
				if (request.getParameter("idx") == null) {
					throw new Exception("잘못된 접근입니다.");
				}
				
				Kanban data = dao.get(request);
				if (data == null) {
					throw new Exception("작업내용이 없습니다.");
				}
				
				
				ArrayList<FileInfo> attachFiles = dao.getAttachFiles();
				
				request.setAttribute("mode", "edit");
				request.setAttribute("data", data);
				request.setAttribute("attachFiles", attachFiles);
			} catch (Exception e) {
				out.printf("<script>alert('%s');layer.close();</script>", e.getMessage());
				return;
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("/views/kanban/form.jsp");
			rd.include(request, response);			
		}
	}

	/** 작업 삭제 */
	private void removeController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if (request.getParameter("idx") == null) {
				throw new Exception("잘못된 접근입니다.");
			}
			KanbanDao dao = KanbanDao.getInstance();
			boolean result = dao.delete(request);
			if (!result) {
				throw new Exception("삭제 실패하였습니다.");
			}
			
			out.print("<script>parent.location.reload();</script>");
		} catch (Exception e) {
			out.printf("<script>alert('%s');</script>", e.getMessage());
		}
	}
	
	/** 작업 상세보기 */
	private void viewController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if (request.getParameter("idx") == null) {
				throw new Exception("잘못된 접근입니다.");
			}
			
			KanbanDao dao = KanbanDao.getInstance();
			Kanban data = dao.get(request);
			if (data == null) {
				throw new Exception("작업내용이 없습니다.");
			}
			// 첨부파일 
			ArrayList<FileInfo> attachFiles = dao.getAttachFiles();
			
			request.setAttribute("data", data);
			request.setAttribute("attachFiles", attachFiles);
			
		} catch (Exception e) {
			out.printf("<script>alert('%s');history.back();</script>", e.getMessage());
			return;
		}
		RequestDispatcher rd = request.getRequestDispatcher("/views/kanban/view.jsp");
		rd.include(request, response);
	}
	
	/**
	 * 작업 구분별 목록 출력 
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void listController(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		try {
			if (request.getParameter("status") == null) {
				throw new Exception("잘못된 접근입니다.");
			}
			
			KanbanDao dao = KanbanDao.getInstance();
			ArrayList<Kanban> list = dao.getList(request);
			request.setAttribute("list", list);
		} catch (Exception e) {
			out.printf("<script>alert('%s');history.back();</script>", e.getMessage());
			return;
		}
		RequestDispatcher rd = request.getRequestDispatcher("/views/kanban/main.jsp");
		rd.include(request, response);
	}
}






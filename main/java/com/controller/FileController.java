package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;

import com.core.*;
import com.models.file.*;

/**
 *  파일 다운로드 /file/download/파일 등록번호
 *   삭제 처리  /file/delete/파일등록번호
 * 
 */
public class FileController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/views/error/404.jsp");
		try {
			String URI = request.getRequestURI();
			String[] URIs = URI.split("/");
			int idx = 0;
			if (URIs[URIs.length - 1] != null) {
				idx = Integer.valueOf(URIs[URIs.length - 1]);
			}
			String mode = URIs[URIs.length - 2];
			switch(mode) {
				case "download" :
					downloadController(request, response, idx);
					break;
				case "delete" : 
					deleteController(request, response, idx);
					break;
				default : 
					rd.forward(request, response);
			}
			
		} catch (ArrayIndexOutOfBoundsException e) {
			rd.forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * 다운로드 
	 * 
	 * @param request
	 * @param response
	 * @param idx - 파일 등록번호 
	 * @throws ServletException
	 * @throws IOException
	 */
	private void downloadController(HttpServletRequest request, HttpServletResponse response, int idx) throws ServletException, IOException {
		FileUpload.getInstance().download(response, idx);
	}
	
	/**
	 * 파일 삭제 
	 * 
	 * @param request
	 * @param response
	 * @param idx 파일 등록번호
	 * @throws ServletException
	 * @throws IOException
	 */
	private void deleteController(HttpServletRequest request, HttpServletResponse response, int idx) throws ServletException, IOException {
		boolean result = FileUpload.getInstance().delete(idx);
		PrintWriter out = response.getWriter();
		out.print(result?"1":"0");
	}
}
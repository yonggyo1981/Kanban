package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

/**
 *  파일 다운로드 /file/download/파일 등록번호
 *   삭제 처리  /file/delete/파일등록번호
 * 
 */
public class FileController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		String URI = request.getRequestURI();
		String[] URIs = URI.split("/");
		int idx = 0;
		if (URIs[URIs.length - 1] != null) {
			idx = Integer.valueOf(URIs[URIs.length - 1]);
		}
		String mode = URIs[URIs.length - 2];
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

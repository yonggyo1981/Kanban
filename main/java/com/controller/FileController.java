package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 *  파일 다운로드 /file/download/파일 등록번호
 *   삭제 처리  /file/delete/파일등록번호
 * 
 */
public class FileController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

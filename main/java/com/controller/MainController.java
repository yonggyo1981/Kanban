package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import com.models.snslogin.*;


/**
 * 메인 페이지 - index.jsp 
 *
 */
public class MainController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		
		SocialLogin.clear();
		
		String naverCodeURL = NaverLogin.getInstance().getCodeURL();
		request.setAttribute("naverCodeURL", naverCodeURL);
		
		RequestDispatcher rd = request.getRequestDispatcher("/views/main/index.jsp");
		rd.include(request, response);
	}
}





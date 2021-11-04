package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;

import com.models.snslogin.*;
import com.core.HttpRequest;
import org.json.simple.*;

/**
 * 메인 페이지 - index.jsp 
 *
 */
public class MainController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		String apiURL = "http://yonggyo.com/~webclass/test.php";
		HashMap<String, String> postData = new HashMap<>();
		postData.put("name", "이름");
		postData.put("message", "안녕하세요.");
		JSONObject json = HttpRequest.request(apiURL,null, "POST", postData);
		System.out.println(json);
		
		SocialLogin.clear(request);
		
		String naverCodeURL = NaverLogin.getInstance().getCodeURL(request);
		request.setAttribute("naverCodeURL", naverCodeURL);
		
		RequestDispatcher rd = request.getRequestDispatcher("/views/main/index.jsp");
		rd.include(request, response);
	}
}





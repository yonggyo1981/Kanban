package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import com.core.Logger;
import com.models.member.*;
import com.models.snslogin.*;

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
			case "change_pw" : // 비밀번호 초기화 
				changePwController(request, response);
				break;
			case "logout" : // 로그아웃 
				logoutController(request, response);
				break;
			case "naver_login" : // 네이버 로그인 Callback URL
				naverLoginController(request, response);
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
			HttpSession session = request.getSession();
			
			request.setAttribute("action", "../member/join"); // 양식 처리 경로
			String socialType = "none";
			Member socialMember = SocialLogin.getSocialMember(request);
			if (socialMember != null) {
				socialType = socialMember.getSocialType();
			}
			
			request.setAttribute("socialType", socialType);
			request.setAttribute("socialMember", socialMember);
			
			RequestDispatcher rd = request.getRequestDispatcher("/views/member/form.jsp");
			rd.include(request, response);
		} else { // 양식 처리
			MemberDao dao = MemberDao.getInstance();
			Member socialMember = SocialLogin.getSocialMember(request);
			try {
				boolean result = dao.join(request);
				if (!result) { // 가입 실패
					throw new Exception("가입에 실패하였습니다.");
				}
				
				// 가입 성공 -> 로그인페이지
				String redirectUrl = "../index.jsp";
				if (socialMember != null) { // 소셜 회원 가입은 로그인 처리하므로 작업 요약으로 이동 
					redirectUrl = "../kanban/work";
				}
				out.printf("<script>parent.location.replace('%s');</script>", redirectUrl);
				
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
		Member member = null;
		try {
			if (request.getAttribute("member") != null) {
				member = (Member)request.getAttribute("member");
			}
			
			if (member == null) {
				throw new Exception("수정할 회원 정보가 없습니다.");
			}
		} catch (Exception e) {
			Logger.log(e);
			out.printf("<script>alert('%s');history.back();</script>", e.getMessage());
			return;
		}
		
		if (httpMethod.equals("GET")) { // 수정 양식
			String socialType = member.getSocialType();
			
			request.setAttribute("socialType", socialType);
			request.setAttribute("action", "../member/info");
			RequestDispatcher rd = request.getRequestDispatcher("/views/member/form.jsp");
			rd.include(request, response);
		} else { // 수정 처리 
			MemberDao dao = MemberDao.getInstance();
			try {
				dao.updateInfo(request);
				out.println("<script>alert('수정되었습니다.');parent.location.reload();</script>");
			} catch (Exception e) {
				Logger.log(e);
				out.printf("<script>alert('%s');</script>", e.getMessage());
			}
		}
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
		if (httpMethod.equals("GET")) {
			SocialLogin.clear();
			
			String naverCodeURL = NaverLogin.getInstance().getCodeURL();
			request.setAttribute("naverCodeURL", naverCodeURL);
			
			RequestDispatcher rd = request.getRequestDispatcher("/views/main/index.jsp");
			rd.include(request, response);
		} else {
			MemberDao dao = MemberDao.getInstance();
			try {
				dao.login(request);
				out.printf("<script>parent.location.replace('%s');</script>", "../kanban/work");
			} catch (Exception e) {
				Logger.log(e);
				out.printf("<script>alert('%s');</script>", e.getMessage());
			}
		}
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
		response.setContentType("text/html; charset=utf-8");
		if (httpMethod.equals("POST")) {
			MemberDao dao = MemberDao.getInstance();
			try {
				String memId = dao.findId(request);
				request.setAttribute("memId", memId);
			} catch (Exception e) {
				Logger.log(e);
				out.printf("<script>alert('%s');</script>", e.getMessage());
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/views/member/findid.jsp");
		rd.include(request, response);
		
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
		
		if (httpMethod.equals("GET")) { // 비밀번호 찾기 양식 
			RequestDispatcher rd = request.getRequestDispatcher("/views/member/findpw.jsp");
			rd.include(request, response);
		} else { // 일치 하는 회원 검증 -> 비밀번호 초기화 페이지로 이동 
			MemberDao dao = MemberDao.getInstance();
			try {
				Member member = dao.findPw(request);
				if (member == null) {
					throw new Exception("일치하는 회원이 없습니다.");
				}
				
				/** 
				 * 비밀번호 초기화 페이지 이동전 처리 
				 * 1. 접속 만료 시간(O)
				 * 2. 비번초기화시에 필요한 회원번호 숨겨서 처리 (O)
				 * 	  세션에 담아서 초기화 처리(POST)에서 조회하여 처리 
				 * 3. 접속 시간 만료
				 * 		 회원번호 삭제(O)
				 *       만료시간 이후 초기화 페이지 접속 -> 접속이 만료 되었다는 메세지 출력, 접속 X(O)
				 */
				HttpSession session = request.getSession();
				long expireTime = System.currentTimeMillis() + (3 * 60 * 1000);
				session.setAttribute("expireTime", expireTime);
				
				// 회원번호 세션 처리
				session.setAttribute("change_pw_memNo", member.getMemNo());
				
				out.printf("<script>parent.location.replace('%s');</script>", "../member/change_pw");
			} catch (Exception e) {
				Logger.log(e);
				out.printf("<script>alert('%s');</script>", e.getMessage());
			}
		}
	}
	
	/**
	 * 비밀번호 초기화 
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void changePwController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (httpMethod.equals("GET")) { // 초기화 양식 
			try {
				HttpSession session = request.getSession();
				long expireTime = 0L;
				if (session.getAttribute("expireTime") != null) {
					expireTime = (Long)session.getAttribute("expireTime");
				}
				
				// 만료시간이 지난 경우 
				if (expireTime < System.currentTimeMillis()) {
					session.removeAttribute("change_pw_memNo");
					throw new Exception("페이지 접속이 만료 되었습니다.");
				}
				
			} catch (Exception e) {
				Logger.log(e);
				out.printf("<script>alert('%s');location.replace('%s');</script>", e.getMessage(), "../member/findpw");
				return;
			}
			RequestDispatcher rd = request.getRequestDispatcher("/views/member/changepw.jsp");
			rd.include(request, response);
		} else { // 초기화 처리 
			try {
				MemberDao dao = MemberDao.getInstance();
				boolean result = dao.changePw(request);
				if (!result) {
					throw new Exception("비밀번호 변경 실패하였습니다.");
				}
				// 변경에 성공하면 -> 로그인 페이지(메인 페이지)
				out.printf("<script>parent.location.replace('%s');</script>", "../index.jsp");
			} catch (Exception e) {
				Logger.log(e);
				out.printf("<script>alert('%s');</script>", e.getMessage());
			}
		}
	}
	
	/**
	 * 로그아웃 
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void logoutController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberDao dao = MemberDao.getInstance();
		dao.logout(request);
		PrintWriter out = response.getWriter();
		out.printf("<script>location.replace('%s');</script>", "../index.jsp");
	}
	
	/**
	 * 네이버 로그인 Callback URL 
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void naverLoginController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NaverLogin naver = NaverLogin.getInstance();
		try {
			String accessToken = naver.getAccessToken();
			naver.getProfile(accessToken);
			
			/**
			 *  네이버 소셜 채널로 이미 가입이 완료된 경우 -> 로그인 처리 
			 *  가입이 안되어 있는 경우 -> 회원 가입 처리 
			 */
			if (naver.isJoin()) { // 가입되어 있는 경우 
				boolean result = naver.login(); // 로그인
				if (!result) { // 로그인 실패 
					throw new Exception("네이버 아이디 로그인 실패!");
				}
				// 로그인 성공시 작업 요약 
				out.printf("<script>location.replace('%s');</script>", "../kanban/work");
			} else { // 미가입
				// 회원 가입 페이지 이동
				out.printf("<script>location.replace('%s');</script>", "../member/join");
			}
			
		} catch (Exception e) {
			Logger.log(e);
			out.printf("<script>alert('%s');location.replace('../member/login');</script>", e.getMessage());
		}
	}
}



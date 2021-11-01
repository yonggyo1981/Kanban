package com.models.snslogin;

import java.util.*;
import java.net.URLEncoder;
import javax.servlet.http.*;

import com.core.Config;
import com.core.Logger;
import com.models.member.Member;

/**
 * 네이버 아이디 로그인 
 *
 */
public class NaverLogin extends SocialLogin {
	
	private String clientId; 
	private String clientSecret;
	private String callbackURL;
	
	private static NaverLogin instance = new NaverLogin();
	private NaverLogin() {}
	
	public static NaverLogin getInstance() {
		if (instance == null) {
			instance = new NaverLogin();
		}
		
		/** 네이버 로그인 설정 처리 */
		if (instance.clientId == null || instance.clientSecret == null || instance.callbackURL == null) {
			try {
				HashMap<String, String> conf = (HashMap<String, String>)Config.getInstance().get("NaverLogin");
				instance.clientId = conf.get("clientId");
				instance.clientSecret = conf.get("secret");
				instance.callbackURL = URLEncoder.encode(conf.get("callbackURL"), "UTF-8");
			} catch (Exception e) {
				Logger.log(e);
			}
		}
		
		return instance;
	}
	
	@Override
	public String getCodeURL(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		String state = String.valueOf(System.currentTimeMillis());
		session.setAttribute("state", state);
				
		StringBuilder sb = new StringBuilder();
		sb.append("https://nid.naver.com/oauth2.0/authorize?");
		sb.append("response_type=code&client_id=");
		sb.append(clientId);
		sb.append("&redirect_uri=");
		sb.append(callbackURL);
		sb.append("&state=");
		sb.append(state);
		
		return sb.toString();
	}

	@Override
	public String getAccessToken(String code, String state) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAccessToken(HttpServletRequest request) throws Exception {
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		return getAccessToken(code, state);
	}
	
	@Override
	public Member getProfile(String accessToken) {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.models.snslogin;

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
		
		return instance;
	}
	
	@Override
	public String getCodeURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAccessToken(String code, String state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member getProfile(String accessToken) {
		// TODO Auto-generated method stub
		return null;
	}

}

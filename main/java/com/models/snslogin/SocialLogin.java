package com.models.snslogin;

import java.net.*;
import java.io.*;

import javax.servlet.http.*;

import com.core.Logger;
import com.models.member.*;

/**
 * 소셜 로그인 추상 클래스 
 *
 */
public abstract class SocialLogin {
	/** 
	 * Access Token을 발급 받기위한 인증 code 발급 URL 생성 
	 * @return
	 */
	public abstract String getCodeURL(HttpServletRequest request);
	
	/**
	 * Access Token 발급 
	 * 
	 * @param code
	 * @param state
	 * @return
	 */
	public abstract String getAccessToken(String code, String state) throws Exception;
	public abstract String getAccessToken(HttpServletRequest request) throws Exception;
	
	/**
	 * 회원 프로필 조회 API를 통해서 각 소셜 채널별 회원 정보 추출 
	 * 
	 * @param accessToken
	 * @return
	 */
	public abstract Member getProfile(String accessToken);
	
	/**
	 * 원격 HTTP 요청...
	 * 
	 * @param apiURL
	 */
	public void httpRequest(String apiURL) {
		try {
			URL url = new URL(apiURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			
			InputStream in;
			int code = conn.getResponseCode();
			
		} catch (Exception e) {
			Logger.log(e);
		}
	}
}





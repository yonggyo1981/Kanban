package com.models.member;

import java.util.*;
import javax.servlet.http.*;

import com.core.DB;

/**
 * MemberDao 클래스 
 *
 */
public class MemberDao {
	private static MemberDao instance = new MemberDao();
	private MemberDao() {};  // 기본 생성자 private -> 외부 생성 X, 내부에서만 생성 O
	
	public static MemberDao getInstance() {
		if (instance != null) {
			instance = new MemberDao();
		}
		return instance;
	}
	
	/**
	 * 회원 가입 처리 
	 * 
	 * @param request
	 * @return
	 */
	public boolean join(HttpServletRequest request) {
		
		ArrayList<Map<String, String>> bindings = new ArrayList<>();
		String sql = "INSERT INTO member (memId, memPw, memPwHint, memNm, cellPhone) VALUES (?,?,?,?,?)";
		String memPw = request.getParameter("memPw");
		String hash = "";
		String cellPhone = request.getParameter("cellPhone");
		bindings.add(setBinding("String", request.getParameter("memId")));
		bindings.add(setBinding("String", hash));
		bindings.add(setBinding("String", request.getParameter("memPwHint")));
		bindings.add(setBinding("String", request.getParameter("memNm")));
		bindings.add(setBinding("String", cellPhone));
		
		int rs  = DB.executeUpdate(sql, bindings);
		
		return (rs > 0)?true:false;
	}
	
	/**
	 * SQL 바인데이터를 Map 형태로 지정
	 * 
	 * @param dataType
	 * @param data
	 * @return
	 */
	public Map<String, String> setBinding(String dataType, String data) {
		Map<String, String> map = new HashMap<>();
		map.put(dataType, data);
		
		return map;
	}
}

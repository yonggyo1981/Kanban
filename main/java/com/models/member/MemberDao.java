package com.models.member;

import java.util.*;
import javax.servlet.http.*;

import org.mindrot.jbcrypt.*;

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
	public boolean join(HttpServletRequest request) throws Exception {
		
		/**
		 * 회원 가입데이트의 유효성 검사
		 */
		checkJoinData(request);
		
		ArrayList<Map<String, String>> bindings = new ArrayList<>();
		String sql = "INSERT INTO member (memId, memPw, memPwHint, memNm, cellPhone) VALUES (?,?,?,?,?)";
		String memPw = request.getParameter("memPw");
		String hash = BCrypt.hashpw(memPw, BCrypt.gensalt(10));
		
		/** 휴대전화번호 형식 -> 숫자로만 구성 */
		String cellPhone = request.getParameter("cellPhone");
		cellPhone = cellPhone.replaceAll("[^\\d]", ""); // 숫자가 아닌 문자 제거 -> 숫자만 남는다
		
		bindings.add(setBinding("String", request.getParameter("memId")));
		bindings.add(setBinding("String", hash));
		bindings.add(setBinding("String", request.getParameter("memPwHint")));
		bindings.add(setBinding("String", request.getParameter("memNm")));
		bindings.add(setBinding("String", cellPhone));
		
		//int rs  = DB.executeUpdate(sql, bindings);
		int rs = 0;
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
	
	/**
	 * 회원 가입 데이터 검증
	 * 
	 * @param request
	 * @throws Exception
	 */
	public void checkJoinData(HttpServletRequest request) throws Exception {
		/**
		 * 1. 필수 항목 체크(O)
		 * 2. 아이디 체크
		 * 			- 1) 자리수 체크(8~30) - O
		 * 			- 2) 알파벳 + 숫자만 입력(O)
		 *          - 3) 아이디 중복 체크
		 * 3. 비밀번호
		 * 			- 1) 자리수 체크(8자리 이상~)
		 * 			- 2) 복잡성 체크
		 * 					- 비밀번호에는 숫자, 알파벳, 특수문자가 각각 1개씩 포함
		 * 			- 3) 비밀번호 확인
		 * 4. 휴대전화번호(필수 항목 X)
		 * 			- 휴대전화번호가 들어오면 - 휴대전화번호 형식에 맞는지 체크 
		 */
		/** 필수 항목 체크 S */
		String[] required = {
			"memId//아이디를 입력해 주세요.",
			"memPw//비밀번호를 입력해 주세요.",
			"memPwRe//비밀번호를 확인해 주세요.",
			"memPwHint//비밀번호 힌트를 입력해 주세요.",
			"memNm//회원명을 입력해 주세요."
		};
		
		for(String re : required) {
			String[] params = re.split("//");
			String value = request.getParameter(params[0]);
			if (value == null || value.trim().equals("")) { // 필수 값이 없는 경우 
				throw new Exception(params[1]);
			}
		}
		/** 필수 항목 체크 E */
		/** 아이디 체크 S */
		// 아이디 자리수 체크(8~30)
		// String - length()
		String memId = request.getParameter("memId");
		if (memId.length() < 8 || memId.length() > 30) {
			throw new Exception("아이디는 8~30개 이하로 입력해 주세요.");
		}
		
		// 알파벳 + 숫자로만 구성 
		if (!memId.matches("[a-zA-Z0-9]+")) {
			throw new Exception("아이디는 알파벳과 숫자로만 구성해 주세요.");
		}
		
		// 아이디 중복 체크 
		String sql = "SELECT COUNT(*) cnt FROM member WHERE memId = ?";
		ArrayList<Map<String, String>> bindings = new ArrayList<>();
		bindings.add(setBinding("String", memId));
		
		
		/** 아이디 체크 E */
		
	}
}

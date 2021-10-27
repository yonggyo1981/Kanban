package com.models.member;

import java.util.*;
import java.sql.*;
import javax.servlet.http.*;
import javax.servlet.*;
import static com.core.DB.setBinding;

import org.mindrot.jbcrypt.*;
import com.core.DB;
import com.core.DBField;
import com.core.Logger;

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
	 * 로그인 유지 처리 
	 * 
	 * @param request
	 */
	public static void init(ServletRequest request) {
		if (request instanceof HttpServletRequest) {
			MemberDao dao = getInstance();
			HttpServletRequest req = (HttpServletRequest)request;
			
			HttpSession session = req.getSession();
			int memNo = 0;
			Member member = null;
			if (session.getAttribute("memNo") != null) {
				memNo = (Integer)session.getAttribute("memNo");
				member = dao.getMember(memNo);
			}
			
			boolean isLogin = false;
			if (member != null) {
				request.setAttribute("member", member);
				isLogin = true;
			} // endif 
			request.setAttribute("isLogin", isLogin);
		} // endif 
	}
	
	/**
	 * 로그인 여부 체크 
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isLogin(ServletRequest request) {
		boolean isLogin = false;
		if (request.getAttribute("isLogin") != null) {
			isLogin = (Boolean)request.getAttribute("isLogin");
		}
		
		return isLogin;
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
		
		ArrayList<DBField> bindings = new ArrayList<>();
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
		
		int rs  = DB.executeUpdate(sql, bindings);
		return (rs > 0)?true:false;
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
		 *          - 3) 아이디 중복 체크 (O)
		 * 3. 비밀번호
		 * 			- 1) 자리수 체크(8자리 이상~) (O)
		 * 			- 2) 복잡성 체크 (보류)
		 * 					- 비밀번호에는 숫자, 알파벳, 특수문자가 각각 1개씩 포함
		 * 			- 3) 비밀번호 확인
		 * 4. 휴대전화번호(필수 항목 X) (O)
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
		String[] fields = { "memId" };
		ArrayList<DBField> bindings = new ArrayList<>();
		bindings.add(setBinding("String", memId));
		int count = DB.getCount("member", fields, bindings);
		if (count > 0) { // 아이디 중복
			throw new Exception("이미 가입된 아이디 입니다.");
		}
		/** 아이디 체크 E */
		
		/** 비밀번호 체크 S */
		// 비밀번호 자리수 체크(8자리 이상)
		String memPw = request.getParameter("memPw");
		if (memPw.length() < 8) {
			throw new Exception("비밀번호는 8자리 이상 입력해 주세요.");
		}
		// 비밀번호 복잡성(숫자 + 알파벳 + 특수문자가 각각 1개 이상 입력)		
		if (!memPw.matches(".*[0-9].*") || !memPw.matches(".*[a-zA-Z].*") || !memPw.matches(".*[!@#$%^&*()].*")) {
			throw new Exception("비밀번호는 1개 이상의 알파벳, 숫자, 특수문자를 각각 포함해야 합니다.");
		}
		
		
		// 비밀번호 확인
		String memPwRe = request.getParameter("memPwRe");
		if (!memPw.equals(memPwRe)) {
			throw new Exception("비밀번호를 확인해 주세요.");
		}
		/** 비밀번호 체크 E */
		
		/** 휴대전화 번호 체크 S */
		String cellPhone = request.getParameter("cellPhone");
		if (cellPhone != null && !cellPhone.trim().equals("")) {
			/** 
			 * 1. 통일성 있도록 숫자로만 추출(숫자가 아닌 문자만 제거 -> 숫자) 
			 * 2. 패턴 체크
			 */
			cellPhone = cellPhone.replaceAll("[^0-9]", "");
			String pattern = "01[016789][0-9]{3,4}[0-9]{4}";
			if (!cellPhone.matches(pattern)) {
				throw new Exception("휴대전화번호 형식이 아닙니다.");
			}
		}
		
		/** 휴대전화 번호 체크 E */
	}
	
	/**
	 * 로그인 처리 
	 * 
	 * @param request - 세션을 사용하기 위해서(HttpSession getSession())
	 * @param memId
	 * @param memPw
	 * @return
	 * @throws Exception 
	 */
	public boolean login(HttpServletRequest request, String memId, String memPw) throws Exception {
		/**
		 * 1. memId를 통해 회원 정보를 조회
		 * 2. 회원 정보가 조회가 되면(실제 회원 있으면) - 비밀번호를 체크
		 * 3. 비밀번호도 일치? -> 세션 처리(회원 번호 - memNo 세션에 저장)
		 */
		
		Member member = getMember(memId);
		if (member == null) { // memId에 일치하는 회원이 X 
			throw new Exception("가입하지 않은 아이디 입니다.");
		}
		
		// 비밀번호 체크 
		boolean match = BCrypt.checkpw(memPw, member.getMemPw());
		if (!match) { // 비밀번호 불일치
			throw new Exception("비밀번호가 일치하지 않습니다.");
		}
		
		// 세션 처리
		HttpSession session = request.getSession();
		session.setAttribute("memNo", member.getMemNo());
		
		return true;
	}
	
	public boolean login(HttpServletRequest request) throws Exception {
		return login(request,
						request.getParameter("memId"),
						request.getParameter("memPw")
				);
	}
	
	public Member getMember(String memId) {
		int memNo = 0;
		String sql = "SELECT memNo FROM member WHERE memId = ?";
		try (Connection conn = DB.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, memId);
			
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				memNo = rs.getInt("memNo");
			}
			rs.close();
			
		} catch (SQLException | ClassNotFoundException e) {
			Logger.log(e);
		}
		
		return (memNo == 0)?null:getMember(memNo);
	}
	
	/**
	 * 회원정보 조회
	 * 
	 * @param memNo
	 * @return
	 */
	public Member getMember(int memNo) {
		String sql = "SELECT * FROM member WHERE memNo = ?";
		ArrayList<DBField> bindings = new ArrayList<>();
		bindings.add(setBinding("Integer", String.valueOf(memNo)));
		
		Member member = DB.executeQueryOne(sql, bindings, new Member());
		
		return member;
	}
	
	/**
	 * 로그아웃
	 *  
	 */
	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
	}
}

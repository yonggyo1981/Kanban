package com.models.kanban;

import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;

import com.core.*;
import com.models.member.*;
import com.models.file.*;

public class KanbanDao {
	
	private static KanbanDao instance = new KanbanDao();
	private ArrayList<FileInfo> attachFiles = null; // 첨부파일 목록
	private static HttpServletRequest request;
	
	private KanbanDao() {};
	
	public static KanbanDao getInstance() {
		if (instance == null) {
			instance = new KanbanDao();
		}
		
		if (request == null) {
			request = Req.get();
		}
		
		return instance;
	}
		
	/**
	 * 작업 목록 추가 
	 * 
	 * @param request
	 * @return
	 */
	public boolean add(HttpServletRequest request) throws Exception {
	
		HashMap<String, String> params = FileUpload.getInstance().upload(request).get();
		
		/** 유효성 검사 S */
		checkWorkData(params);
		/** 유효성 검사 E */
		
		int memNo = 0;
		if (request.getAttribute("member") != null) {
			Member member = (Member)request.getAttribute("member");
			memNo = member.getMemNo();
		}
		
		String sql = "INSERT INTO worklist (gid, memNo, status, subject, content) VALUES(?,?,?,?,?)";
		ArrayList<DBField> bindings = new ArrayList<>();
		bindings.add(DB.setBinding("Long", params.get("gid")));
		bindings.add(DB.setBinding("Integer", String.valueOf(memNo)));
		bindings.add(DB.setBinding("String", params.get("status")));
		bindings.add(DB.setBinding("String", params.get("subject")));
		bindings.add(DB.setBinding("String", params.get("content")));
		
		int rs = DB.executeUpdate(sql, bindings);
		
		return (rs  > 0)?true:false;
	}
	
	/**
	 * 작업 수정 처리 
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean edit(HttpServletRequest request) throws Exception {
		HashMap<String, String> params = FileUpload.getInstance().upload(request).get();
				
		/** 유효성 검사 S */
		if (params.get("idx") == null) {
			throw new Exception("잘못된 접근입니다.");
		}
		
		checkWorkData(params);
		/** 유효성 검사 E */
		
		String sql = "UPDATE worklist SET status = ?, subject = ?, content = ?, modDt = NOW() WHERE idx = ?";
		ArrayList<DBField> bindings = new ArrayList<>();
		bindings.add(DB.setBinding("String", params.get("status")));
		bindings.add(DB.setBinding("String", params.get("subject")));
		bindings.add(DB.setBinding("String", params.get("content")));
		bindings.add(DB.setBinding("Integer", params.get("idx")));
		
		int rs = DB.executeUpdate(sql, bindings);
		return (rs >0)?true:false;
	}
	
	/**
	 * 작업 추가, 수정시 데이터 검증 
	 * 
	 * @param params
	 * @throws Exception
	 */
	public void checkWorkData(HashMap<String, String> params) throws Exception {
		String[] required = {
				"status//작업구분을 선택하세요",
				"subject//제목을 입력하세요.",
				"content//작업내용을 입력하세요",
		};
		for (String s : required) {
			String[] param = s.split("//");
			String value = params.get(param[0]);
			if (value == null || value.trim().equals("")) {
				throw new Exception(param[1]);
			}
		}
	}
	/**
	 * 작업 목록 조회 
	 * 
	 * @param status
	 * @return
	 */
	public ArrayList<Kanban> getList(Object object) {
		String status = null;
		HttpServletRequest request = Req.get();
		if (object instanceof HttpServletRequest) {
			request = (HttpServletRequest)object;
			status = request.getParameter("status");
		} else {
			status = (String)object;
		}
		System.out.println("member : " + request.getAttribute("member"));
		int memNo = 0;
		if (request.getAttribute("member") != null) {
			Member member = (Member)request.getAttribute("member");
			memNo = member.getMemNo();
		}
		
		ArrayList<DBField> bindings = new ArrayList<>();
		String sql = "SELECT a.*, b.memId, b.memNm FROM worklist a LEFT JOIN member b ON a.memNo = b.memNo WHERE a.memNo = ?";
		bindings.add(DB.setBinding("Integer", String.valueOf(memNo)));
		if (status != null) {
			sql += " AND a.status = ?";
			bindings.add(DB.setBinding("String", status));
		}
		
		sql += " ORDER BY a.regDt DESC";
		
		ArrayList<Kanban> list = DB.executeQuery(sql, bindings, new Kanban());
		
		return list;
	}
		
	public ArrayList<Kanban> getList() {
		return getList(null);
	}
	
	/**
	 * 작업 상세 
	 * 
	 * @param idx 작업 등록번호
	 * @return
	 */
	public Kanban get(int idx) {
		
		String sql = "SELECT a.*, b.memId, b.memNm FROM worklist a LEFT JOIN member b ON a.memNo = b.memNo WHERE a.idx = ?";
		ArrayList<DBField> bindings = new ArrayList<>();
		bindings.add(DB.setBinding("Integer", String.valueOf(idx)));
		Kanban data = DB.executeQueryOne(sql, bindings, new Kanban());
		
		/** 첨부파일 S */
		if (data != null) {
			long gid = data.getGid();
			attachFiles = FileUpload.getInstance().getFiles(gid);
		}
		/** 첨부파일 E */
		return data;
	}
	
	public Kanban get(HttpServletRequest request) {
		int idx = 0;
		if (request.getParameter("idx") != null) {
			idx = Integer.valueOf(request.getParameter("idx"));
		}
		return get(idx);
	}
	
	/**
	 * 작업 상세 첨부파일 목록 
	 * 
	 * @return
	 */
	public ArrayList<FileInfo> getAttachFiles() {
		return attachFiles;
	}
	
	/**
	 * 작업 삭제 
	 * 
	 * @param idx 작업 등록번호
	 * @return
	 */
	public boolean delete(int idx) {
		
		/**
		 * 0. 작업 정보 - O
		 * 1. 첨부 파일 삭제 - O
		 * 2. 작업 내용 삭제
		 */
		Kanban data = get(idx);
		if (data == null)
			return false;
		
		FileUpload fileUpload = FileUpload.getInstance();
		ArrayList<FileInfo> list = getAttachFiles();
		for (FileInfo file : list) {
			fileUpload.delete(file.getIdx());
		}
		
		String sql = "DELETE FROM worklist WHERE idx = ?";
		ArrayList<DBField> bindings = new ArrayList<>();
		bindings.add(DB.setBinding("Integer", String.valueOf(idx)));
		int rs = DB.executeUpdate(sql, bindings);
		
		return (rs > 0)?true:false;
	}
	
	public boolean delete(HttpServletRequest request) throws Exception {
		int idx = 0;
		if (request.getParameter("idx") != null) {
			idx = Integer.valueOf(request.getParameter("idx"));
		}
		
		/** 수정, 삭제 권한이 있는지 체크 */
		boolean result = checkAuth(request, idx);
		if (!result) { // 삭제 권한 없음 
			throw new Exception("삭제 권한이 없습니다.");
		}
		
		return delete(idx);
	}
	
	/**
	 * 수정, 삭제 권한 여부 체크 
	 * @param request
	 * @param idx
	 * @return
	 */
	public boolean checkAuth(HttpServletRequest request, int idx) {
		if (!MemberDao.isLogin(request)) { // 로그인이 안된 경우 -> 권한 없음
			return false;
		}
		
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("member");
		Kanban data = get(idx);
		int memNo = member.getMemNo();
		if (data != null && memNo == data.getMemNo()) {
			return true; // 수정, 삭제권한 있음..
		}
		return false;
	}
}



package com.models.kanban;

import java.util.*;
import javax.servlet.http.*;

import com.core.*;
import com.models.member.*;

public class KanbanDao {
	
	private static KanbanDao instance = new KanbanDao();
	private KanbanDao() {};
	
	public static KanbanDao getInstance() {
		if (instance == null) {
			instance = new KanbanDao();
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
		/** 유혀성 검사 E */
		
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
	 * 작업 목록 조회 
	 * 
	 * @param status
	 * @return
	 */
	public ArrayList<Kanban> getList(String status) {
		
		return null;
	}
	
	public ArrayList<Kanban> getList() {
		return getList(null);
	}
}

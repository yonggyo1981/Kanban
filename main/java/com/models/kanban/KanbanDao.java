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
	public boolean add(HttpServletRequest request) {
		
		HashMap<String, String> params = FileUpload.getInstance().upload(request).get();
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
	
}

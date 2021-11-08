package com.models.kanban;

import java.sql.*;
import com.models.*;

public class Kanban extends Dto<Kanban> {
	private int idx;  // 작업 번호 
	private long gid; // 그룹 ID
	private int memNo; // 회원번호
	private String memId; //회원 아이디
	private String memNm; // 회원명 
	private String status; // 작업 상태(ready, progress, done)
	private String subject; // 작업명
	private String content; // 작업 내용 
	private String regDt; // 작업 등록일
	
	public Kanban() {}
	
	public Kanban(int idx, long gid, int memNo, String memId, String memNm, String status, String subject, String content, String regDt) {
		this.idx = idx;
		this.gid = gid;
		this.memNo = memNo;
		this.memId = memId;
		this.memNm = memNm;
		this.status = status;
		this.subject = subject;
		this.content = content;
		this.regDt = regDt;
	}
	
	public Kanban(ResultSet rs) throws SQLException {
		this(
				rs.getInt("idx"),
				rs.getLong("gid"),
				rs.getInt("memNo"),
				rs.getString("memId"),
				rs.getString("memNm"),
				rs.getString("status"),
				rs.getString("subject"),
				rs.getString("content"),
				rs.getString("regDt")
		);
	}
	
	public int getIdx() {
		return idx;
	}
	
	public void setIdx(int idx) {
		this.idx = idx;
	}
	
	public long getGid() {
		return gid;
	}
	
	public void setGid(long gid) {
		this.gid = gid;
	}
	
	public int getMemNo() {
		return memNo;
	}
	
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getRegDt() {
		return regDt;
	}
	
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	@Override
	public Kanban setResultSet(ResultSet rs) throws SQLException {
		return new Kanban(rs);
	}
}

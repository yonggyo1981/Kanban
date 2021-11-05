package com.models.file;

import java.sql.*;
import com.models.*;

public class FileInfo extends Dto {
	
	private int idx; // 파일 등록번호
	private long gid; // 파일 그룹 ID 
	private String originalName; // 원 파일명
	private String mimeType; // 파일 형식 
	private String regDt; // 파일 업로드 일시
	
	public FileInfo() {}
	
	public FileInfo(int idx, long gid, String originalName, String mimeType, String regDt) {
		this.idx = idx;
		this.gid = gid;
		this.originalName = originalName;
		this.mimeType = mimeType;
		this.regDt = regDt;
	}
	
	public FileInfo(ResultSet rs) throws SQLException {
		this(
			rs.getInt("idx"),
			rs.getLong("gid"),
			rs.getString("originalName"),
			rs.getString("mimeType"),
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
	
	public String getOriginalName() {
		return originalName;
	}
	
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	
	public String getMimeType() {
		return mimeType;
	}
	
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	public String getRegDt() {
		return regDt;
	}
	
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	@Override
	public FileInfo setResultSet(ResultSet rs) throws SQLException {
		return new FileInfo(rs);
	}
}

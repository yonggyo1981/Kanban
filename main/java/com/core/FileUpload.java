package com.core;

import java.util.*;
import java.io.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.*; // FileItem
import org.apache.commons.fileupload.servlet.*; // SerlvetFileUpload
import org.apache.commons.fileupload.disk.*; // DiskFileItemFactory

/**
 * 파일관련 클래스 
 *   - 1. 업로드 
 *   - 2. 다운로드 
 *   - 3. 파일 삭제 
 *
 */
public class FileUpload {
	
	private static FileUpload instance = new FileUpload();
	private HashMap<String, String> params = new HashMap<>(); // 일반 양식 데이터 
	private long maxFileSize = 20 * 1024 * 1024; // 업로드 최대 용량 
	
	private FileUpload() {}
	
	public static FileUpload getInstance() {
		if (instance == null) {
			instance = new FileUpload();
		}
		
		return instance;
	}
	
	/**
	 * 파일 업로드 처리 
	 * 		- 파일 데이터 -> 파일 DB에 기록한 후 증감번호인 idx -> 서버 저장용 파일명
	 * 		- 일반데이터 -> HashMap형태로 따로 저장 
	 * 						 -> 메서드 체이닝 방식으로 get 함수 호출시 
	 * @param request
	 */
	public FileUpload upload(HttpServletRequest request) {
		try {
			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = upload.parseRequest(request);
			upload.setHeaderEncoding("UTF-8"); // 한글 파일명 깨짐 방지
			upload.setSizeMax(maxFileSize);
			
		} catch (Exception e) {
			Logger.log(e);
		}
		return this;
	}
	
	/**
	 * 파일 업로드 양식 처리시 일반 양식데이터 모음
	 * 
	 * @return
	 */
	public HashMap<String, String> get() {
		return params;
	}
	
	/**
	 * 최대 업로드 파일 사이즈 변경 
	 * 
	 * @param size
	 */
	public void setMaxFileSize(long size) {
		maxFileSize = size;
	}
}	




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
	
	private FileUpload() {}
	
	public static FileUpload getInstance() {
		if (instance == null) {
			instance = new FileUpload();
		}
		
		return instance;
	}
	
	/**
	 * 
	 * @param request
	 */
	public void upload(HttpServletRequest request) {
		
	}
}




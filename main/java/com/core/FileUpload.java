package com.core;

import java.util.*;
import java.io.*;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.*; // FileItem
import org.apache.commons.fileupload.servlet.*; // SerlvetFileUpload
import org.apache.commons.fileupload.disk.*; // DiskFileItemFactory

import com.models.file.*;

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
			
			String uploadPath = request.getServletContext().getRealPath(File.separator + "resources" + File.separator + "upload");
			
			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = upload.parseRequest(request);
			upload.setHeaderEncoding("UTF-8"); // 한글 파일명 깨짐 방지
			upload.setSizeMax(maxFileSize);
			
			/** 일반 양식데이터 */
			for(FileItem item : items) {
				if (item.isFormField()) {
					String fieldName = item.getFieldName();
					String value = item.getString("UTF-8");
					params.put(fieldName, value);
				}
			}
			
			/** 파일데이터 처리 */
			for(FileItem item : items) {
				if (!item.isFormField()) {
					String fileName = item.getName(); // 경로포함 파일 명 C:\폴더1\폴더\파일명.확장자
					
					
					fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
					if (fileName == null || fileName.equals("")) {
						continue;
					}
					
					String mimeType = item.getContentType();
					/** 
					 * 1. 파일 정보를 DB에 기록(O)
					 * 2. 추가된 증감번호 -> idx (O)
					 * 3. 서버에 업로드될 경로 생성
					 * 	 증감번호 -> 10진수  (나머지 연산자 -> 균등하게 10개 폴더에 저장)
					 *   0번, 1번, 2번, 3번, 4번 ... 9번
					 *  4. 파일 업로드 FileItem.write(File 인스턴스)
					 *  		- 실제 서버에 올라갈 파일 경로
					 *  		uploadPath + "/" + (idx % 10) + "/" + idx;   
					 */
					
					   // DB에 파일 정보 기록 
					 long gid = System.currentTimeMillis();
					 if (params.get("gid") != null) {
						gid = Long.valueOf(params.get("gid"));
					 }
					 String sql = "INSERT INTO fileinfo (gid, originalName, mimeType) VALUES (?, ?, ?)";  
					ArrayList<DBField> bindings = new ArrayList<>();
					bindings.add(DB.setBinding("Long", String.valueOf(gid)));
					bindings.add(DB.setBinding("String", fileName));
					bindings.add(DB.setBinding("String", mimeType));
					
					int idx = DB.executeUpdate(sql, bindings, true);
					if (idx < 1) continue; // 파일 정보 기록 실패시 -> 업로드 처리 X
					
					// 업로드될 경로
					String folder = String.valueOf(idx % 10);
					String folderPath = uploadPath + File.separator + folder;
					File dir = new File(folderPath);
					if (!dir.exists()) { // 폴더가 존재 X -> 생성 
						dir.mkdir();
					}
					
					File file = new File(folderPath + File.separator + idx);
					item.write(file);
				}
			}
			
			
		} catch (Exception e) {
			Logger.log(e);
		}
		return this;
	}
	
	/**
	 * 파일 다운로드 처리 
	 * 
	 * @param response
	 * @param idx
	 */
	public void download(HttpServletResponse response, int idx) throws IOException {
		FileInfo file = getFile(idx);
		if (file == null) {
			return;
		}
		
		/**
		 * FileInputStream  + BufferedInputSream - 읽고 
		 * ServletOutputStream - getOutStream() -> 화면에 출력
		 */
		String filePath = file.getUploadedPath();
		String fileName = file.getOriginalName();
		fileName = URLEncoder.encode(fileName, "UTF-8");
		File fileInfo = new File(filePath);
		// 파일이 존재하는지 여부 체크 
		if (!fileInfo.exists()) {
			return;
		}
		
		long fileSize = fileInfo.length();
		
		response.setHeader("Content-Type", "application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		response.setHeader("Cache-Control", "must-revalidate");
		response.setHeader("Content-Length", String.valueOf(fileSize));
		
		try (FileInputStream fis = new FileInputStream(filePath);
			 BufferedInputStream bis = new BufferedInputStream(fis);
			OutputStream out = response.getOutputStream()) {
			int i;
			while((i = bis.read()) != -1) {
				out.write(i);
			}
			out.flush();
		} catch (IOException e) {
			Logger.log(e);
		}
	}
	
	/**
	 * 파일 삭제 
	 * 
	 * @param idx
	 * @return
	 */
	public boolean delete(int idx) {
		/**
		 * 1. 파일 정보를 idx로 조회
		 * 2. 파일 삭제 
		 * 3. 파일 정보 DB 삭제 
		 */
		FileInfo fileInfo = getFile(idx);
		if (fileInfo == null) {
			return false;
		}
		
		File file = new File(fileInfo.getUploadedPath());
		if (file.exists()) {
			file.delete();
		}
		
		String sql = "DELETE FROM fileInfo WHERE idx = ?";
		ArrayList<DBField> bindings = new ArrayList<>();
		bindings.add(DB.setBinding("Integer", String.valueOf(idx)));
		int rs = DB.executeUpdate(sql, bindings);
		
		return (rs > 0)?true:false;
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
	
	/**
	 * 그룹 ID(gid)로 파일 목록 
	 * 
	 * @param gid
	 * @return
	 */
	public ArrayList<FileInfo> getFiles(long gid) {
		String sql = "SELECT * FROM fileinfo WHERE gid = ?";
		ArrayList<DBField> bindings = new ArrayList<>();
		bindings.add(DB.setBinding("Long", String.valueOf(gid)));
		
		ArrayList<FileInfo> list = DB.executeQuery(sql, bindings, new FileInfo());
		
		return list;
	}
	
	/**
	 * 파일 등록번호(idx)로 파일 정보 조회 
	 * 
	 * @param idx
	 * @return
	 */
	public FileInfo getFile(int idx) {
		String sql = "SELECT * FROM fileinfo WHERE idx = ?";
		ArrayList<DBField> bindings = new ArrayList<>();
		bindings.add(DB.setBinding("Integer", String.valueOf(idx)));
		FileInfo file = DB.executeQueryOne(sql, bindings, new FileInfo());
		
		return file;
	}
}	




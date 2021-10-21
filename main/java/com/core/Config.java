package com.core;

import java.io.*;
import java.util.*;

import javax.servlet.*;

import org.json.simple.*;
import org.json.simple.parser.*;

/**
 * 사이트 설정 관리 
 * 
 */
public class Config {
	/** 
	* src/main/config/config.json을 읽어서 설정 HashMap
	*  
	*/
	public Config(ServletRequest request) {
		String configPath = request.getServletContext().getRealPath(".");
		configPath += File.separator + ".." + File.separator + "config" + File.separator + "config.json";
		
		File file = new File(configPath);
		if (!file.exists()) { // 설정 파일 존재 X 중지 
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		try (FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr)) {
			String line = null;
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String json = sb.toString();
		System.out.println(json);
 	
	}
}





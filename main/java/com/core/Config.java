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
	
	private HashMap<String, Object> conf = new HashMap<>(); // 설정 담기
	
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
		
		try {
			JSONObject  json = (JSONObject)new JSONParser().parse(sb.toString());
			Iterator<String> ir = json.keySet().iterator();
			while(ir.hasNext()) {
				String key = ir.next();
				conf.put(key, json.get(key));
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 설정 조회 
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		if (!conf.containsKey(key)) {
			return null;
		}
		
		Object value = conf.get(key);
		// 설정 값이 문자열이면 그냥 반환 
		if (value instanceof String) {
			return value;
		}
		
		// 아니면 JSONObject -> HashMap -> Object
		HashMap<String, String> map = new HashMap<>();
		JSONObject json = (JSONObject)value;
		Iterator<String> ir = json.keySet().iterator();
		while(ir.hasNext()) {
			String k = ir.next();
			String v = (String)json.get(k);
			map.put(k, v);
		}
		
		return map;
	}
}





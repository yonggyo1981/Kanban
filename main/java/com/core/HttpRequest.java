package com.core;

import java.io.*;
import java.net.*;
import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.*;

public class HttpRequest {
	/**
	 * 
	 * @param apiURL
	 * @param headers
	 * @return
	 */
	public static JSONObject request(String apiURL, HashMap<String, String> headers, String method, HashMap<String, String> postData) {
		JSONObject json = null;
		try {
			URL url = new URL(apiURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			
			/** 요청 헤더 처리 */
			if (headers != null) {
				Iterator<String> ir = headers.keySet().iterator();
				while(ir.hasNext()) {
					String key = ir.next();
					String value = headers.get(key);
					conn.setRequestProperty(key, value);
				}
			}
			
			InputStream in;
			int code = conn.getResponseCode(); // 200, HttpURLConnection.HTTP_OK
			if (code == HttpURLConnection.HTTP_OK) {
				in = conn.getInputStream();
			} else {
				in = conn.getErrorStream();
			}
			
			StringBuilder sb = new StringBuilder();
			try (in;
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(isr)) {
				String line = null;
				while((line = br.readLine()) != null) {
					sb.append(line);
				}
			} catch (IOException e) {
				Logger.log(e);
			}
			
			String data = sb.toString();
			if (data != null && !data.trim().equals("")) {
				JSONParser parser = new JSONParser();
				json = (JSONObject)parser.parse(data);
			}
		} catch (Exception e) {
			Logger.log(e);
		}
		
		return json;
	}
	
	public static JSONObject request(String apiURL, HashMap<String, String> headers) {
		return request(apiURL, headers, "GET", null);
	}
}

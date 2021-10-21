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
		
		System.out.println(configPath);
	}
}





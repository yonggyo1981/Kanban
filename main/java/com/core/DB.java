package com.core;

import java.sql.*;
import java.util.*;

import com.core.*;

/**
 * DB 연결 클래스
 *
 */
public class DB {
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		HashMap<String, String> config = (HashMap<String, String>)Config.getInstance().get("DB");
		
		Class.forName(config.get("Driver"));
		String url = config.get("Url");
		String user = config.get("user");
		String password = config.get("password");
		Connection conn = DriverManager.getConnection(url, user, password);
		
		return conn;
	}
	
	public static <E> ArrayList<E> executeQuery(String sql, ArrayList<Map<String, String>> bindings, E dto) {
		ArrayList<E> list = null;
		try (Connection conn = DB.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			if (bindings != null) {
				for (Map<String, String> map : bindings) {
					Iterator<String> ir = map.keySet().iterator();
					if (ir.hasNext()) {
						String dataType = ir.next();
						String value = map.get(dataType);
						switch(dataType) {
							case "String" :
								break;
							case "Integer" :
								break;
							case "Double" : 
								break;
						}
					}
				}
			}
			
		} catch (SQLException | ClassNotFoundException e) {
			
		}
		
		
		return null;
	}

}

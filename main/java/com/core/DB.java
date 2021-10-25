package com.core;

import java.sql.*;
import java.util.*;

import com.core.*;
import com.models.*;

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
	
	public static <E extends Dto> ArrayList<E> executeQuery(String sql, ArrayList<Map<String, String>> bindings, E dto) { // E -> Object
		
		ArrayList<E> list = new ArrayList<>();
		ArrayList<String> logBindings = new ArrayList<>();
		
		try (Connection conn = DB.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			if (bindings != null) {
				int no = 1;
				for (Map<String, String> map : bindings) {
					Iterator<String> ir = map.keySet().iterator();
					if (ir.hasNext()) {
						String dataType = ir.next();
						String value = map.get(dataType);
						logBindings.add(value); // 바인딩 데이터를 로그로 기록
						switch(dataType) {
							case "String" :
								pstmt.setString(no, value);
								break;
							case "Integer" :
								pstmt.setInt(no, Integer.valueOf(value));
								break;
							case "Double" : 
								pstmt.setDouble(no, Double.valueOf(value));
								break;
						}
					}
					no++;
				} // endfor 
			
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					dto.setResultSet(rs);
					list.add(dto);
				}
				rs.close();
				
				// SQL 로그 기록
				StringBuilder sb = new StringBuilder();
				sb.append("SQL : ");
				sb.append(sql);
				sb.append(" / Bindings : ");
				sb.append(logBindings.toString());
				Logger.log(sb, Logger.INFO);
				
			}
			
		} catch (SQLException | ClassNotFoundException e) {
			Logger.log(e);
		}
		
		return list;
	}

}

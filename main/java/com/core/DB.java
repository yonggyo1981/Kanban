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
	
	public static <E extends Dto> ArrayList<E> executeQuery(String sql, ArrayList<DBField> bindings, E dto) { // E -> Object
		
		ArrayList<E> list = new ArrayList<>();
		ArrayList<String> logBindings = new ArrayList<>();
		
		try (Connection conn = DB.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			if (bindings != null) {
				// 바인딩 처리
				processBinding(pstmt, bindings);
					
			
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					list.add((E)dto.setResultSet(rs));
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
	
	public static<E extends Dto> E executeQueryOne(String sql, ArrayList<Map<String, String>> bindings, E dto) {
		ArrayList<E> list = executeQuery(sql, bindings, dto);
		if (list == null) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	/**
	 * UPDATE, INSERT, DELETE에서 사용
	 * 
	 * @param sql
	 * @param bindings
	 * @param isReturnGeneratedKey Insert인 경우 추가된 번호 반환
	 * @return int - INSERT인 경우 -> 추가된 증감번호(Primary Key, Auto Increment), 나머지는 - 반영된 투플의 개수
	 * 		
	 */
	public static int executeUpdate(String sql, ArrayList<Map<String,String>> bindings, boolean isReturnGeneratedKey) {
		
		int rs = 0;
		ArrayList<String> logBindings = new ArrayList<>();
		
		try(Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			int no = 1;
			for(Map<String, String> map : bindings) {
				Iterator<String> ir = map.keySet().iterator();
				if (ir.hasNext()) { // Map은 1개씩만 추가
					String dataType = ir.next();
					String value = map.get(dataType);
					logBindings.add(value);
					
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
			
			rs = pstmt.executeUpdate();
			
			/** INSERT후 추가된 증감번호 */
			if (isReturnGeneratedKey) {
				ResultSet gkrs = pstmt.getGeneratedKeys();
				if (gkrs.next()) {
					rs = gkrs.getInt(1);
				}
				gkrs.close();
			}
			
			// SQL 로그 기록
			StringBuilder sb = new StringBuilder();
			sb.append("SQL : ");
			sb.append(sql);
			sb.append(" / Bindings : ");
			sb.append(logBindings.toString());
			sb.append("/ rs : ");
			sb.append(rs);		
			Logger.log(sb, Logger.INFO);
			
		} catch (SQLException | ClassNotFoundException e) {
			Logger.log(e);
		}
		
		return rs;
	}
	
	public static int executeUpdate(String sql, ArrayList<Map<String, String>> bindings) {
		return executeUpdate(sql, bindings, false);
	}
	
	/**
	 * 테이블 + 조건(=)에 따른 개수 
	 * 
	 * @param tableName
	 * @param fields 조건 속성명
	 * @param bindings
	 * @return
	 */
	public static int getCount(String tableName, String[] fields, ArrayList<Map<String, String>> bindings) {
		int count = 0;
		
		ArrayList<String> logBindings = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(*) cnt FROM ");
		sb.append(tableName);
		
		if (fields != null && fields.length > 0) {
			boolean isFirst = true;
			sb.append(" WHERE ");
			for(String field : fields) {
				if (!isFirst) {
					sb.append(" AND ");
				}
				sb.append(field);
				sb.append("= ?");
				
				isFirst = false;
			}
		} // endif 
		
		String sql = sb.toString();
		try(Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			/** 데이터 바인딩 S */
			if (fields != null && fields.length > 0 && bindings != null) {
				int no = 1;
				for(Map<String, String> map : bindings) {
					Iterator<String> ir = map.keySet().iterator();
					if (ir.hasNext()) {
						String dataType = ir.next();
						String value = map.get(dataType);
						logBindings.add(value);
						
						switch(dataType) {
							case "String":
								pstmt.setString(no, value);
								break;
							case "Integer":
								pstmt.setInt(no, Integer.valueOf(value));
								break;
							case "Double":
								pstmt.setDouble(no, Double.valueOf(value));
								break;
						}
					} // endif 
					no++;
				}
			}
			/** 데이터 바인딩 E */
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("cnt");
			}
			rs.close();
			
		} catch (SQLException | ClassNotFoundException e) {
			Logger.log(e);
		}
		
		// SQL 로그 기록
		sb = new StringBuilder();
		sb.append("SQL : ");
		sb.append(sql);
		sb.append(" / Bindings : ");
		sb.append(logBindings.toString());
		sb.append("/ count : ");
		sb.append(count);		
		Logger.log(sb, Logger.INFO);
		
		return count;
	}
	
	public static int getCount(String tableName) {
		return getCount(tableName, null, null);
	}
	
	/**
	 * SQL 바인데이터를 Map 형태로 지정
	 * 
	 * @param dataType
	 * @param data
	 * @return
	 */
	public static Map<String, String> setBinding(String dataType, String data) {
		Map<String, String> map = new HashMap<>();
		map.put(dataType, data);
		
		return map;
	}
	
	/**
	 * SQL 바인딩 처리 
	 *  
	 * @param pstmt
	 * @param bindings
	 */
	public ArrayList<String> processBinding(PreparedStatement pstmt, ArrayList<DBField> bindings) throws SQLException {
		
		ArrayList<String> logBindings = new ArrayList<>(); // 로그용 바인딩 데이터
		
		int no = 1;
		for(DBField binding : bindings) {
			String dataType = binding.getType();
			String value = binding.getValue();
			logBindings.add(value);
			
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
			no++;
		}
		
		return logBindings;
	}
}



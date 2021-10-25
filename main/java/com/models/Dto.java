package com.models;

import java.sql.*;

public abstract class Dto {
	/** 
	 * ResultSet으로 값 지정
	 * @param rs
	 */
	public abstract void setResultSet(ResultSet rs) throws SQLException;
}

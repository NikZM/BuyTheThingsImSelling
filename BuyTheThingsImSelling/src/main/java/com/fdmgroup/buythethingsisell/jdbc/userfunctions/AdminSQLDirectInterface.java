package com.fdmgroup.buythethingsisell.jdbc.userfunctions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.fdmgroup.buythethingsisell.entities.JSONConverter;
import com.fdmgroup.buythethingsisell.jdbc.ConnectionFactory;

public class AdminSQLDirectInterface {

	@Resource
	private ConnectionFactory connectionFactory;
	@Resource
	private JSONConverter jc;
	
	public String executeQuery(String s) throws SQLException{
		Connection conn = connectionFactory.getConnection();
		Statement stmt = getStatement(conn);
		ResultSet res = stmt.executeQuery(s);
		ResultSetMetaData rsmd = res.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		List<String> columnHeders = new ArrayList<String>();
		for(int i = 1; i <= columnsNumber; i++) {
			columnHeders.add(rsmd.getColumnName(i));
		}
		List<List<String>> resultArray = new ArrayList<List<String>>();
		resultArray.add(columnHeders);
		while (res.next()){
			List<String> rowArray = new ArrayList<String>();
			for(int i = 1; i <= columnsNumber; i++) {
				try {
				rowArray.add(res.getString(i));
				} catch (SQLException sqlEx){
					try {
						res.getBlob(i);
						rowArray.add("(blob)");
					} catch (SQLException sqlEx2){
						rowArray.add("null");
					}
				}
			}
			resultArray.add(rowArray);
		}
		conn.close();
		return listListFormatter(resultArray);
	}
	
	public int executeUpdate(String s) throws SQLException{
		Connection conn = connectionFactory.getConnection();
		Statement stmt = getStatement(conn);
		int i = stmt.executeUpdate(s);
		conn.close();
		return i;
	}
	
	private Statement getStatement(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		return stmt;
	}
	
	public String listListFormatter(List<List<String>> listlist){
		StringBuilder strB = new StringBuilder();
		for(int i = 0; i < listlist.size(); i++){
			if(i == 0) {
				strB.append(jc.toJsonField("headers", listlist.get(i)));
				strB.append(", \"rows\":[");
			} else {
				strB.append("[");
				for(String temp : listlist.get(i)){
					strB.append("\"" +temp + "\",");
				}
				strB.deleteCharAt(strB.length() - 1);
				strB.append("]");
				if(listlist.size()-1 != i){
					strB.append(", ");
				}
			}
		}
		strB.append("]");
		return ("{" + strB.toString() + "}");
	}
}

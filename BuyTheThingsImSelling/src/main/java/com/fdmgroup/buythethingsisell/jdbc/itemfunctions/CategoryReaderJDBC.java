package com.fdmgroup.buythethingsisell.jdbc.itemfunctions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.fdmgroup.buythethingsisell.jdbc.ConnectionFactory;
import com.fdmgroup.buythethingsisell.jdbc.SQLQueries;

public class CategoryReaderJDBC {
	
	final static Logger logger = Logger.getLogger(CategoryReaderJDBC.class);
			
	@Resource
	private ConnectionFactory connectionFactory;
	
	public List<String> getAllCategories(){
		List<String> categories = new ArrayList<String>();
		Connection conn = connectionFactory.getConnection();
		try {
			PreparedStatement prepStmt = conn.prepareStatement(SQLQueries.GetAllCategories.query());
			ResultSet res = prepStmt.executeQuery();
			while(res.next()){
				categories.add(res.getString(1));
			}
		} catch (SQLException e) {
			logger.error("Failed to retrieve categories list from database");
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.fatal("Unable to close connection");
			}
		}
		return categories;
	}
}
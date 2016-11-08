package com.fdmgroup.buythethingsisell.jdbc.itemfunctions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.fdmgroup.buythethingsisell.entities.ItemsEntityWrapper;
import com.fdmgroup.buythethingsisell.entities.JSONConverter;
import com.fdmgroup.buythethingsisell.jdbc.ConnectionFactory;
import com.fdmgroup.buythethingsisell.jdbc.SQLQueries;

public class ItemByCategoryJDBC {
	
	final static Logger logger = Logger.getLogger(ItemByCategoryJDBC.class);
	
	@Resource
	private ItemEntityBuilder itemEntityBuilder;
	@Resource
	private ConnectionFactory connectionFactory;
	@Resource
	private JSONConverter jsonConverter;
	
	public ItemsEntityWrapper getItemsByCategory(String catName){
		ItemsEntityWrapper iew = new ItemsEntityWrapper(jsonConverter);
		Connection conn = connectionFactory.getConnection();
		try {
			PreparedStatement prepStmt = conn.prepareStatement(SQLQueries.GetItemsByCategory.query());
			prepStmt.setString(1, catName);
			ResultSet res = prepStmt.executeQuery();
			iew = itemEntityBuilder.getItemsByPage(res);
		} catch (SQLException e) {
			logger.error("Could not return items for category: " + catName);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.fatal("Unable to close connection");
			}
		}
		return iew;
	}
	
}
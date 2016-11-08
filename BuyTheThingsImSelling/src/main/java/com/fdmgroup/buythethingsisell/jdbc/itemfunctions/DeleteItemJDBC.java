package com.fdmgroup.buythethingsisell.jdbc.itemfunctions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.fdmgroup.buythethingsisell.jdbc.ConnectionFactory;
import com.fdmgroup.buythethingsisell.jdbc.SQLQueries;

public class DeleteItemJDBC {
	
	final static Logger logger = Logger.getLogger(DeleteItemJDBC.class);
	
	@Resource
	private ConnectionFactory connectionFactory;
	
	public boolean deleteItem(int itemID){
		boolean submitted = true;
		Connection conn = connectionFactory.getConnection();
		try {
			conn.setAutoCommit(false);
			
			PreparedStatement deleteUsers = conn.prepareStatement(SQLQueries.DeleteUsersItemsByID.query());
			PreparedStatement deleteStock = conn.prepareStatement(SQLQueries.DeleteItemStockByID.query());
			PreparedStatement deleteCategories = conn.prepareStatement(SQLQueries.DropItemCategories.query());
			PreparedStatement deleteItem = conn.prepareStatement(SQLQueries.DeleteItemByID.query());
			
			deleteUsers.setInt(1, itemID);
			deleteStock.setInt(1, itemID);
			deleteCategories.setInt(1, itemID);
			deleteItem.setInt(1, itemID);
			
			if (deleteUsers.executeUpdate() != 1){
				submitted = false;
			}
			if (deleteStock.executeUpdate() != 1){
				submitted = false;
			}
			deleteCategories.executeUpdate();
			if (deleteItem.executeUpdate() != 1){
				submitted = false;
			}
			if (submitted){
				conn.commit();
			} else {
				conn.rollback();
			}
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			logger.error("Item \"" + itemID + "\" not deleted");
			submitted = false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.fatal("Unable to close connection");
			}
		}
		return submitted;
	}
}
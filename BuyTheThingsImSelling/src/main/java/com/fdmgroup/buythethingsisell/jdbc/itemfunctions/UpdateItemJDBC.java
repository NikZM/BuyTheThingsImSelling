package com.fdmgroup.buythethingsisell.jdbc.itemfunctions;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.fdmgroup.buythethingsisell.entities.ItemEntity;
import com.fdmgroup.buythethingsisell.jdbc.ConnectionFactory;
import com.fdmgroup.buythethingsisell.jdbc.SQLQueries;

public class UpdateItemJDBC {
	
	final static Logger logger = Logger.getLogger(UpdateItemJDBC.class);
	
	@Resource
	private ConnectionFactory connectionFactory;

	public boolean updateItem(ItemEntity itemEntity){
		
		Connection conn = connectionFactory.getConnection();
		Boolean submitted = true;
		try {
			conn.setAutoCommit(false);
			PreparedStatement itemStmt = conn.prepareStatement(SQLQueries.UpdateItemInfo.query());
			PreparedStatement stockStmt = conn.prepareStatement(SQLQueries.UpdateItemStock.query());
			PreparedStatement dropCats = conn.prepareStatement(SQLQueries.DropItemCategories.query());
			PreparedStatement catStmt = conn.prepareStatement(SQLQueries.AddItemToCategory.query());
			
			int itemID = itemEntity.getId();
			
			itemStmt.setString(1, itemEntity.getTitle());
			Blob descripBlob = conn.createBlob();
			descripBlob.setBytes(1, itemEntity.getDescription().getBytes());
			itemStmt.setBlob(2, descripBlob);
			itemStmt.setDouble(3, itemEntity.getPrice());
			itemStmt.setString(4, itemEntity.getImgURL());
			itemStmt.setInt(5, itemID);
			
			stockStmt.setInt(1, itemID);
			stockStmt.setInt(2, itemEntity.getUnitsAvailable());
			stockStmt.setInt(3, itemID);
			stockStmt.setInt(4, itemEntity.getUnitsAvailable());
			
			dropCats.setInt(1, itemID);
			for (String cat : itemEntity.getCategories()){
				catStmt.setString(1, cat);
				catStmt.setInt(2, itemID);
				catStmt.addBatch();
			}
			
			if (itemStmt.executeUpdate() != 1){
				submitted = false;
			} 
			if (stockStmt.executeUpdate() != 1){
				submitted = false;
			}
			dropCats.executeUpdate();
			catStmt.executeBatch();
			if (submitted){
				conn.commit();
			} else {
				conn.rollback();
			}
			conn.setAutoCommit(true);
			
		} catch (SQLException e) {
			logger.error("Unable to update item: " + itemEntity.toString());
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
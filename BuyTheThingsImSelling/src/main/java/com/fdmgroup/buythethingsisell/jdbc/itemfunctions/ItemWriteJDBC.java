package com.fdmgroup.buythethingsisell.jdbc.itemfunctions;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.fdmgroup.buythethingsisell.entities.ItemEntity;
import com.fdmgroup.buythethingsisell.jdbc.ConnectionFactory;
import com.fdmgroup.buythethingsisell.jdbc.SQLQueries;

public class ItemWriteJDBC {
	
	final static Logger logger = Logger.getLogger(ItemWriteJDBC.class);

	@Resource
	private ConnectionFactory connectionFactory;

	public boolean postItem(ItemEntity itemEntity, String email) {
		Connection conn = connectionFactory.getConnection();
		try {
			PreparedStatement prepStmt = conn.prepareStatement("SELECT USERID FROM USERS WHERE EMAIL = ?");
			prepStmt.setString(1, email);
			ResultSet res = prepStmt.executeQuery();
			if (res.next()) {
				int userID = res.getInt(1);
				return postItem(itemEntity, userID, conn);
			} else {
				return false;
			}
		} catch (SQLException e) {
			logger.error("Could not submit item: " + itemEntity.toString());
			return false;
		} finally {
			try {
				if (!conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.fatal("Unable to close connection");
			}
		}
	}

	public boolean postItem(ItemEntity itemEntity, int userID) throws SQLException {
		return postItem(itemEntity, userID, null);
	}

	private boolean postItem(ItemEntity itemEntity, int userID, Connection conn) throws SQLException {
		if (conn == null) {
			conn = connectionFactory.getConnection();
		}
		Boolean submitted = true;

		conn.setAutoCommit(false);
		PreparedStatement itemStmt = conn.prepareStatement(SQLQueries.AddItem.query());
		PreparedStatement stockStmt = conn.prepareStatement(SQLQueries.UpdateItemStock.query());
		PreparedStatement catStmt = conn.prepareStatement(SQLQueries.AddItemToCategory.query());
		PreparedStatement userStmt = conn.prepareStatement(SQLQueries.AddItemToUser.query());

		int itemID = getNextItemSeqID(conn);

		itemStmt.setInt(1, itemID);
		itemStmt.setString(2, itemEntity.getTitle());
		Blob descripBlob = conn.createBlob();
		descripBlob.setBytes(1, itemEntity.getDescription().getBytes());
		itemStmt.setBlob(3, descripBlob);
		itemStmt.setDouble(4, itemEntity.getPrice());
		itemStmt.setString(5, itemEntity.getImgURL());

		stockStmt.setInt(1, itemID);
		stockStmt.setInt(2, itemEntity.getUnitsAvailable());
		stockStmt.setInt(3, itemID);
		stockStmt.setInt(4, itemEntity.getUnitsAvailable());

		for (String cat : itemEntity.getCategories()) {
			catStmt.setString(1, cat);
			catStmt.setInt(2, itemID);
			catStmt.addBatch();
		}

		userStmt.setInt(1, userID);
		userStmt.setInt(2, itemID);

		if (itemStmt.executeUpdate() != 1) {
			submitted = false;
		}
		if (stockStmt.executeUpdate() != 1) {
			submitted = false;
		} 
		if (userStmt.executeUpdate() != 1) {
			submitted = false;
		}
		catStmt.executeBatch();
		if (submitted) {
			conn.commit();
		} else {
			conn.rollback();
		}
		conn.setAutoCommit(true);
		conn.close();

		return submitted;
	}

	private int getNextItemSeqID(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet res = stmt.executeQuery(SQLQueries.ItemIDNextVal.query());
		if (res.next()) {
			return res.getInt(1);
		} else {
			logger.error("Could not retrieve next item id increment");
			throw new SQLException();
		}
	}
}
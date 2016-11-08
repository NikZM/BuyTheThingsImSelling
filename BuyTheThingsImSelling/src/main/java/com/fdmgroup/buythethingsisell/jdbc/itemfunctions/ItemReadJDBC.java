package com.fdmgroup.buythethingsisell.jdbc.itemfunctions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.fdmgroup.buythethingsisell.entities.ItemEntity;
import com.fdmgroup.buythethingsisell.entities.ItemsEntityWrapper;
import com.fdmgroup.buythethingsisell.jdbc.ConnectionFactory;
import com.fdmgroup.buythethingsisell.jdbc.SQLQueries;

public class ItemReadJDBC {

	final static Logger logger = Logger.getLogger(ItemReadJDBC.class);
	
	@Resource
	private ItemEntityBuilder itemEntityBuilder;
	@Resource
	private ConnectionFactory connectionFactory;
	
	public ItemEntity getItemByID(int id) {
		ItemEntity itemEntity = null;
		Connection conn = connectionFactory.getConnection();
		try {
			PreparedStatement prepStmt = conn.prepareStatement(SQLQueries.GetItem.query());
			prepStmt.setInt(1, id);
			ResultSet res = prepStmt.executeQuery();

			PreparedStatement prepStmtCat = conn.prepareStatement(SQLQueries.GetItemCategories.query());
			prepStmtCat.setInt(1, id);
			ResultSet resCat = prepStmtCat.executeQuery();

			PreparedStatement prepStmtRev = conn.prepareStatement(SQLQueries.GetItemReviews.query());
			prepStmtRev.setInt(1, id);
			ResultSet resRev = prepStmtRev.executeQuery();

			itemEntity = itemEntityBuilder.getItemByID(res, resCat, resRev);
		} catch (SQLException e) {
			logger.error("Could not retrieve item \"" + id + "\"");
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.fatal("Unable to close connection");
			}
		}
		return itemEntity;
	}

	public ItemsEntityWrapper getItemsByPage(int lowBound, int upperBound, int pageNumInh) {
		ItemsEntityWrapper iew = null;
		Connection conn = connectionFactory.getConnection();
		try {
			PreparedStatement prepStmt = conn.prepareStatement(SQLQueries.GetItemList.query());
			prepStmt.setInt(1, lowBound);
			prepStmt.setInt(2, upperBound);
			ResultSet res = prepStmt.executeQuery();
			iew = itemEntityBuilder.getItemsByPage(res);
			
			PreparedStatement pageNum = conn.prepareStatement(SQLQueries.GetPageCount.query());
			ResultSet pgNumCount = pageNum.executeQuery();
			pgNumCount.next();
			double a = pgNumCount.getDouble(1);
			int diff = upperBound - lowBound;
			iew.setPageNumber(pageNumInh);
			iew.setPageMax((int) Math.ceil(a/diff));
		} catch (SQLException e) {
			logger.error("Could not retrieve items between: " + lowBound + " & " + upperBound);
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
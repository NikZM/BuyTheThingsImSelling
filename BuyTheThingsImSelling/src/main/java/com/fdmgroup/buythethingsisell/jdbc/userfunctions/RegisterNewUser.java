package com.fdmgroup.buythethingsisell.jdbc.userfunctions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.fdmgroup.buythethingsisell.entities.User;
import com.fdmgroup.buythethingsisell.jdbc.ConnectionFactory;
import com.fdmgroup.buythethingsisell.jdbc.SQLQueries;

public class RegisterNewUser {
	
	final static Logger logger = Logger.getLogger(RegisterNewUser.class);
	
	@Resource
	private ConnectionFactory connectionFactory;
	
	public boolean registerNewUser(User user){
		boolean success = true;
		Connection conn = connectionFactory.getConnection();
		try {
			Statement userIDStmt = conn.createStatement();
			ResultSet userIDResSet = userIDStmt.executeQuery(SQLQueries.UserIDNextVal.query());
			userIDResSet.next();
			int userID = userIDResSet.getInt(1);
			user.setUserID(userID);
			conn.setAutoCommit(false);
			
			PreparedStatement newUserStmt = conn.prepareStatement(SQLQueries.AddUser.query());
			newUserStmt.setInt(1, user.getUserID());
			newUserStmt.setString(2, user.getUserName());
			newUserStmt.setString(3, user.getPasswordHashed());
			
			PreparedStatement userRoleOne = conn.prepareStatement(SQLQueries.AddRoles.query());
			userRoleOne.setInt(1, user.getUserID());
			userRoleOne.setInt(2, 1);
			
			PreparedStatement userExInfo = conn.prepareStatement(SQLQueries.AddUserExInfo.query());
			userExInfo.setInt(1, user.getUserID());
			userExInfo.setString(2, user.getPaypalAcc());
			
			int i = newUserStmt.executeUpdate();
			i += userRoleOne.executeUpdate();
			i += userExInfo.executeUpdate();
			if (i !=3){
				conn.rollback();
			} else {
				conn.commit();
			}
			conn.setAutoCommit(true);
		} catch (SQLException sqlEx){
			logger.error("Unable to register user: " + user.toString());
			success = false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.fatal("Unable to close connection");
			}
		}
		return success;
	}
}
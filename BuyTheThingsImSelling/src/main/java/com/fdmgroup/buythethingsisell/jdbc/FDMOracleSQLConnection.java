package com.fdmgroup.buythethingsisell.jdbc;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fdmgroup.buythethingsisell.entities.EntityFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class FDMOracleSQLConnection implements ConnectionFactory{
	
	final static Logger logger = Logger.getLogger(FDMOracleSQLConnection.class);
	private ComboPooledDataSource cpds;
	
	@Autowired
	public FDMOracleSQLConnection(EntityFactory entityFactory) throws PropertyVetoException {
			cpds = entityFactory.getComboPooledDataSource();
	        cpds.setDriverClass("oracle.jdbc.driver.OracleDriver");
	        cpds.setJdbcUrl("jdbc:oracle:thin:@oracle.fdmgroup.com:1521:xe");
	        cpds.setUser("nikolasmedgyesy");
	        cpds.setPassword(PasswordMask.getPassword());
	        
	        cpds.setMinPoolSize(0);
	        cpds.setAcquireIncrement(3);
	        cpds.setMaxPoolSize(20);
	        cpds.setMaxStatements(180);
	        cpds.setMaxIdleTime(600);
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = cpds.getConnection();
		} catch (SQLException e) {
			logger.error("Could not return database connection: " + e.getMessage());
		}
		return conn;
	}

}
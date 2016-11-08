package com.fdmgroup.buythethingsisell.jdbc;

import java.sql.Connection;

public interface ConnectionFactory {
	
	public Connection getConnection();

}

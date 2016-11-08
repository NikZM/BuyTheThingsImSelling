package com.fdmgroup.buythethingsisell.jdbc;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.buythethingsisell.entities.EntityFactory;

public class FDMOracleSQLConnectionTest {
	
	private EntityFactory entityFactory;
	private EntityFactory spyEntityFactory;
	private ConnectionFactory connectionFactory;
	
	@Before
	public void setup() throws PropertyVetoException, SQLException{
		entityFactory = new EntityFactory();
		spyEntityFactory = spy(entityFactory);
		connectionFactory = new FDMOracleSQLConnection(spyEntityFactory);
	}

	//Full Database Connection Test - Not Mocked (due to ComboPooledDataSourceBeingFinal)
	@Test
	public void test() throws PropertyVetoException, SQLException{
		Connection c = connectionFactory.getConnection();
		verify(spyEntityFactory).getComboPooledDataSource();
		assertTrue(!c.isClosed());
		c.close();
		assertTrue(c.isClosed());
	}
	


}

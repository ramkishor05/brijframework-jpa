package org.brijframework.jpa.factories;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.brijframework.jpa.EntitySchema;
import org.brijframework.jpa.factories.internal.EntityConfigFactoryImpl;
import org.brijframework.jpa.util.ConnectionUtil;

public class EntityConnectionFactory {
	
	private static EntityConnectionFactory factory;
	
	public static EntityConnectionFactory factory() {
		if(factory==null) {
			factory=new EntityConnectionFactory();
		}
		return factory;
	}

	public DataSource getDataSource(EntitySchema schema) {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(schema.getDatabase().getDriver());
		basicDataSource.setUrl(schema.getConnectionURL());
		if (ConnectionUtil.isOracle(schema.getDatabase())) {
			basicDataSource.setUsername(schema.getCatalog());
		} 
		if(ConnectionUtil.isPostgres(schema.getDatabase())) {
			basicDataSource.setUsername(schema.getUsername());
		}
		if (ConnectionUtil.isMySql(schema.getDatabase())) {
			basicDataSource.setUsername(schema.getUsername());
		}
		basicDataSource.setPassword(schema.getPassword());
		basicDataSource.setInitialSize(schema.getInitialPoolSize());
		basicDataSource.setMaxIdle(schema.getMaxIdlePoolSize());
		basicDataSource.setMinIdle(schema.getMinIdlePoolSize());
		basicDataSource.setTimeBetweenEvictionRunsMillis(schema.getTimeBetweenEvictionRunsMillis());
		return basicDataSource;
	}
	
	public Connection getConnection(EntitySchema schema) throws SQLException {
		DataSource dataSource=getDataSource(schema);
		return dataSource.getConnection();
	}
	
	public Connection getConnection(String schemaID) throws SQLException {
		EntitySchema schema=EntityConfigFactoryImpl.getFactory().find(schemaID);
		if(schema==null) {
			System.err.println("EntitySchema not found ");
		}
		DataSource dataSource=getDataSource(schema);
		return dataSource.getConnection();
	}
	
}

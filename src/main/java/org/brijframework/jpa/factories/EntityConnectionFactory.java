package org.brijframework.jpa.factories;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.brijframework.jpa.EntitySchema;
import org.brijframework.jpa.util.ConnectionUtil;

public class EntityConnectionFactory {
	private static EntityConnectionFactory factory;
    public int initialPoolSize = 1; 
	public int maxPoolSize = 4; 
	public int maxIdlePoolSize = 2; 
	public int minIdlePoolSize = 1; 
	public long timeBetweenEvictionRunsMillis = 60000;
	
	public static EntityConnectionFactory factory() {
		if(factory==null) {
			factory=new EntityConnectionFactory();
		}
		return factory;
	}

	public DataSource getDataSource(EntitySchema schema) {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(schema.database.getDriver());
		basicDataSource.setUrl(schema.getConnectionURL());
		if (ConnectionUtil.isOracle(schema.database)) {
			basicDataSource.setUsername(schema.catalog);
		} 
		if(ConnectionUtil.isPostgres(schema.database)) {
			basicDataSource.setUsername(schema.database.getUsername());
		}
		if (ConnectionUtil.isMySql(schema.database)) {
			basicDataSource.setUsername(schema.database.getUsername());
		}
		basicDataSource.setPassword(schema.password);
		basicDataSource.setInitialSize(this.initialPoolSize);
		basicDataSource.setMaxIdle(this.maxIdlePoolSize);
		basicDataSource.setMinIdle(this.minIdlePoolSize);
		basicDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		return basicDataSource;
	}
	
	public Connection getConnection(EntitySchema schema) throws SQLException {
		DataSource dataSource=getDataSource(schema);
		return dataSource.getConnection();
	}
	
}

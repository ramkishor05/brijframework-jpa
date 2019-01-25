package org.brijframework.jpa;

import java.util.ArrayList;
import java.util.List;

import org.brijframework.jpa.util.ConnectionUtil;

public class EntitySchema {
	public String id;
	
	public String catalog;
	
	public String username;
	
	public String password;
	
	public String packages;

	public EntityDatabase database;

	public String persistenceUnit;

	public List<Class<?>> entities = new ArrayList<>();
	
	public String getConnectionURL() {
		String conString = null;
		if (ConnectionUtil.isMySql(database)) {
			conString = ConnectionUtil.getMySqlURL(database) + this.catalog;
		} else if (ConnectionUtil.isOracle(this.database)) {
			conString = ConnectionUtil.getOracleURL(database);
		} else if (ConnectionUtil.isPostgres(this.database)) {
			conString = ConnectionUtil.getPostgresSqlURL(database) + this.catalog;
		} else {
			throw new RuntimeException("Unsupported Database Vendor configured for " + this.catalog);
		}
		if (ConnectionUtil.isMySql(this.database) || ConnectionUtil.isPostgres(this.database)) {
			conString += "?useUnicode=yes&characterEncoding=UTF-8";
		}
		return conString;
	}
	
}

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPackages() {
		return packages;
	}

	public void setPackages(String packages) {
		this.packages = packages;
	}

	public EntityDatabase getDatabase() {
		return database;
	}

	public void setDatabase(EntityDatabase database) {
		this.database = database;
	}

	public String getPersistenceUnit() {
		return persistenceUnit;
	}

	public void setPersistenceUnit(String persistenceUnit) {
		this.persistenceUnit = persistenceUnit;
	}

	public List<Class<?>> getEntities() {
		return entities;
	}

	public void setEntities(List<Class<?>> entities) {
		this.entities = entities;
	}

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

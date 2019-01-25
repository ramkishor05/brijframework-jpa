package org.brijframework.jpa.util;

import org.brijframework.jpa.EntityDatabase;

public class ConnectionUtil {
	public static String getOracleURL(EntityDatabase database) {
		StringBuffer connectionString = new StringBuffer();
		connectionString.append("jdbc:oracle:thin:");
		connectionString.append("@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=" + database.getNetwork().getHost());
		connectionString.append(")(PORT=" + database.getNetwork().getPort());
		connectionString.append(")))(CONNECT_DATA=(SERVICE_NAME=" + database.getSid());
		connectionString.append(")(SERVER=DEDICATED)))");
		return connectionString.toString();
	}

	public static String getMySqlURL(EntityDatabase database) {
		return "jdbc:mysql://" + database.getNetwork().getHost() + ":" + database.getNetwork().getPort() + "/";
	}

	public static String getPostgresSqlURL(EntityDatabase database) {
		return "jdbc:postgresql://" + database.getNetwork().getHost() + ":" + database.getNetwork().getPort() + "/";
	}

	public static boolean isMySql(EntityDatabase database) {
		return database.getVendor() != null && database.getVendor().equalsIgnoreCase("MySql");
	}

	public static boolean isOracle(EntityDatabase database) {
		return database.getVendor() != null && database.getVendor().equalsIgnoreCase("Oracle");
	}

	public static boolean isPostgres(EntityDatabase database) {
		return database.getVendor() != null && database.getVendor().equalsIgnoreCase("PostgreSQL");
	}
}

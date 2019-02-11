package org.brijframework.jpa.processor.internal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.brijframework.jpa.builder.NamedParameterStatement;
import org.brijframework.jpa.factories.EntityConnectionFactory;
import org.brijframework.jpa.factories.EntityParameterFactory;
import org.brijframework.jpa.factories.EntityPropertiesFactory;

public class EntityQueryManager {

	public List<Map<String, Object>> fatch(String schema, String namedQuery) throws SQLException {
		return fatch(schema, namedQuery,EntityParameterFactory.getFactory().find(namedQuery));
	}

	public List<Map<String, Object>> fatch(String schema, String namedQuery, Map<String, Object> paramters)
			throws SQLException {
		NamedParameterStatement statement = getStatement(schema, namedQuery, paramters);
		ResultSet result = statement.executeQuery();
		return fatch(result, statement);
	}

	public List<Map<String, Object>> fatch(ResultSet result, NamedParameterStatement statement) throws SQLException {
		int columnCount = statement.getStatement().getMetaData().getColumnCount();
		Map<String, Integer> columns = new HashMap<>();
		for (int column = 1; column <= columnCount; column++) {
			columns.put(statement.getStatement().getMetaData().getColumnName(column), column);
		}
		List<Map<String, Object>> table = new ArrayList<>();
		while (result.next()) {
			Map<String, Object> row = new HashMap<>();
			columns.forEach((key, columnIndex) -> {
				try {
					row.put(key, result.getObject(columnIndex));
				} catch (SQLException e) {
				}
			});
			table.add(row);
		}
		return table;
	}

	private NamedParameterStatement getStatement(String schema, String namedQuery, Map<String, Object> paramters)
			throws SQLException {
		Connection connection = EntityConnectionFactory.factory().getConnection(schema);
		String query = EntityPropertiesFactory.getFactory().find(namedQuery);
		NamedParameterStatement statement = new NamedParameterStatement(connection, query);
		paramters.forEach((name, value) -> {
			try {
				statement.setObject(name, value);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		return statement;
	}
	
	public int update(String schema, String namedQuery, Map<String, Object> paramters) throws SQLException {
		NamedParameterStatement statement = getStatement(schema, namedQuery, paramters);
		int count = statement.executeUpdate();
		return count;
	}

	public int update(String schema, String namedQuery) throws SQLException {
		return update(schema, namedQuery, EntityParameterFactory.getFactory().find(namedQuery));
	}
}

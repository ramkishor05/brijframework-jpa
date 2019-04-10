package org.brijframework.jpa.processor.internal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.brijframework.jpa.builder.NamedStatement;
import org.brijframework.jpa.factories.EntityConnectionFactory;
import org.brijframework.jpa.factories.EntityQueryFactory;
import org.brijframework.jpa.files.EntityQuery;
import org.brijframework.jpa.util.EntityDataBuilder;
import org.brijframework.jpa.util.EntityMapper;

public class EntityQueryManager {
	/**
	 * Getting connection for schema
	 * 
	 * @param schema
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection(String schema) throws SQLException {
		return EntityConnectionFactory.factory().getConnection(schema);
	}

	/**
	 * Getting named statement for named query
	 * 
	 * @param connection
	 * @param namedQuery
	 * @return
	 * @throws SQLException
	 */
	private NamedStatement getStatement(Connection connection, String namedQuery) throws SQLException {
		return new NamedStatement(connection, namedQuery);
	}

	/**
	 * 
	 * @param schema
	 * @param queryId
	 * @param cls
	 * @return
	 * @throws SQLException
	 */
	public List<? extends Object> fatch(String schema, String queryId, Class<?> cls) throws SQLException {
		EntityQuery entityQuery = EntityQueryFactory.getFactory().find(queryId);
		List<Map<String, Object>> fatchList = fatch(schema, entityQuery);
		if (fatchList == null || entityQuery.getMapperTo() == null) {
			return null;
		}
		return buildList(fatchList, cls);
	}

	/***
	 * 
	 * @param schema
	 * @param entityQuery
	 * @return
	 * @throws SQLException
	 */
	private List<Map<String, Object>> fatch(String schema, EntityQuery entityQuery) throws SQLException {
		if (entityQuery == null) {
			System.err.println("Entity named query not found ");
			return null;
		}
		return fatchList(schema, entityQuery.getQuery(), entityQuery.getParameters(), entityQuery.getMapperTo());
	}

	/***
	 * 
	 * @param schema
	 * @param namedQuery
	 * @param paramters
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> fatchList(String schema, String namedQuery, Map<String, Object> paramters)
			throws SQLException {
		try (Connection connection = getConnection(schema)) {
			if (connection == null) {
				System.err.println("Connection not found due to schema not config.");
				return null;
			}
			NamedStatement statement = getStatement(connection, namedQuery).setParamters(paramters);
			return statement.fatch(statement.executeQuery());
		}
	}

	/***
	 * 
	 * @param schema
	 * @param namedQuery
	 * @param paramters
	 * @param mapper
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> fatchList(String schema, String namedQuery, Map<String, Object> paramters,
			Map<String, String> mapper) throws SQLException {
		try (Connection connection = getConnection(schema)) {
			if (connection == null) {
				System.err.println("Connection not found due to schema not config.");
				return null;
			}
			NamedStatement statement = getStatement(connection, namedQuery).setParamters(paramters);
			List<Map<String, Object>> list = statement.fatch(statement.executeQuery());
			if (mapper == null) {
				return list;
			}
			return EntityMapper.listMapper(list, mapper);
		}

	}

	/***
	 * 
	 * @param fatchList
	 * @param cls
	 * @return
	 */
	public List<?> buildList(List<Map<String, Object>> fatchList, Class<?> cls) {
		List<Object> buildList = new ArrayList<>();
		fatchList.forEach(rowMap -> {
			buildList.add(EntityDataBuilder.buildObject(rowMap, cls));
		});
		return buildList;
	}

	/***
	 * 
	 * @param schema
	 * @param queryId
	 * @return
	 * @throws SQLException
	 */
	public int update(String schema, String queryId) throws SQLException {
		try (Connection connection = getConnection(schema)) {
			if (connection == null) {
				System.err.println("Connection not found due to schema not config.");
				return 0;
			}
			EntityQuery entityQuery = EntityQueryFactory.getFactory().find(queryId);
			if (entityQuery == null) {
				System.err.println("Entity named query not found.");
				return 0;
			}
			return update(connection, entityQuery);
		}
	}

	/***
	 * 
	 * @param connection
	 * @param entityQuery
	 * @return
	 * @throws SQLException
	 */
	private int update(Connection connection, EntityQuery entityQuery) throws SQLException {
		if (connection == null) {
			System.err.println("Connection not found due to schema not config.");
			return 0;
		}
		if (entityQuery == null) {
			System.err.println("Entity named query not found.");
			return 0;
		}
		return update(connection, entityQuery.getQuery(), entityQuery.getParameters());
	}

	/***
	 * 
	 * @param schema
	 * @param queryId
	 * @param paramters
	 * @return
	 * @throws SQLException
	 */
	public int update(String schema, String queryId, Map<String, Object> paramters) throws SQLException {
		try (Connection connection = getConnection(schema)) {
			if (connection == null) {
				System.err.println("Connection not found due to schema not config.");
				return 0;
			}
			return update(connection, queryId, paramters);
		}
	}

	/***
	 * 
	 * @param connection
	 * @param entityQuery
	 * @param paramters
	 * @return
	 * @throws SQLException
	 */
	public int update(Connection connection, EntityQuery entityQuery, Map<String, Object> paramters)
			throws SQLException {
		if (entityQuery == null) {
			System.err.println("Entity named query not found.");
			return 0;
		}
		NamedStatement statement = getStatement(connection, entityQuery.getQuery()).setParamters(paramters);
		return statement.executeUpdate();
	}

	/***
	 * 
	 * @param connection
	 * @param queryId
	 * @param paramters
	 * @return
	 * @throws SQLException
	 */
	public int update(Connection connection, String queryId, Map<String, Object> paramters) throws SQLException {
		if (queryId == null) {
			System.err.println("Entity named query not found.");
			return 0;
		}
		EntityQuery entityQuery = EntityQueryFactory.getFactory().find(queryId);
		if (entityQuery == null) {
			System.err.println("Entity named query not found.");
			return 0;
		}
		return update(connection, entityQuery, paramters);
	}
}

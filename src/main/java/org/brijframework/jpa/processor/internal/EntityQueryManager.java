package org.brijframework.jpa.processor.internal;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.brijframework.jpa.builder.NamedStatement;
import org.brijframework.jpa.factories.EntityConnectionFactory;
import org.brijframework.jpa.factories.EntityQueryFactory;
import org.brijframework.jpa.files.EntityQuery;
import org.brijframework.support.enums.Access;
import org.brijframework.util.accessor.MetaAccessorUtil;
import org.brijframework.util.accessor.PropertyAccessorUtil;
import org.brijframework.util.reflect.InstanceUtil;
import org.brijframework.util.reflect.ReflectionUtils;

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

	private List<Map<String, Object>> fatch(String schema, EntityQuery entityQuery) throws SQLException {
		if (entityQuery == null) {
			System.err.println("Entity named query not found ");
			return null;
		}
		return fatchList(schema, entityQuery.getQuery(), entityQuery.getParameters(), entityQuery.getMapperTo());
	}

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
			return listMapper(list, mapper);
		}

	}

	private List<Map<String, Object>> listMapper(List<Map<String, Object>> list, Map<String, String> mapper) {
		List<Map<String, Object>> listMapper = new ArrayList<>();
		for (Map<String, Object> map : list) {
			listMapper.add(getMapped(map, mapper));
		}
		return listMapper;
	}

	private Map<String, Object> getMapped(Map<String, Object> row, Map<String, String> mapper) {
		Map<String, Object> rowMap = new HashMap<>();
		row.forEach((key, value) -> {
			String mapKey = mapper.get(key);
			if (mapKey != null) {
				rowMap.put(mapKey, value);
			} else {
				rowMap.put(key, value);
			}
		});
		return rowMap;
	}

	public List<?> buildList(List<Map<String, Object>> fatchList, Class<?> cls) {
		List<Object> buildList = new ArrayList<>();
		fatchList.forEach(rowMap -> {
			buildList.add(buildObject(rowMap, cls));
		});
		return buildList;
	}

	public Object buildObject(Map<String, Object> rowMap, Class<?> cls) {
		Object entity = InstanceUtil.getInstance(cls);
		Set<String> unmapped = new HashSet<>();
		rowMap.forEach((key, value) -> {
			Field colling = MetaAccessorUtil.getFieldMeta(entity.getClass(), key, Access.PRIVATE);
			if (colling != null) {
				if (ReflectionUtils.isProjectClass(colling.getType())) {

				}
				PropertyAccessorUtil.setProperty(entity, colling, value);
			} else {
				unmapped.add(key);
			}
		});
		System.err.println("Invalid unmapped keys " + unmapped);
		return entity;
	}
	
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

	public int update(String schema, String queryId, Map<String, Object> paramters) throws SQLException {
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
			return update(connection, entityQuery, paramters);
		}
	}
	
	public int update(Connection connection , EntityQuery entityQuery, Map<String, Object> paramters) throws SQLException {
		if (entityQuery == null) {
			System.err.println("Entity named query not found.");
			return 0;
		}
		NamedStatement statement = getStatement(connection, entityQuery.getQuery()).setParamters(paramters);
		return statement.executeUpdate();
	}
	
	public int update(Connection connection , String entityQuery, Map<String, Object> paramters) throws SQLException {
		if (entityQuery == null) {
			System.err.println("Entity named query not found.");
			return 0;
		}
		NamedStatement statement = getStatement(connection, entityQuery).setParamters(paramters);
		return statement.executeUpdate();
	}
}

package org.brijframework.jpa.files;

import java.util.Map;

public class EntityQuery {
	private String id;
	private String query;
	private Map<String, Object> parameters;

	private Map<String, String> mapperTo;

	private Map<String, String> mapperBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public Map<String, String> getMapperTo() {
		return mapperTo;
	}

	public void setMapperTo(Map<String, String> mapperTo) {
		this.mapperTo = mapperTo;
	}

	public Map<String, String> getMapperBy() {
		return mapperBy;
	}

	public void setMapperBy(Map<String, String> mapperBy) {
		this.mapperBy = mapperBy;
	}

}

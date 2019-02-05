package org.brijframework.jpa.files;

import java.util.Map;

public class ModelData implements JsonData{
	private String id;
	private String type;
	private Map<String, Object> properties;

	@Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public Map<String, Object> getProperties() {
		return properties;
	}
	@Override
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

}

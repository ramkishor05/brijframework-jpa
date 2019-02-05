package org.brijframework.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityProvider {
	
	public String id;
	public String providerName;
	public String connectionURL;
	
	public Map<String, String> properties=new HashMap<String, String>();
	
	public List<EntitySchema> schemas=new  ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getConnectionURL() {
		return connectionURL;
	}

	public void setConnectionURL(String connectionURL) {
		this.connectionURL = connectionURL;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public List<EntitySchema> getSchemas() {
		return schemas;
	}

	public void setSchemas(List<EntitySchema> schemas) {
		this.schemas = schemas;
	}
	
	
}

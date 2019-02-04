package org.brijframework.jpa;

import java.util.HashMap;
import java.util.Map;

public class EntityConfigration {
	
	private String id;
	
	public Map<String, EntityProvider> providers=new HashMap<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, EntityProvider> getProviders() {
		return providers;
	}

	public void setProviders(Map<String, EntityProvider> providers) {
		this.providers = providers;
	}
}

package org.brijframework.jpa.factories;

import java.util.LinkedHashMap;

import org.brijframework.jpa.EntityConfigration;

public class EntityConfigFactory {
	
	private LinkedHashMap<String, EntityConfigration> cache = new LinkedHashMap<>();
	
	public void register(EntityConfigration model) {
		getCache().put(model.getId(), model);
	}
	
	public EntityConfigration find(String key) {
		return getCache().get(key);
	}
	
	public LinkedHashMap<String, EntityConfigration> getCache() {
		return cache;
	}
}

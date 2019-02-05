package org.brijframework.jpa.factories;

import java.util.LinkedHashMap;

import org.brijframework.jpa.EntityConfigration;
import org.brijframework.jpa.context.EntityContext;

public class EntityConfigFactory {
	
	private LinkedHashMap<String, EntityConfigration> cache = new LinkedHashMap<>();
	
	private static EntityConfigFactory factory;
	
	private EntityContext entityContext;
	
	public static EntityConfigFactory getFactory() {
		if (factory == null) {
			factory = new EntityConfigFactory();
		}
		return factory;
	}

	public void setContext(EntityContext entityContext) {
		this.entityContext=entityContext;
	}
	
	public EntityContext getContext() {
		return entityContext;
	}

	
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

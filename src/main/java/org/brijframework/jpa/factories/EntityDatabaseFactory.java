package org.brijframework.jpa.factories;

import java.util.LinkedHashMap;

import org.brijframework.jpa.EntityDatabase;
import org.brijframework.jpa.builder.DataBuilder;
import org.brijframework.jpa.context.EntityContext;
import org.brijframework.jpa.factories.files.JsonFileDataFactory;

public class EntityDatabaseFactory {
	LinkedHashMap<String, EntityDatabase> cache = new LinkedHashMap<>();
	
	private static EntityDatabaseFactory factory;
	
	private EntityContext entityContext;
	
	public static EntityDatabaseFactory getFactory() {
		if (factory == null) {
			factory = new EntityDatabaseFactory();
			factory.loadFactory();
		}
		return factory;
	}
	
	public void loadFactory() {
		JsonFileDataFactory.getFactory().forType(EntityDatabase.class.getName(), database->{
			try {
				EntityDatabase databaseModel=DataBuilder.getDataObject(database);
				register(databaseModel);
			}catch (Exception e) {
			}
		});
	}

	public void setContext(EntityContext entityContext) {
		this.entityContext=entityContext;
	}
	
	public EntityContext getContext() {
		return entityContext;
	}

	
	public void register(EntityDatabase model) {
		getCache().put(model.getId(), model);
	}
	
	public EntityDatabase find(String key) {
		return getCache().get(key);
	}
	
	public LinkedHashMap<String, EntityDatabase> getCache() {
		return cache;
	}
}

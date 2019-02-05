package org.brijframework.jpa.factories;

import java.util.LinkedHashMap;

import org.brijframework.jpa.EntitySchema;
import org.brijframework.jpa.builder.DataBuilder;
import org.brijframework.jpa.context.EntityContext;
import org.brijframework.jpa.factories.files.JsonFileDataFactory;

public class EntitySchemaFactory {
	LinkedHashMap<String, EntitySchema> cache = new LinkedHashMap<>();
	
	private static EntitySchemaFactory factory;
	
	private EntityContext entityContext;
	
	public static EntitySchemaFactory getFactory() {
		if (factory == null) {
			factory = new EntitySchemaFactory();
			factory.loadFactory();
		}
		return factory;
	}
	
	public void loadFactory() {
		JsonFileDataFactory.getFactory().forType(EntitySchema.class.getName(), schema->{
			try {
				EntitySchema schemaModel=DataBuilder.getDataObject(schema);
				register(schemaModel);
			}catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public void setContext(EntityContext entityContext) {
		this.entityContext=entityContext;
	}
	
	public EntityContext getContext() {
		return entityContext;
	}

	
	public void register(EntitySchema model) {
		getCache().put(model.getId(), model);
	}
	
	public EntitySchema find(String key) {
		return getCache().get(key);
	}
	
	public LinkedHashMap<String, EntitySchema> getCache() {
		return cache;
	}
}

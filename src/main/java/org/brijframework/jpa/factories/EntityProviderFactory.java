package org.brijframework.jpa.factories;

import java.util.LinkedHashMap;

import org.brijframework.jpa.EntityProvider;
import org.brijframework.jpa.builder.DataBuilder;
import org.brijframework.jpa.context.EntityContext;
import org.brijframework.jpa.factories.files.JsonFileDataFactory;

public class EntityProviderFactory {
	LinkedHashMap<String, EntityProvider> cache = new LinkedHashMap<>();
	
	private static EntityProviderFactory factory;
	
	private EntityContext entityContext;
	
	public static EntityProviderFactory getFactory() {
		if (factory == null) {
			factory = new EntityProviderFactory();
			factory.loadFactory();
		}
		return factory;
	}
	
	public void loadFactory() {
		JsonFileDataFactory.getFactory().forType(EntityProvider.class.getName(), schema->{
			try {
				EntityProvider providerModel=DataBuilder.getDataObject(schema);
				register(providerModel);
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

	
	public void register(EntityProvider model) {
		getCache().put(model.getId(), model);
	}
	
	public EntityProvider find(String key) {
		return getCache().get(key);
	}
	
	public LinkedHashMap<String, EntityProvider> getCache() {
		return cache;
	}
}

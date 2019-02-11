package org.brijframework.jpa.context;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.brijframework.jpa.EntitySchema;
import org.brijframework.jpa.container.EntityDataContainer;
import org.brijframework.jpa.container.EntityModelContainer;
import org.brijframework.jpa.factories.EntityConfigFactory;
import org.brijframework.jpa.factories.EntityDataFactory;
import org.brijframework.jpa.factories.EntityModelFactory;
import org.brijframework.jpa.factories.EntityParameterFactory;
import org.brijframework.jpa.factories.EntityPropertiesFactory;
import org.brijframework.jpa.factories.files.JsonDataEntityFactory;
import org.brijframework.jpa.factories.internal.AnnoEntityModelFactory;
import org.brijframework.jpa.factories.internal.EntityConfigFactoryImpl;

public class EntityContext {
  
	private Properties properties=new Properties();
	
	public  ConcurrentHashMap<String, EntitySchema> schemaMap = new ConcurrentHashMap<>();
	
	public  ConcurrentHashMap<String, Class<?>> adpterMap = new ConcurrentHashMap<>();
	
	public void start() {
		EntityConfigFactory	configFactory=EntityConfigFactoryImpl.getFactory();
		configFactory.setContext(this);
		configFactory.loadFactory();
		
		EntityPropertiesFactory propertiesFactory=EntityPropertiesFactory.getFactory();
		propertiesFactory.setEntityContext(this);
		propertiesFactory.loadFactory();
		
		EntityParameterFactory parameterFactory=EntityParameterFactory.getFactory();
		parameterFactory.setEntityContext(this);
		parameterFactory.loadFactory();
		
		EntityModelContainer entityModelContainer=EntityModelContainer.getContainer(); 
		entityModelContainer.setContext(this);
		
		EntityModelFactory entityModelFactory=EntityModelFactory.getFactory();
		entityModelFactory.setContainer(entityModelContainer);
		entityModelFactory.loadFactory();
		
		EntityModelFactory annoEntityDataFactory=AnnoEntityModelFactory.getFactory();
		annoEntityDataFactory.setContext(this);
		annoEntityDataFactory.setContainer(entityModelContainer);
		annoEntityDataFactory.loadFactory();
		
		entityModelContainer.build();
		entityModelContainer.procced();
		
		EntityDataContainer entityDataContainer=EntityDataContainer.getContainer();
		entityDataContainer.setContext(this);
		
		EntityDataFactory entityDataFactory=EntityDataFactory.getFactory();
		entityDataFactory.setContext(this);
		entityDataFactory.setContainer(entityDataContainer);
		entityDataFactory.loadFactory();
		
		EntityDataFactory jsonEntityDataFactory=JsonDataEntityFactory.getFactory();
		jsonEntityDataFactory.setContext(this);
		jsonEntityDataFactory.setContainer(entityDataContainer);
		jsonEntityDataFactory.loadFactory();
		
		entityDataContainer.build();
		entityDataContainer.procced();
	}
	
	public void stop() {
		EntityDataContainer entityDataContainer=EntityDataContainer.getContainer();
		entityDataContainer.getCache().clear();
		
		EntityDataFactory entityDataFactory=EntityDataFactory.getFactory();
		entityDataFactory.getCache().clear();
		
		EntityDataFactory jsonEntityDataFactory=JsonDataEntityFactory.getFactory();
		jsonEntityDataFactory.getCache().clear();
	}
	
	public void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}
	
	public void setObject(String key, Object value) {
		properties.put(key, value);
	}
	
	public Object getObject(String key) {
		return properties.get(key);
	}
	
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	public void setAdpter(String key, Class<?> adpter) {
		adpterMap.put(key, adpter);
	}
	
	public Class<?> getAdpter(String key) {
		return adpterMap.get(key);
	}

	public ConcurrentHashMap<String, Class<?>> getAdpterMap() {
		return adpterMap;
	}
}

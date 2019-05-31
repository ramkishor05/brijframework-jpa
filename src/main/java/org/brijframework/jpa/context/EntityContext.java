package org.brijframework.jpa.context;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.brijframework.jpa.EntitySchema;
import org.brijframework.jpa.container.EntityDataContainer;
import org.brijframework.jpa.container.EntityMetaContainer;
import org.brijframework.jpa.factories.EntityConfigFactory;
import org.brijframework.jpa.factories.EntityMapperFactory;
import org.brijframework.jpa.factories.EntityMetaFactory;
import org.brijframework.jpa.factories.EntityPropertiesFactory;
import org.brijframework.jpa.factories.EntityQueryFactory;
import org.brijframework.jpa.factories.data.EntityDataFactory;
import org.brijframework.jpa.factories.data.json.JsonEntityDataFactory;
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
		
		EntityMapperFactory mapperFactory=EntityMapperFactory.getFactory();
		mapperFactory.setEntityContext(this);
		mapperFactory.loadFactory();
		
		EntityQueryFactory parameterFactory=EntityQueryFactory.getFactory();
		parameterFactory.setEntityContext(this);
		parameterFactory.loadFactory();
		
		EntityMetaContainer entityModelContainer=EntityMetaContainer.getContainer(); 
		entityModelContainer.setContext(this);
		
		EntityMetaFactory entityModelFactory=EntityMetaFactory.getFactory();
		entityModelFactory.setContainer(entityModelContainer);
		entityModelFactory.loadFactory();
		
		EntityMetaFactory annoEntityDataFactory=AnnoEntityModelFactory.getFactory();
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
		
		EntityDataFactory jsonEntityDataFactory=JsonEntityDataFactory.getFactory();
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
		
		EntityDataFactory jsonEntityDataFactory=JsonEntityDataFactory.getFactory();
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

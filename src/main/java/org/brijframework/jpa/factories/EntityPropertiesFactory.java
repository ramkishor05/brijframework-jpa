package org.brijframework.jpa.factories;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Properties;

import org.brijframework.jpa.container.EntityMetaContainer;
import org.brijframework.jpa.context.EntityContext;
import org.brijframework.jpa.util.EntityConstants;

public class EntityPropertiesFactory {
	private static EntityPropertiesFactory factory;
	
	private LinkedHashMap<Object,Object> cache = new LinkedHashMap<>();
	
	private EntityMetaContainer container ;

	private EntityContext entityContext;

	public static EntityPropertiesFactory getFactory() {
		if (factory == null) {
			factory = new EntityPropertiesFactory();
		}
		return factory;
	}

	public void loadFactory() {
		String statement=getEntityContext().getProperty(EntityConstants.IMPORT_PROPERTIES_STATEMENT);
		if(statement==null) {
			System.err.println("statement config not found : "+EntityConstants.IMPORT_PROPERTIES_STATEMENT);
			return ;
		}
		File statementPath=new File(statement);
		if(!statementPath.exists()) {
			System.err.println("statement path not found : "+statementPath);
			return ;
		}
		loadDir(statementPath);
	}
	
	public void loadDir(File file) {
		if(file.isDirectory()) {
			for(File sub:file.listFiles()) {
				loadDir(sub);
			}
		}else {
			loadFile(file);
		}
	}
	
	public void loadFile(File file) {
		if(!file.getName().endsWith(".properties")) {
			return;
		}
		try(InputStream inputStream=new FileInputStream(file)) {
			Properties properties=new Properties();
			properties.load(inputStream);
			cache.putAll(properties);
		} catch (Exception e) {
		}
	}


	public void setEntityContext(EntityContext entityContext) {
		this.entityContext = entityContext;
	}
	
	public EntityContext getEntityContext() {
		return entityContext;
	}
	
	public void setContainer(EntityMetaContainer container) {
		this.container = container;
	}
	
	public EntityMetaContainer getContainer() {
		return container;
	}
	
	public LinkedHashMap<Object,Object> getCache() {
		return cache;
	}

	public String find(String key) {
		return String.valueOf(getCache().get(key));
	}
}

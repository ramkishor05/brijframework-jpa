package org.brijframework.jpa.factories;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.brijframework.jpa.container.EntityModelContainer;
import org.brijframework.jpa.context.EntityContext;
import org.brijframework.jpa.util.EntityConstants;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class EntityParameterFactory {
	private static EntityParameterFactory factory;

	private LinkedHashMap<Object, Map<String,Object>> cache = new LinkedHashMap<>();

	private EntityModelContainer container;

	private EntityContext entityContext;

	public static EntityParameterFactory getFactory() {
		if (factory == null) {
			factory = new EntityParameterFactory();
		}
		return factory;
	}

	public void loadFactory() {
		String statement = getEntityContext().getProperty(EntityConstants.IMPORT_PROPERTIES_STATEMENT);
		if (statement == null) {
			System.err.println("statement config not found : " + EntityConstants.IMPORT_PROPERTIES_STATEMENT);
			return;
		}
		File statementPath = new File(statement);
		if (!statementPath.exists()) {
			System.err.println("statement path not found : " + statementPath);
			return;
		}
		loadDir(statementPath);
	}

	public void loadDir(File file) {
		if (file.isDirectory()) {
			for (File sub : file.listFiles()) {
				loadDir(sub);
			}
		} else {
			loadFile(file);
		}
	}

	public void loadFile(File file) {
		if (!file.getName().endsWith(".json")) {
			return;
		}
		try (InputStream src = new FileInputStream(file)) {
			ObjectMapper mapper=new ObjectMapper();
			MapLikeType typeRef =TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, HashMap.class);
			Map<Object, Map<String,Object>> properties=mapper.readValue(src, typeRef);
			cache.putAll(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setEntityContext(EntityContext entityContext) {
		this.entityContext = entityContext;
	}

	public EntityContext getEntityContext() {
		return entityContext;
	}

	public void setContainer(EntityModelContainer container) {
		this.container = container;
	}

	public EntityModelContainer getContainer() {
		return container;
	}

	public LinkedHashMap<Object, Map<String,Object>> getCache() {
		return cache;
	}

	public Map<String, Object> find(String key) {
		return getCache().get(key);
	}
}

package org.brijframework.jpa.factories;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.brijframework.jpa.container.EntityMetaContainer;
import org.brijframework.jpa.context.EntityContext;
import org.brijframework.jpa.util.EntityConstants;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class EntityMapperFactory {
	private static EntityMapperFactory factory;

	private LinkedHashMap<String, Map<String,String>> cache = new LinkedHashMap<>();

	private EntityMetaContainer container;

	private EntityContext entityContext;

	public static EntityMapperFactory getFactory() {
		if (factory == null) {
			factory = new EntityMapperFactory();
		}
		return factory;
	}

	public void loadFactory() {
		String mapper = getEntityContext().getProperty(EntityConstants.IMPORT_PROPERTIES_MAPPER);
		if (mapper == null) {
			System.err.println("mapper config not found : " + EntityConstants.IMPORT_PROPERTIES_MAPPER);
			return;
		}
		File mapperPath = new File(mapper);
		if (!mapperPath.exists()) {
			System.err.println("mapper path not found : " + mapperPath);
			return;
		}
		loadDir(mapperPath);
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
			Map<String, Map<String,String>> properties=mapper.readValue(src, typeRef);
			getCache().putAll(properties);
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

	public void setContainer(EntityMetaContainer container) {
		this.container = container;
	}

	public EntityMetaContainer getContainer() {
		return container;
	}

	public LinkedHashMap<String, Map<String,String>> getCache() {
		return cache;
	}

	public Map<String, String> find(String key) {
		return getCache().get(key);
	}
}

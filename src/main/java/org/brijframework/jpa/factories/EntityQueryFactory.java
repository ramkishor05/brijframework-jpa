package org.brijframework.jpa.factories;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.brijframework.jpa.container.EntityMetaContainer;
import org.brijframework.jpa.context.EntityContext;
import org.brijframework.jpa.files.EntityQuery;
import org.brijframework.jpa.util.EntityConstants;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class EntityQueryFactory {
	private static EntityQueryFactory factory;

	private LinkedHashMap<Object, EntityQuery> cache = new LinkedHashMap<>();

	private EntityMetaContainer container;

	private EntityContext entityContext;

	public static EntityQueryFactory getFactory() {
		if (factory == null) {
			factory = new EntityQueryFactory();
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
		if (file.getName().endsWith(".json")) {
			try (InputStream src = new FileInputStream(file)) {
				ObjectMapper mapper=new ObjectMapper();
				CollectionLikeType typeRef =TypeFactory.defaultInstance().constructCollectionLikeType(ArrayList.class, EntityQuery.class);
				ArrayList<EntityQuery> list=mapper.readValue(src, typeRef);
				list.forEach(query->{
					getCache().put(query.getId(), query);
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (file.getName().endsWith(".properties")) {
			
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

	public LinkedHashMap<Object, EntityQuery> getCache() {
		return cache;
	}

	public EntityQuery find(String key) {
		return getCache().get(key);
	}
}

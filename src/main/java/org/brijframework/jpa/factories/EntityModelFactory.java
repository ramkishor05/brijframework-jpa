package org.brijframework.jpa.factories;

import java.util.LinkedHashMap;

import org.brijframework.jpa.container.EntityModelContainer;
import org.brijframework.jpa.context.EntityContext;
import org.brijframework.jpa.model.EntityModel;

public class EntityModelFactory {

	private static EntityModelFactory factory;
	
	private LinkedHashMap<String, EntityModel> cache = new LinkedHashMap<>();
	
	private EntityModelContainer container ;

	private EntityContext entityContext;

	public static EntityModelFactory getFactory() {
		if (factory == null) {
			factory = new EntityModelFactory();
		}
		return factory;
	}

	public void register(EntityModel model) {
		this.getCache().put(model.getId(), model);
		this.loadInContainer(model);
	}

	public EntityModel find(String model) {
		if(getCache().containsKey(model)) {
			return getCache().get(model);
		}
		return findInContainer(model);
	}
	
	public EntityModelContainer getContainer() {
		return container;
	}
	public void setContainer(EntityModelContainer container) {
		this.container = container;
	}
	
	public EntityModelFactory loadFactory() {
		return this;
	}


	public void loadInContainer(EntityModel model) {
		if(getContainer()==null) {
			return;
		}
		if(getContainer().find(model.getId())==null) {
			getContainer().register(model);
		}
	}
	
	public EntityModel findInContainer(String model) {
		if(getContainer()==null) {
			return null;
		}
		return getContainer().find(model);
	}
	
	public LinkedHashMap<String, EntityModel> getCache() {
		return cache;
	}

	public void setContext(EntityContext entityContext) {
		this.entityContext=entityContext;
	}
	
	public EntityContext getContext() {
		return entityContext;
	}
}

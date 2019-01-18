package org.brijframework.jpa.container;

import java.util.LinkedHashMap;

import org.brijframework.jpa.context.EntityContext;
import org.brijframework.jpa.model.EntityModel;

public class EntityModelContainer {
	private EntityContext  context;
	
	private LinkedHashMap<String, EntityModel> cache = new LinkedHashMap<>();
	
	private static EntityModelContainer container;
	
	public static EntityModelContainer getContainer() {
		if(container==null) {
			container=new EntityModelContainer();
		}
		return container;
	}

	public LinkedHashMap<String, EntityModel> getCache() {
		return cache;
	}
	
	public void register(EntityModel model) {
		getCache().put(model.getId(), model);
	}

	public EntityModel find(String model) {
		return getCache().get(model);
	}

	public void build() {
	}

	public void procced() {
	}
	
	public void setContext(EntityContext context) {
		this.context = context;
	}
	
	public EntityContext getContext() {
		return context;
	}
	
}

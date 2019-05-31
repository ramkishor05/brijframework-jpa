package org.brijframework.jpa.container;

import java.util.LinkedHashMap;

import org.brijframework.jpa.context.EntityContext;

public class EntityConfigContainer {
	
	private EntityContext  context;
	
	private LinkedHashMap<String, Object> cache = new LinkedHashMap<>();
	
	private static EntityConfigContainer container;
	
	public static EntityConfigContainer getContainer() {
		if(container==null) {
			container=new EntityConfigContainer();
		}
		return container;
	}

	public LinkedHashMap<String, Object> getCache() {
		return cache;
	}
	
	public void register(String id,Object model) {
		getCache().put(id, model);
	}

	@SuppressWarnings("unchecked")
	public <T> T find(String model) {
		return (T) getCache().get(model);
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

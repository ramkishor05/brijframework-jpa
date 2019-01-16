package org.brijframework.jpa.container;

import java.util.LinkedHashMap;

import org.brijframework.jpa.EntityGroup;

public class EntityModelContainer {

	private LinkedHashMap<String, EntityGroup> cache = new LinkedHashMap<>();
	
	private static EntityModelContainer container;
	
	public static EntityModelContainer getContainer() {
		if(container==null) {
			container=new EntityModelContainer();
		}
		return container;
	}

	public LinkedHashMap<String, EntityGroup> getCache() {
		return cache;
	}
	
	public void register(EntityGroup model) {
		getCache().put(model.getId(), model);
	}

	public EntityGroup find(String model) {
		return getCache().get(model);
	}

	public void build() {
		// TODO Auto-generated method stub
		
	}

	public void procced() {
		// TODO Auto-generated method stub
		
	}
	
}

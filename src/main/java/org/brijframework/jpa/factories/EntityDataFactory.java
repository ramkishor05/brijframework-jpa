package org.brijframework.jpa.factories;

import java.util.LinkedHashMap;

import org.brijframework.jpa.EntityGroup;
import org.brijframework.jpa.container.EntityDataContainer;
import org.brijframework.jpa.data.EntityData;

public class EntityDataFactory {

	private LinkedHashMap<String, EntityData> cache = new LinkedHashMap<>();
	
	private static EntityDataFactory factory;
	
	private EntityDataContainer container;

	public static EntityDataFactory getFactory() {
		if (factory == null) {
			factory = new EntityDataFactory();
		}
		return factory;
	}

	public void register(EntityData model) {
		this.getCache().put(model.getId(), model);
		this.loadInContainer(model);
	}

	public EntityData find(String model) {
		if(getCache().containsKey(model)) {
			return getCache().get(model);
		}
		return loadInContainer(model);
	}
	
	public EntityDataContainer getContainer() {
		return container;
	}

	public void setContainer(EntityDataContainer container) {
		this.container = container;
	}

	public LinkedHashMap<String, EntityData> getCache() {
		return cache;
	}

	public EntityDataFactory loadFactory() {
		return this;
	}
	
	public void loadInContainer(EntityData model) {
		if(getContainer()==null) {
			return;
		}
		EntityGroup group =getContainer().find(model.getId());
		if(group==null) {
			group=new EntityGroup();
			group.setId(model.getId());
			group.setEntityData(model);
			getContainer().register(group);
		}
	}
	
	public EntityData loadInContainer(String model) {
		if(getContainer()==null) {
			return null;
		}
		EntityGroup group =getContainer().find(model);
		if(group!=null) {
			return group.getEntityData();
		}
		return null;
	}
}
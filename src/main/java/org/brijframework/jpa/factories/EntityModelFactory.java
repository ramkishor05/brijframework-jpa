package org.brijframework.jpa.factories;

import java.util.LinkedHashMap;

import org.brijframework.jpa.EntityGroup;
import org.brijframework.jpa.container.EntityModelContainer;
import org.brijframework.jpa.model.EntityModel;

public class EntityModelFactory {

	private static EntityModelFactory factory;
	
	private LinkedHashMap<String, EntityModel> cache = new LinkedHashMap<>();
	
	private EntityModelContainer container ;

	public static EntityModelFactory getFactory() {
		if (factory == null) {
			factory = new EntityModelFactory();
		}
		return factory;
	}

	public void register(EntityModel model) {

	}

	public EntityModel find(String model) {
		return null;
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
		EntityGroup group =getContainer().find(model.getId());
		if(group==null) {
			group=new EntityGroup();
			group.setId(model.getId());
			group.setEntityModel(model);
			getContainer().register(group);
		}
	}
	
	public EntityModel loadInContainer(String model) {
		if(getContainer()==null) {
			return null;
		}
		EntityGroup group =getContainer().find(model);
		if(group!=null) {
			return group.getEntityModel();
		}
		return null;
	}
	
	public LinkedHashMap<String, EntityModel> getCache() {
		return cache;
	}
}

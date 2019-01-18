package org.brijframework.jpa.group;

import org.brijframework.jpa.model.EntityData;
import org.brijframework.jpa.model.EntityModel;

public class EntityDataGroup {

	private String id;
	
	private EntityData entityData;
	
	private Object entityObject;
	
	private EntityModel entityModel;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public EntityData getEntityData() {
		return entityData;
	}

	public void setEntityData(EntityData entityData) {
		this.entityData = entityData;
	}

	public Object getEntityObject() {
		return entityObject;
	}

	public void setEntityObject(Object entityObject) {
		this.entityObject = entityObject;
	}
	
	public void setEntityModel(EntityModel entityModel) {
		this.entityModel = entityModel;
	}
	
	public EntityModel getEntityModel() {
		return entityModel;
	}
	
	@Override
	public String toString() {
		if(entityData!=null) {
			return entityData.getEntity();
		}
		return super.toString();
	}
}

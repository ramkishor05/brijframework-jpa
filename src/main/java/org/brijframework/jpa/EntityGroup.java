package org.brijframework.jpa;

public class EntityGroup {

	private EntityModel entityModel;
	
	private Object entityObject;

	public EntityModel getEntityModel() {
		return entityModel;
	}

	public void setEntityModel(EntityModel entityModel) {
		this.entityModel = entityModel;
	}

	public Object getEntityObject() {
		return entityObject;
	}

	public void setEntityObject(Object entityObject) {
		this.entityObject = entityObject;
	}
}

package org.brijframework.jpa.processor;

import org.brijframework.jpa.files.EntityData;
import org.brijframework.jpa.model.EntityModel;

public interface EntityProcessor {

	public abstract boolean constains(EntityData entityData, EntityModel entityModel, Object object);

	public abstract boolean init();

	public abstract boolean persist(EntityData entityData, EntityModel entityModel, Object object);
	
	public abstract boolean update(EntityData entityData, EntityModel entityModel, Object object);
	
	public abstract boolean delete(EntityData entityData, EntityModel entityModel, Object object);

	public abstract boolean finish();
}
